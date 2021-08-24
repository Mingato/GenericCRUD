package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.repository.MyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyObjectService {

    @Autowired
    private MyClassService myClassService;

    @Autowired
    private MyObjectRepository myObjectRepository;

    public List<MyObject> findByName(String name){
        myClassService.findByName(name);
        return myObjectRepository.findByName(name);
    }

    public MyObject findByNameAndId(String name, String id) {
        myClassService.findByName(name);
        return myObjectRepository.findById(id).orElseThrow();
    }

    public MyObject insert(String name, MyObject myObject){
        myClassService.findByName(name);
        myObject.setMyInstance(validateFields(myObject));
        return myObjectRepository.save(myObject);
    }

    public MyObject update(String name, MyObject myObject){
        myClassService.findByName(name);
        myObject.setMyInstance(validateFields(myObject));
        return myObjectRepository.save(myObject);
    }

    private Map<String, Object> validateFields(MyObject myObject) {
        MyClass myClass = MyClassInstanced.getMyClassByName(myObject.getName());
        Map<String, Object> myMap = new HashMap<>();

        if(myClass != null) {
            validateFields(myObject.getMyInstance(), myClass.getFields(), myMap);
        }

        return myMap;
    }

    private void validateFields(Map<String, Object> myObject, List<MyField> fields, Map<String, Object> myMap) {
        if(fields != null) {
            fields.forEach(myField -> {
                Object fieldValue = myObject.getOrDefault(myField.getName(), null);
                String fieldName = myField.getName();

                validateFieldNotNull(fieldValue, fieldName, myField.isRequired());
                validateFieldType(fieldValue, fieldName, myField.getType());

                myMap.put(fieldName, fieldValue);

                //validade HashMap Field
                if(fieldValue.getClass().getTypeName().toLowerCase().contains("HashMap".toLowerCase())){
                    validateFields((LinkedHashMap) fieldValue, myField.getFields(), myMap);
                }
            });
        }
    }

    private void validateFieldNotNull(Object fieldValue, String fieldName, boolean required) {
        if (required) {
            Assert.notNull(fieldValue, "Field '" + fieldName + "' cannot be null");
        }
    }

    private void validateFieldType(Object fieldValue, String fieldName, String type) {
        if (fieldValue != null) {
            Assert.isTrue(fieldValue.getClass().getTypeName().toLowerCase().contains(type.toLowerCase()),
                    "Field '" + fieldName + "' is type '" + fieldValue.getClass().getTypeName()
                            +"', but the type required is '" + type + "'");
        }
    }

    public void delete(String name, String id){
        myClassService.findByName(name);
        myObjectRepository.deleteById(id);
    }

}
