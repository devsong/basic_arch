package com.gzs.learn.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.inf.PageResponseDto.PageResponse;
import com.gzs.learn.inf.PageResponseDto.PageResponseDtoBuilder;
import com.gzs.learn.log.dao.UserOperationLogMapper;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.log.po.UserOperationLogPo;
import com.gzs.learn.log.service.IUserOperationLogService;

@Component
@Transactional
public class UserOperationLogServiceImpl implements IUserOperationLogService {
    @Autowired
    private UserOperationLogMapper userOperationLogMapper;

    @Override
    public boolean insertOperationLog(UserOperationLogDto operationLogDto) {
        UserOperationLogPo po = new UserOperationLogPo();
        BeanUtil.copyProperties(operationLogDto, po);
        userOperationLogMapper.insertSelective(po);
        return true;
    }

    @Override
    public PageResponseDto<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto) {
        PageInfo<UserOperationLogPo> pageInfo = PageHelper
                .startPage(userOperationLogSearchDto.getPage(), userOperationLogSearchDto.getPageSize()).doSelectPageInfo(() -> {
                    userOperationLogMapper.getOperationLogs(userOperationLogSearchDto);
                });
        List<UserOperationLogDto> dtos = BeanUtil.copyList(pageInfo.getList(), UserOperationLogDto.class);
        PageResponse pageResponse = PageResponse.builder().page(userOperationLogSearchDto.getPage())
                .pageSize(userOperationLogSearchDto.getPageSize()).total((int) pageInfo.getTotal()).build();

        PageResponseDtoBuilder<UserOperationLogDto> builder = PageResponseDto.builder();
        PageResponseDto<UserOperationLogDto> logResp = builder.code(0).msg("success").data(dtos).page(pageResponse).build();
        return logResp;
    }

    @Override
    public UserOperationLogDto getDetail(Long id) {
        UserOperationLogPo po = userOperationLogMapper.selectByPrimaryKey(id);
        if (po == null) {
            return null;
        }
        UserOperationLogDto result = new UserOperationLogDto();
        BeanUtil.copyProperties(po, result);
        return result;
    }

}
