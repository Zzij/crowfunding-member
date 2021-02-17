package com.zz.crow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {

    @RequestMapping("/")
    public String showPortalPage(){
        return "portal";
    }
}