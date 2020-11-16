package com.tanx.blogs.dao.archivesandvisitor;

import com.tanx.blogs.pojo.model.ArchivesAndVisitor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tanxiang
 */
@Repository
@MapperScan
public interface ArchivesAndVisitorMapping {

    /**
     * 查询文章链接访客信息
     * @param paramArchivesAndVisitor 文章链接访客信息
     * @return 返回数据
     */
    @Select({"select * from `archivesandvisitor` where archivesId=#{archivesId} and visitorId=#{visitorId}"})
    ArchivesAndVisitor queryArchivesAndVisitor(ArchivesAndVisitor paramArchivesAndVisitor);

    /**
     * 增加文章链接访客数据
     * @param paramArchivesAndVisitor 文章链接访客数据
     * @return 返回结果
     */
    @Insert({"insert into`archivesandvisitor`(`archivesId`,`visitorId`) values(#{archivesId},#{visitorId})"})
    int addArchivesAndVisitor(ArchivesAndVisitor paramArchivesAndVisitor);

    /**
     * 删除文章链接访客数据
     * @param archivesAndVisitorId 文章链接访客ID
     * @return 返回删除结果
     */
    @Delete("delete from `archivesandvisitor` where archivesAndVisitorId = #{archivesAndVisitorId}")
    int removeArchivesAndVisitor(@Param("archivesAndVisitorId") long archivesAndVisitorId);
}
