package com.gzs.learn.web.modular.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gzs.learn.rbac.dubbo.DubboRbacCommonService;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.web.common.controller.BaseController;

@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {
    @Autowired
    private DubboRbacCommonService dubboRbacCommonService;

    @RequestMapping("")
    public String blackboard(Model model) {
        List<NoticeDto> notices = dubboRbacCommonService.searchNotice(null);
        model.addAttribute("noticeList", notices);
        return "/blackboard.html";
    }
}
