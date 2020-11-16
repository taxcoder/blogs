package com.tanx.blogs.service.archivesdiscuss;

import com.tanx.blogs.pojo.model.ArchivesDiscuss;
import com.tanx.blogs.pojo.model.DailyDiscuss;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/24 上午10:07
 */
@Service
public interface ArchivesDiscussService {

    /**
     * 增加一级评论信息
     * @param discuss 一级评论信息
     * @return 增加的结果
     */
    int addDiscuss(ArchivesDiscuss discuss);

    /**
     *  通过文章ID获取评论信息
     * @param archivesId 文章ID
     * @return 返回评论信息
     */
    List<ArchivesDiscuss> queryDiscussByArchivesId(String archivesId);

    /**
     * 根据文章ID和回复对象查询是否存在此数据
     * @param archivesId 文章ID
     * @param reply 回复对象
     * @return 返回结果
     */
    int queryDiscussByArchivesIdAndReply(String archivesId, String reply);

    /**
     * 查询最新的
     * @return 数据
     */
    List<ArchivesDiscuss> queryDiscuss();
}
