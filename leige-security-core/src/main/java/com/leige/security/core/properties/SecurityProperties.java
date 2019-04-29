package com.leige.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ${desc}
 * @author zhangyalei
 * @version 1.0.1 2018/8/3 15:28
 * @date 2018/8/3 15:28
 * @since 1.0
 */
@ConfigurationProperties(prefix = "leige.security")
@Data
public class SecurityProperties {
    /** leige.security.browser 路径下的配置会被映射到该配置类中 */
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

    private SocialProperties social = new SocialProperties();
}
