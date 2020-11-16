package com.tanx.blogs.dao.archivesandtag;


import com.tanx.blogs.pojo.model.Archives;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tanxiang
 */
@Repository
@MapperScan
public interface ArchivesAndTagMapping {
    /**
     * 增加文章链接标签
     * @param paramLong1 文章ID
     * @param paramLong2 标签ID
     * @return 是否增加成功
     */
    @Insert({"insert into `archivesandtag`(`archivesId`,`tagId`) values(#{archivesId},#{tagId})"})
    int addArchivesAndTag(@Param("archivesId") long paramLong1, @Param("tagId") long paramLong2);
}
