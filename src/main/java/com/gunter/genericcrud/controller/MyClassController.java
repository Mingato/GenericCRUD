package com.gunter.genericcrud.controller;

import com.gunter.genericcrud.domain.MyClass;
import com.gunter.genericcrud.domain.MyField;
import com.gunter.genericcrud.repository.MyClassRepository;
import com.gunter.genericcrud.service.MyClassInstanced;
import com.gunter.genericcrud.service.MyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class MyClassController {

    @Autowired
    private MyClassService myClassService;


    @Autowired
    public MyClassController(MyClassRepository myClassRepository){
        MyClassInstanced.init(myClassRepository);
    }

    @GetMapping
    public ResponseEntity<List<MyClass>> getAll(){
        return ResponseEntity.ok(myClassService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<MyClass> find(@PathVariable("name") String name){
        return ResponseEntity.ok(myClassService.findByName(name));
    }

    @PostMapping("/{name}")
    public ResponseEntity<MyClass> insert(@PathVariable("name") String name, @RequestBody MyClass myClass){
        myClass.setName(name);
        return ResponseEntity.ok(myClassService.insertOrUpdate(myClass));
    }
    @PostMapping("/{name}/fields")
    public ResponseEntity<MyClass> addFields(@PathVariable("name") String name, @RequestBody List<MyField> fields){
        return ResponseEntity.ok(myClassService.addFields(name, fields));
    }

    @PutMapping("/{name}")
    public ResponseEntity<MyClass> update(@PathVariable("name") String name, @RequestBody MyClass myClass){
        myClass.setName(name);
        return ResponseEntity.ok(myClassService.insertOrUpdate(myClass));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> delete(@PathVariable("name") String name){
        myClassService.delete(name);
        return ResponseEntity.ok("Deleted");
    }

}
