package com.tanx.blogs.service.archivesandtag;

import com.tanx.blogs.pojo.model.ArchivesAndTag;
import org.springframework.stereotype.Service;

@Service
public interface ArchivesAndTagService {
    /**
     * 增加文章与标签的链接
     * @param paramArchivesAndTag 文章与标签的链接的信息
     * @return 返回数据
     */
    boolean addArchivesAndTag(ArchivesAndTag paramArchivesAndTag);
}
