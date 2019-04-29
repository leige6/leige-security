package com.leige.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 14:46
 * @date 2018/8/4 14:46
 * @since 1.0
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        log.info("{}短信模拟发送{}",mobile,code);
        System.out.println("短信模拟发送：" + mobile + " -> " + code);
    }
}
