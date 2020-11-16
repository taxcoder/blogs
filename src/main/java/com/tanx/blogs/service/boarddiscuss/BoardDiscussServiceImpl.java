package com.tanx.blogs.service.boarddiscuss;

import com.tanx.blogs.dao.boarddiscuss.BoardDiscussMapping;
import com.tanx.blogs.pojo.model.BoardDiscuss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/26 上午9:19
 */
@Service
public class BoardDiscussServiceImpl implements BoardDiscussService {

    private BoardDiscussMapping mapping;

    @Autowired
    public void setMapping(BoardDiscussMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public boolean addBoardDiscuss(BoardDiscuss discuss) {
        return mapping.addBoardDiscuss(discuss)==1;
    }

    @Override
    public List<BoardDiscuss> queryBoardDiscuss() {
        return mapping.queryBoardDiscuss();
    }

    @Override
    public boolean queryBoardDiscussByReply(String attachment,String reply) {
        return mapping.queryBoardDiscussByReply(Long.parseLong(attachment),reply)!=0;
    }
}
