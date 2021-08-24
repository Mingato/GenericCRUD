package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.repository.MyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class MyObjectService {

    @Autowired
    private MyClassService myClassService;

    @Autowired
    private MyObjectRepository myObjectRepository;

    @Autowired ValidateObjectsService validateObjectsService;

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
        myObject.setMyInstance(validateObjectsService.validateFields(myObject));
        return myObjectRepository.save(myObject);
    }

    public MyObject update(String name, MyObject myObject){
        myClassService.findByName(name);
        myObject.setMyInstance(validateObjectsService.validateFields(myObject));
        return myObjectRepository.save(myObject);
    }



    public void delete(String name, String id){
        myClassService.findByName(name);
        myObjectRepository.deleteById(id);
    }

}
