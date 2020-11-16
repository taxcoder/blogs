package com.tanx.blogs.pojo.model;

import java.util.Date;
import java.util.List;

/**
 * @author tanxiang
 */

public class Archives {
    private long archivesId;
    private String title;
    private String subtitle;
    private long typeId;
    private int goodNumber;
    private int readNumber;
    private Date uploadTime;
    private String address;
    private String archivesTypeName;
    private List<Tag> tags;
    private int discussNumber;

    public Archives() {}

    public Archives(String title, String subtitle, long typeId, String address) {
        this.title = title;
        this.subtitle = subtitle;
        this.typeId = typeId;
        this.address = address;
    }

    public Archives(String title, String subtitle, long typeId, String address, Date uploadTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.typeId = typeId;
        this.address = address;
        this.uploadTime = uploadTime;
    }

    public Archives(long archivesId, String title, String subtitle, long typeId, int goodNumber, int readNumber, Date uploadTime, String address) {
        this.archivesId = archivesId;
        this.title = title;
        this.subtitle = subtitle;
        this.typeId = typeId;
        this.goodNumber = goodNumber;
        this.readNumber = readNumber;
        this.uploadTime = uploadTime;
        this.address = address;
    }

    public Archives(long archivesId, String title, String subtitle, long typeId, int goodNumber, int readNumber, Date uploadTime, String address, String archivesTypeName) {
        this.archivesId = archivesId;
        this.title = title;
        this.subtitle = subtitle;
        this.typeId = typeId;
        this.goodNumber = goodNumber;
        this.readNumber = readNumber;
        this.uploadTime = uploadTime;
        this.address = address;
        this.archivesTypeName = archivesTypeName;
    }

    public Archives(long archivesId, String title, String subtitle, long typeId, int goodNumber, int readNumber, Date uploadTime, String address, String archivesTypeName, List<Tag> tags) {
        this.archivesId = archivesId;
        this.title = title;
        this.subtitle = subtitle;
        this.typeId = typeId;
        this.goodNumber = goodNumber;
        this.readNumber = readNumber;
        this.uploadTime = uploadTime;
        this.address = address;
        this.archivesTypeName = archivesTypeName;
        this.tags = tags;
    }

    public Archives(long archivesId, String title, String subtitle, long typeId, int goodNumber, int readNumber, Date uploadTime, String address, String archivesTypeName, List<Tag> tags, int discussNumber) {
        this.archivesId = archivesId;
        this.title = title;
        this.subtitle = subtitle;
        this.typeId = typeId;
        this.goodNumber = goodNumber;
        this.readNumber = readNumber;
        this.uploadTime = uploadTime;
        this.address = address;
        this.archivesTypeName = archivesTypeName;
        this.tags = tags;
        this.discussNumber = discussNumber;
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

    public long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArchivesTypeName() {
        return this.archivesTypeName;
    }

    public void setArchivesTypeName(String archivesTypeName) {
        this.archivesTypeName = archivesTypeName;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getDiscussNumber() {
        return discussNumber;
    }

    public void setDiscussNumber(int discussNumber) {
        this.discussNumber = discussNumber;
    }

    @Override
    public String toString() {
        return "Archives{" +
                "archivesId=" + archivesId +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", typeId=" + typeId +
                ", goodNumber=" + goodNumber +
                ", readNumber=" + readNumber +
                ", uploadTime=" + uploadTime +
                ", address='" + address + '\'' +
                ", archivesTypeName='" + archivesTypeName + '\'' +
                ", tags=" + tags +
                ", discussNumber=" + discussNumber +
                '}';
    }
}