package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.domain.MyTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class ValidateObjectsService {

    private String dateMessageErrorParser = "";

    public Map<String, Object> validateFields(MyObject myObject) {
        MyClass myClass = MyClassInstanced.getMyClassByName(myObject.getName());

        validateFields(myObject.getMyInstance(), myClass.getFields(), myObject.getName());

        return myObject.getMyInstance();
    }

    private void validateFields(Map<String, Object> myObject, List<MyField> fields, String parentFieldName) {
        //TODO: para cada objeto só adicionar os campos que estão em fields
        if(fields != null) {
            fields.forEach(myField -> {
                Object fieldValue = myObject.getOrDefault(myField.getName(), null);

                validateFieldNotNull(fieldValue, myField, parentFieldName);
                validateFieldType(fieldValue, myField.getName(), myField.getType(), myField, parentFieldName);
            });
        }
    }

    private void validateFieldNotNull(Object fieldValue, MyField myField, String parentFieldName) {
        if (myField.isRequired()) {
            Assert.notNull(fieldValue, "Field '" + parentFieldName+ "." + myField.getName() + "' cannot be null");
        }
    }

    private void validateFieldType(Object fieldValue, String fieldName, String fieldType, MyField myField, String parentFieldName) {
        validateType(fieldValue, fieldType, myField, parentFieldName + "." + fieldName);

        //validade HashMap Field
        if(fieldValue.getClass().getTypeName().toUpperCase().contains(MyTypes.HASHMAP.typeName)){
            validateFields((LinkedHashMap) fieldValue, myField.getFields(), parentFieldName+"."+fieldName);
        }

        validateFilesInList(fieldValue, myField, parentFieldName);
    }

    private void validateType(Object fieldValue, String fieldType, MyField myField, String parentFieldName) {
        String errorMessage = "Field '" + parentFieldName + "' is type '" +
                fieldValue.getClass().getTypeName()
                + "', but the type required is '" + fieldType + "'";

        if(MyTypes.NUMBER.typeName.equalsIgnoreCase(fieldType)){
            Assert.isTrue(isNumberType(fieldValue.getClass()), errorMessage);
        }else if(MyTypes.DATE.typeName.equalsIgnoreCase(fieldType)){
            Assert.isTrue(fieldValue.getClass().isInstance(""), errorMessage + " that type is a String");
            validateDate(String.valueOf(fieldValue), myField, parentFieldName);
        } else{
            Assert.isTrue(fieldValue.getClass().getTypeName().toLowerCase().contains(fieldType.toLowerCase()), errorMessage);
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

    private void validateDate(String date, MyField myField, String parentFieldName) {

        if(Strings.isEmpty(myField.getDateFormat())) {
            boolean isValidDate = false;

            if( canParseDate(date, "yyyy-MM") ||
                canParseDate(date, "yyyy-MM-dd") ||
                canParseDate(date, "yyyy-MM-dd HH:mm") ||
                canParseDate(date, "yyyy-MM-dd HH:mm:ss")){
                isValidDate = true;
            }else{
                try{
                    ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                    isValidDate = true;
                }catch (Exception ignored){}
            }

            Assert.isTrue(isValidDate, "The Field '" + parentFieldName + "' = '"
                                + date + "' has error " + dateMessageErrorParser);

        }else{

            Assert.isTrue(canParseDate(date, myField.getDateFormat()), "The Field '" + parentFieldName + "' = '"
                    + date + "' has error: " + dateMessageErrorParser);
        }

    }

    private boolean canParseDate(String date, String dateFormat){
        try{
            org.joda.time.format.DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern(dateFormat);
            org.joda.time.DateTime jodaDate =  fmt.parseDateTime(date);
            jodaDate.toDate();

            log.info("dateFormated: " + jodaDate.toString());
            return true;

        }catch (Exception e){
            dateMessageErrorParser = e.getMessage() + " | " + e.getCause();
        }

        return false;
    }

    private static boolean isCollection(Object obj) {
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
