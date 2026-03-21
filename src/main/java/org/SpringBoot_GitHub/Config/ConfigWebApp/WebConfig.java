package org.SpringBoot_GitHub.Config.ConfigWebApp;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.SpringBoot_GitHub.Loggings.InterceptorLoggingApiUsuario;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    private final InterceptorLoggingApiUsuario interceptorLoggingApiUsuarios;

    public WebConfig(InterceptorLoggingApiUsuario interceptorLoggingApiUsuarios) {
		this.interceptorLoggingApiUsuarios = interceptorLoggingApiUsuarios;
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorLoggingApiUsuarios);
    }
}