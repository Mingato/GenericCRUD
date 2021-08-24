package com.gunter.genericcrud.controller;

import com.gunter.genericcrud.domain.MyObject;
import com.gunter.genericcrud.service.MyObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MyObjectController {

    @Autowired
    private MyObjectService myObjectService;


    @GetMapping("/{name}")
    public ResponseEntity<List<Object>> findAll(@PathVariable("name") String name){

        return ResponseEntity.ok(
                myObjectService.findByName(name).stream()
                        .map(MyObject::getMyInstanceWithId)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{name}/{id}")
    public ResponseEntity<Map<String,Object>> findAll(@PathVariable("name") String name, @PathVariable("id") String id){
        return ResponseEntity.ok(myObjectService.findByNameAndId(name, id).getMyInstanceWithId());
    }

    @PostMapping("/{name}")
    public ResponseEntity<Map<String,Object>> insert(@PathVariable("name") String name, @RequestBody Map<String,Object> genericObject){
        return ResponseEntity.ok(myObjectService.insert(name, new MyObject(null, name, genericObject)).getMyInstanceWithId());
    }

    @PutMapping("/{name}/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable("name") String name, @PathVariable("id") String id,
                                           @RequestBody Map<String,Object> genericObject){
        return ResponseEntity.ok(myObjectService.update(name, new MyObject(id, name, genericObject)).getMyInstanceWithId());
    }

    @DeleteMapping("/{name}/{id}")
    public ResponseEntity<String> delete(@PathVariable("name") String name, @PathVariable("id") String id){
        myObjectService.delete(name, id);
        return ResponseEntity.ok(name +" deleted!");
    }
}
