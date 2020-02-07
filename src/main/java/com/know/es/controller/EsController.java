
package com.know.es.controller;


import com.know.es.config.RestHighLevelClientService;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class EsController {

    @Autowired
    RestHighLevelClientService service;

    @GetMapping("/es")
    public String testHigh(HttpServletResponse httpServletResponse) throws IOException {
        String source = "{\n" +
                "  \"name\" : \"耐苦无领运动半袖\",\n" +
                "  \"price\" : 300,\n" +
                "  \"num\" : 800,\n" +
                "  \"date\" : \"2019-07-28\"\n" +
                "}";

        IndexResponse response = service.autoAddDoc("idx_clouthing", source);
        return response.toString();


    }
}

