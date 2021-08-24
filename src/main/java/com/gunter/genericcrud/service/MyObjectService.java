package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.repository.MyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
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

        if(myClass!= null) {
            myClass.getFields().forEach(myField -> {
                Object fieldValue = myObject.getMyInstance().getOrDefault(myField.getName(), null);
                String fieldName = myField.getName();

                if (myField.isRequired()) {
                    Assert.notNull(fieldValue, "Field '" + fieldName + "' cannot be null");
                    validateFieldType(fieldName, fieldValue, myField.getType());
                    myMap.put(fieldName, fieldValue);
                } else {
                    if (fieldValue != null) {
                        validateFieldType(fieldName, fieldValue, myField.getType());
                        myMap.put(fieldName, fieldValue);
                    }
                }
            });
        }

        return myMap;
    }

    private void validateFieldType(String fieldName, Object fieldValue, String type) {
        Assert.isTrue(fieldValue.getClass().getTypeName().toLowerCase().contains(type.toLowerCase()),
                "Field '" + fieldName + "' does not match the type '" + type + "'");
    }

    public void delete(String name, String id){
        myClassService.findByName(name);
        myObjectRepository.deleteById(id);
    }

}
