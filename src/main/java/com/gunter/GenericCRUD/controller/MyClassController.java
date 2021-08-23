package com.gunter.GenericCRUD.controller;

import com.gunter.GenericCRUD.domain.MyClass;
import com.gunter.GenericCRUD.domain.MyField;
import com.gunter.GenericCRUD.service.MyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class MyClassController {

    @Autowired
    private MyClassService myClassService;

    @GetMapping
    public ResponseEntity<List<MyClass>> getAll(){
        return ResponseEntity.ok(myClassService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<MyClass> find(@PathVariable("name") String name){
        return ResponseEntity.ok(myClassService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<MyClass> insert(@RequestBody MyClass myClass){
        return ResponseEntity.ok(myClassService.insert(myClass));
    }
    @PostMapping("/{name}/fields")
    public ResponseEntity<MyClass> addFields(@PathVariable("name") String name, @RequestBody List<MyField> fields){
        return ResponseEntity.ok(myClassService.addFields(name, fields));
    }

    @PutMapping
    public ResponseEntity<MyClass> upSert(@RequestBody MyClass myClass){
        return ResponseEntity.ok(myClassService.upSert(myClass));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> delete(@PathVariable("name") String name){
        myClassService.delete(name);
        return ResponseEntity.ok("Deleted");
    }

}
