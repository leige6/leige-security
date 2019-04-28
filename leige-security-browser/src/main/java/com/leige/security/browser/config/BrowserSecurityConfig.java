package com.leige.security.browser.config;


import com.leige.security.browser.MyPasswordEncoder;
import com.leige.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.leige.security.core.properties.SecurityConstants;
import com.leige.security.core.properties.SecurityProperties;
import com.leige.security.core.properties.SessionProperties;
import com.leige.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * Created by leige on 2018/11/13.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;


    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

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

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfigs;

    /**
     * @see BrowserSecurityBeanConfig
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    /**
     * @see BrowserSecurityBeanConfig
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer.tokenRepository
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 该对象里面有定义创建表的语句
        // 可以设置让该类来创建表
        // 但是该功能只用使用一次，如果数据库已经存在表则会报错
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SessionProperties session = securityProperties.getBrowser().getSession();
        //http.httpBasic()
        http.apply(validateCodeSecurityConfig) .and()
                .apply(smsCodeAuthenticationSecurityConfigs)
                .and()
                .formLogin()
       .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL).loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
       .successHandler(myAuthenticationSuccessHandler)
       .failureHandler(myAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(session.getMaximumSessions()) //限制同一个用户只能有一个session登录
                .maxSessionsPreventsLogin(session.isMaxSessionsPreventsLogin())// 当session达到最大后，阻止后登录的行为
                .expiredSessionStrategy(sessionInformationExpiredStrategy)  // 失效后的策略。定制型更高，失效前的请求还能拿到
                .and()
                .and()
                .logout()
//                .logoutUrl("/singout")  // 退出请求路径
                // 与logoutSuccessUrl互斥，有handler则logoutSuccessUrl失效
                // 通过处理器增加配置了页面则跳转到页面，没有则输出json
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_SESSION_INVALID_URL+".html",
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        securityProperties.getBrowser().getLoginPage(),securityProperties.getBrowser().getSignOutUrl()).permitAll()
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
