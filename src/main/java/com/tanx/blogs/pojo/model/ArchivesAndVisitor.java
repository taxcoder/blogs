package com.tanx.blogs.pojo.model;

public class ArchivesAndVisitor
{
    private long archivesAndVisitorId;
    private long archivesId;
    private long visitorId;

    public ArchivesAndVisitor() {}

    public ArchivesAndVisitor(long archivesId, long visitorId) {
        this.archivesId = archivesId;
        this.visitorId = visitorId;
    }

    public ArchivesAndVisitor(long archivesAndVisitorId, long archivesId, long visitorId) {
        this.archivesAndVisitorId = archivesAndVisitorId;
        this.archivesId = archivesId;
        this.visitorId = visitorId;
    }

    public long getArchivesAndVisitorId() {
        return this.archivesAndVisitorId;
    }

    public void setArchivesAndVisitorId(long archivesAndVisitorId) {
        this.archivesAndVisitorId = archivesAndVisitorId;
    }

    public long getArchivesId() {
        return this.archivesId;
    }

    public void setArchivesId(long archivesId) {
        this.archivesId = archivesId;
    }

    public long getVisitorId() {
        return this.visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    @Override
    public String toString() {
        return "ArchivesAndVisitor{archivesAndVisitorId=" + this.archivesAndVisitorId + ", archivesId=" + this.archivesId + ", visitorId=" + this.visitorId + '}';
    }
}
