package com.tanx.blogs.dao.visitor;

import com.tanx.blogs.pojo.model.Visitor;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@Repository
@MapperScan
public interface VisitorMapping {
    /**
     * 增加访客的信息
     * @param paramVisitor 访客信息
     * @return 返回结果
     */
    int addVisitor(Visitor paramVisitor);

    /**
     * 获取访客总数
     * @return 返回访客总数
     */
    @Select({"select count(1) from `visitor`"})
    int visitorCount();

    /**
     * 查询当前访客的信息
     * @param paramVisitor 访客信息
     * @return 返回访客信息
     */
    Visitor queryVisitorInfo(Visitor paramVisitor);

    /**
     * 查询访客的IP
     * @param paramString 访客IP
     * @return 返回访客信息
     */
    @Select({"select * from `visitor` where ip = #{ip}"})
    List<Visitor> queryVisitorIp(@Param("ip") String paramString);
}
