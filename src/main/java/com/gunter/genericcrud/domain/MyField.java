package com.gunter.genericcrud.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MyField {
    private String name;
    private String type;
    private boolean required;
    private String description = "";

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MyField> fields;
}
