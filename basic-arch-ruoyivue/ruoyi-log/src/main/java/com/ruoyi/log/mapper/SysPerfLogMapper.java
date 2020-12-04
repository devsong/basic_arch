package com.ruoyi.log.mapper;

import java.util.List;
import com.ruoyi.log.domain.SysPerfLog;
import com.ruoyi.log.dto.SysPerfLogSearchDto;

/**
 * 系统接口日志Mapper接口
 * 
 * @author guanzhisong
 * @date 2020-12-04
 */
public interface SysPerfLogMapper {
    /**
     * 查询系统接口日志
     * 
     * @param id 系统接口日志ID
     * @return 系统接口日志
     */
    SysPerfLog selectSysPerfLogById(Long id);

    /**
     * 查询系统接口日志列表
     * 
     * @param sysPerfLog 系统接口日志
     * @return 系统接口日志集合
     */
    List<SysPerfLog> selectSysPerfLogList(SysPerfLog sysPerfLog);

    /**
     * 新增系统接口日志
     * 
     * @param sysPerfLog 系统接口日志
     * @return 结果
     */
    int insertSysPerfLog(SysPerfLog sysPerfLog);

    /**
     * 修改系统接口日志
     * 
     * @param sysPerfLog 系统接口日志
     * @return 结果
     */
    int updateSysPerfLog(SysPerfLog sysPerfLog);

    /**
     * 删除系统接口日志
     * 
     * @param id 系统接口日志ID
     * @return 结果
     */
    int deleteSysPerfLogById(Long id);

    /**
     * 批量删除系统接口日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSysPerfLogByIds(Long[] ids);

    /**
     * 查询日志列表
     * @param searchDto
     * @return
     */
    List<SysPerfLog> searchPerfLogs(SysPerfLogSearchDto searchDto);
}
