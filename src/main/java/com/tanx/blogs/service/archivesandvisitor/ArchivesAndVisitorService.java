package com.tanx.blogs.service.archivesandvisitor;


import com.tanx.blogs.pojo.model.ArchivesAndVisitor;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public interface ArchivesAndVisitorService {
    /**
     * 查询文章链接访客信息
     * @param paramArchivesAndVisitor 文章链接访客信息
     * @return 返回数据
     */
    ArchivesAndVisitor queryArchivesAndVisitor(ArchivesAndVisitor paramArchivesAndVisitor);

    /**
     * 增加文章链接访客数据
     * @param paramArchivesAndVisitor 文章链接访客数据
     * @return 返回结果
     */
    int addArchivesAndVisitor(ArchivesAndVisitor paramArchivesAndVisitor);

    /**
     * 删除文章链接访客数据
     * @param archivesAndVisitorId 文章链接访客ID
     * @return 返回删除结果
     */
    int removeArchivesAndVisitor(long archivesAndVisitorId);
}
