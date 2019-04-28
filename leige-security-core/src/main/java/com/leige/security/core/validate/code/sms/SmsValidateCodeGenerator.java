package com.leige.security.core.validate.code.sms;



import com.leige.security.core.properties.SmsCodeProperties;
import com.leige.security.core.validate.code.ValidateCode;
import com.leige.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 图片验证码工具类
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 11:30
 * @date 2018/8/4 11:30
 * @since 1.0
 */
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
    private SmsCodeProperties smsCodeProperties;

    public SmsValidateCodeGenerator(SmsCodeProperties smsCodeProperties) {
        this.smsCodeProperties = smsCodeProperties;
    }

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        int count = smsCodeProperties.getLength();
        int expireIn = smsCodeProperties.getExpireIn();
        String smsCode = RandomStringUtils.randomNumeric(count);
        return new ValidateCode(smsCode, expireIn);
    }

    public SmsCodeProperties getSmsCodeProperties() {
        return smsCodeProperties;
    }

    public void setSmsCodeProperties(SmsCodeProperties smsCodeProperties) {
        this.smsCodeProperties = smsCodeProperties;
    }
}
