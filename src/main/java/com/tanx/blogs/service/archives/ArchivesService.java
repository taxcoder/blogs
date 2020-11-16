package com.tanx.blogs.service.archives;

import com.tanx.blogs.pojo.model.Archives;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public interface ArchivesService {
    /**
     * 增加文章
     * @param paramArchives 文章信息
     * @return 返回结果
     */
    int addArchives(Archives paramArchives);

    /**
     * 查询文章标题
     * @param paramString 文章标题
     * @return 返回结果
     */
    Archives selectArchivesTitle(String paramString);

    /**
     * 查询最新文章
     * @return 返回最新文章
     */
    List<Archives> selectArchivesNews();

    /**
     * 查询最热文章
     * @return 返回最热文章
     */
    List<Archives> selectArchivesHots();

    /**
     * 文章分页
     * @param paramInt 当前页
     * @return 返回分页后的数据
     */
    List<Archives> selectArchivesLimit(int paramInt);

    /**
     * 查询文章的分类名
     * @param paramInt 当前页
     * @param showPage 每页显示数据
     * @return 返回数据
     */
    List<Archives> queryArchivesTypeName(int paramInt,int showPage);

    /**
     * 根据文章ID查询数据
     * @param paramLong 文章ID
     * @return 返回数据
     */
    Archives queryArchivesId(String paramLong);

    /**
     * 查询文章总数
     * @return 返回文章总数
     */
    int queryArchivesCount();

    /**
     * 修改文章的点赞数
     * @param paramLong 文章ID
     * @param paramInt 新的点赞数
     * @return 返回是否成功
     */
    boolean updateArchivesGoodNumber(long paramLong, int paramInt);

    /**
     * 修改文章的阅读数
     * @param paramLong 文章ID
     * @param paramInt 新的阅读数
     * @return 返回是否成功
     */
    int updateArchivesReadNumber(long paramLong, int paramInt);


    /**
     * 修改文章的阅读数
     * @param paramLong 文章ID
     * @param paramInt 新的评论数
     * @return 返回是否成功
     */
    int updateArchivesDiscussNumber(String paramLong, int paramInt);

    /**
     * 根据分类查询文章
     * @param paramLong 分类ID
     * @param paramInt 当前页
     * @return 返回分页后的数据
     */
    List<Archives> queryTypeArchives(long paramLong, int paramInt);

    /**
     * 根据分类ID查询文章总数
     * @param paramLong 分类ID
     * @return 返回总数
     */
    int queryTypeArchivesCount(long paramLong);

    /**
     * 根据标签ID查询文章
     * @param paramLong 标签ID
     * @param paramInt 当前页
     * @return 返回分页后的数据
     */
    List<Archives> queryArchivesByTagId(long paramLong, int paramInt);

    /**
     * 查询指定标签中文章的总数
     * @param paramLong 标签ID
     * @return 返回数据
     */
    int queryArchivesByTagIdCount(long paramLong);

    /**
     * 根据搜索框提供的信息查询文章（时间）
     * @param paramLong 搜索框内的数字
     * @param paramString 模糊查询的信息
     * @return 返回查询到的总数据
     */
    int queryArchivesByIdOrUploadTimeCount(long paramLong, String paramString);

    /**
     * 查询文章根据上传的时间
     * @param paramLong 转化为ID的数据
     * @param paramString 数据
     * @param paramInt 当前页
     * @return 返回分页后的数据
     */
    List<Archives> queryArchivesByIdOrUploadTime(long paramLong, String paramString, int paramInt);

    /**
     * 查询文章根据标题，获取总数
     * @param paramString1  数据
     * @param paramString2  数据
     * @return 返回总数
     */
    int queryArchivesByTitleAndUploadTimeCount(String paramString1, String paramString2);

    /**
     * 查询文章根据标题，获取分页后的数据
     * @param paramString1 数据
     * @param paramString2 数据
     * @param paramInt 当前页
     * @return 返回数据
     */
    List<Archives> queryArchivesByTitleAndUploadTime(String paramString1, String paramString2, int paramInt);

    /**
     * 根据文章ID查询指定的标签
     * @param archivesId 文章ID
     * @return 返回数据
     */
    Archives queryArchivesByIdAndTag(String archivesId);

    /**
     * 删除文章
     * @param archivesId 文章ID
     * @return 返回结果
     */
    int deleteArchives(String archivesId);

    /**
     * 获取文件
     * @param title 文章标题
     * @param typeId 分类ID
     * @return 返回文件
     */
    List<Archives> queryArchivesByTitleAndTypeId(String title,String typeId);


    /**
     * 包含分类名的文章进行分页
     * @param paramInt1 当前页
     * @return 返回数据
     */
    List<Archives> queryArchivesTypeNameAsc(int paramInt1);

    /**
     * 删除文章
     * @param archivesId 文章ID
     * @return 返回结果
     */
    int deleteArchivesById(Long archivesId);

    /**
     * 修改文章标题和说明
     * @param archivesId 文章ID
     * @param subTitle 说明
     * @param title 标题
     * @return 返回结果
     */
    int updateArchivesInfo(String title,String subTitle,Long archivesId);

    /**
     * 查询数据
     * @param archivesId 文章ID
     * @param title 文章标题
     * @param uploadTime 文章上传时间
     * @param current 当前页
     * @return 返回数据
     */
    List<Archives> queryArchivesSearch(Long archivesId,String title,String uploadTime,int current);

    /**
     * 查询搜索文章的总数
     * @param archivesId 文章ID
     * @param title 文章标题
     * @param uploadTime 文章上传时间
     * @return 返回总数
     */
    int queryArchivesSearchCount(Long archivesId,String title,String uploadTime);
}
