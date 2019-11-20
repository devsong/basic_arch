package com.gzs.learn.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
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
    public List<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto) {
        List<UserLoginLogPo> pos = userLoginLogMapper.getLoginLogs(userLoginLogSearchDto);
        List<UserLoginLogDto> result = BeanUtil.copyList(pos, UserLoginLogDto.class);
        return result;
    }

    @Override
    public UserLoginLogDto getDetail(Long id) {
        UserLoginLogPo po = userLoginLogMapper.selectByPrimaryKey(id);
        UserLoginLogDto dto = new UserLoginLogDto();
        BeanUtil.copyProperties(po, dto);
        return dto;
    }

}
