package com.gunter.GenericCRUD.repository;

import com.gunter.GenericCRUD.domain.MyClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyClassRepository extends MongoRepository<MyClass, String> {
}
