package com.tanx.blogs.dao.changelog;

import com.tanx.blogs.pojo.model.ChangeLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tan
 */
@MapperScan
@Repository
public interface ChangeLogMapping {

    /**
     * 增加日志
     * @param paramChangeLog 日志信息
     * @return 影响的行数
     */
    @Insert({"insert into `changelog`(`content`,`uploadTime`) values(#{content},#{uploadTime})"})
    int addChangeLog(ChangeLog paramChangeLog);

    /**
     * 查询修改日志
     * @param paramInt1 当前页
     * @param paramInt2 显示的条数
     * @return 查询到的数据
     */
    @Select({"select * from `changelog` order by `uploadTime` desc limit #{current},#{displayData}"})
    List<ChangeLog> queryChangeLog(@Param("current") int paramInt1, @Param("displayData") int paramInt2);

    /**
     * 获取日志的总数
     * @return 日志总数
     */
    @Select({"select count(1) from `changelog`"})
    int changeLogCount();

    /**
     * 删除一个日志信息
     * @param paramLong 日志ID
     * @return 影响的行数
     */
    @Delete({"delete from `changelog` where changeLogId = #{changeLogId}"})
    int removeChangeLog(@Param("changeLogId") long paramLong);
}
