package com.gzs.learn.web.modular.system.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzs.learn.rbac.dubbo.DubboRbacCommonService;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.Dict;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.shiro.ShiroKit;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.wrapper.NoticeWrapper;

/**
 * 通知控制器
 *
 * @author fengshuonan
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    private String PREFIX = "/system/notice/";

    @Autowired
    private DubboRbacCommonService dubboRbacCommonService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * 跳转到修改通知
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Integer noticeId, Model model) {
        NoticeDto notice = dubboRbacCommonService.getNotice(noticeId);
        model.addAttribute("notice", notice);
        LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 跳转到首页通知
     */
    @RequestMapping("/hello")
    public String hello() {
        List<NoticeDto> notices = dubboRbacCommonService.searchNotice(null);
        super.setAttr("noticeList", notices);
        return "/blackboard.html";
    }

    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<NoticeDto> notices = dubboRbacCommonService.searchNotice(null);
        return super.warpObject(new NoticeWrapper(notices));
    }

    /**
     * 新增通知
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增通知", key = "title", dict = Dict.NoticeMap)
    public Object add(NoticeDto notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        notice.setCreater(ShiroKit.getUser().getId() + "");
        notice.setCreatetime(new Date());
        dubboRbacCommonService.insertNotice(notice);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知", key = "noticeId", dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer noticeId) {
        // 缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));
        dubboRbacCommonService.deleteNotice(noticeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知", key = "title", dict = Dict.NoticeMap)
    public Object update(NoticeDto notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getId(), notice.getTitle(), notice.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        NoticeDto old = dubboRbacCommonService.getNotice(notice.getId().intValue());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        dubboRbacCommonService.updateNotice(old);
        return SUCCESS_TIP;
    }
}
