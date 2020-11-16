package com.tanx.blogs.dao.tag;

import com.tanx.blogs.pojo.model.Tag;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tan
 */
@Repository
@MapperScan
public interface TagMapping {
    /**
     * 增加标签
     * @param paramString 标签名
     * @return 影响的行数
     */
    @Insert({"insert into `tag`(`tagName`) values(#{tagName})"})
    int addTag(@Param("tagName") String paramString);

    /**
     * 删除标签
     * @param paramLong 标签ID
     * @return 影响的行数
     */
    @Delete({"delete from `tag` where `tagId` = #{tagId} limit 1"})
    int deleteTag(@Param("tagId") long paramLong);

    /**
     * 修改标签名
     * @param paramLong 标签ID
     * @param paramString 新标签名
     * @return 影响的行数
     */
    @Update({"update `tag` set `tagName` = #{tagName} where `tagId` = #{tagId}"})
    int updateTag(@Param("tagId") long paramLong, @Param("tagName") String paramString);

    /**
     * 查询标签
     * @param paramString 标签名
     * @return 返回标签
     */
    @Select({"select * from `tag` where `tagName` = #{tagName}"})
    Tag selectTag(@Param("tagName") String paramString);

    /**
     * 查询所有的标签
     * @return 返回数据
     */
    @Select({"select * from `tag`"})
    List<Tag> selectTags();

    /**
     * 查询标签的总数
     * @return 返回标签的总数
     */
    @Select({"select count(1) from `tag`"})
    int selectTagsCount();

    /**
     * 查询指定文章的标签
     * @return 返回数据
     */
    @Select({"select distinct t.* from `tag` as t ,`archivesandtag` as at where  at.tagId = t.tagId"})
    List<Tag> queryIsArchivesByTags();
}
