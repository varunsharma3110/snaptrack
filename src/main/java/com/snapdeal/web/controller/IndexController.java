package com.snapdeal.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
