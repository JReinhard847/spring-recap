package org.example.springrecap;


import lombok.RequiredArgsConstructor;
import org.example.springrecap.model.ToDOObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepo repo;
    private final OpenAIService openAIService;

    public List<ToDOObject> getAll(){
        return repo.findAll();
    }

    public ToDOObject getById(String id){
        return repo.findById(id).orElseThrow();
    }

    public ToDOObject createNewObject(ToDOObject object){
        // Mongo handles id generation since the passed object has an id value of null because the frontend doesnt pass an id
        ToDOObject spellchecked = object.withDescription(openAIService.spellCheck(object.getDescription()));
        return repo.insert(spellchecked);
    }

    public ToDOObject updateToDO(ToDOObject updated){
        return repo.save(updated);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }




}
