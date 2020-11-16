package com.tanx.blogs.pojo.model;


/**
 * @Author tanxiang
 */
public class DailyAndVisitor {
    private long dailyAndVisitorId;
    private long dailyId;
    private long visitorId;

    public DailyAndVisitor() {}

    public DailyAndVisitor(long dailyId, long visitorId) {
        this.dailyId = dailyId;
        this.visitorId = visitorId;
    }

    public DailyAndVisitor(long dailyAndVisitorId, long dailyId, long visitorId) {
        this.dailyAndVisitorId = dailyAndVisitorId;
        this.dailyId = dailyId;
        this.visitorId = visitorId;
    }

    public long getDailyAndVisitorId() {
        return this.dailyAndVisitorId;
    }

    public void setDailyAndVisitorId(long dailyAndVisitorId) {
        this.dailyAndVisitorId = dailyAndVisitorId;
    }

    public long getDailyId() {
        return this.dailyId;
    }

    public void setDailyId(long dailyId) {
        this.dailyId = dailyId;
    }

    public long getVisitorId() {
        return this.visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }
}
