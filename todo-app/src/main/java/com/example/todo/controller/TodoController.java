package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing Todo CRUD API endpoints.
 *
 * Endpoints:
 *  POST   /api/createTodo         — Create a new task
 *  GET    /api/getAll             — Get all tasks
 *  GET    /api/getTask/{id}       — Get a task by taskId
 *  PUT    /api/updateTask/{id}    — Update a task by taskId
 *  DELETE /api/deleteTask/{id}    — Delete a task by taskId
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // ─────────────────────────────────────────────
    // POST /api/createTodo
    // ─────────────────────────────────────────────
    @PostMapping("/createTodo")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        log.info("POST /api/createTodo — body: {}", todo);
        Todo created = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ─────────────────────────────────────────────
    // GET /api/getAll
    // ─────────────────────────────────────────────
    @GetMapping("/getAll")
    public ResponseEntity<List<Todo>> getAllTodos() {
        log.info("GET /api/getAll");
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // ─────────────────────────────────────────────
    // GET /api/getTask/{id}
    // ─────────────────────────────────────────────
    @GetMapping("/getTask/{id}")
    public ResponseEntity<Todo> getTaskById(@PathVariable Integer id) {
        log.info("GET /api/getTask/{}", id);
        Todo todo = todoService.getTaskById(id);
        return ResponseEntity.ok(todo);
    }

    // ─────────────────────────────────────────────
    // PUT /api/updateTask/{id}
    // ─────────────────────────────────────────────
    @PutMapping("/updateTask/{id}")
    public ResponseEntity<Todo> updateTask(
            @PathVariable Integer id,
            @RequestBody Todo todo) {
        log.info("PUT /api/updateTask/{}", id);
        Todo updated = todoService.updateTask(id, todo);
        return ResponseEntity.ok(updated);
    }

    // ─────────────────────────────────────────────
    // DELETE /api/deleteTask/{id}
    // ─────────────────────────────────────────────
    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        log.info("DELETE /api/deleteTask/{}", id);
        todoService.deleteTask(id);
        return ResponseEntity.ok("Todo with taskId " + id + " deleted successfully.");
    }
}
