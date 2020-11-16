package com.tanx.blogs.service.archivesandtag;

import com.tanx.blogs.dao.archivesandtag.ArchivesAndTagMapping;
import com.tanx.blogs.pojo.model.ArchivesAndTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchivesAndTagServiceImpl
        implements ArchivesAndTagService
{
    private ArchivesAndTagMapping mapping;

    @Autowired
    public void setMapping(ArchivesAndTagMapping mapping) {
        this.mapping = mapping;
    }


    @Override
    public boolean addArchivesAndTag(ArchivesAndTag archivesAndTag) {
        for (Long tag : archivesAndTag.getTagId()) {
            if (mapping.addArchivesAndTag(archivesAndTag.getArchivesId(), tag) == 0) {
                return false;
            }
        }
        return true;
    }
}
