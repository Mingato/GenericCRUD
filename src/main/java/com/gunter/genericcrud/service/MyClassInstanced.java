package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.repository.MyClassRepository;
import lombok.Data;

import java.util.List;

@Data
public class MyClassInstanced {

    private static List<MyClass> myClasses;

    public static void init(MyClassRepository myClassRepository){
        myClasses = myClassRepository.findAll();
    }


    public static MyClass getMyClassByName(String name){
        for(MyClass myClass: myClasses){
            if(myClass.getName().equals(name)){
                return myClass;
            }
        }

        throw new NullPointerException("getMyClassByName: Class " + name + " not found");
    }
}
