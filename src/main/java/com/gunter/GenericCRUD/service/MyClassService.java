package com.gunter.GenericCRUD.service;

import com.gunter.GenericCRUD.domain.MyClass;
import com.gunter.GenericCRUD.domain.MyField;
import com.gunter.GenericCRUD.repository.MyClassRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyClassService {

    @Autowired
    private MyClassRepository myClassRepository;

    public List<MyClass> getAll(){
        //TODO: buscar da variavel em memoria
        return myClassRepository.findAll();
    }

    public MyClass findByName(String name){
        //TODO: buscar da variavel em memoria
        return myClassRepository.findById(name).orElseThrow();
    }

    public MyClass insert(MyClass myClass){
        //TODO: inserir na variavel em memoria
        return myClassRepository.save(myClass);
    }

    public MyClass upSert(MyClass myClass){
        //TODO: inserir na variavel em memoria
        return myClassRepository.save(myClass);
    }

    public void delete(String name){
        //TODO: deletar da variavel em memoria
        myClassRepository.deleteById(name);
    }

    public MyClass addFields(String name, List<MyField> fields) {
        //TODO: inserir na variavel em memoria
        MyClass myClass = findByName(name);

        //TODO:verificar se não possui campos repetidos
        myClass.getFields().addAll(fields);

        myClassRepository.save(myClass);
        return myClass;
    }
}
