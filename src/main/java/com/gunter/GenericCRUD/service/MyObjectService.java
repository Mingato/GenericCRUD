package com.gunter.GenericCRUD.service;

import com.gunter.GenericCRUD.domain.MyObject;
import com.gunter.GenericCRUD.repository.MyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return myObjectRepository.save(myObject);
    }

    public MyObject upSert(String name, MyObject myObject){
        myClassService.findByName(name);
        return myObjectRepository.save(myObject);
    }

    public void delete(String name, String id){
        myClassService.findByName(name);
        myObjectRepository.deleteById(id);
    }

}
