package com.example.studentapi.dto;

import java.time.LocalDateTime;

public class StudentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String course;
    private Integer age;
    private LocalDateTime registeredAt;

    // ── Getters ──────────────────────────────────────────
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCourse() { return course; }
    public Integer getAge() { return age; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }

    // ── Setters ──────────────────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCourse(String course) { this.course = course; }
    public void setAge(Integer age) { this.age = age; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
}
