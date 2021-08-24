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

}
