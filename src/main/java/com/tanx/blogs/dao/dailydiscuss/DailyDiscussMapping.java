package com.tanx.blogs.dao.dailydiscuss;

import com.tanx.blogs.pojo.model.DailyDiscuss;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/19 下午10:06
 */
@Repository
@MapperScan
public interface DailyDiscussMapping {

    /**
     * 增加一级评论信息
     * @param discuss 一级评论信息
     * @return 增加的结果
     */
    @Insert("insert into `dailyDiscuss`(`username`,`dailyId`,`content`,`writeTime`,`email`,`headImage`,`reply`,`grade`,`attachmentDiscussId`) " +
            "values(#{username},#{dailyId},#{content},#{writeTime},#{email},#{headImage},#{reply},#{grade},#{attachmentDiscussId})")
    int addDiscuss(DailyDiscuss discuss);

    /**
     *  通过日记ID获取评论信息
     * @param dailyId 日记ID
     * @return 返回评论信息
     */
    @Select("select * from `dailyDiscuss` where `dailyId` = #{dailyId} order by writeTime asc")
    List<DailyDiscuss> queryDiscussByDailyId(long dailyId);

    /**
     * 根据日记ID和回复对象查询是否存在此数据
     * @param dailyId 日记ID
     * @param reply 回复对象
     * @return 返回结果
     */
    @Select("select count(1) from `dailyDiscuss` where `dailyId` = #{dailyId} and `username` = #{reply}")
    int queryDiscussByDailyIdAndReply(@Param("dailyId") long dailyId,@Param("reply") String reply);
}
