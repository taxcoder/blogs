package com.tanx.blogs.service.visitor;


import com.tanx.blogs.dao.visitor.VisitorMapping;
import com.tanx.blogs.pojo.model.Visitor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public class VisitorServiceImpl implements VisitorService {
    private VisitorMapping mapping;

    @Autowired
    public void setMapping(VisitorMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int addVisitor(Visitor visitor) {
        return this.mapping.addVisitor(visitor);
    }

    @Override
    public int visitorCount() {
        return this.mapping.visitorCount();
    }

    @Override
    public Visitor queryVisitorInfo(Visitor visitor) {
        return this.mapping.queryVisitorInfo(visitor);
    }


    @Override
    public List<Visitor> queryVisitorIp(String ip) {
        return this.mapping.queryVisitorIp(ip);
    }
}
