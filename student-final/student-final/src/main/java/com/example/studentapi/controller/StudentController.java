package com.example.studentapi.controller;

import com.example.studentapi.dto.ApiResponse;
import com.example.studentapi.dto.StudentRequest;
import com.example.studentapi.dto.StudentResponse;
import com.example.studentapi.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection (no Lombok needed)
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // POST /api/students → Register student → 201 Created
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> registerStudent(
            @Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.registerStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Student registered successfully!", student));
    }

    // GET /api/students → Get all students → 200 OK
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("Found " + students.size() + " student(s).", students));
    }

    // GET /api/students/{id} → Get by ID → 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        StudentResponse student = studentService.getStudentById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("Student found.", student));
    }
}
