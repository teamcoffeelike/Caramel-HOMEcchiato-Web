package com.hanul.coffeelike.caramelweb.controller.api;

import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class BaseExceptionHandlingController{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@ExceptionHandler({
			MissingServletRequestParameterException.class,
			MethodArgumentTypeMismatchException.class
	})
	public String onBadParameterException(Exception ex){
		return JsonHelper.failure("bad_parameter");
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public String onException(Exception ex){
		logger.error("Unexpected error", ex);
		return JsonHelper.failure("unexpected");
	}
}
