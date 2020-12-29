package com.hanul.coffeelike.caramelweb.controller.api;

import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController{
	@RequestMapping("/api/*")
	public String fallback(){
		return JsonHelper.failure("bad_request");
	}
}
