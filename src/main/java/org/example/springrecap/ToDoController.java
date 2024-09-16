package org.example.springrecap;


import lombok.RequiredArgsConstructor;
import org.example.springrecap.model.ToDOObject;
import org.example.springrecap.model.ToDOStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService service;


    @GetMapping
    public List<ToDOObject> getAll(){
        return service.getAll();
   }

   @GetMapping("/{id}")
   public ToDOObject getById(@PathVariable String id){
        return service.getById(id);
   }

    @PostMapping
    public ToDOObject addToDo(@RequestBody ToDOObject toadd){
        return service.createNewObject(toadd);
    }

    @PutMapping("/{id}")
    public ToDOObject updateObject(@RequestBody ToDOObject toUpdate){
        return service.updateToDO(toUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        service.deleteById(id);
    }
}
