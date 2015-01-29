package com.zhangkai.Activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	 @RequestMapping(value = "/login")
	    public String login() {
	        return "login";
	    }
	 
	 @RequestMapping(value = "/main/index")
	    public String index() {
	        return "/main/index";
	    }

	    @RequestMapping(value = "/main/welcome")
	    public String welcome() {
	        return "/main/welcome";
	    }
}
