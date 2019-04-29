package com.leige.security.browser.config;

import com.leige.security.browser.logout.MyLogoutSuccessHandler;
import com.leige.security.browser.session.MyInvalidSessionStrategy;
import com.leige.security.browser.session.MySessionInformationExpiredStrategy;
import com.leige.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 提供可配置策略
 * @author zhailiang
 */
@Configuration

public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new MyInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new MySessionInformationExpiredStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        MyLogoutSuccessHandler myLogoutSuccessHandler = new MyLogoutSuccessHandler();
        myLogoutSuccessHandler.setSecurityProperties(securityProperties);
        return myLogoutSuccessHandler;
    }
}
