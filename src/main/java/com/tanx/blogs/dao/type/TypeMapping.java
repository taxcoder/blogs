package com.tanx.blogs.dao.type;

import com.tanx.blogs.pojo.model.Type;
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
public interface TypeMapping {

    /**
     * 增加分类
     * @param paramString 分类名
     * @return 影响的行数
     */
    @Insert({"insert into `type`(`typeName`) values(#{typeName})"})
    int addType(@Param("typeName") String paramString);

    /**
     * 删除分类
     * @param paramLong 分类ID
     * @return 返回影响行数
     */
    @Delete({"delete from `type` where `typeId` = #{typeId} limit 1"})
    int deleteType(@Param("typeId") long paramLong);

    /**
     * 修改分类
     * @param paramLong 分类ID
     * @param paramString 分类名
     * @return 影响的行数
     */
    @Update({"update `type` set `typeName` = #{typeName} where `typeId` = #{typeId}"})
    int updateType(@Param("typeId") long paramLong, @Param("typeName") String paramString);

    /**
     * 查询所有的分类
     * @return 返回数据
     */
    @Select({"select * from `type`"})
    List<Type> selectTypes();

    /**
     * 查询分类的总数
     * @return 返回数据
     */
    @Select({"select count(1) from `type`"})
    int selectTypesCount();

    /**
     * 查询分类ID
     * @param paramLong 分类ID
     * @return 返回数据
     */
    @Select({"select * from `type` where typeId = #{typeId}"})
    Type selectTypeId(@Param("typeId") long paramLong);

    /**
     * 查询文章对应的分类
     * @return 返回数据
     */
    @Select({"select distinct t.typeId,typeName from `type` as t,`archives` as  a where t.typeId = a.typeId"})
    List<Type> queryIsArchivesTypes();

    /**
     * 根据分类名查询数据
     * @param paramString 分类名
     * @return 返回数据
     */
    @Select({"select * from `type` where typeName = #{typeName}"})
    Type queryTypeByName(@Param("typeName") String paramString);
}
