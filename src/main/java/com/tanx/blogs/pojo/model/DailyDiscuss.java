package com.tanx.blogs.pojo.model;


import java.util.Date;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/19 下午8:57
 */
public class DailyDiscuss {
    private long discussId;
    private String username;
    private long dailyId;
    private String content;
    private Date writeTime;
    private String email;
    private String headImage;
    private String reply;
    private int grade;
    private long attachmentDiscussId;

    public DailyDiscuss() {

    }

    public DailyDiscuss(String username,long dailyId, String content, Date writeTime, String email, String headImage) {
        this.username = username;
        this.dailyId = dailyId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
    }

    public DailyDiscuss(String username, long dailyId, String content, Date writeTime, String email, String headImage, String reply, int grade) {
        this.username = username;
        this.dailyId = dailyId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
    }

    public DailyDiscuss(String username, long dailyId, String content, Date writeTime, String email, String headImage, String reply, int grade, long attachmentDiscussId) {
        this.username = username;
        this.dailyId = dailyId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public DailyDiscuss(long discussId, String username, long dailyId, String content, Date writeTime, String email, String headImage) {
        this.discussId = discussId;
        this.username = username;
        this.dailyId = dailyId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
    }

    public DailyDiscuss(long discussId, String username, long dailyId, String content, Date writeTime, String email, String headImage, String reply, int grade) {
        this.discussId = discussId;
        this.username = username;
        this.dailyId = dailyId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
    }

    public DailyDiscuss(long discussId, String username, long dailyId, String content, Date writeTime, String email, String headImage, String reply, int grade, long attachmentDiscussId) {
        this.discussId = discussId;
        this.username = username;
        this.dailyId = dailyId;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public long getDiscussId() {
        return discussId;
    }

    public void setDiscussId(long discussId) {
        this.discussId = discussId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDailyId() {
        return dailyId;
    }

    public void setDailyId(long dailyId) {
        this.dailyId = dailyId;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getReply() {
        return reply;
    }


    public long getAttachmentDiscussId() {
        return attachmentDiscussId;
    }

    public void setAttachmentDiscussId(long attachmentDiscussId) {
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "DailyDiscuss{" +
                "discussId=" + discussId +
                ", username='" + username + '\'' +
                ", dailyId=" + dailyId +
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
