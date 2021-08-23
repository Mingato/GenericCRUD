package com.gunter.GenericCRUD.service;

import com.gunter.GenericCRUD.domain.MyObject;
import com.gunter.GenericCRUD.repository.MyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyObjectService {

    @Autowired
    private MyObjectRepository myObjectRepository;

    public List<MyObject> findByName(String name){
        //TODO: verificar se o objeto existe
        return myObjectRepository.findByName(name);
    }

    public MyObject findByNameAndId(String name, String id) {
        //TODO: verificar se o objeto existe
        return myObjectRepository.findById(id).orElseThrow();
    }

    public MyObject insert(String name, MyObject myObject){
        //TODO: verificar se o objeto existe
        return myObjectRepository.save(myObject);
    }

    public MyObject upSert(String name, MyObject myObject){
        //TODO: verificar se o objeto existe
        return myObjectRepository.save(myObject);
    }

    public void delete(String name, String id){
        //TODO: verificar se o objeto existe
        myObjectRepository.deleteById(id);
    }

}
