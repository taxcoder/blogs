package com.tanx.blogs.service.changelog;


import com.tanx.blogs.dao.changelog.ChangeLogMapping;
import com.tanx.blogs.pojo.model.ChangeLog;
import com.tanx.blogs.utils.VariableUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeLogServiceImpl implements ChangeLogService {
    private ChangeLogMapping mapping;

    @Autowired
    public void setMapping(ChangeLogMapping mapping) {
        this.mapping = mapping;
    }


    @Override
    public int addChangeLog(ChangeLog log) {
        return this.mapping.addChangeLog(log);
    }

    @Override
    public List<ChangeLog> queryChangeLog(int current) {
        return this.mapping.queryChangeLog(current * VariableUtils.SHWODPAGE * 2 - VariableUtils.SHWODPAGE * 2, VariableUtils.SHWODPAGE * 2);
    }

    @Override
    public int removeChangeLog(long changeLogId) {
        return this.mapping.removeChangeLog(changeLogId);
    }

    @Override
    public int changeLogCount() {
        return this.mapping.changeLogCount();
    }
}
