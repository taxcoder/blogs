package com.tanx.blogs.pojo.model;

public class Type {
    private long typeId;
    private String typeName;

    public Type() {}

    public Type(long typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Type{typeId=" + this.typeId + ", typeName='" + this.typeName + '\'' + '}';
    }
}
