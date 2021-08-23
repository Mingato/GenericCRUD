package com.gunter.GenericCRUD.service;

import com.gunter.GenericCRUD.domain.MyClass;
import com.gunter.GenericCRUD.repository.MyClassRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
public class MyClassInstanced {

    private static List<MyClass> myClasses;

    public static void init(MyClassRepository myClassRepository){
        myClasses = myClassRepository.findAll();
    }
}
