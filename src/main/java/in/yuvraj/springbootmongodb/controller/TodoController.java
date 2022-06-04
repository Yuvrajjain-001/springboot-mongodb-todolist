package in.yuvraj.springbootmongodb.controller;

import in.yuvraj.springbootmongodb.exception.TodoCollectionException;
import in.yuvraj.springbootmongodb.model.TodoDTO;
import in.yuvraj.springbootmongodb.repository.TodoRepository;
import in.yuvraj.springbootmongodb.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;
    @Autowired
    private TodoService todoService;

@GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
List<TodoDTO> todos=todoService.getAllTodos();
return new ResponseEntity<>(todos,todos.size()>0 ? HttpStatus.OK:HttpStatus.NOT_FOUND);
}
@PostMapping("/todos")
public ResponseEntity<?> createTodo (@RequestBody TodoDTO todo){
    try{
        todoService.createTodo(todo);
        return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
    }catch (ConstraintViolationException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }catch (TodoCollectionException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id){
        Optional <TodoDTO> todoOptional= todoRepo.findById(id);
       try{
           return new ResponseEntity<>(todoService.getTodoById(id),HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

       }
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodoById(@RequestBody TodoDTO todo,@PathVariable("id") String id){
try{
    todoService.updateTodoById(id,todo);
    return new ResponseEntity<>("Update todo with id " +id,HttpStatus.OK);
}catch (ConstraintViolationException e){
    return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
}catch (TodoCollectionException e){
    return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

}
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById (@PathVariable("id") String id){
        try{
            todoService.deleteTodoById(id);
            return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);

        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
