package com.tanx.blogs.service.dailydiscuss;

import com.tanx.blogs.pojo.model.DailyDiscuss;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/19 下午10:19
 */
@Service
public interface DailyDiscussService {

    /**
     * 增加一级评论信息
     * @param discuss 一级评论信息
     * @return 增加的结果
     */
    int addDiscuss(DailyDiscuss discuss);

    /**
     *  通过日记ID获取评论信息
     * @param dailyId 日记ID
     * @return 返回评论信息
     */
    List<DailyDiscuss> queryDiscussByDailyId(String dailyId);

    /**
     * 根据日记ID和回复对象查询是否存在此数据
     * @param dailyId 日记ID
     * @param reply 回复对象
     * @return 返回结果
     */
    boolean queryDiscussByDailyIdAndReply(String dailyId,String reply);
}
