package org.example.test;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class JasyptTest {

    PooledPBEStringEncryptor encryptor;

    @BeforeEach
    public void setUp() {
        encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("test");
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
    }

    //加密
    @Test
    public void getPass() {
        String str_src = "123456";
        String str_enc = encryptor.encrypt(str_src);
        String str_dec = encryptor.decrypt(str_enc);
        System.out.println("enc:" + str_enc);
        System.out.println("dec:" + str_dec);
    }
}