package com.tanx.blogs.service.archivesandvisitor;


import com.tanx.blogs.dao.archivesandvisitor.ArchivesAndVisitorMapping;
import com.tanx.blogs.pojo.model.ArchivesAndVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public class ArchivesAndVisitorServiceImpl implements ArchivesAndVisitorService {
    private ArchivesAndVisitorMapping mapping;

    @Autowired
    public void setMapping(ArchivesAndVisitorMapping mapping) {
        this.mapping = mapping;
    }


    @Override
    public ArchivesAndVisitor queryArchivesAndVisitor(ArchivesAndVisitor archivesAndVisitor) {
        return this.mapping.queryArchivesAndVisitor(archivesAndVisitor);
    }


    @Override
    public int addArchivesAndVisitor(ArchivesAndVisitor archivesAndVisitor) {
        return this.mapping.addArchivesAndVisitor(archivesAndVisitor);
    }

    @Override
    public int removeArchivesAndVisitor(long archivesAndVisitorId) {
        return mapping.removeArchivesAndVisitor(archivesAndVisitorId);
    }
}
