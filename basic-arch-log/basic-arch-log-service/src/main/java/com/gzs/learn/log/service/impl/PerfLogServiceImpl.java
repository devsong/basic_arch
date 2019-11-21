package com.gzs.learn.log.service.impl;

import static com.gzs.learn.log.LogSystemConstant.CODE_SUCCESS;
import static com.gzs.learn.log.enums.SysPerfLogDurationEnum.BY_DATE;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.inf.PageResponseDto.PageResponse;
import com.gzs.learn.inf.PageResponseDto.PageResponseDtoBuilder;
import com.gzs.learn.log.dao.SysPerfLogCountMapper;
import com.gzs.learn.log.dao.SysPerfLogMapper;
import com.gzs.learn.log.dao.SysPerfLogMetaMapper;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogCountPo;
import com.gzs.learn.log.po.SysPerfLogMetaPo;
import com.gzs.learn.log.po.SysPerfLogPo;
import com.gzs.learn.log.service.IPerfLogService;

@Component
@Transactional
public class PerfLogServiceImpl implements IPerfLogService {

    @Autowired
    private SysPerfLogMetaMapper sysPerfLogMetaMapper;

    @Autowired
    private SysPerfLogCountMapper sysPerfLogCountMapper;

    @Autowired
    private SysPerfLogMapper sysPerfLogMapper;

    @Override
    public boolean insertPerfLogMeta(SysPerfLogMetaPo po) {
        try {
            sysPerfLogMetaMapper.insertSelective(po);
        } catch (DuplicateKeyException e) {
            // ignore
        }
        return true;
    }

    @Override
    public boolean insertPerfLog(SysPerfLogDto sysLogDto) {
        SysPerfLogMetaPo metaPo = new SysPerfLogMetaPo();
        BeanUtil.copyProperties(sysLogDto, metaPo);
        long metaId = 0;
        // 检查元数据信息是否写入
        SysPerfLogMetaPo exist = getExistMetaPo(sysLogDto);
        if (exist == null) {
            insertPerfLogMeta(metaPo);
            metaId = metaPo.getId();
        } else {
            metaId = exist.getId();
        }
        // 写入性能日志
        SysPerfLogPo po = new SysPerfLogPo();
        BeanUtil.copyProperties(sysLogDto, po);
        po.setMetaId(metaId);
        po.setId(null);
        int row = sysPerfLogMapper.insertSelective(po);
        // 写入统计日志
        createCountLog(sysLogDto, metaId);

        return row == 1;
    }

    private void createCountLog(SysPerfLogDto sysLogDto, long metaId) {
        SysPerfLogCountPo countPo = new SysPerfLogCountPo();
        countPo.setMetaId(metaId);
        countPo.setCountDate(DateFormatUtils.format(sysLogDto.getCreateTime(), BY_DATE.getPattern()));
        countPo.setCountDuration(DateFormatUtils.format(sysLogDto.getCreateTime(), sysLogDto.getDurationEnum().getPattern()));
        List<SysPerfLogCountPo> exists = sysPerfLogCountMapper.select(countPo);
        countPo.setCreateTime(sysLogDto.getCreateTime());

        long totalExecute = 0, exceptionExecute = 0, sysExceptionExecute = 0, bizExceptionExecute = 0, totalTime = 0, maxTime = 0,
                minTime = 0;
        double avgTime = 0;
        if (CollectionUtils.isEmpty(exists)) {
            // 执行时间
            int executeTime = sysLogDto.getExecuteTimespan();
            totalExecute += 1;
            totalTime += executeTime;
            maxTime = executeTime;
            minTime = executeTime;
            avgTime = executeTime;
            if (sysLogDto.getCode() != CODE_SUCCESS) {
                exceptionExecute += 1;
                sysExceptionExecute += sysLogDto.getCode() < CODE_SUCCESS ? 1L : 0;
                bizExceptionExecute += sysLogDto.getCode() > CODE_SUCCESS ? 1L : 0;
            }
        } else {
            // 执行次数
            SysPerfLogCountPo existPo = exists.get(0);
            long executeCount = existPo.getExecuteTotal();
            totalExecute = executeCount + 1;

            // 执行时间
            int executeTime = sysLogDto.getExecuteTimespan();

            long total = existPo.getExecuteTimeTotal(), max = existPo.getExecuteTimeMax(), min = existPo.getExecuteTimeMin();
            total += executeTime;
            max = max > executeTime ? max : executeTime;
            min = min < executeTime ? min : executeTime;

            totalTime += total;
            maxTime = max;
            minTime = min;
            avgTime = new BigDecimal(totalTime).divide(new BigDecimal(totalExecute), 2, RoundingMode.HALF_UP).doubleValue();
            if (sysLogDto.getCode() != CODE_SUCCESS) {
                exceptionExecute = existPo.getExecuteException() + 1;
                sysExceptionExecute = existPo.getExecuteSysException() + (sysLogDto.getCode() < CODE_SUCCESS ? 1L : 0);
                bizExceptionExecute = existPo.getExecuteBizException() + (sysLogDto.getCode() > CODE_SUCCESS ? 1L : 0);
            }
        }

        countPo.setExecuteTotal(totalExecute);
        countPo.setExecuteException(exceptionExecute);
        countPo.setExecuteSysException(sysExceptionExecute);
        countPo.setExecuteBizException(bizExceptionExecute);

        countPo.setExecuteTimeTotal(totalTime);
        countPo.setExecuteTimeMax((int) maxTime);
        countPo.setExecuteTimeAvg(avgTime);
        countPo.setExecuteTimeMin((int) minTime);

        sysPerfLogCountMapper.saveCountPo(countPo);
    }

    @Override
    public PageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto) {
        PageInfo<SysPerfLogPo> pageInfo = PageHelper.startPage(searchDto.getPage(), searchDto.getPageSize(), true)
                .doSelectPageInfo(() -> sysPerfLogMapper.searchPerfLogs(searchDto));
        PageResponseDtoBuilder<SysPerfLogDto> builder = PageResponseDto.builder();

        PageResponse page = PageResponse.builder().page(searchDto.getPage()).pageSize(searchDto.getPageSize())
                .total((int) pageInfo.getTotal()).build();

        if (pageInfo.getTotal() == 0) {
            return builder.code(0).msg("success").page(page).data(Lists.newArrayList()).build();
        }

        long metaId = pageInfo.getList().get(0).getMetaId();

        SysPerfLogMetaPo metaPo = sysPerfLogMetaMapper.selectByPrimaryKey(metaId);
        List<SysPerfLogDto> targetDtos = BeanUtil.copyList(pageInfo.getList(), SysPerfLogDto.class);
        for (SysPerfLogDto dto : targetDtos) {
            dto.setProduct(metaPo.getProduct());
            dto.setGroupName(metaPo.getGroupName());
            dto.setApp(metaPo.getApp());
            dto.setClazz(metaPo.getClazz());
            dto.setMethod(metaPo.getMethod());
            dto.setOperatorIp(metaPo.getOperatorIp());
        }
        PageResponseDto<SysPerfLogDto> result = builder.code(0).msg("success").page(page).data(targetDtos).build();
        result.setData(targetDtos);
        return result;
    }

    private SysPerfLogMetaPo getExistMetaPo(SysPerfLogDto sysLogDto) {
        SysPerfLogMetaPo query = SysPerfLogMetaPo.builder().product(sysLogDto.getProduct()).groupName(sysLogDto.getGroupName())
                .app(sysLogDto.getApp()).clazz(sysLogDto.getClazz()).method(sysLogDto.getMethod()).operatorIp(sysLogDto.getOperatorIp())
                .build();
        List<SysPerfLogMetaPo> exists = sysPerfLogMetaMapper.select(query);
        if (!CollectionUtils.isEmpty(exists)) {
            return exists.get(0);
        }
        return null;
    }
}
