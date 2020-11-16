package com.tanx.blogs.dao.archives;

import com.tanx.blogs.pojo.model.Archives;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tanxiang
 */
@Repository
@MapperScan
public interface ArchivesMapping {
    /**
     * 增加文章
     * @param paramArchives 文章信息
     * @return 返回结果
     */
    @Insert({"insert into `archives`(`title`,`subtitle`,`typeId`,`uploadTime`,`address`) values(#{title},#{subtitle},#{typeId},#{uploadTime},#{address})"})
    int addArchives(Archives paramArchives);

    /**
     * 根据标题查询文章
     * @param paramString 文章标题
     * @return 返回数据
     */
    @Select({"select * from `archives` where title = #{title}"})
    Archives selectArchivesTitle(@Param("title") String paramString);

    /**
     * 文章根据上传时间进行分页（最新文章）
     * @return 返回数据
     */
    @Select({"select * from `archives` order by `uploadTime` desc limit 0,5"})
    List<Archives> selectArchivesNews();

    /**
     * 文章根据上传时间进行分页（最热文章）
     * @return 返回数据
     */
    @Select({"select * from `archives` order by readNumber desc limit 0,5"})
    List<Archives> selectArchivesHots();

    /**
     * 文章指定页分页
     * @param paramInt1 当前页
     * @param paramInt2 每页显示数据
     * @return 返回数据
     */
    @Select({"select * from `archives` order by uploadTime desc limit #{currentPage},#{showData}"})
    List<Archives> selectArchivesLimit(@Param("currentPage") int paramInt1, @Param("showData") int paramInt2);

    /**
     * 包含分类名的文章进行分页
     * @param paramInt1 当前页
     * @param paramInt2 每页显示数据
     * @return 返回数据
     */
    @Select({"select a.*,t.typeName as archivesTypeName from `archives` as a,`type` as t where a.typeId = t.typeId order by uploadTime desc limit #{currentPage},#{showData}"})
    List<Archives> queryArchivesTypeName(@Param("currentPage") int paramInt1, @Param("showData") int paramInt2);


    /**
     * 包含分类名的文章进行分页
     * @param paramInt1 当前页
     * @param paramInt2 每页显示数据
     * @return 返回数据
     */
    @Select({"select a.*,t.typeName as archivesTypeName from `archives` as a,`type` as t where a.typeId = t.typeId order by uploadTime asc limit #{currentPage},#{showData}"})
    List<Archives> queryArchivesTypeNameASC(@Param("currentPage") int paramInt1, @Param("showData") int paramInt2);
    /**
     * 根据文章ID查询文章
     * @param paramLong 文章ID
     * @return 返回数据
     */
    @Select({"select * from `archives` where archivesId = #{archivesId}"})
    Archives queryArchivesId(@Param("archivesId") long paramLong);

    /**
     * 查询文章总数
     * @return 返回文章总数
     */
    @Select({"select count(1) from `archives`"})
    int queryArchivesCount();

    /**
     * 修改文章点赞数据
     * @param paramLong 新的点赞数
     * @param paramInt 文章ID
     * @return 返回结果
     */
    @Update({"update `archives` set goodNumber = #{goodNumber} where `archivesId` = #{archivesId}"})
    int updateArchivesGoodNumber(@Param("archivesId") long paramLong, @Param("goodNumber") int paramInt);

    /**
     * 修改文章阅读数据
     * @param paramLong 新的阅读数
     * @param paramInt 文章ID
     * @return 返回结果
     */
    @Update({"update `archives` set readNumber = #{readNumber} where `archivesId` = #{archivesId}"})
    int updateArchivesReadNumber(@Param("archivesId") long paramLong, @Param("readNumber") int paramInt);

    /**
     * 根据分类ID查询文章并分页
     * @param paramLong 分类ID
     * @param paramInt1 当前页
     * @param paramInt2 每页显示数据
     * @return 返回数据
     */
    @Select({"select * from `archives` where typeId = #{typeId} order by `uploadTime` desc limit #{currentPage},#{showPageNumber}"})
    List<Archives> queryTypeArchives(@Param("typeId") long paramLong, @Param("currentPage") int paramInt1, @Param("showPageNumber") int paramInt2);

    /**
     * 查询包含指定分类ID的文章数
     * @param paramLong 分类ID
     * @return 返回数据
     */
    @Select({"select count(1) from `archives` where typeId = #{typeId}"})
    int queryTypeArchivesCount(@Param("typeId") long paramLong);

    /**
     * 查询文章根据标签ID
     * @param paramLong 标签ID
     * @param paramInt1 当前页
     * @param paramInt2 每页显示数据
     * @return 返回分页后的数据
     */
    @Select({"select a.archivesId,a.title,a.subtitle,a.goodNumber,a.readNumber,a.uploadTime,t.* from `archives` as a ,`tag` as t ,`archivesandtag` as at where at.archivesId = a.archivesId and at.tagId = t.tagId and t.tagId = #{tagId} order by a.uploadTime desc limit #{current},#{display}"})
    List<Archives> queryArchivesByTagId(@Param("tagId") long paramLong, @Param("current") int paramInt1, @Param("display") int paramInt2);

    /**
     * 查询文章包含标签的个数
     * @param paramLong 标签ID
     * @return 返回个数
     */
    @Select({"select count(1) from `archives` as a ,`tag` as t ,`archivesandtag` as at where at.archivesId = a.archivesId and at.tagId = t.tagId and t.tagId = #{tagId}"})
    int queryArchivesByTagIdCount(@Param("tagId") long paramLong);

    /**
     * 根据ID查询文章并且在指定上传时间内
     * @param paramLong 文章ID
     * @param paramString 上传时间
     * @return 返回个数
     */
    int queryArchivesByIdOrUploadTimeCount(@Param("archivesId") long paramLong, @Param("uploadTime") String paramString);

    /**
     * 根据ID查询文章并且在指时间内
     * @param paramMap map集合
     * @return 返回查询到的数据
     */
    List<Archives> queryArchivesByIdOrUploadTime(Map<String, Object> paramMap);

    /**
     * 根据文章标题查询文章并且在根据上传时间的总数
     * @param paramMap map集合
     * @return 返回个数
     */
    int queryArchivesByTitleAndUploadTimeCount(Map<String, Object> paramMap);

    /**
     * 根据文章标题查询文章并且在指定上传时间内
     * @param paramMap map集合
     * @return 返回数据
     */
    List<Archives> queryArchivesByTitleAndUploadTime(Map<String, Object> paramMap);

    /**
     * 根据文章ID查询指定的标签
     * @param archivesId 文章ID
     * @return 返回数据
     */
    Archives queryArchivesByIdAndTag(long archivesId);

    /**
     * 修改文章的阅读数
     * @param paramLong 文章ID
     * @param paramInt 新的评论数
     * @return 返回是否成功
     */
    @Update({"update `archives` set discussNumber = #{discussNumber} where `archivesId` = #{archivesId}"})
    int updateArchivesDiscussNumber(@Param("archivesId") Long paramLong,@Param("discussNumber") int paramInt);

    /**
     * 删除文章根据文章ID
     * @param parseLong 文章ID
     * @return 返回结果
     */
    @Delete("delete form `archives` where archivesId = #{archivesId}")
    int deleteArchives(@Param("archivesId") long parseLong);

    /**
     * 查询文章
     * @param title 文章标题
     * @param parseLong 文章分类ID
     * @return 返回结果
     */
    @Select("select * from `archives` where title = #{title} and typeId = #{typeId}")
    List<Archives> queryArchivesByTitleAndTypeId(@Param("title") String title,@Param("typeId") long parseLong);

    /**
     * 删除文章
     * @param archivesId 文章ID
     * @return 返回结果
     */
    @Delete("delete from `archives` where `archivesId` = #{archivesId}")
    int deleteArchivesById(@Param("archivesId")Long archivesId);

    /**
     * 修改文章标题和说明
     * @param archivesId 文章ID
     * @param subTitle 说明
     * @param title 标题
     * @return 返回结果
     */
    @Update("update `archives` set `title` = #{title},subtitle = #{subtitle} where `archivesId` = #{archivesId}")
    int updateArchivesInfo(@Param("title") String title,@Param("subtitle") String subTitle,@Param("archivesId")Long archivesId);

    /**
     * 查询数据
     * @param map map集合
     * @return 返回数据
     */
    List<Archives> queryArchivesSearch(Map<String,Object> map);

    /**
     * 查询文章总数
     * @param map map集合
     * @return 返回总数
     */
    int queryArchivesSearchCount(Map<String, Object> map);
}
