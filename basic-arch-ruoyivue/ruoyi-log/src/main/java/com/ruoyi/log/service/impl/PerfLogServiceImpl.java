package com.ruoyi.log.service.impl;

import static com.gzs.learn.common.util.ClassUtil.dealProxyMethod;
import static com.ruoyi.log.LogSystemConstant.CODE_SUCCESS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.inf.PageResponseDto.PageResponse;
import com.gzs.learn.inf.PageResponseDto.PageResponseDtoBuilder;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.log.domain.SysPerfLogCountPo;
import com.ruoyi.log.domain.SysPerfLogMetaPo;
import com.ruoyi.log.domain.SysPerfLogPo;
import com.ruoyi.log.dto.SysPerfLogDto;
import com.ruoyi.log.dto.SysPerfLogSearchDto;
import com.ruoyi.log.enums.SysPerfLogDurationEnum;
import com.ruoyi.log.mapper.SysPerfLogCountMapper;
import com.ruoyi.log.mapper.SysPerfLogMapper;
import com.ruoyi.log.mapper.SysPerfLogMetaMapper;
import com.ruoyi.log.service.IPerfLogService;

@Component
@DataSource(value = DataSourceType.LOG)
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
            po.setClazz(dealProxyMethod(po.getClazz()));
            po.setMethod(dealProxyMethod(po.getMethod()));
            sysPerfLogMetaMapper.insertSelective(po);
        } catch (DuplicateKeyException e) {
            // ignore
        }
        return true;
    }

    @Override
    public boolean insertPerfLog(SysPerfLogDto sysLogDto) {
        sysLogDto.setClazz(dealProxyMethod(sysLogDto.getClazz()));
        sysLogDto.setMethod(dealProxyMethod(sysLogDto.getMethod()));
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
        countPo.setCountDate(DateFormatUtils.format(sysLogDto.getCreateTime(), SysPerfLogDurationEnum.BY_DATE.getPattern()));
        countPo.setCountDuration(DateFormatUtils.format(sysLogDto.getCreateTime(), sysLogDto.getDurationEnum().getPattern()));

        List<SysPerfLogCountPo> exists = sysPerfLogCountMapper.exist(countPo);
        countPo.setCreateTime(sysLogDto.getCreateTime());
        // 次数
        long totalExecute = 0, exceptionExecute = 0, sysExceptionExecute = 0, bizExceptionExecute = 0;
        // 时间
        long totalTime = 0, maxTime = 0, minTime = 0;
        // 平均执行时间
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

    @DataSource(value = DataSourceType.LOG_SLAVE)
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
        List<SysPerfLogPo> poList = pageInfo.getList();
        SysPerfLogMetaPo metaPo = sysPerfLogMetaMapper.selectByPrimaryKey(metaId);
        List<SysPerfLogDto> dtos = Lists.newArrayListWithCapacity(poList.size());
        dtos = BeanUtil.copyList(poList, SysPerfLogDto.class);

        for (SysPerfLogDto dto : dtos) {
            dto.setProduct(metaPo.getProduct());
            dto.setGroupName(metaPo.getGroupName());
            dto.setApp(metaPo.getApp());
            dto.setClazz(metaPo.getClazz());
            dto.setMethod(metaPo.getMethod());
            dto.setOperatorIp(metaPo.getOperatorIp());
        }
        PageResponseDto<SysPerfLogDto> result = builder.code(0).msg("success").page(page).data(dtos).build();
        result.setData(dtos);
        return result;
    }

    private SysPerfLogMetaPo getExistMetaPo(SysPerfLogDto sysLogDto) {
        SysPerfLogMetaPo query = SysPerfLogMetaPo.builder().product(sysLogDto.getProduct()).groupName(sysLogDto.getGroupName())
                .app(sysLogDto.getApp()).clazz(sysLogDto.getClazz()).method(sysLogDto.getMethod()).operatorIp(sysLogDto.getOperatorIp())
                .build();
        List<SysPerfLogMetaPo> exists = sysPerfLogMetaMapper.exist(query);
        if (!CollectionUtils.isEmpty(exists)) {
            return exists.get(0);
        }
        return null;
    }
}
