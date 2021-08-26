package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.domain.MyTypes;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ValidateObjectsService {

    public Map<String, Object> validateFields(MyObject myObject) {
        MyClass myClass = MyClassInstanced.getMyClassByName(myObject.getName());

        validateFields(myObject.getMyInstance(), myClass.getFields(), myObject.getName());

        return myObject.getMyInstance();
    }

    private void validateFields(Map<String, Object> myObject, List<MyField> fields, String parentFieldName) {
        if(fields != null) {
            fields.forEach(myField -> {
                Object fieldValue = myObject.getOrDefault(myField.getName(), null);
                String fieldName = myField.getName();

                validateFieldNotNull(fieldValue, fieldName, myField.isRequired(), parentFieldName);
                validateFieldType(fieldValue, fieldName, myField.getType(), myField, parentFieldName);
            });
        }
    }

    private void validateFieldNotNull(Object fieldValue, String fieldName, boolean required, String parentFieldName) {
        if (required) {
            Assert.notNull(fieldValue, "Field '" + parentFieldName+ "." + fieldName + "' cannot be null");
        }
    }

    private void validateFieldType(Object fieldValue, String fieldName, String type, MyField myField, String parentFieldName) {
        validateType(fieldValue, fieldName, type, parentFieldName);

        //validade HashMap Field
        if(fieldValue.getClass().getTypeName().toUpperCase().contains(MyTypes.HASHMAP.typeName)){
            validateFields((LinkedHashMap) fieldValue, myField.getFields(), parentFieldName+"."+fieldName);
        }

        validateFilesInList(fieldValue, myField, parentFieldName);
    }

    private void validateType(Object fieldValue, String fieldName, String type, String parentFieldName) {
        String errorMessage = "Field '" + parentFieldName + "." + fieldName + "' is type '" +
                fieldValue.getClass().getTypeName()
                + "', but the type required is '" + type + "'";

        if(MyTypes.NUMBER.typeName.equalsIgnoreCase(type)){
            Assert.isTrue(isNumberType(fieldValue.getClass()), errorMessage);
        }else if(MyTypes.DATE.typeName.equalsIgnoreCase(type)){
            Assert.isTrue(fieldValue.getClass().isInstance(""), errorMessage + " that type is a String");
            //TODO: passar o formato da data
            validateDate(String.valueOf(fieldValue));
        } else{
            Assert.isTrue(fieldValue.getClass().getTypeName().toLowerCase().contains(type.toLowerCase()), errorMessage);
        }

    }

    private void validateFilesInList(Object fieldValue, MyField myField, String parentFieldName){
        if(isCollection(fieldValue)) {

            String listType = myField.getFieldTypeList().getType();
            ArrayList list = (ArrayList) fieldValue;
            int index = 0;
            for(Object element: list) {
                validateFieldType(element, myField.getName()+"[" + index + "]", listType, myField.getFieldTypeList(), parentFieldName);
                index++;
            }
        }
    }

    private void validateDate(String date) {
        //TODO: validar a data de acordo com o seu formato
        var zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        zonedDateTime.toLocalDate();
    }

    public static boolean isCollection(Object obj) {
        return obj.getClass().isArray() || obj instanceof Collection;
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getNumberTypes();

    public static boolean isNumberType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getNumberTypes()
    {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(BigDecimal.class);
        return ret;
    }
}
