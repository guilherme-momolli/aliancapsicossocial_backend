package br.com.aliancapsicossocial.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditFilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<AuditMdcFilter> auditMdcFilterRegistration(AuditMdcFilter auditMdcFilter) {
        FilterRegistrationBean<AuditMdcFilter> registration = new FilterRegistrationBean<>(auditMdcFilter);
        registration.setEnabled(false);
        return registration;
    }
}
