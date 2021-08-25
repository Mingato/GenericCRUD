package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyTypes;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidateClassesService {

    public void validateTypesAndStructure(MyClass myClass) {
        validate(myClass.getFields(), myClass.getName());
    }

    private void validate(List<MyField> fields, String parentFieldName) {
        fields.forEach(myField ->
            validate(myField, parentFieldName)
        );
    }

    private void validate(MyField myField, String parentFieldName) {
        Assert.notNull(myField.getName(), "Field 'name' of " + parentFieldName + " can't be null!");
        parentFieldName = parentFieldName + "." + myField.getName();

        Assert.notNull(myField.getType(), "Field 'type' of " + parentFieldName + " can't be null!");
        final String myType = myField.getType().toUpperCase();

        if(MyTypes.HASHMAP.toString().equals(myType)){
            Assert.notEmpty(myField.getFields(), "the attribute 'fields' in " + parentFieldName + " can't be empty, because it's an HasMap type!");
            validate(myField.getFields(), parentFieldName);
        } else if(MyTypes.LIST.toString().equals(myType)){
            Assert.notNull(myField.getFieldTypeList(), "fieldTypeList in " + parentFieldName + " can't be null, because a list needs a listTypeField");

            validate(myField.getFieldTypeList(), parentFieldName);
        } else if( !(MyTypes.STRING.toString().equals(myType) ||
                    MyTypes.INTEGER.toString().equals(myType) ||
                    MyTypes.NUMBER.toString().equals(myType) ||
                    MyTypes.BOOLEAN.toString().equals(myType) )){
            Assert.isTrue(false, "Field '" + parentFieldName + "' has a unknown type!");
        }
    }

}
