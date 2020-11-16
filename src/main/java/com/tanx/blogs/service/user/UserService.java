package com.tanx.blogs.service.user;


import com.tanx.blogs.pojo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tan
 */
public interface UserService {

    /**
     * 根据ID查询用户信息
     * @param userId 用户ID
     * @return  返回数据
     */
    boolean queryUserById(String userId);

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
    User queryLoginName(String loginName);

    /**
     * 判断显示名是否重复
     * @param username 显示名
     * @return 返回结果
     */
    boolean queryUserName(String username);

    /**
     * 判断显示名是否重复
     * @param username 显示名
     * @return 返回结果
     */
    List<User> queryUserNameList(String username);

    /**
     * 修改用户的激活状态
     * @param paramString1 包含用户信息的map集合
     * @param paramString2 包含用户信息的map集合
     * @return 返回影响的行数
     */
    int updateUserActive(String paramString1, String paramString2);

    /**
     * 判断用户修改新密码
     * @param password 新密码
     * @param userId 用户ID
     * @param loginName 登录名
     * @return 返回英影响的行数
     */
    int updatePassword(String password, long userId, String loginName);

    /**
     * 查询邮箱
     * @param paramString 邮箱
     * @return 返回查询到的数据
     */
    boolean queryEmail(String paramString);

    /**
     * 验证登录名和邮箱
     * @param paramString1 邮箱
     * @param paramString2 登录名
     * @return 用户的信息
     */
    com.tanx.blogs.pojo.dto.User queryLoginNameEmail(String paramString1, String paramString2);

    /**
     * 忘记密码
     * @param paramString1 新密码
     * @param paramString2 邮箱
     * @param paramString3 登录名
     * @return 影响的行数
     */
    int updateOldPassword(String paramString1, String paramString2, String paramString3);

    /**
     * 查询所有的用户中权限为true的
     * @return 返回用户
     */
    List<User> queryRank();

    /**
     * 修改地址
     * @param userId 用户ID
     * @param address 地址
     * @return 影响的行数
     */
    int updateAdminInfoAddress(long userId, String address);

    /**
     * 修改签名
     * @param userId 用户ID
     * @param sign 签名
     * @return 影响的行数
     */
    int updateAdminInfoSign(long userId, String sign);

    /**
     * 修改用户头像
     * @param headImg 用户的头像
     * @param userId 用户的ID
     * @return 返回影响的行数
     */
    int updateImage(String headImg, long userId);
}
