package com.leige.security.core.validate.code;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *
 * </pre>
 * @author zhuqiang
 * @version 1.0.0
 * @date 2018/8/4 11:28
 * @since 1.0.0
 */

public interface ValidateCodeGenerator {
    /**
     * 创建验证码
     * @param request
     * @return
     * @throws Exception
     */
    ValidateCode generate(HttpServletRequest request) throws Exception;
}
