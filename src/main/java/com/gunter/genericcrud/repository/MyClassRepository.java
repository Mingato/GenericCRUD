package com.gunter.genericcrud.repository;

import com.gunter.genericcrud.domain.MyClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyClassRepository extends MongoRepository<MyClass, String> {
}
