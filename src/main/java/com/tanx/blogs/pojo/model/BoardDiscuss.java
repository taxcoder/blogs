package com.tanx.blogs.pojo.model;

import java.util.Date;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/26 上午9:01
 */
public class BoardDiscuss {
    private long boardDiscussId;
    private String username;
    private String websiteAddress;
    private String content;
    private Date writeTime;
    private String email;
    private String headImage;
    private String reply;
    private int grade;
    private long attachmentDiscussId;

    public BoardDiscuss() {

    }

    public BoardDiscuss(String username, String websiteAddress, String content, Date writeTime, String email, String headImage, String reply, int grade, long attachmentDiscussId) {
        this.username = username;
        this.websiteAddress = websiteAddress;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public BoardDiscuss(long boardDiscussId, String username, String websiteAddress, String content, Date writeTime, String email, String headImage, String reply, int grade, long attachmentDiscussId) {
        this.boardDiscussId = boardDiscussId;
        this.username = username;
        this.websiteAddress = websiteAddress;
        this.content = content;
        this.writeTime = writeTime;
        this.email = email;
        this.headImage = headImage;
        this.reply = reply;
        this.grade = grade;
        this.attachmentDiscussId = attachmentDiscussId;
    }

    public long getBoardDiscussId() {
        return boardDiscussId;
    }

    public void setBoardDiscussId(long boardDiscussId) {
        this.boardDiscussId = boardDiscussId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
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
        return "BoardDiscuss{" +
                "boardDiscussId=" + boardDiscussId +
                ", username='" + username + '\'' +
                ", websiteAddress='" + websiteAddress + '\'' +
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
