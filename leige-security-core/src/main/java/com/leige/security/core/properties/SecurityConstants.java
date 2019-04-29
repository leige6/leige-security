package com.leige.security.core.properties;

/**
 * 常量配置
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/5 16:08
 */
public interface SecurityConstants {
    /** 验证码前缀 */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    /**
     * 当请求需要身份认证时，默认跳转的url
     * @see cn.mrcode.imooc.springsecurity.securitybrowser.BrowserSecurityController
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
    /**
     * 默认的用户名密码登录请求处理url ： 框架拦截认证url，
     * 登录的时候提交地址写这个，只是框架内部使用，配置方不需要存储在该地址
     * 还是根据security过滤器链原理决定了，拦截指定的url，完成特定服务的功能
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";
    /** 默认的手机验证码登录请求处理url */
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";
    /**
     * 默认登录页面
     * @see cn.mrcode.imooc.springsecurity.securitybrowser.BrowserSecurityController
     * @see BrowserProperties
     */
    String DEFAULT_LOGIN_PAGE_URL = "/leige-signIn.html";
    /** 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称 */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /** 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称 */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /** session失效时跳转的地址 */
    String DEFAULT_SESSION_INVALID_URL = "/leige-session-invalid";

    /** openId登录拦截地址 */
    String DEFAULT_LOGIN_PROCESSING_URL_OPEN_ID = "/authentication/openid";
    /** openId字段名 */
    String DEFAULT_PARAMETER_NAME_OPEN_ID = "openId";
    /** providerId参数名 */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
}
