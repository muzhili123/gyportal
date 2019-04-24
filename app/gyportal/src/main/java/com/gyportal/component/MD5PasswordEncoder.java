package com.gyportal.component;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * create by lihuan at 18/11/9 18:18
 */
public class MD5PasswordEncoder implements PasswordEncoder {
    MD5Util md5Util = new MD5Util();
    @Override
    public String encode(CharSequence rawPassword) {
        return md5Util.encode((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(md5Util.encode((String)rawPassword));
    }
}
