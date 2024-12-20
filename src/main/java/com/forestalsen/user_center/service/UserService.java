package com.forestalsen.user_center.service;

import com.forestalsen.user_center.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;


/**
* @author asus
 * 在此编写用户服务
* @description 针对表【user】的数据库操作Service
* @createDate 2024-11-27 19:56:37
*/
public interface UserService extends IService<User> {



    /**
     *
     * @param account 用户账户
     * @param password 用户密码
     * @param checkPassword 校验密码
     * @return 返回用户id
     */
    Long userRegister(String account, String password, String checkPassword);


    /**
     * 用户登陆接口
     *
     * @param account
     * @param password
     * @param request
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String account, String password, HttpServletRequest request);

    /**
     *
     * 判断是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);


    /**
     * 用户注销
     * @param request
     * @return
     */
    Integer userLogout(HttpServletRequest request);

    Boolean updateUsername(User user);

}
