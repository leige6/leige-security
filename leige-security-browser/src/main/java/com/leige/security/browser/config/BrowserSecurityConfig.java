package com.leige.security.browser.config;


import com.leige.security.browser.MyPasswordEncoder;
import com.leige.security.core.properties.SecurityConstants;
import com.leige.security.core.properties.SecurityProperties;
import com.leige.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by leige on 2018/11/13.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return  new MyPasswordEncoder();
    }*/

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic()
        http.apply(validateCodeSecurityConfig)
                .and()
                .formLogin()
       .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL).loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
       .successHandler(myAuthenticationSuccessHandler)
       .failureHandler(myAuthenticationFailureHandler)
       .and().authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_SESSION_INVALID_URL+".html",
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        securityProperties.getBrowser().getLoginPage()).permitAll()
       .anyRequest()
       .authenticated()
       .and()
       .csrf()
       .disable();
    }

    //解决静态资源被拦截的问题
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/*","/fonts/**","/**/*.png","/**/*.jpg","/**/*.ico");
    }

}
