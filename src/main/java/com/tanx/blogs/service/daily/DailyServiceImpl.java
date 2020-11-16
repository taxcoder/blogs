package com.tanx.blogs.service.daily;

import com.tanx.blogs.dao.daily.DailyMapping;
import com.tanx.blogs.pojo.model.Daily;
import com.tanx.blogs.utils.IsEmptyUtil;
import com.tanx.blogs.utils.VariableUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author tanxiang
 */
@Service
public class DailyServiceImpl implements DailyService {
    private DailyMapping mapping;

    @Autowired
    public void setMapping(DailyMapping mapping) {
        this.mapping = mapping;
    }


    @Override
    public int addDaily(Daily daily) {
        return this.mapping.addDaily(daily);
    }


    @Override
    public List<Daily> queryDaily(int currentPage) {
        return this.mapping.queryDaily(currentPage * 2 * VariableUtils.SHWODPAGE - 2 * VariableUtils.SHWODPAGE, VariableUtils.SHWODPAGE * 2);
    }


    @Override
    public int dailyCount() {
        return this.mapping.dailyCount();
    }


    @Override
    public Daily queryDailyById(String dailyId) {
        if(!IsEmptyUtil.isNumber(dailyId)){
            return null;
        }
        return this.mapping.queryDailyById(Long.parseLong(dailyId));
    }


    @Override
    public int addDailyByGoodNumber(int goodNumber, long dailyId) {
        return this.mapping.addDailyByGoodNumber(goodNumber, dailyId);
    }

    @Override
    public int addDailyByDiscussNumber(int paramInt, long paramLong) {
        return mapping.addDailyByDiscussNumber(paramInt,paramLong);
    }
}