package com.jk.cashregister.configuration;

import com.jk.cashregister.service.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
		@Autowired
		LocaleResolver localeResolver;
		@Autowired
		MessageSource messageSource;

		@ExceptionHandler(BindException.class)
		public ModelAndView	bindErrorHandler(HttpServletRequest req, BindException e) {
				ModelAndView modelAndView = new ModelAndView();
				List<ObjectError> allErrors = e.getAllErrors();
				modelAndView.addObject("allErrors", allErrors);
				modelAndView.addObject("url", req.getRequestURL());
				modelAndView.addObject("referer", req.getHeader("Referer"));
				modelAndView.setViewName("/inputerror");
				return modelAndView;
		}

		@ExceptionHandler(CustomException.class)
		public ModelAndView	customErrorHandler(HttpServletRequest req, CustomException e) {
				ModelAndView modelAndView = new ModelAndView();

				Locale locale = localeResolver.resolveLocale(req);
				String message = messageSource.getMessage(e.getBundledKey(), new Object[]{}, locale);
				log.info(message);

				modelAndView.addObject("message", message);
				modelAndView.addObject("url", req.getRequestURL());
				modelAndView.addObject("referer", req.getHeader("Referer"));
				modelAndView.setViewName("/customerror");

				return modelAndView;
		}
}
