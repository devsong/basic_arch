package com.gzs.learn.log.service.impl;

import static com.gzs.learn.log.ILogConstant.DEFAULT_DATA_DURATION;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.log.LogPageResponseDto;
import com.gzs.learn.log.LogPageResponseDto.LogPageResponseDtoBuilder;
import com.gzs.learn.log.LogPageResponseDto.PageResponse;
import com.gzs.learn.log.dao.SysPerfLogMapper;
import com.gzs.learn.log.dao.SysPerfLogMetaPoMapper;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogMetaPo;
import com.gzs.learn.log.po.SysPerfLogPo;
import com.gzs.learn.log.service.IPerfLogService;

@Component
@Transactional
public class PerfLogServiceImpl implements IPerfLogService {

    @Autowired
    private SysPerfLogMapper sysPerfLogMapper;

    @Autowired
    private SysPerfLogMetaPoMapper sysPerfLogMetaPoMapper;

    @Override
    public boolean insertPerfLog(SysPerfLogDto sysLogDto) {
        SysPerfLogPo po = new SysPerfLogPo();
        BeanUtil.copyProperties(sysLogDto, po);
        po.setId(null);
        int row = sysPerfLogMapper.insertSelective(po);
        SysPerfLogMetaPo metaPo = new SysPerfLogMetaPo();
        BeanUtil.copyProperties(sysLogDto, metaPo);
        try {
            sysPerfLogMetaPoMapper.insertSelective(metaPo);
        } catch (DuplicateKeyException e) {
            // ignore
        }
        return row == 1;
    }

    @Override
    public LogPageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto) {
        Date now = new Date();
        if (searchDto.getCreateTimeEnd() == null) {
            searchDto.setCreateTimeEnd(now);
        }
        if (searchDto.getCreateTimeStart() == null) {
            searchDto.setCreateTimeStart(DateUtils.addMonths(searchDto.getCreateTimeEnd(), DEFAULT_DATA_DURATION));
        }
        PageInfo<SysPerfLogPo> pageInfo = PageHelper.startPage(searchDto.getPage(), searchDto.getPageSize(), true)
                .doSelectPageInfo(() -> sysPerfLogMapper.searchPerfLogs(searchDto));
        PageResponse pageResponse = PageResponse.builder().page(searchDto.getPage()).pageSize(searchDto.getPageSize())
                .total((int) pageInfo.getTotal()).build();
        List<SysPerfLogDto> targetDtos = BeanUtil.copyList(pageInfo.getList(), SysPerfLogDto.class);

        LogPageResponseDtoBuilder<SysPerfLogDto> builder = LogPageResponseDto.builder();
        LogPageResponseDto<SysPerfLogDto> result = builder.code(0).msg("success").page(pageResponse).data(targetDtos).build();
        result.setData(targetDtos);
        return result;
    }
}
