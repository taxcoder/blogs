package com.tanx.blogs.service.user;


import com.tanx.blogs.dao.user.UserMapper;
import com.tanx.blogs.pojo.model.User;
import com.tanx.blogs.utils.FilterDataUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tanx.blogs.utils.IsEmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tan
 */
@Service
public class UserServiceImpl implements UserService {
    private UserMapper mapper;

    @Autowired
    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean queryUserById(String userId) {
        if(!IsEmptyUtil.isNumber(userId)){
            return false;
        }
        return mapper.queryUserById(Long.parseLong(userId))!=null;
    }

    @Override
    public int userRegister(User user) {
        return this.mapper.userRegister(user);
    }

    @Override
    public User userLogin(com.tanx.blogs.pojo.dto.User user) {
        return this.mapper.userLogin(user);
    }

    @Override
    public User queryLoginName(String loginName) {
        return this.mapper.queryLoginName(FilterDataUtil.filterTrimAndFeed(loginName));
    }

    @Override
    public boolean queryUserName(String username) {
        return mapper.queryUserName(FilterDataUtil.filterTrimAndFeed(username)).size() == 0;
    }

    @Override
    public List<User> queryUserNameList(String username) {
        return (this.mapper.queryUserName(FilterDataUtil.filterTrimAndFeed(username)));
    }

    @Override
    public int updateUserActive(String loginName, String email) {
        Map<String, String> map = new HashMap<>(4);
        map.put("loginName", loginName);
        map.put("email", email);
        return this.mapper.updateUserActive(map);
    }

    @Override
    public int updatePassword(String newPassword, long userId, String loginName) {
        return this.mapper.updatePassword(newPassword, userId, loginName);
    }

    @Override
    public boolean queryEmail(String email) {
        return (this.mapper.queryEmail(email).size() == 0);
    }

    @Override
    public com.tanx.blogs.pojo.dto.User queryLoginNameEmail(String email, String loginName) {
        return this.mapper.queryLoginNameEmail(email, loginName);
    }

    @Override
    public int updateOldPassword(String newPassword, String email, String loginName) {
        return this.mapper.updateOldPassword(newPassword, email, loginName);
    }

    @Override
    public List<User> queryRank() {
        return this.mapper.queryRank();
    }

    @Override
    public int updateAdminInfoAddress(long userId, String address) {
        return this.mapper.updateAdminInfoAddress(userId, address);
    }

    @Override
    public int updateAdminInfoSign(long userId, String sign) {
        return this.mapper.updateAdminInfoSign(userId, sign);
    }

    @Override
    public int updateImage(String image, long userId) {
        return this.mapper.updateImage(image, userId);
    }
}
