package in.yuvraj.springbootmongodb.service;

import in.yuvraj.springbootmongodb.exception.TodoCollectionException;
import in.yuvraj.springbootmongodb.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
public List<TodoDTO> getAllTodos();
public TodoDTO getTodoById(String id) throws TodoCollectionException;
    public void updateTodoById(String id,TodoDTO todo) throws TodoCollectionException;

    public void deleteTodoById(String id) throws TodoCollectionException;

}
