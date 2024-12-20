package com.forestalsen.user_center.service;
import java.time.LocalDateTime;

import com.forestalsen.user_center.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class userServiceTest {

    @Resource
     private UserService userService;
    @Autowired
    private com.forestalsen.user_center.service.impl.userServiceImpl userServiceImpl;

    @Test
    public void test() {
        User user = new User();
        user.setAccount("aaa");
        user.setUsername("test");
        user.setAvatar("");
        user.setGender(0);
        user.setPassword("123");
        user.setEmail("123");
        user.setStatus(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setIsDelete(0);

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        //测试非空
        String account = "aaaa";
        String password = "";
        String checkpassword = "1234";
        Long result = userService.userRegister(account, password, checkpassword);
        Assertions.assertTrue(result == -1);
        //测试账号长度
       account = "aaa";
       password = "123456";
       checkpassword = "123456";
       result = userService.userRegister(account, password, checkpassword);
       Assertions.assertTrue(result == -1);
        //测试密码长度
        account = "aaaa";
        password = "1234";
        checkpassword = "1234";
        result = userService.userRegister(account, password, checkpassword);
        Assertions.assertTrue(result == -1);
        //测试密码校验
        account = "aaaa";
        password = "12345";
        checkpassword = "123456";
        result = userService.userRegister(account, password, checkpassword);
        Assertions.assertTrue(result == -1);
        //测试账户非法字符
        account = "a aaa";
        password = "123456";
        checkpassword = "123456";
        result = userService.userRegister(account, password, checkpassword);
        Assertions.assertTrue(result == -1);
        //测试重复账户
        account = "aaaa";
        password = "123456";
        checkpassword = "123456";
        userService.userRegister(account, password, checkpassword);
        account = "aaaa";
        password = "123456";
        checkpassword = "123456";
        result = userService.userRegister(account, password, checkpassword);
        Assertions.assertTrue(result == -1);

        account = "forest_alsen";
        password = "Xf031104";
        checkpassword = "Xf031104";
        userService.userRegister(account, password, checkpassword);

    }

    @Test
    void encryptPassword() {
        String password ="123456";
        String hex = userServiceImpl.encryptPassword(password);
        Assertions.assertEquals("8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92",hex);
    }

    @Test
    void userLogin() {
//        String account ="aaaa";
//        String password ="123456";
//        Long result = userService.userLogin(account,password);
//        Assertions.assertTrue(result == -1);
//
//       account ="a12345";
//       password ="8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92";
//       result = userService.userLogin(account,password);
//        Assertions.assertTrue(result == -1);
//
//        account ="aaaa";
//        password ="8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92";
//        result = userService.userLogin(account,password);
//        Assertions.assertTrue(result == 3);
    }
}