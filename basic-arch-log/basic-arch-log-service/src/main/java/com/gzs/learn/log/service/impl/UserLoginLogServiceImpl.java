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
import com.gzs.learn.log.dao.UserLoginLogMapper;
import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.log.po.UserLoginLogPo;
import com.gzs.learn.log.service.IUserLoginLogService;

@Component
@Transactional
public class UserLoginLogServiceImpl implements IUserLoginLogService {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Override
    public boolean insertUserLoginLog(UserLoginLogDto userLoginLogDto) {
        UserLoginLogPo loginPo = new UserLoginLogPo();
        BeanUtil.copyProperties(userLoginLogDto, loginPo);
        userLoginLogMapper.insertSelective(loginPo);
        return true;
    }

    @Override
    public PageResponseDto<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto) {
        PageInfo<UserLoginLogPo> pageInfo = PageHelper.startPage(userLoginLogSearchDto.getPage(), userLoginLogSearchDto.getPageSize())
                .doSelectPageInfo(() -> {
                    userLoginLogMapper.getLoginLogs(userLoginLogSearchDto);
                });
        List<UserLoginLogDto> dtos = BeanUtil.copyList(pageInfo.getList(), UserLoginLogDto.class);
        PageResponseDtoBuilder<UserLoginLogDto> builder = PageResponseDto.builder();
        PageResponse pageResponse = PageResponse.builder().page(userLoginLogSearchDto.getPage())
                .pageSize(userLoginLogSearchDto.getPageSize()).total((int) pageInfo.getTotal()).build();
        PageResponseDto<UserLoginLogDto> logResp = builder.code(0).msg("success").data(dtos).page(pageResponse).build();
        return logResp;
    }

    @Override
    public UserLoginLogDto getDetail(Long id) {
        UserLoginLogPo po = userLoginLogMapper.selectByPrimaryKey(id);
        UserLoginLogDto dto = new UserLoginLogDto();
        BeanUtil.copyProperties(po, dto);
        return dto;
    }

}
