package com.tanx.blogs.service.type;

import com.tanx.blogs.dao.type.TypeMapping;
import com.tanx.blogs.pojo.model.Type;
import java.util.List;

import com.tanx.blogs.utils.IsEmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl
        implements TypeService
{
    private TypeMapping mapping;

    @Autowired
    public void setMapping(TypeMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int addType(String typeName) {
        return this.mapping.addType(typeName);
    }

    @Override
    public int deleteType(long typeId) {
        return this.mapping.deleteType(typeId);
    }

    @Override
    public int updateType(long typeId, String typeName) {
        return this.mapping.updateType(typeId, typeName);
    }

    @Override
    public Type selectTypeId(long typeId) {
        return this.mapping.selectTypeId(typeId);
    }

    @Override
    public List<Type> selectTypes() {
        return this.mapping.selectTypes();
    }

    @Override
    public List<Type> queryIsArchivesTypes() {
        return this.mapping.queryIsArchivesTypes();
    }

    @Override
    public Type queryTypeByName(String name) {
        return this.mapping.queryTypeByName(name);
    }

    @Override
    public int selectTypesCount() {
        return this.mapping.selectTypesCount();
    }
}
