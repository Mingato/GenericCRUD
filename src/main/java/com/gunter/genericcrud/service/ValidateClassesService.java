package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyTypes;
import com.gunter.genericcrud.exception.MyTypeDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
            Assert.notEmpty(myField.getFields(), "Field 'fields' in " + parentFieldName + " can't be null");
            validate(myField.getFields(), parentFieldName);
        } else if(MyTypes.LIST.toString().equals(myType)){
            Assert.notNull(myField.getFieldTypeList(), "Field 'fieldTypeList' in " + parentFieldName + " can't be null");

            validate(myField.getFieldTypeList(), parentFieldName);
        }else if(MyTypes.DATE.toString().equals(myType)){
            //TODO: verificar quando for data se popssui o formato da data
            //Assert.notNull(myField.getFieldTypeList(), "Field 'fieldTypeList' in " + parentFieldName + " can't be null");

        }

        validateTypes(myType, parentFieldName);
    }

    private void validateTypes(String myType, String parentFieldName){
        boolean doesNotHaveType = true;
        for(MyTypes myTypes: MyTypes.values()){
            if (myTypes.typeName.equalsIgnoreCase(myType)) {
                doesNotHaveType = false;
                break;
            }
        }

        if(doesNotHaveType){
            throw new MyTypeDoesNotExistException("Field '" + parentFieldName + "' with type '" + myType + "' does not exist");
        }
    }

}
