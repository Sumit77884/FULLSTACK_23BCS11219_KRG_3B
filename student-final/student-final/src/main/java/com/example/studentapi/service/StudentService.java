package com.example.studentapi.service;

import com.example.studentapi.dto.StudentRequest;
import com.example.studentapi.dto.StudentResponse;
import com.example.studentapi.exception.EmailAlreadyExistsException;
import com.example.studentapi.exception.StudentNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection (no Lombok needed)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Register a new student
    public StudentResponse registerStudent(StudentRequest request) {
        // Check if email already exists
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        // Build Student entity from request
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setCourse(request.getCourse());
        student.setAge(request.getAge());

        // Save to database
        Student saved = studentRepository.save(student);

        // Return response
        return toResponse(saved);
    }

    // Get all students
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> responses = new ArrayList<>();
        for (Student student : students) {
            responses.add(toResponse(student));
        }
        return responses;
    }

    // Get student by ID
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return toResponse(student);
    }

    // Convert Student entity to StudentResponse DTO
    private StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setCourse(student.getCourse());
        response.setAge(student.getAge());
        response.setRegisteredAt(student.getRegisteredAt());
        return response;
    }
}
