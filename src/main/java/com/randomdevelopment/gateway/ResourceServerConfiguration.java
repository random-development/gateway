package com.randomdevelopment.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
    @Bean("resourceServerRequestMatcher")
    public RequestMatcher resources() {
        return new AntPathRequestMatcher("/gateway-with-auth/**");
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .requestMatcher(resources()).authorizeRequests()
            .anyRequest().authenticated();
    }
    
    @Primary
    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:7000/oauth/check_token");
        tokenService.setClientId("resource-client");
        tokenService.setClientSecret("noonewilleverguess2");
        return tokenService;
    }


}