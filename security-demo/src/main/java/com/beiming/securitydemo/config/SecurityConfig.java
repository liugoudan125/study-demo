package com.beiming.securitydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;

/**
 * SecurityConfig
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private ObjectMapper objectMapper;

    //    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.exceptionHandling(new Customizer<ExceptionHandlingConfigurer<HttpSecurity>>() {
            @Override
            public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
                configurer.accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<String, Object>() {
                            {
                                put("code", 403);
                                put("message", "Access denied");
                            }
                        }));
                    }
                });
            }
        });
        return http.build();
    }

    public static class CaptchaException extends AuthenticationException {

        @Serial
        private static final long serialVersionUID = -5456861293850959377L;

        public CaptchaException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public CaptchaException(String msg) {
            super(msg);
        }

        public CaptchaException() {
            super("验证码错误");
        }
    }
//    @Bean
//    public

}
