package com.gunter.GenericCRUD.controller;

import com.gunter.GenericCRUD.domain.MyObject;
import com.gunter.GenericCRUD.service.MyObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyObjectController {

    @Autowired
    private MyObjectService myObjectService;


    @GetMapping("/{name}")
    public ResponseEntity<List<MyObject>> findAll(@PathVariable("name") String name){
        return ResponseEntity.ok(myObjectService.findByName(name));
    }

    @GetMapping("/{name}/{id}")
    public ResponseEntity<MyObject> findAll(@PathVariable("name") String name, @PathVariable("id") String id){
        return ResponseEntity.ok(myObjectService.findByNameAndId(name, id));
    }

    @PostMapping("/{name}")
    public ResponseEntity<MyObject> insert(@PathVariable("name") String name, @RequestBody MyObject myObject){
        return ResponseEntity.ok(myObjectService.insert(name, myObject));
    }

    @PutMapping("/{name}/{id}")
    public ResponseEntity<MyObject> upSert(@PathVariable("name") String name, @RequestBody MyObject myObject){
        return ResponseEntity.ok(myObjectService.upSert(name, myObject));
    }

    @DeleteMapping("/{name}/{id}")
    public ResponseEntity<String> delete(@PathVariable("name") String name, @PathVariable("id") String id){
        myObjectService.delete(name, id);
        return ResponseEntity.ok(name +" deleted!");
    }
}
