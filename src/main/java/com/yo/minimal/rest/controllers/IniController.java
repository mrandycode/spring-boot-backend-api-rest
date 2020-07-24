package com.yo.minimal.rest.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IniController {
    @RequestMapping("/")
    @ResponseBody
    String home (){
        return "Welcome to yo-minimal Application";
    }
}
