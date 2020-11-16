package com.tanx.blogs.dao.dailyandvisitor;


import com.tanx.blogs.pojo.model.DailyAndVisitor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author tanxiang
 */
@MapperScan
@Repository
public interface DailyAndVisitorMapping {

    /**
     * 根据访客IP查询日记是否存在
     * @param paramDailyAndVisitor 日记链接访客
     * @return 返回数据
     */
    @Select({"select * from `dailyandvisitor` where dailyId=#{dailyId} and visitorId=#{visitorId}"})
    DailyAndVisitor queryDailyAndVisitor(DailyAndVisitor paramDailyAndVisitor);

    /**
     * 增加访客访问记录
     * @param paramDailyAndVisitor 访客信息
     * @return 是否成功
     */
    @Insert({"insert into`dailyandvisitor`(`dailyId`,`visitorId`) values(#{dailyId},#{visitorId})"})
    int addDailyAndVisitor(DailyAndVisitor paramDailyAndVisitor);
}
