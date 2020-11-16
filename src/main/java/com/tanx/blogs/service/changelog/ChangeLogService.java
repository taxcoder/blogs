package com.tanx.blogs.service.changelog;

import com.tanx.blogs.pojo.model.ChangeLog;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ChangeLogService {
    int addChangeLog(ChangeLog paramChangeLog);

    List<ChangeLog> queryChangeLog(int paramInt);

    int removeChangeLog(long paramLong);

    int changeLogCount();
}
