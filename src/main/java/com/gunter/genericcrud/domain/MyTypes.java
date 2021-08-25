package com.gunter.genericcrud.domain;

public enum MyTypes {
    HASHMAP("HASHMAP"),
    LIST("LIST"),
    STRING("STRING"),
    INTEGER("INTEGER"),
    NUMBER("NUMBER"),
    BOOLEAN("BOOLEAN");

    public final String typeName;
    MyTypes(String typeName) {
        this.typeName = typeName;
    }
}
