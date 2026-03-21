package org.SpringBoot_GitHub.Loggings;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterceptorLoggingApiUsuario implements HandlerInterceptor{

    	private static final Logger accessLog =
            LoggerFactory.getLogger("API_ACCESS_LOG");

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        request.setAttribute(START_TIME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        long startTime = (long) request.getAttribute(START_TIME);
        long duration = System.currentTimeMillis() - startTime;

        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        accessLog.info(
                "IP={} METHOD={} URI={} STATUS={} TIME={}ms",
                ip, method, uri, status, duration
        );
    }
}