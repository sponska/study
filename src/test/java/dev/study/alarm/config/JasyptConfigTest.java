package dev.study.alarm.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {

    @Test
    void stringEncryptor() {
        JasyptConfig jasyptConfig = new JasyptConfig();
        StringEncryptor stringEncryptor = jasyptConfig.stringEncryptor();

        String encrypt = stringEncryptor.encrypt("xoxb-175823403904-939351495456-4jaYup3rUnTYbWxOKZdQ2Se8");
        System.out.println(encrypt);
    }
}