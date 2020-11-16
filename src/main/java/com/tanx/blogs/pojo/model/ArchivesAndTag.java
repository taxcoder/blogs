package com.tanx.blogs.pojo.model;


import java.util.List;

public class ArchivesAndTag {
    private long archivesAndTagId;
    private long archivesId;
    private List<Long> tagId;

    public ArchivesAndTag() {}

    public ArchivesAndTag(long archivesId, List<Long> tagId) {
        this.archivesId = archivesId;
        this.tagId = tagId;
    }

    public ArchivesAndTag(long archivesAndTagId, long archivesId, List<Long> tagId) {
        this.archivesAndTagId = archivesAndTagId;
        this.archivesId = archivesId;
        this.tagId = tagId;
    }

    public long getArchivesAndTagId() {
        return this.archivesAndTagId;
    }

    public void setArchivesAndTagId(long archivesAndTagId) {
        this.archivesAndTagId = archivesAndTagId;
    }

    public long getArchivesId() {
        return this.archivesId;
    }

    public void setArchivesId(long archivesId) {
        this.archivesId = archivesId;
    }

    public List<Long> getTagId() {
        return this.tagId;
    }

    public void setTagId(List<Long> tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "ArchivesAndTag{archivesAndTagId=" + this.archivesAndTagId + ", archivesId=" + this.archivesId + ", tagId=" + this.tagId + '}';
    }
}