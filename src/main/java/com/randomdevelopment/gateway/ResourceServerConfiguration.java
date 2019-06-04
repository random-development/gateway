package com.randomdevelopment.gateway;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${security.oauth2.resource.token-info-uri}")
	private String tokenInfoUri;
	
    @Bean("resourceServerRequestMatcher")
    public RequestMatcher resources() {
        return new AntPathRequestMatcher("/gateway-with-auth/**");
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .requestMatcher(resources()).authorizeRequests()
            .anyRequest().authenticated()
            .and().logout().logoutSuccessUrl("/").permitAll();
    }
    
    @Primary
    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(tokenInfoUri);
        tokenService.setClientId("resource-client");
        tokenService.setClientSecret("noonewilleverguess2");
        return tokenService;
    }


}