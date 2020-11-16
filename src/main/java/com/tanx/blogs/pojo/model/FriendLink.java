package com.tanx.blogs.pojo.model;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/25 下午7:00
 */
public class FriendLink {
    private long friendLinkId;
    private String friendName;
    private String friendBrief;
    private String friendAddress;
    private String friendImage;
    private String email;
    private String active;

    public FriendLink() {

    }

    public FriendLink(String friendName, String friendBrief, String friendAddress, String friendImage) {
        this.friendName = friendName;
        this.friendBrief = friendBrief;
        this.friendAddress = friendAddress;
        this.friendImage = friendImage;
    }

    public FriendLink(String friendName, String friendBrief, String friendAddress, String friendImage, String email) {
        this.friendName = friendName;
        this.friendBrief = friendBrief;
        this.friendAddress = friendAddress;
        this.friendImage = friendImage;
        this.email = email;
    }

    public FriendLink(long friendLinkId, String friendName, String friendBrief, String friendAddress, String friendImage) {
        this.friendLinkId = friendLinkId;
        this.friendName = friendName;
        this.friendBrief = friendBrief;
        this.friendAddress = friendAddress;
        this.friendImage = friendImage;
    }

    public FriendLink(long friendLinkId, String friendName, String friendBrief, String friendAddress, String friendImage, String email) {
        this.friendLinkId = friendLinkId;
        this.friendName = friendName;
        this.friendBrief = friendBrief;
        this.friendAddress = friendAddress;
        this.friendImage = friendImage;
        this.email = email;
    }

    public FriendLink(long friendLinkId, String friendName, String friendBrief, String friendAddress, String friendImage, String email, String active) {
        this.friendLinkId = friendLinkId;
        this.friendName = friendName;
        this.friendBrief = friendBrief;
        this.friendAddress = friendAddress;
        this.friendImage = friendImage;
        this.email = email;
        this.active = active;
    }

    public long getFriendLinkId() {
        return friendLinkId;
    }

    public void setFriendLinkId(long friendLinkId) {
        this.friendLinkId = friendLinkId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendBrief() {
        return friendBrief;
    }

    public void setFriendBrief(String friendBrief) {
        this.friendBrief = friendBrief;
    }

    public String getFriendAddress() {
        return friendAddress;
    }

    public void setFriendAddress(String friendAddress) {
        this.friendAddress = friendAddress;
    }

    public String getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(String friendImage) {
        this.friendImage = friendImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "FriendLink{" +
                "friendLinkId=" + friendLinkId +
                ", friendName='" + friendName + '\'' +
                ", friendBrief='" + friendBrief + '\'' +
                ", friendAddress='" + friendAddress + '\'' +
                ", friendImage='" + friendImage + '\'' +
                ", email='" + email + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
