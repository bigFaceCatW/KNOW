package com.know.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Facecat
 * @Date: 2020/3/17 14:35
 */
@Controller
@RequestMapping("/login")
public class IndexController {

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index(ModelMap map) {
//返回值给页面
        map.addAttribute("name", "小石潭记");
        return "index";
    }

//    @RequestMapping("login")
//    public ModelAndView indexNational(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("casLogoutUrl", "ceshi");
//        modelAndView.addObject("userName", "文磊");
//        modelAndView.setViewName("index");
//        return modelAndView;
//
//
//    }
}
