package org.example.springrecap;

import org.example.springrecap.model.ToDOObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToDoRepo extends MongoRepository<ToDOObject,String> {
}
