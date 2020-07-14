package com.ruoyi.web.controller;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.constant.Constants;

@Controller
@RequestMapping("test")
public class AsyncController {

    @ResponseBody
    @RequestMapping("async")
    public Object testAsync(HttpServletRequest request) {
        AsyncContext asyncContext = request.startAsync();
        Constants.THREAD_POOL.submit(() -> {
            try {
                asyncContext.getResponse().getWriter().write("hello");
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncContext.complete();
        });
        return "success";
    }
}
