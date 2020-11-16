package com.tanx.blogs.pojo.model;

public class Tag {
    private long tagId;
    private String tagName;

    public Tag() {}

    public Tag(long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
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
        return "Tag{tagId=" + this.tagId + ", tagName='" + this.tagName + '\'' + '}';
    }
}
