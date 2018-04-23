package de.hse.todosbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("todos")
@CrossOrigin(origins = "http://localhost:8081")
public class TodoRESTController {

    @Autowired
    private ITodoRepository todoRepository;

    @GetMapping("active")
    public List<Todo> getActiveTodos(){
        return todoRepository.findByIsActive(true);
    }

    @GetMapping("done")
    public List<Todo> getDoneTodos(){
        return  todoRepository.findByIsActive(false);
    }

    @PostMapping("add")
    public Todo addNewTodo(@RequestBody Todo newTodo){
        todoRepository.save(newTodo);
        return newTodo;
    }

    @PostMapping("done/{id}")
    public Todo todoDone(@PathVariable int id){
        Todo todo = todoRepository.findById(id);
        todo.setActive(false);
        todoRepository.save(todo);
        return todo;
    }
}
