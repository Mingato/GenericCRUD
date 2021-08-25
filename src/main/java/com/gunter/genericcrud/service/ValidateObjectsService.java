package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyObject;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidateObjectsService {

    public Map<String, Object> validateFields(MyObject myObject) {
        MyClass myClass = MyClassInstanced.getMyClassByName(myObject.getName());
        Map<String, Object> myMap = new HashMap<>();

        if(myClass != null) {
            validateFields(myObject.getMyInstance(), myClass.getFields(), myMap, myObject.getName());
        }

        return myMap;
    }

    private void validateFields(Map<String, Object> myObject, List<MyField> fields, Map<String, Object> myMap, String parentFieldName) {
        if(fields != null) {
            fields.forEach(myField -> {
                Object fieldValue = myObject.getOrDefault(myField.getName(), null);
                String fieldName = myField.getName();

                validateFieldNotNull(fieldValue, fieldName, myField.isRequired(), parentFieldName);
                validateFieldType(fieldValue, fieldName, myField.getType(), myField, myMap, parentFieldName);

                myMap.put(fieldName, fieldValue);
            });
        }
    }

    private void validateFieldsHashMap(Map<String, Object> myObject, List<MyField> fields, Map<String, Object> myMap, String parentFieldName) {
        if(fields != null) {
            fields.forEach(myField -> {
                Object fieldValue = myObject.getOrDefault(myField.getName(), null);
                String fieldName = myField.getName();

                validateFieldNotNull(fieldValue, fieldName, myField.isRequired(), parentFieldName);
                validateFieldType(fieldValue, fieldName, myField.getType(), myField, myMap, parentFieldName);
            });
        }
    }

    private void validateFieldNotNull(Object fieldValue, String fieldName, boolean required, String parentFieldName) {
        if (required) {
            Assert.notNull(fieldValue, "Field '" + parentFieldName+ "." + fieldName + "' cannot be null");
        }
    }

    private void validateFieldType(Object fieldValue, String fieldName, String type, MyField myField, Map<String, Object> myMap, String parentFieldName) {
        //TODO:caso o tipo for double e receber um integer, considerar o type correto
        if (fieldValue != null) {
            Assert.isTrue(fieldValue.getClass().getTypeName().toLowerCase().contains(type.toLowerCase()),
                    "Field '" + parentFieldName + "." + fieldName + "' is type '" +
                            fieldValue.getClass().getTypeName()
                            +"', but the type required is '" + type + "'");
        }

        //validade HashMap Field
        if(fieldValue.getClass().getTypeName().toLowerCase().contains("HashMap".toLowerCase())){
            validateFieldsHashMap((LinkedHashMap) fieldValue, myField.getFields(), myMap, parentFieldName+"."+fieldName);
        }

        validateFilesInList(fieldValue, myField, myMap, parentFieldName);
    }

    private void validateFilesInList(Object fieldValue, MyField myField, Map<String, Object> myMap, String parentFieldName){
        //TODO: validar itens de uma lista de acordo com o parâmetro list type
        if(fieldValue.getClass().getTypeName().toLowerCase().contains("List".toLowerCase())) {
            //TODO: percorrer lista e verificar o tipo de cada elemento
            //TODO: para os tipos genericos String, Integer criar uma lista com os elementos, pois isto já irá validar se o tipo esta correto
            //TODO: para tipo de hashMap chamar função de validateFieldType para cada elemento
            //myField.getListType();
            //List<Integer> variable = (List<Integer>) (List<?>) fieldValue;
            //validateFieldType
        }

    }
}
