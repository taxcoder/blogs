package com.tanx.blogs.service.tag;

import com.tanx.blogs.pojo.model.Tag;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
    int addTag(String paramString);

    int deleteTag(long paramLong);

    int updateTag(long paramLong, String paramString);

    Tag selectTag(String paramString);

    int selectTagsCount();

    List<Tag> selectTags();

    List<Tag> queryIsArchivesByTags();
}
