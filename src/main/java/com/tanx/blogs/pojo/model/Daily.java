package com.tanx.blogs.pojo.model;

import java.util.Date;

/**
 * @author tanxiang
 */
public class Daily {
    private long dailyId;
    private String content;
    private int goodNumber;
    private Date uploadTime;
    private int discussNumber;

    public Daily(long dailyId, String content, int goodNumber, Date uploadTime, int discussNumber) {
        this.dailyId = dailyId;
        this.content = content;
        this.goodNumber = goodNumber;
        this.uploadTime = uploadTime;
        this.discussNumber = discussNumber;
    }

    public Daily(long dailyId, String content, int goodNumber, Date uploadTime) {
        this.dailyId = dailyId;
        this.content = content;
        this.goodNumber = goodNumber;
        this.uploadTime = uploadTime;
    }

    public Daily(String content, int goodNumber, Date uploadTime) {
        this.content = content;
        this.goodNumber = goodNumber;
        this.uploadTime = uploadTime;
    }

    public Daily(String content, Date uploadTime) {
        this.content = content;
        this.uploadTime = uploadTime;
    }


    public Daily() {}


    public long getDailyId() {
        return this.dailyId;
    }

    public void setDailyId(long dailyId) {
        this.dailyId = dailyId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGoodNumber() {
        return this.goodNumber;
    }

    public void setGoodNumber(int goodNumber) {
        this.goodNumber = goodNumber;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getDiscussNumber() {
        return discussNumber;
    }

    public void setDiscussNumber(int discussNumber) {
        this.discussNumber = discussNumber;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "dailyId=" + dailyId +
                ", content='" + content + '\'' +
                ", goodNumber=" + goodNumber +
                ", uploadTime=" + uploadTime +
                ", discussNUmber=" + discussNumber +
                '}';
    }
}
