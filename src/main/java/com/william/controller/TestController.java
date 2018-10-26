package com.william.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tz.service.DemoService;


@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private DemoService demoService;
	@RequestMapping("/test")
	@ResponseBody
	public String test(String userName){
		return demoService.getInfo(userName);
	}
}
