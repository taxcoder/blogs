package com.tanx.blogs.service.dailydiscuss;

import com.tanx.blogs.dao.dailydiscuss.DailyDiscussMapping;
import com.tanx.blogs.pojo.model.DailyDiscuss;
import com.tanx.blogs.utils.IsEmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/19 下午10:19
 */
@Service
public class DailyDiscussServiceImpl implements DailyDiscussService{
    private DailyDiscussMapping mapping;

    @Autowired
    public void setMapping(DailyDiscussMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int addDiscuss(DailyDiscuss discuss) {
        return mapping.addDiscuss(discuss);
    }

    @Override
    public List<DailyDiscuss> queryDiscussByDailyId(String dailyId) {
        List<DailyDiscuss> list = new ArrayList<>();
        if(!IsEmptyUtil.isNumber(dailyId)){
            return list;
        }
        list = mapping.queryDiscussByDailyId(Long.parseLong(dailyId));
        return list;
    }

    @Override
    public boolean queryDiscussByDailyIdAndReply(String dailyId, String reply) {
        if(!IsEmptyUtil.isNumber(dailyId)){
            return false;
        }
        return mapping.queryDiscussByDailyIdAndReply(Long.parseLong(dailyId), reply) != 0;
    }
}
