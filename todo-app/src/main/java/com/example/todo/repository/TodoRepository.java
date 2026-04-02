package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository layer for Todo.
 * Extends MongoRepository to get CRUD operations out of the box.
 */
@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    /**
     * Find a todo by its integer business taskId.
     */
    Optional<Todo> findByTaskId(Integer taskId);

    /**
     * Check existence by integer business taskId.
     */
    boolean existsByTaskId(Integer taskId);

    /**
     * Delete by integer business taskId.
     */
    void deleteByTaskId(Integer taskId);
}
