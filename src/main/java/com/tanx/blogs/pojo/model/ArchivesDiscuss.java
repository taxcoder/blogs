package com.tanx.blogs.pojo.model;

import java.util.Date;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/23 下午10:06
 */
public class ArchivesDiscuss {
    private long archivesDiscussId;
    private String username;
    private long archivesId;
    private String content;
    private Date writeTime;
    private String email;
    private String headImage;
    private String reply;
    private int grade;
    private long attachmentDiscussId;

    public ArchivesDiscuss() {

    }

    public ArchivesDiscuss(String username, long archivesId, String content, Date writeTime, String email, String headImage, String reply, int grade, long attachmentDiscussId) {
        this.username = username;
        this.archivesId = archivesId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public ArchivesDiscuss(long archivesDiscussId, String username, long archivesId, String content, Date writeTime, String email, String headImage, String reply, int grade, long attachmentDiscussId) {
        this.archivesDiscussId = archivesDiscussId;
        this.username = username;
        this.archivesId = archivesId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public long getArchivesDiscussId() {
        return archivesDiscussId;
    }

    public void setArchivesDiscussId(long archivesDiscussId) {
        this.archivesDiscussId = archivesDiscussId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(long archivesId) {
        this.archivesId = archivesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getAttachmentDiscussId() {
        return attachmentDiscussId;
    }

    public void setAttachmentDiscussId(long attachmentDiscussId) {
        this.attachmentDiscussId = attachmentDiscussId;
    }

    @Override
    public String toString() {
        return "ArchivesDiscuss{" +
                "archivesDiscussId=" + archivesDiscussId +
                ", username='" + username + '\'' +
                ", archivesId=" + archivesId +
                ", content='" + content + '\'' +
                ", writeTime=" + writeTime +
                ", email='" + email + '\'' +
                ", headImage='" + headImage + '\'' +
                ", reply='" + reply + '\'' +
                ", grade=" + grade +
                ", attachmentDiscussId=" + attachmentDiscussId +
                '}';
    }
}
