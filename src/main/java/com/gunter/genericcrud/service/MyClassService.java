package com.gunter.genericcrud.service;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.repository.MyClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MyClassService {

    @Autowired
    private MyClassRepository myClassRepository;

    @Autowired
    private ValidateClassesService validateClassesService;

    public List<MyClass> getAll(){
        return myClassRepository.findAll();
    }

    public MyClass findByName(String name){
        MyClass myClass = myClassRepository.findById(name).orElseThrow();
        MyClassInstanced.init(myClassRepository);
        return myClass;
    }

    public MyClass insertOrUpdate(MyClass myClass){
        validateClassesService.validateTypesAndStructure(myClass);
        myClass = myClassRepository.save(myClass);
        MyClassInstanced.init(myClassRepository);
        return myClass;
    }



    public void delete(String name){
        myClassRepository.deleteById(name);
        MyClassInstanced.init(myClassRepository);
    }

    public MyClass addFields(String name, List<MyField> fields) {
        MyClass myClass = findByName(name);

        myClass.setFields(mergeFields(myClass.getFields(), fields));

        myClassRepository.save(myClass);
        MyClassInstanced.init(myClassRepository);
        return myClass;
    }

    private List<MyField> mergeFields(List<MyField> oldFields, List<MyField> newFields){
        List<MyField> mergedFields;

        mergedFields = updateFields(oldFields, newFields);
        mergedFields.addAll(addNonEqualsFields(oldFields, newFields));

        return mergedFields;
    }

    private List<MyField> updateFields(List<MyField> oldFields, List<MyField> newFields) {
        List<MyField> mergedFields = new ArrayList<>();

        oldFields.forEach(oldField -> {
            newFields.forEach(newField ->{
                if(oldField.getName().equals(newField.getName())){
                    oldField.setDescription(newField.getDescription());
                    oldField.setName(newField.getName());
                    oldField.setRequired(newField.isRequired());
                    oldField.setType(newField.getType());
                }
            });

            mergedFields.add(oldField);
        });

        return mergedFields;
    }

    private Collection<? extends MyField> addNonEqualsFields(List<MyField> oldFields, List<MyField> newFields) {
        List<MyField> mergedFields = new ArrayList<>();

        newFields.forEach(newField -> {
            AtomicBoolean hasField = new AtomicBoolean(false);
            oldFields.forEach(oldField ->{
                if(oldField.getName().equals(newField.getName())){
                    hasField.set(true);
                }
            });

            if(!hasField.get()) {
                mergedFields.add(newField);
            }
        });

        return mergedFields;
    }


}
