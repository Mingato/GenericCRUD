package com.gunter.genericcrud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MyObject {

    @Id
    @Indexed
    private String id;

    @Indexed
    @NonNull
    private String name;
    private MyMap<String, Object> myInstance = new MyMap();

    @JsonIgnore
    public MyMap<String, Object> getMyInstanceWithId(){
        MyMap<String, Object> myInstanceWithId = myInstance;
        myInstanceWithId.put("id", id);
        return myInstanceWithId;
    }
}
