package com.tanx.blogs.dao.boarddiscuss;

import com.tanx.blogs.pojo.model.ArchivesDiscuss;
import com.tanx.blogs.pojo.model.BoardDiscuss;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/26 上午9:04
 */
@Repository
@MapperScan
public interface BoardDiscussMapping {

    /**
     * 增加留言板评论
     * @param discuss 评论信息
     * @return 返回结果
     */
    int addBoardDiscuss(BoardDiscuss discuss);

    /**
     *  获取留言板评论信息
     * @return 返回评论信息
     */
    @Select("select * from `boardDiscuss` order by writeTime asc")
    List<BoardDiscuss> queryBoardDiscuss();

    /**
     * 根据回复对象查询是否存在此数据
     * @param reply 回复对象
     * @return 返回结果
     */
    @Select("select count(1) from `boardDiscuss` where `username` = #{reply} and boardDiscussId = #{attachmentDiscussId}")
    int queryBoardDiscussByReply(@Param("attachmentDiscussId") Long attachment,@Param("reply") String reply);
}
