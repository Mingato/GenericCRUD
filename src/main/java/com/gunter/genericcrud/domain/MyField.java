package com.gunter.genericcrud.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gunter.genericcrud.service.MyClassInstanced;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MyField {

    private String name;

    private String type;

    private boolean required = false;

    private String description = "";

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MyField> fields;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private MyField fieldTypeList;

    //if myClassReference non null and type is HASHMAP, so its a reference to another class
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String myClassNameReference;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String dateFormat;

    public List<MyField> getFields() {
        if(MyTypes.HASHMAP.typeName.equalsIgnoreCase(type) && myClassNameReference != null){
            return Objects.requireNonNull(MyClassInstanced.getMyClassByName(myClassNameReference)).getFields();
        }
        return fields;
    }
}
