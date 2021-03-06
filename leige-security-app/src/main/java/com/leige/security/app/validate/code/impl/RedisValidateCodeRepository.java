package com.leige.security.app.validate.code.impl;

import com.leige.security.app.AppConstants;
import com.leige.security.core.validate.code.ValidateCode;
import com.leige.security.core.validate.code.ValidateCodeException;
import com.leige.security.core.validate.code.ValidateCodeRepository;
import com.leige.security.core.validate.code.ValidateCodeType;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/8 14:06
 * @date 2018/8/8 14:06
 * @since 1.0
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    /**
     * @see RedisAutoConfiguration#redisTemplate(org.springframework.data.redis.connection.RedisConnectionFactory)
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    /** 验证码放入redis规则模式：CODE_{TYPE}_{DEVICEId} */
    private final static String CODE_KEY_PATTERN = "CODE_%s_%s";

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(buildKey(request, validateCodeType), code, 30, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String key = buildKey(request, validateCodeType);
        // 拿到创建 create() 存储到session的code验证码对象
        return (ValidateCode) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String key = buildKey(request, validateCodeType);
        redisTemplate.delete(key);
    }

    /**
     * 构建验证码放入redis时的key; 在保存的时候也使用该key
     * {@link AbstractValidateCodeProcessor#save(ServletWebRequest, ValidateCode)}
     * @param validateCodeType
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String deviceId = request.getHeader(AppConstants.DEFAULT_HEADER_DEVICE_ID);
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return String.format(CODE_KEY_PATTERN, validateCodeType, deviceId);
    }
}
