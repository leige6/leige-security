package com.leige.security.core.config;

import com.leige.security.core.utils.Md5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by mac on 2018/11/13.
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return Md5Util.md5Encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String passwordInput= Md5Util.md5Encode(charSequence.toString());
        return s.equals(passwordInput);
    }
}