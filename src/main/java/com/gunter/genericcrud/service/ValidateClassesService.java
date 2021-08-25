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
        parentFieldName = parentFieldName + "." + myField.getName();
        final String myType = myField.getType().toUpperCase();
        Assert.notNull(myField.getName(), "Field '" + parentFieldName + "' can't be null!");
        Assert.notNull(myType, "Type of field '" + parentFieldName + "' can't be null!");
        Assert.notNull(myField.isRequired(), "Required from '" + parentFieldName + "' can't be null!");


        if(MyTypes.HASHMAP.equals(myType)){
            Assert.notEmpty(myField.getFields(), "Field " + parentFieldName + " can't be empty!");
            validate(myField.getFields(), parentFieldName);
        } else if(MyTypes.LIST.equals(myType)){
            Assert.notNull(myField.getFieldTypeList(), "Field List in '" + parentFieldName + "' can't be null!");

            validate(myField.getFieldTypeList(), parentFieldName);
        } else if( !(MyTypes.STRING.equals(myType) ||
                    MyTypes.INTEGER.equals(myType) ||
                    MyTypes.NUMBER.equals(myType) ||
                    MyTypes.BOOLEAN.equals(myType) )){
            //TODO: lançar excessao pois não possui o type valido
        }
        System.out.println("");
    }

    private boolean isValidType(String myType){
        if(MyTypes.HASHMAP.equals(myType) ||
            MyTypes.LIST.equals(myType) ||
            MyTypes.STRING.equals(myType) ||
            MyTypes.INTEGER.equals(myType) ||
            MyTypes.NUMBER.equals(myType) ||
            MyTypes.BOOLEAN.equals(myType)){
            return true;
        }

        return false;
    }
}
