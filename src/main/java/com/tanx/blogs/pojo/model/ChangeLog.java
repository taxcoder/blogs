package com.tanx.blogs.pojo.model;

import java.util.Date;

public class ChangeLog
{
    private long changeLogId;
    private String content;
    private Date uploadTime;

    public ChangeLog() {}

    public ChangeLog(String content, Date uploadTime) {
        this.content = content;
        this.uploadTime = uploadTime;
    }

    public ChangeLog(long changeLogId, String content, Date uploadTime) {
        this.changeLogId = changeLogId;
        this.content = content;
        this.uploadTime = uploadTime;
    }

    public long getChangeLogId() {
        return this.changeLogId;
    }

    public void setChangeLogId(long changeLogId) {
        this.changeLogId = changeLogId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "ChangeLog{changeLogId=" + this.changeLogId + ", content='" + this.content + '\'' + ", uploadTime=" + this.uploadTime + '}';
    }
}
