package com.tanx.blogs.service.friendlink;

import com.tanx.blogs.pojo.model.FriendLink;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/25 下午7:24
 */
@Service
public interface FriendLinkService {

    /**
     * 增加友链
     * @param friend 友链信息
     * @return 返回增加结果
     */
    boolean addFriendLink(FriendLink friend);

    /**
     * 获取随机的数据
     * @return 返回数据
     */
    List<FriendLink> queryFriendLink();

    /**
     * 查询友链数据
     * @param friendName 友链名
     * @param active 激活
     * @param current 当前页
     * @return 返回数据
     */
    List<FriendLink> queryFriendLinkData(String friendName,boolean active,int current);

    /**
     * 查询友链数据总数
     * @param friendName 友链名
     * @param active 激活
     * @return 返回总数
     */
    int queryFriendLinkDataCount(String friendName,boolean active);

    /**
     * 根据友链ID查询数据
     * @param friendLinkId 友链ID
     * @return 返回数据
     */
    FriendLink queryFriendLinkById(String friendLinkId);

    /**
     * 根据友链ID删除友链
     * @param friendLinkId 友链ID
     * @return 返回结果
     */
    int deleteFriendLinkById(String friendLinkId);

    /**
     * 修改友链状态
     * @param friendLinkId 友链ID
     * @return 返回结果
     */
    int updateFriendLinkActive(Long friendLinkId);

    /**
     * 修改友链状态
     * @param friendLinkId 友链ID
     * @return 返回结果
     */
    int updateFriendLinkActiveFalse(Long friendLinkId);
}
