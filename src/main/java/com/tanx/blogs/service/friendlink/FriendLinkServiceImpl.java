package com.tanx.blogs.service.friendlink;

import com.tanx.blogs.dao.friendlink.FriendLinkMapping;
import com.tanx.blogs.pojo.model.FriendLink;
import com.tanx.blogs.utils.IsEmptyUtil;
import com.tanx.blogs.utils.VariableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/25 下午7:25
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService{

    private FriendLinkMapping mapping;

    @Autowired
    public void setMapping(FriendLinkMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public boolean addFriendLink(FriendLink friend) {
        return mapping.addFriendLink(friend)==1;
    }

    @Override
    public List<FriendLink> queryFriendLink() {
        return mapping.queryFriendLink();
    }

    @Override
    public List<FriendLink> queryFriendLinkData(String friendName, boolean active,int current) {
        Map<String,Object> map = new HashMap<>(8);
        map.put("friendName", IsEmptyUtil.isStringEmpty(friendName)?null:"%"+friendName+"%");
        map.put("active",active);
        map.put("current",current * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE);
        map.put("showPage", VariableUtils.SHWODPAGE);
        return mapping.queryFriendLinkData(map);
    }

    @Override
    public int queryFriendLinkDataCount(String friendName, boolean active) {
        Map<String,Object> map = new HashMap<>(8);
        map.put("friendName",IsEmptyUtil.isStringEmpty(friendName)?null:"%"+friendName+"%");
        map.put("active",active);
        return mapping.queryFriendLinkDataCount(map);
    }

    @Override
    public FriendLink queryFriendLinkById(String friendLinkId) {
        if(!IsEmptyUtil.isNumber(friendLinkId)){
            return null;
        }
        return mapping.queryFriendLinkById(Long.parseLong(friendLinkId));
    }

    @Override
    public int deleteFriendLinkById(String friendLinkId) {
        if(!IsEmptyUtil.isNumber(friendLinkId)){
            return 0;
        }
        return mapping.deleteFriendLinkById(Long.parseLong(friendLinkId));
    }

    @Override
    public int updateFriendLinkActive(Long friendLinkId) {
        return mapping.updateFriendLinkActive(friendLinkId);
    }

    @Override
    public int updateFriendLinkActiveFalse(Long friendLinkId) {
        return mapping.updateFriendLinkActiveFalse(friendLinkId);
    }
}
