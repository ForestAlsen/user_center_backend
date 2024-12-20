package com.forestalsen.user_center.mapper;

import com.forestalsen.user_center.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author asus
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-11-27 19:56:37
* @Entity com.forestalsen.user_center.entity.user
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where account = #{account}")
    public User getUserByAccount(String account);

    @Select("select * from user where password =#{password}")
    public User getUserByPassword(String password);
}




