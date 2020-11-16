package com.tanx.blogs.dao.friendlink;

import com.tanx.blogs.pojo.model.FriendLink;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/25 下午7:06
 */
@Repository
@MapperScan
public interface FriendLinkMapping {

    /**
     * 增加友链
     * @param friend 友链信息
     * @return 返回增加结果
     */
    int addFriendLink(FriendLink friend);

    /**
     * 获取随机的数据
     * @return 返回数据
     */
    List<FriendLink>  queryFriendLink();

    /**
     * 查询友链数据
     * @param map map集合
     * @return 返回数据
     */
    List<FriendLink> queryFriendLinkData(Map<String,Object> map);

    /**
     * 查询友链数据总数
     * @param map map集合
     * @return 返回总数
     */
    int queryFriendLinkDataCount(Map<String,Object> map);

    /**
     * 根据友链ID查询数据
     * @param parseLong 友链ID
     * @return 返回数据
     */
    @Select("select * from `friendLink` where `friendLinkId` = #{friendLinkId}")
    FriendLink queryFriendLinkById(@Param("friendLinkId") long parseLong);

    /**
     * 根据友链ID删除友链
     * @param friendLinkId 友链ID
     * @return 返回结果
     */
    @Delete("delete from `friendLink` where `friendLinkId` = #{friendLinkId}")
    int deleteFriendLinkById(@Param("friendLinkId") long friendLinkId);

    /**
     * 修改友链激活状态
     * @param friendLinkId 友链ID
     * @return 返回结果
     */
    @Update("update `friendLink` set `active` = true where `friendLinkId` = #{friendLinkId}")
    int updateFriendLinkActive(Long friendLinkId);

    /**
     * 修改友链激活状态
     * @param friendLinkId 友链ID
     * @return 返回结果
     */
    @Update("update `friendLink` set `active` = false where `friendLinkId` = #{friendLinkId}")
    int updateFriendLinkActiveFalse(@Param("friendLinkId") Long friendLinkId);
}
