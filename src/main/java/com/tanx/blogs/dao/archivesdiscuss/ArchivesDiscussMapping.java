package com.tanx.blogs.dao.archivesdiscuss;

import com.tanx.blogs.pojo.model.ArchivesDiscuss;
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
 * @Date 2020/8/24 上午10:02
 */

@Repository
@MapperScan
public interface ArchivesDiscussMapping {

    /**
     * 增加一级评论信息
     * @param discuss 一级评论信息
     * @return 增加的结果
     */
    @Insert("insert into `archivesDiscuss`(`username`,`archivesId`,`content`,`writeTime`,`email`,`headImage`,`reply`,`grade`,`attachmentDiscussId`) " +
            "values(#{username},#{archivesId},#{content},#{writeTime},#{email},#{headImage},#{reply},#{grade},#{attachmentDiscussId})")
    int addDiscuss(ArchivesDiscuss discuss);

    /**
     *  通过文章ID获取评论信息
     * @param archivesId 文章ID
     * @return 返回评论信息
     */
    @Select("select * from `archivesDiscuss` where `archivesId` = #{archivesId} order by writeTime asc")
    List<ArchivesDiscuss> queryDiscussByArchivesId(long archivesId);

    /**
     * 根据文章ID和回复对象查询是否存在此数据
     * @param archivesId 文章ID
     * @param reply 回复对象
     * @return 返回结果
     */
    @Select("select count(1) from `archivesDiscuss` where `archivesId` = #{archivesId} and `username` = #{reply}")
    int queryDiscussByArchivesIdAndReply(@Param("archivesId") long archivesId,@Param("reply") String reply);

    /**
     * 查询最新的评论
     * @return 数据
     */
    @Select("select * from `archivesDiscuss` order by `writeTime` desc limit 0,5")
    List<ArchivesDiscuss> queryDiscuss();
}
