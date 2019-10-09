package com.gzs.learn.web.modular.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzs.learn.rbac.dubbo.DubboRbacCommonService;
import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.CommonResponse;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.common.persistence.dao.DictMapper;
import com.gzs.learn.web.common.persistence.model.Dict;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.service.IDictService;
import com.gzs.learn.web.modular.system.vo.DictVo;
import com.gzs.learn.web.modular.system.wrapper.DictWrapper;

/**
 * 字典控制器
 *
 * @author fengshuonan
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Autowired
    private DubboRbacCommonService dubboRbacCommonService;
    @Autowired
    private DictMapper dictMapper;

    @Autowired
    IDictService dictService;
    private String PREFIX = "/system/dict/";

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add.html";
    }

    /**
     * 跳转到修改字典
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/dict_edit/{dictId}")
    public String deptUpdate(@PathVariable Integer dictId, Model model) {
        Dict dict = dictMapper.selectByPrimaryKey(dictId);
        model.addAttribute("dict", dict);
        Dict queryModel = new Dict();
        dict.setPid(dictId);
        List<Dict> subDicts = dictMapper.select(queryModel);
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = com.gzs.learn.web.common.constant.Dict.DictMap)
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object add(String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dictService.addDict(dictName, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public CommonResponse<List<DictVo>> list(String condition) {
        List<DictDto> list = dubboRbacCommonService.searchDict(condition);
        return CommonResponse.buildSuccess(DictWrapper.convertDictDto(list));
    }

    /**
     * 字典详情
     */
    @RequestMapping(value = "/detail/{dictId}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public CommonResponse<DictVo> detail(@PathVariable("dictId") Integer dictId) {
        DictDto dict = dubboRbacCommonService.getDict(dictId);
        DictVo dictDto = DictWrapper.convertDictDto(dict);
        return CommonResponse.buildSuccess(dictDto);
    }

    /**
     * 修改字典
     */
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = com.gzs.learn.web.common.constant.Dict.DictMap)
    @RequestMapping(value = "/update")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object update(Integer dictId, String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictId, dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dictService.editDict(dictId, dictName, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = com.gzs.learn.web.common.constant.Dict.DeleteDict)
    @RequestMapping(value = "/delete")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delete(@RequestParam Integer dictId) {
        // 缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));

        dictService.delteDict(dictId);
        return SUCCESS_TIP;
    }

}
