package com.tanx.blogs.service.boarddiscuss;

import com.tanx.blogs.pojo.model.BoardDiscuss;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/26 上午9:18
 */
@Service
public interface BoardDiscussService {

    /**
     * 增加留言板评论
     * @param discuss 评论信息
     * @return 返回结果
     */
    boolean addBoardDiscuss(BoardDiscuss discuss);

    /**
     *  获取留言板评论信息
     * @return 返回评论信息
     */
    List<BoardDiscuss> queryBoardDiscuss();

    /**
     * 根据回复对象查询是否存在此数据
     * @param reply 回复对象
     * @return 返回结果
     */
    boolean queryBoardDiscussByReply(String attachment,String reply);
}
