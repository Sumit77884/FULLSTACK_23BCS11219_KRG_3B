package com.example.todo.service;

import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.exception.DuplicateResourceException;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation containing all business logic for Todo CRUD operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    /**
     * Create a new todo. Checks that taskId is unique before saving.
     */
    @Override
    public Todo createTodo(Todo todo) {
        log.info("Creating todo with taskId: {}", todo.getTaskId());

        if (todoRepository.existsByTaskId(todo.getTaskId())) {
            throw new DuplicateResourceException(
                    "Todo with taskId " + todo.getTaskId() + " already exists.");
        }

        // Ensure default status is false if not explicitly set
        if (!todo.isStatus()) {
            todo.setStatus(false);
        }

        Todo saved = todoRepository.save(todo);
        log.info("Todo created successfully: {}", saved);
        return saved;
    }

    /**
     * Retrieve all todos from the database.
     */
    @Override
    public List<Todo> getAllTodos() {
        log.info("Fetching all todos");
        return todoRepository.findAll();
    }

    /**
     * Retrieve a single todo by its integer taskId.
     */
    @Override
    public Todo getTaskById(Integer taskId) {
        log.info("Fetching todo with taskId: {}", taskId);
        return todoRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Todo not found with taskId: " + taskId));
    }

    /**
     * Update an existing todo. Only title and status can be updated.
     */
    @Override
    public Todo updateTask(Integer taskId, Todo updated) {
        log.info("Updating todo with taskId: {}", taskId);

        Todo existing = todoRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Todo not found with taskId: " + taskId));

        // Update allowed fields
        if (updated.getTitle() != null && !updated.getTitle().isBlank()) {
            existing.setTitle(updated.getTitle());
        }
        existing.setStatus(updated.isStatus());

        Todo saved = todoRepository.save(existing);
        log.info("Todo updated successfully: {}", saved);
        return saved;
    }

    /**
     * Delete a todo by its integer taskId.
     */
    @Override
    public void deleteTask(Integer taskId) {
        log.info("Deleting todo with taskId: {}", taskId);

        if (!todoRepository.existsByTaskId(taskId)) {
            throw new ResourceNotFoundException(
                    "Todo not found with taskId: " + taskId);
        }

        todoRepository.deleteByTaskId(taskId);
        log.info("Todo with taskId {} deleted successfully", taskId);
    }
}
