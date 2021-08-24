package com.gunter.genericcrud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MyField {
    private String name;
    private String type;
    private boolean required;
    private String description = "";
}
