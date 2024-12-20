package com.forestalsen.user_center.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forestalsen.user_center.common.BaseResponse;
import com.forestalsen.user_center.common.ErrorCode;
import com.forestalsen.user_center.common.ResultUtils;
import com.forestalsen.user_center.exception.BusinessException;
import com.forestalsen.user_center.model.User;
import com.forestalsen.user_center.model.request.UserLoginRequest;
import com.forestalsen.user_center.model.request.UserRegisterRequest;
import com.forestalsen.user_center.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


import static com.forestalsen.user_center.constant.UserConstant.USER_LOGIN_STATE;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        String account = userRegisterRequest.getAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(account,password,checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        Long user_register = userService.userRegister(account, password, checkPassword);
        return ResultUtils.success(user_register);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();

        if(StringUtils.isAnyBlank(account,password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        return ResultUtils.success(userService.userLogin(account, password,request));
    }


    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if(request==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(1);
    }



    @GetMapping("/get_current")
    //获取登陆态
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User user = userService.getById(currentUser.getId());
        User SafetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(SafetyUser);
    }






    @GetMapping("/search_user")
    //仅管理员可用
    public BaseResponse<List<User>> searchUser(String username,HttpServletRequest request) {
        if(!userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NOT_AUTH,"非管理员权限");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username", username);
        }
        List<User> users = userService.list(queryWrapper);
        List<User> result = users.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());//将用户数据脱敏展示

         return ResultUtils.success(result);
    }


    /**
     * 删除用户
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/delete")
    //仅管理员可用
    public BaseResponse<Boolean> userDelete(@RequestBody Long id, HttpServletRequest request) {
       if(userService.isAdmin(request)){
        return ResultUtils.success(userService.removeById(id));
       }
       throw new BusinessException(ErrorCode.NULL_ERROR);
    }


    @GetMapping("/get_all_user")
    public List<User> getAllUser() {
        return userService.list();
    }

}
