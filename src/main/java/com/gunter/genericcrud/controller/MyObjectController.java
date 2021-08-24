package com.gunter.genericcrud.controller;

import com.gunter.genericcrud.domain.MyMap;
import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.service.MyObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<MyObject> insert(@PathVariable("name") String name, @RequestBody MyMap<String,Object> genericObject){
        return ResponseEntity.ok(myObjectService.insert(name, new MyObject(null, name, genericObject)));
    }

    @PutMapping("/{name}/{id}")
    public ResponseEntity<MyObject> update(@PathVariable("name") String name, @PathVariable("id") String id,
                                           @RequestBody MyMap<String,Object> genericObject){
        return ResponseEntity.ok(myObjectService.update(name, new MyObject(id, name, genericObject)));
    }

    @DeleteMapping("/{name}/{id}")
    public ResponseEntity<String> delete(@PathVariable("name") String name, @PathVariable("id") String id){
        myObjectService.delete(name, id);
        return ResponseEntity.ok(name +" deleted!");
    }
}
