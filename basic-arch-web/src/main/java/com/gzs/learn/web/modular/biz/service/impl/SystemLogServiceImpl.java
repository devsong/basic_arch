package com.gzs.learn.web.modular.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gzs.learn.log.dubbo.DubboPerfLogService;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.constant.DSEnum;
import com.gzs.learn.web.common.page.PageReq;
import com.gzs.learn.web.common.persistence.dao.logs.LoginLogMapper;
import com.gzs.learn.web.common.persistence.dao.logs.OperationLogMapper;
import com.gzs.learn.web.common.persistence.model.logs.LoginLog;
import com.gzs.learn.web.common.persistence.model.logs.OperationLog;
import com.gzs.learn.web.core.mutidatesource.DataSourceContextHolder;
import com.gzs.learn.web.modular.biz.bo.QueryLogBo;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;

@Component
@Transactional
public class SystemLogServiceImpl implements ISystemLogService {
    @Autowired
    private DubboPerfLogService dubboPerflogService;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public List<LoginLog> getLoginLogs(QueryLogBo queryLogBo) {
        PageReq pageReq = queryLogBo.getPageReq();
        PageHelper.offsetPage(pageReq.getOffset(), pageReq.getLimit());
        List<LoginLog> loginLogs = loginLogMapper.getLoginLogs(queryLogBo);
        return loginLogs;
    }

    @Override
    public LoginLog getLoginLogDetail(Long id) {
        return loginLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveLoginLog(LoginLog loginLog) {
        DataSourceContextHolder.setDataSourceType(DSEnum.DATA_SOURCE_LOGS);
        return loginLogMapper.insertSelective(loginLog) == 1;
    }

    @Override
    public void truncateLoginLog() {
        loginLogMapper.truncate();
    }

    @Override
    public List<OperationLog> getOperationLogs(QueryLogBo queryLogBo) {
        PageReq pageReq = queryLogBo.getPageReq();
        PageHelper.offsetPage(pageReq.getOffset(), pageReq.getLimit());
        List<OperationLog> operationLogs = operationLogMapper.getOperationLogs(queryLogBo);
        return operationLogs;
    }

    @Override
    public OperationLog getOperationLogDetail(Long id) {
        return operationLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveOperationLog(OperationLog operationLog) {
        return operationLogMapper.insertSelective(operationLog) == 1;
    }

    @Override
    public void truncateBizLog() {
        operationLogMapper.truncate();
    }

    @Override
    public void savePerfLog(SysPerfLogDto sysPerfLogDto) {
        Const.SYSTEM_POOL.execute(() -> {
            dubboPerflogService.insertPerflog(sysPerfLogDto);
        });
    }
}
