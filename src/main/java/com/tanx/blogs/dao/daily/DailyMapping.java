package com.tanx.blogs.dao.daily;

import com.tanx.blogs.pojo.model.Daily;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tanxiang
 */
@Repository
@MapperScan
public interface DailyMapping {

    /**
     * 增加日记
     * @param daily 日记的信息
     * @return 返回增加结果
     */
    @Insert({"insert into `daily`(`content`,`uploadTime`) values(#{content},#{uploadTime})"})
    int addDaily(Daily daily);

    /**
     * 查询日记
     * @param paramInt1 当前页
     * @param paramInt2 每页显示数据
     * @return 返回指定页数的数据
     */
    @Select({"select * from `daily` order by `uploadTime` desc limit #{currentPage},#{showPage}"})
    List<Daily> queryDaily(@Param("currentPage") int paramInt1, @Param("showPage") int paramInt2);

    /**
     * 查询日记的总数
     * @return 返回总数
     */
    @Select({"select count(1) from `daily`"})
    int dailyCount();

    /**
     * 根据ID查询日记信息
     * @param paramLong 日记ID
     * @return 返回日记信息
     */
    @Select({"select * from `daily` where dailyId=#{dailyId}"})
    Daily queryDailyById(long paramLong);

    /**
     * 增加日记的点赞数
     * @param paramInt 点赞数
     * @param paramLong 日记ID
     * @return 是否成功
     */
    @Update({"update `daily` set goodNumber=#{goodNumber} where dailyId=#{dailyId}"})
    int addDailyByGoodNumber(@Param("goodNumber") int paramInt, @Param("dailyId") long paramLong);

    /**
     * 增加日记的评论数
     * @param paramInt 评论数
     * @param paramLong 日记ID
     * @return 是否成功
     */
    @Update({"update `daily` set discussNumber=#{discussNumber} where dailyId=#{dailyId}"})
    int addDailyByDiscussNumber(@Param("discussNumber") int paramInt, @Param("dailyId") long paramLong);
}
