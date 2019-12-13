package com.gzs.learn.web.modular.system.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gzs.learn.web.common.controller.BaseController;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {
    @RequestMapping("/welcome")
    public String console() {
        return "/frame/welcome.html";
    }

    @RequestMapping("/theme")
    public String theme() {
        return "/frame/theme.html";
    }

    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return "/frame/password.html";
    }

    @RequestMapping("/msg")
    public String message() {
        return "/frame/msg.html";
    }

    /**
     * 通用的树列表选择器
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:59 PM
     */
    @RequestMapping("/commonTree")
    public ModelAndView deptTreeList(@RequestParam("formName") String formName, @RequestParam("formId") String formId,
            @RequestParam("treeUrl") String treeUrl) {
        ModelAndView mav = new ModelAndView("/common/tree_dlg.html");
        try {
            mav.addObject("formName", URLDecoder.decode(formName, "UTF-8"));
            mav.addObject("formId", URLDecoder.decode(formId, "UTF-8"));
            mav.addObject("treeUrl", URLDecoder.decode(treeUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        return mav;
    }
}
