package com.tanx.blogs.service.archives;

import com.tanx.blogs.dao.archives.ArchivesMapping;
import com.tanx.blogs.pojo.model.Archives;
import com.tanx.blogs.utils.IsEmptyUtil;
import com.tanx.blogs.utils.VariableUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public class ArchivesServiceImpl implements ArchivesService {
    private ArchivesMapping mapping;

    @Autowired
    public void setMapping(ArchivesMapping mapping) {
        this.mapping = mapping;
    }


    @Override
    public int addArchives(Archives archives) {
        return this.mapping.addArchives(archives);
    }


    @Override
    public Archives selectArchivesTitle(String title) {
        return this.mapping.selectArchivesTitle(title);
    }


    @Override
    public List<Archives> selectArchivesNews() {
        return this.mapping.selectArchivesNews();
    }


    @Override
    public List<Archives> selectArchivesHots() {
        return this.mapping.selectArchivesHots();
    }


    @Override
    public List<Archives> selectArchivesLimit(int currentPage) {
        return this.mapping.selectArchivesLimit(currentPage * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE, VariableUtils.SHWODPAGE);
    }


    @Override
    public List<Archives> queryArchivesTypeName(int currentPage,int showPage) {
        return this.mapping.queryArchivesTypeName(currentPage * showPage - showPage, showPage);
    }


    @Override
    public Archives queryArchivesId(String archivesId) {
        if(!IsEmptyUtil.isNumber(archivesId)){
            return null;
        }
        return this.mapping.queryArchivesId(Long.parseLong(archivesId));
    }


    @Override
    public int queryArchivesCount() {
        return this.mapping.queryArchivesCount();
    }


    @Override
    public boolean updateArchivesGoodNumber(long archivesId, int newGoodNumber) {
        return this.mapping.updateArchivesGoodNumber(archivesId, newGoodNumber)==0;
    }


    @Override
    public int updateArchivesReadNumber(long archivesId, int newReadNumber) {
        return this.mapping.updateArchivesReadNumber(archivesId, newReadNumber);
    }

    @Override
    public int updateArchivesDiscussNumber(String paramLong, int paramInt) {
        if(!IsEmptyUtil.isNumber(paramLong)){
            return 0;
        }
        return mapping.updateArchivesDiscussNumber(Long.parseLong(paramLong),paramInt);
    }


    @Override
    public List<Archives> queryTypeArchives(long typeId, int currentPage) {
        return this.mapping.queryTypeArchives(typeId, currentPage * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE, VariableUtils.SHWODPAGE);
    }


    @Override
    public int queryTypeArchivesCount(long typeId) {
        return this.mapping.queryTypeArchivesCount(typeId);
    }

    @Override
    public List<Archives> queryArchivesByTagId(long tagId, int current) {
        return this.mapping.queryArchivesByTagId(tagId, current * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE, VariableUtils.SHWODPAGE);
    }


    @Override
    public int queryArchivesByTagIdCount(long tagId) {
        return this.mapping.queryArchivesByTagIdCount(tagId);
    }


    @Override
    public int queryArchivesByIdOrUploadTimeCount(long archivesId, String uploadTime) {
        return this.mapping.queryArchivesByIdOrUploadTimeCount(archivesId, uploadTime);
    }


    @Override
    public List<Archives> queryArchivesByIdOrUploadTime(long archivesId, String uploadTime, int current) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("archivesId", archivesId);
        map.put("uploadTime", "%" + uploadTime + "%");
        map.put("current", current * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE);
        map.put("display", VariableUtils.SHWODPAGE);
        return this.mapping.queryArchivesByIdOrUploadTime(map);
    }


    @Override
    public int queryArchivesByTitleAndUploadTimeCount(String title, String uploadTime) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("title", "%" + title + "%");
        map.put("uploadTime", "%" + uploadTime + "%");
        return this.mapping.queryArchivesByTitleAndUploadTimeCount(map);
    }


    @Override
    public List<Archives> queryArchivesByTitleAndUploadTime(String title, String uploadTime, int current) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("title", "%" + title + "%");
        map.put("uploadTime", "%" + uploadTime + "%");
        map.put("current", current * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE);
        map.put("display", VariableUtils.SHWODPAGE);
        return this.mapping.queryArchivesByTitleAndUploadTime(map);
    }

    @Override
    public Archives queryArchivesByIdAndTag(String archivesId) {
        if(!IsEmptyUtil.isNumber(archivesId)){
            return null;
        }
        return mapping.queryArchivesByIdAndTag(Long.parseLong(archivesId));
    }

    @Override
    public int deleteArchives(String archivesId) {
        if(!IsEmptyUtil.isNumber(archivesId)){
            return 0;
        }
        return mapping.deleteArchives(Long.parseLong(archivesId));
    }

    @Override
    public List<Archives> queryArchivesByTitleAndTypeId(String title,String typeId) {
        List<Archives> list = new ArrayList<>(8);
        if(IsEmptyUtil.isNumber(typeId)){
            return list;
        }
        if(IsEmptyUtil.isStringEmpty(title)){
            return list;
        }
        list = mapping.queryArchivesByTitleAndTypeId(title,Long.parseLong(typeId));
        return list;
    }

    @Override
    public List<Archives> queryArchivesTypeNameAsc(int paramInt1) {
        return mapping.queryArchivesTypeNameASC((paramInt1*VariableUtils.SHWODPAGE)-VariableUtils.SHWODPAGE,VariableUtils.SHWODPAGE);
    }

    @Override
    public int deleteArchivesById(Long archivesId) {
        return mapping.deleteArchivesById(archivesId);
    }

    @Override
    public int updateArchivesInfo(String title, String subTitle, Long archivesId) {
        return mapping.updateArchivesInfo(title,subTitle,archivesId);
    }

    @Override
    public List<Archives> queryArchivesSearch(Long archivesId,String title,String uploadTime,int current) {
        Map<String,Object> map = new HashMap<>(10);
        map.put("archivesId",archivesId);
        map.put("title",title==null?null:"%"+title+"%");
        map.put("uploadTime",uploadTime==null?null:"%"+uploadTime+"%");
        map.put("current",current * VariableUtils.SHWODPAGE - VariableUtils.SHWODPAGE);
        map.put("showPage",VariableUtils.SHWODPAGE);
        return mapping.queryArchivesSearch(map);
    }

    @Override
    public int queryArchivesSearchCount(Long archivesId, String title, String uploadTime) {
        Map<String,Object> map = new HashMap<>(10);
        map.put("archivesId",archivesId);
        map.put("title","%"+title+"%");
        map.put("uploadTime","%"+uploadTime+"%");
        return mapping.queryArchivesSearchCount(map);
    }
}