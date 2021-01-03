package com.hanul.coffeelike.caramelweb.controller.api;

import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController{
	@RequestMapping(value = "/api/*", produces = "application/json;charset=UTF-8")
	public String fallback(){
		return JsonHelper.failure("bad_request");
	}
}
