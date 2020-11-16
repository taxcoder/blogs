package com.tanx.blogs.dao.user;

import com.tanx.blogs.pojo.model.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tan
 */
@Repository
@MapperScan

public interface UserMapper {

    /**
     * 用户注册
     * @param paramUser 用户信息
     * @return 返回增加的结果
     */
    int userRegister(User paramUser);

    /**
     * 用户登录
     * @param paramUser 登录用户的信息
     * @return 是否查询到用户
     */
    User userLogin(com.tanx.blogs.pojo.dto.User paramUser);

    /**
     * 判断登录名是否重复
     * @param loginName 登录名
     * @return 是否查询到
     */
    @Select({"select * from user where loginName = #{loginName}"})
    User queryLoginName(@Param("loginName") String loginName);

    /**
     * 判断显示名是否重复
     * @param username 显示名
     * @return 返回结果
     */
    @Select({"select * from user where `username` = #{username}"})
    List<User> queryUserName(@Param("username") String username);

    /**
     * 修改用户的激活状态
     * @param map 包含用户信息的map集合
     * @return 返回影响的行数
     */
    int updateUserActive(Map<String, String> map);

    /**
     * 判断用户修改新密码
     * @param password 新密码s
     * @param userId 用户ID
     * @param loginName 登录名
     * @return 返回英影响的行数
     */
    @Update({"update `user` set `password` = #{password} where `userId` = #{userId} and `loginName` = #{loginName}"})
    int updatePassword(@Param("password") String password, @Param("userId") long userId, @Param("loginName") String loginName);

    /**
     * 查询邮箱
     * @param paramString 邮箱
     * @return 返回查询到的数据
     */
    @Select({"select * from `user` where email = #{email}"})
    List<User> queryEmail(@Param("email") String paramString);

    /**
     * 验证登录名和邮箱
     * @param paramString1 邮箱
     * @param paramString2 登录名
     * @return 用户的信息
     */
    @Select({"select * from `user` where loginName = #{loginName} and email = #{email}"})
    com.tanx.blogs.pojo.dto.User queryLoginNameEmail(@Param("email") String paramString1, @Param("loginName") String paramString2);

    /**
     * 忘记密码
     * @param paramString1 新密码
     * @param paramString2 邮箱
     * @param paramString3 登录名
     * @return 影响的行数
     */
    @Update({"update `user` set `password` = #{password} where `email` = #{email} and `loginName` = #{loginName}"})
    int updateOldPassword(@Param("password") String paramString1, @Param("email") String paramString2, @Param("loginName") String paramString3);

    /**
     * 查询所有的用户中权限为true的
     * @return 返回用户
     */
    @Select({"select * from `user` where `rank` = 1"})
    List<User> queryRank();

    /**
     * 修改签名
     * @param userId 用户ID
     * @param sign 签名
     * @return 影响的行数
     */
    @Update({"update `user` set `sign` = #{sign} where `userId` = #{userId} and `rank` = 1 "})
    int updateAdminInfoSign(@Param("userId") long userId, @Param("sign") String sign);

    /**
     * 修改地址
     * @param userId 用户ID
     * @param address 地址
     * @return 影响的行数
     */
    @Update({"update `user` set `address` = #{address} where `userId` = #{userId} and `rank` = 1 "})
    int updateAdminInfoAddress(@Param("userId") long userId, @Param("address") String address);

    /**
     * 修改用户头像
     * @param headImg 用户的头像
     * @param userId 用户的ID
     * @return 返回影响的行数
     */
    @Update({"update `user` set headImg = #{headImg} where userId = #{userId}"})
    int updateImage(@Param("headImg") String headImg, @Param("userId") long userId);


    /**
     * 根据ID查询用户信息
     * @param userId 用户ID
     * @return  返回数据
     */
    @Select("select * from `user` where `userId` = #{userId} and `rank` = 1")
    User queryUserById(@Param("userId") long userId);
}

