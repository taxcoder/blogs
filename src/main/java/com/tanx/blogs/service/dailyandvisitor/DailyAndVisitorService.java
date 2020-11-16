package com.tanx.blogs.service.dailyandvisitor;

import com.tanx.blogs.pojo.model.DailyAndVisitor;
import org.springframework.stereotype.Service;

@Service
public interface DailyAndVisitorService {
    DailyAndVisitor queryDailyAndVisitor(DailyAndVisitor paramDailyAndVisitor);

    int addDailyAndVisitor(DailyAndVisitor paramDailyAndVisitor);
}
