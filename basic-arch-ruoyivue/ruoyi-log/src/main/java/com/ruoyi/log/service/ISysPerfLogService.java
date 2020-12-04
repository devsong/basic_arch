package com.ruoyi.log.service;

import java.util.List;

import com.ruoyi.log.domain.SysPerfLog;
import com.ruoyi.log.domain.SysPerfLogMetaPo;
import com.ruoyi.log.dto.SysPerfLogDto;
import com.ruoyi.log.dto.SysPerfLogSearchDto;

/**
 * 系统接口日志Service接口
 * 
 * @author guanzhisong
 * @date 2020-12-04
 */
public interface ISysPerfLogService {
    /**
     * 查询系统接口日志
     * 
     * @param id 系统接口日志ID
     * @return 系统接口日志
     */
    public SysPerfLog selectSysPerfLogById(Long id);

    /**
     * 查询系统接口日志列表
     * 
     * @param sysPerfLog 系统接口日志
     * @return 系统接口日志集合
     */
    public List<SysPerfLog> selectSysPerfLogList(SysPerfLog sysPerfLog);

    /**
     * 新增系统接口日志
     * 
     * @param sysPerfLog 系统接口日志
     * @return 结果
     */
    public int insertSysPerfLog(SysPerfLog sysPerfLog);

    /**
     * 修改系统接口日志
     * 
     * @param sysPerfLog 系统接口日志
     * @return 结果
     */
    public int updateSysPerfLog(SysPerfLog sysPerfLog);

    /**
     * 批量删除系统接口日志
     * 
     * @param ids 需要删除的系统接口日志ID
     * @return 结果
     */
    public int deleteSysPerfLogByIds(Long[] ids);

    /**
     * 删除系统接口日志信息
     * 
     * @param id 系统接口日志ID
     * @return 结果
     */
    public int deleteSysPerfLogById(Long id);

    boolean insertPerfLogMeta(SysPerfLogMetaPo po);

    boolean insertPerfLog(SysPerfLogDto sysLogDto);

    List<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto);
}
