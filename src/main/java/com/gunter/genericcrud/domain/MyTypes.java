package com.gunter.genericcrud.domain;

public enum MyTypes {
    HASHMAP("HASHMAP"),
    LIST("LIST"),
    STRING("STRING"),
    NUMBER("NUMBER"),
    BOOLEAN("BOOLEAN");
    //TODO: adicionar tipo datas

    public final String typeName;
    MyTypes(String typeName) {
        this.typeName = typeName;
    }
}
