package com.jk.cashregister.auth;

import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
				security.passwordEncoder(NoOpPasswordEncoder.getInstance())
								.checkTokenAccess("permitAll()")
								.tokenKeyAccess("premitAll()");
		}


}
