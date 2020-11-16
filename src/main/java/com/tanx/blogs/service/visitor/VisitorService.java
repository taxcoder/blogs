package com.tanx.blogs.service.visitor;

import com.tanx.blogs.pojo.model.Visitor;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public interface VisitorService {
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
    List<Visitor> queryVisitorIp(String paramString);
}
