package com.forestalsen.user_center.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.forestalsen.user_center.common.ErrorCode;
import com.forestalsen.user_center.exception.BusinessException;
import com.forestalsen.user_center.model.User;
import com.forestalsen.user_center.service.UserService;
import com.forestalsen.user_center.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.forestalsen.user_center.constant.UserConstant.ADMIN_ROLE;
import static com.forestalsen.user_center.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author asus
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-11-27 19:56:37
*/

@Service
@Slf4j //允许使用日志来记录
public class userServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {



    @Resource
    private UserMapper userMapper;

    //加密密码
    public String encryptPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[]encryptedPassword = md.digest(password.getBytes()); //将密码转化成字节数组并哈希
            StringBuilder hexString = new StringBuilder();//将字节数组转化成16进制数
            for (byte b : encryptedPassword) {
                hexString.append(String.format("%02X", b));
            }
            System.out.println(hexString);
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }



    @Override
    public Long userRegister(String account, String password, String checkPassword) {
        //1. 校验
        if(StringUtils.isAnyBlank(account, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        if(account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度不足4位");
        }
        if(password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度不足6位");
        }
        //账户不能包含特殊字符
        String vailPattern = "^[a-zA-Z0-9_]+$";
        Matcher matcher = Pattern.compile(vailPattern).matcher(account);
        if(!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号中含非法字符");
        }
        //查询是否有重复账户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        long count = this.count(queryWrapper);
        if(count > 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"账号已存在");
        }
        //2.加密密码
//        try{
//            String toEncryptPassword = password;//要加密的密码
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[]encryptedPassword = md.digest(toEncryptPassword.getBytes()); //将密码转化成字节数组并哈希
//            StringBuilder hexString = new StringBuilder();//将字节数组转化成16进制数
//            for (byte b : encryptedPassword) {
//                hexString.append(String.format("%02X", b));
//            }
//            System.out.println(hexString);

            String hexString = encryptPassword(password);
            //3.插入用户数据
            User user = new User();
            user.setAccount(account);
            user.setPassword(hexString);
            boolean InsertSuccess = this.save(user);//向数据库中插入用户数据
            if (!InsertSuccess) {
                throw new RuntimeException();
            }
        return user.getId();
        }

    @Override
    public User userLogin(String account, String password, HttpServletRequest request) {
        //1. 校验账户和密码长度
        if(StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        if(account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度不足4位");
        }
        if(password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度不足6位");
        }
        //账户不能包含特殊字符
        String vailPattern = "^[a-zA-Z0-9_]+$";
        Matcher matcher = Pattern.compile(vailPattern).matcher(account);
        if(!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号中含非法字符");
        }
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", encryptPassword(password));
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            log.info("user login fail,can't find account or can't match password");
            throw new BusinessException(ErrorCode.NULL_ERROR,"密码或账号错误");
        }

        //2.用户脱敏
        User safetyUser = getSafetyUser(user);


        //3.记录用户登陆态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);



        return safetyUser;


//        User u1 = userMapper.getUserByAccount(account);//用户输入的账号
//        if(u1.getAccount()!=null) {
//               //校验密码
//               if(u1.getPassword().equals(encryptPassword(password))) {
//                   return u1;
//               }
//                   //密码错误
//                   return null;
//            }//账号不存在
//            else return null;
    }

    @Override
    public User getSafetyUser(User user){
        //2.用户脱敏
        if(user == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setAccount(user.getAccount());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setGender(user.getGender());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setStatus(user.getStatus());
        safetyUser.setRole(user.getRole());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public Boolean updateUsername(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", user.getUsername());
//        this.update(user, queryWrapper);
        return null;
    }


    @Override
    public boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getRole() == ADMIN_ROLE;
    }
}




