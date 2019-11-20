package com.gzs.learn.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
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
    public List<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto) {
        List<UserOperationLogPo> operationLogs = userOperationLogMapper.getOperationLogs(userOperationLogSearchDto);
        List<UserOperationLogDto> results = BeanUtil.copyList(operationLogs, UserOperationLogDto.class);
        return results;
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
