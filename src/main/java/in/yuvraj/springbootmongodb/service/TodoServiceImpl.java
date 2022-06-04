package in.yuvraj.springbootmongodb.service;
import in.yuvraj.springbootmongodb.exception.TodoCollectionException;
import in.yuvraj.springbootmongodb.model.TodoDTO;
import in.yuvraj.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException ,TodoCollectionException {

Optional<TodoDTO> todoOptional= todoRepo.findByTodo(todo.getTodo());
    if(todoOptional.isPresent()){
        throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
    }else{
        todo.setCreatedAt(new Date(System.currentTimeMillis()));
    todoRepo.save(todo);
    }
    }

    @Override
    public List<TodoDTO> getAllTodos(){
        List<TodoDTO> todos=todoRepo.findAll();
        if(todos.size()>0){
            return todos;
        }else{
            return new ArrayList<TodoDTO>();
        }
    }
    @Override
    public  TodoDTO getTodoById(String id) throws TodoCollectionException{
    Optional<TodoDTO> todoOptional = todoRepo.findById(id);
    if(!todoOptional.isPresent()){
        throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
    }else{
        return todoOptional.get();

    }
    }

    @Override
    public void updateTodoById(String id, TodoDTO todo) throws TodoCollectionException {
Optional<TodoDTO> todoWithId =todoRepo.findById(id);
Optional<TodoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());
if(todoWithId.isPresent()){

    if(todoWithSameName.isPresent() && todoWithSameName.get().getId().equals(id)){
        throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
    }
    TodoDTO todoUpdate =todoWithId.get();
    todoUpdate.setTodo(todo.getTodo());
    todoUpdate.setDescription(todo.getDescription());
    todoUpdate.setCompleted(todo.getCompleted());
    todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

todoRepo.save(todoUpdate);
}else {
    throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
}

    }

    @Override
    public void deleteTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
if(!todoOptional.isPresent()){
    throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
}else{
    todoRepo.deleteById(id);
}
}
}
