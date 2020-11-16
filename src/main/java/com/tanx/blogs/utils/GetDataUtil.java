package com.tanx.blogs.utils;

import com.tanx.blogs.pojo.model.Archives;
import com.tanx.blogs.pojo.model.Tag;
import com.tanx.blogs.pojo.model.Type;
import java.util.List;

/**
 * @author tan
 */
public class GetDataUtil
{
    private List<Archives> archivesNews;
    private List<Tag> tags;
    private List<Type> types;
    private int archivesAllNumber;
    private int dailyCount;
    private int visitorCount;
    private String bulletinBoard;

    public GetDataUtil() {}

    public GetDataUtil(List<Archives> archivesNews,  List<Tag> tags, List<Type> types, int archivesAllNumber) {
        this.archivesNews = archivesNews;
        this.tags = tags;
        this.types = types;
        this.archivesAllNumber = archivesAllNumber;
    }

    public GetDataUtil(List<Archives> archivesNews,  List<Tag> tags, List<Type> types, int archivesAllNumber, int dailyCount) {
        this.archivesNews = archivesNews;
        this.tags = tags;
        this.types = types;
        this.archivesAllNumber = archivesAllNumber;
        this.dailyCount = dailyCount;
    }

    public GetDataUtil(List<Archives> archivesNews,  List<Tag> tags, List<Type> types, int archivesAllNumber, int dailyCount, int visitorCount) {
        this.archivesNews = archivesNews;
        this.tags = tags;
        this.types = types;
        this.archivesAllNumber = archivesAllNumber;
        this.dailyCount = dailyCount;
        this.visitorCount = visitorCount;
    }

    public GetDataUtil(List<Archives> archivesNews,  List<Tag> tags, List<Type> types, int archivesAllNumber, int dailyCount, int visitorCount, String bulletinBoard) {
        this.archivesNews = archivesNews;
        this.tags = tags;
        this.types = types;
        this.archivesAllNumber = archivesAllNumber;
        this.dailyCount = dailyCount;
        this.visitorCount = visitorCount;
        this.bulletinBoard = bulletinBoard;
    }


    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Type> getTypes() {
        return this.types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public int getArchivesAllNumber() {
        return this.archivesAllNumber;
    }

    public List<Archives> getArchivesNews() {
        return this.archivesNews;
    }

    public void setArchivesNews(List<Archives> archivesNews) {
        this.archivesNews = archivesNews;
    }

    public void setArchivesAllNumber(int archivesAllNumber) {
        this.archivesAllNumber = archivesAllNumber;
    }

    public int getDailyCount() {
        return this.dailyCount;
    }

    public void setDailyCount(int dailyCount) {
        this.dailyCount = dailyCount;
    }

    public int getVisitorCount() {
        return this.visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public String getBulletinBoard() {
        return this.bulletinBoard;
    }

    public void setBulletinBoard(String bulletinBoard) {
        this.bulletinBoard = bulletinBoard;
    }
}