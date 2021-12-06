package com.kkb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class HelloController {
    @RequestMapping("/hello")
    public ModelAndView hello(){
        System.out.println("Hello");
        ModelAndView mv = new ModelAndView();
        mv.addObject("hello","你好，欢迎使用");
        mv.setViewName("hello");
        return mv;
    }
}
