package com.tanx.blogs.service.dailyandvisitor;

import com.tanx.blogs.dao.dailyandvisitor.DailyAndVisitorMapping;
import com.tanx.blogs.pojo.model.DailyAndVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyAndVisitorServiceImpl implements DailyAndVisitorService {
    private DailyAndVisitorMapping mapping;

    @Autowired
    public void setMapping(DailyAndVisitorMapping mapping) {
        this.mapping = mapping;
    }


    public DailyAndVisitor queryDailyAndVisitor(DailyAndVisitor dailyAndVisitor) {
        return this.mapping.queryDailyAndVisitor(dailyAndVisitor);
    }


    public int addDailyAndVisitor(DailyAndVisitor dailyAndVisitor) {
        return this.mapping.addDailyAndVisitor(dailyAndVisitor);
    }
}
