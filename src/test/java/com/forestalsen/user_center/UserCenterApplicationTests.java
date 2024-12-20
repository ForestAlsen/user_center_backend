package com.forestalsen.user_center;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
        try{
        String password = "ascah1213";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[]encryptedPassword = md.digest(password.getBytes()); //将密码转化成字节数组并哈希
        StringBuilder hexString = new StringBuilder();//将字节数组转化成16进制数
            for (byte b : encryptedPassword) {
                hexString.append(String.format("%02X", b));
            }
            System.out.println(hexString);

    }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not be found:",e);
        }
    }

}
