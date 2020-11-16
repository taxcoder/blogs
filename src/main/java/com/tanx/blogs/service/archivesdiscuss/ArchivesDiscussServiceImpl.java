package com.tanx.blogs.service.archivesdiscuss;

import com.tanx.blogs.dao.archivesdiscuss.ArchivesDiscussMapping;
import com.tanx.blogs.pojo.model.ArchivesDiscuss;
import com.tanx.blogs.pojo.model.DailyDiscuss;
import com.tanx.blogs.utils.IsEmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/24 上午10:08
 */
@Service
public class ArchivesDiscussServiceImpl implements ArchivesDiscussService{

    private ArchivesDiscussMapping mapping;

    @Autowired
    public void setMapping(ArchivesDiscussMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int addDiscuss(ArchivesDiscuss discuss) {
        return mapping.addDiscuss(discuss);
    }

    @Override
    public List<ArchivesDiscuss> queryDiscussByArchivesId(String archivesId) {
        List<ArchivesDiscuss> list = new ArrayList<>();
        if(!IsEmptyUtil.isNumber(archivesId)){
            return list;
        }
        list = mapping.queryDiscussByArchivesId(Long.parseLong(archivesId));
        return list;
    }

    @Override
    public int queryDiscussByArchivesIdAndReply(String archivesId, String reply) {
        if(!IsEmptyUtil.isNumber(archivesId)){
            return 0;
        }
        return mapping.queryDiscussByArchivesIdAndReply(Long.parseLong(archivesId),reply);
    }

    @Override
    public List<ArchivesDiscuss> queryDiscuss() {
        return mapping.queryDiscuss();
    }
}
