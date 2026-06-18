
package com.example.library.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class IssueTransaction{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;

 @ManyToOne
 private Book book;

 @ManyToOne
 private Member member;

 private LocalDate issueDate;
 private LocalDate dueDate;
 private LocalDate returnDate;
}
