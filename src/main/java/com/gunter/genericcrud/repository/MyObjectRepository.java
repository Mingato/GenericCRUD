package com.gunter.genericcrud.repository;

import com.gunter.genericcrud.domain.MyObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyObjectRepository extends MongoRepository<MyObject, String> {

    List<MyObject> findByName(String name);
}
