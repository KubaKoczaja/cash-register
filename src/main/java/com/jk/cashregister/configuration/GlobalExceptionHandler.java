package com.jk.cashregister.configuration;

import com.jk.cashregister.service.exception.CustomException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

		@ExceptionHandler(BindException.class)
		public ModelAndView	bindErrorHandler(HttpServletRequest req, BindException e) {
				ModelAndView modelAndView = new ModelAndView();
				List<ObjectError> allErrors = e.getAllErrors();
				modelAndView.addObject("allErrors", allErrors);
				modelAndView.addObject("url", req.getRequestURL());
				modelAndView.setViewName("/inputerror");
				return modelAndView;
		}
		@ExceptionHandler(CustomException.class)
		public ModelAndView	customErrorHandler(HttpServletRequest req, Exception e) {
				ModelAndView modelAndView = new ModelAndView();

				String message = e.getMessage();

				modelAndView.addObject("message", message);
				modelAndView.addObject("url", req.getRequestURL());
				modelAndView.addObject("referer", req.getHeader("Referer"));
				modelAndView.setViewName("/customerror");

				return modelAndView;
		}
}
