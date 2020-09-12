package com.know.info.controller;

import com.know.info.service.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Facecat
 * @Date: 2020/5/3 9:34
 */
@RestController
@RequestMapping("/request")
public class ResponseController {

    @Resource
    private ResponseService responseService;

    @GetMapping("/getData")
    public Map<String,Object> createData(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("products", responseService.queryListService());
        return map;

    }
}
