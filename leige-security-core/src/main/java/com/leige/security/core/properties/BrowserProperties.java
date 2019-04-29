package com.leige.security.core.properties;

import lombok.Data;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 15:29
 * @date 2018/8/3 15:29
 * @since 1.0
 */
@Data
public class BrowserProperties {
    /** 登录页面路径 */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    private LoginType loginType = LoginType.JSON;
    private int rememberMeSeconds = 600; // 记住我功能默认超时时间60秒
    /** 注册页面 */
    private String signUpUrl = "/leige-signUp.html";
    /** 退出成功页面 */
    private String signOutUrl;

    private SessionProperties session = new SessionProperties();
}
