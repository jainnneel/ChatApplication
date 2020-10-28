package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegController {

    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
    
}
