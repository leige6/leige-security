package com.leige.security.core.utils;

import java.security.MessageDigest;

public class Md5Util {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * MD5加密,默认UTF-8
     *
     * @param arg 加密数据
     * @return
     */
    public static String md5Encode(String arg) {

        if (arg == null) {
            arg = "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(arg.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != md5) {
            return toHex(md5.digest());
        }
        return "";
    }

    private static String toHex(byte[] bytes) {
        StringBuffer str = new StringBuffer(32);
        for (byte b : bytes) {
            str.append(HEX_DIGITS[(b & 0xf0) >> 4]);
            str.append(HEX_DIGITS[(b & 0x0f)]);
        }
        return str.toString();
    }
}
