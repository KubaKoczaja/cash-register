package com.jk.cashregister.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

		@Bean
		public LocaleResolver localeResolver() {
				SessionLocaleResolver slr = new SessionLocaleResolver();
				slr.setDefaultLocale(Locale.ENGLISH);
				return slr;
		}

		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
				LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
				lci.setParamName("lang");
				return lci;
		}

		@Bean("messageSource")
		public MessageSource messageSource() {
				ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
				messageSource.setBasenames("messages");
				messageSource.setDefaultEncoding("UTF-8");
				return messageSource;
		}


		@Override
		public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(localeChangeInterceptor());
		}

		@Bean
		@Override
		public LocalValidatorFactoryBean getValidator() {
				LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
				bean.setValidationMessageSource(messageSource());
				return bean;
		}

}
