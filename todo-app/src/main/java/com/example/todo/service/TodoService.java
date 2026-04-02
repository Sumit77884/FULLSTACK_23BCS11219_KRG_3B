package com.example.todo.service;

import com.example.todo.model.Todo;

import java.util.List;

/**
 * Service interface that defines all business operations for Todo.
 */
public interface TodoService {

    /**
     * Create a new todo task.
     * @param todo the todo to create
     * @return the saved todo
     */
    Todo createTodo(Todo todo);

    /**
     * Retrieve all todo tasks.
     * @return list of all todos
     */
    List<Todo> getAllTodos();

    /**
     * Retrieve a single todo by its integer taskId.
     * @param taskId the integer task id
     * @return the found todo
     */
    Todo getTaskById(Integer taskId);

    /**
     * Update an existing todo by its integer taskId.
     * @param taskId the integer task id
     * @param updated fields to update
     * @return the updated todo
     */
    Todo updateTask(Integer taskId, Todo updated);

    /**
     * Delete a todo by its integer taskId.
     * @param taskId the integer task id
     */
    void deleteTask(Integer taskId);
}
