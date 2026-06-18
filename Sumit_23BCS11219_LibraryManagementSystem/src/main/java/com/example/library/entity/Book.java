
package com.example.library.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(indexes = @Index(name="idx_title",columnList="title"))
public class Book{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 private String title;
 private String author;
 private boolean available=true;

 @OneToMany(mappedBy="book")
 private List<IssueTransaction> transactions;
}
