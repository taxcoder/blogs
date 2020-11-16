package com.tanx.blogs.service.type;

import com.tanx.blogs.pojo.model.Type;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TypeService {
    int addType(String paramString);

    int deleteType(long paramLong);

    int updateType(long paramLong, String paramString);

    Type selectTypeId(long paramLong);

    List<Type> selectTypes();

    List<Type> queryIsArchivesTypes();

    Type queryTypeByName(String paramString);

    int selectTypesCount();
}
