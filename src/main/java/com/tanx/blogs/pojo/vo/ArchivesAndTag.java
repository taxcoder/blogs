package com.tanx.blogs.pojo.vo;


import java.util.Date;

public class ArchivesAndTag {
    private long archivesId;
    private String title;
    private String subtitle;
    private int goodNumber;
    private int readNumber;
    private Date uploadTime;
    private long tagId;
    private String tagName;

    public ArchivesAndTag() {}

    public ArchivesAndTag(long archivesId, String title, String subtitle, int goodNumber, int readNumber, Date uploadTime, long tagId, String tagName) {
        this.archivesId = archivesId;
        this.title = title;
        this.subtitle = subtitle;
        this.goodNumber = goodNumber;
        this.readNumber = readNumber;
        this.uploadTime = uploadTime;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public long getArchivesId() {
        return this.archivesId;
    }

    public void setArchivesId(long archivesId) {
        this.archivesId = archivesId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getGoodNumber() {
        return this.goodNumber;
    }

    public void setGoodNumber(int goodNumber) {
        this.goodNumber = goodNumber;
    }

    public int getReadNumber() {
        return this.readNumber;
    }

    public void setReadNumber(int readNumber) {
        this.readNumber = readNumber;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public long getTagId() {
        return this.tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "ArchivesAndTag{archivesId=" + this.archivesId + ", title='" + this.title + '\'' + ", subtitle='" + this.subtitle + '\'' + ", goodNumber=" + this.goodNumber + ", readNumber=" + this.readNumber + ", uploadTime=" + this.uploadTime + ", tagId=" + this.tagId + ", tagName='" + this.tagName + '\'' + '}';
    }
}
