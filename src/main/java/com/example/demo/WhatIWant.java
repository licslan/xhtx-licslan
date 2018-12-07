package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**   *
 * @author licslan
 * */
@Controller
public class WhatIWant {

	@RequestMapping("/hih")
	@ResponseBody
	public String gethi() {
		return "hi i am hwl";
	}

	@RequestMapping("/hi")
	public String index(Model model) {
		model.addAttribute("name", "LICSLAN");
		return "LICSLAN/hi";
	}
}
