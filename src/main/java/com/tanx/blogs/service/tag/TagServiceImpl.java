package com.tanx.blogs.service.tag;


import com.tanx.blogs.dao.tag.TagMapping;
import com.tanx.blogs.pojo.model.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tan
 */
@Service
public class TagServiceImpl implements TagService {
    private TagMapping mapping;

    @Autowired
    public void setMapping(TagMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int addTag(String tagName) {
        return this.mapping.addTag(tagName);
    }

    @Override
    public int deleteTag(long tagId) {
        return this.mapping.deleteTag(tagId);
    }

    @Override
    public int updateTag(long tagId, String tagName) {
        return this.mapping.updateTag(tagId, tagName);
    }


    @Override
    public Tag selectTag(String tagName) {
        return this.mapping.selectTag(tagName);
    }

    @Override
    public int selectTagsCount() {
        return this.mapping.selectTagsCount();
    }

    @Override
    public List<Tag> selectTags() {
        return this.mapping.selectTags();
    }

    @Override
    public List<Tag> queryIsArchivesByTags() {
        return this.mapping.queryIsArchivesByTags();
    }
}