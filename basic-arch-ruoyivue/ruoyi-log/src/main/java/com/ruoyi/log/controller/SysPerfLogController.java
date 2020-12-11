package com.ruoyi.log.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.PerfLog;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.log.domain.SysPerfLog;
import com.ruoyi.log.dto.SysPerfLogDto;
import com.ruoyi.log.dto.SysPerfLogMetaRequestDto;
import com.ruoyi.log.dto.SysPerfLogSearchDto;
import com.ruoyi.log.service.ISysPerfLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 系统接口日志Controller
 * 
 * @author guanzhisong
 * @date 2020-12-04
 */
@RestController
@RequestMapping("/system/log")
@PerfLog(ignore = true)
public class SysPerfLogController extends BaseController {
    @Autowired
    private ISysPerfLogService sysPerfLogService;

    /**
     * 查询系统接口日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPerfLogSearchDto sysPerfLogSearchDto) {
        startPage();
        List<SysPerfLogDto> list = sysPerfLogService.searchPerfLogs(sysPerfLogSearchDto);
        return getDataTable(list);
    }

    /**
     * 导出系统接口日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "系统接口日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysPerfLog sysPerfLog) {
        List<SysPerfLog> list = sysPerfLogService.selectSysPerfLogList(sysPerfLog);
        ExcelUtil<SysPerfLog> util = new ExcelUtil<SysPerfLog>(SysPerfLog.class);
        return util.exportExcel(list, "log");
    }

    /**
     * 获取系统接口日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysPerfLogService.selectSysPerfLogById(id));
    }

    /**
     * 新增系统接口日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "系统接口日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPerfLog sysPerfLog) {
        return toAjax(sysPerfLogService.insertSysPerfLog(sysPerfLog));
    }

    /**
     * 修改系统接口日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "系统接口日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPerfLog sysPerfLog) {
        return toAjax(sysPerfLogService.updateSysPerfLog(sysPerfLog));
    }

    /**
     * 删除系统接口日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "系统接口日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysPerfLogService.deleteSysPerfLogByIds(ids));
    }

    @RequestMapping("meta_info")
    public AjaxResult metaInfo(SysPerfLogMetaRequestDto sysPerfLogMetaRequestDto) {
        List<String> result = sysPerfLogService.selectMetaInfo(sysPerfLogMetaRequestDto);
        return AjaxResult.success(result);
    }
}
