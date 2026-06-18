
package com.example.library.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Member{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 private String name;

 @OneToMany(mappedBy="member")
 private List<IssueTransaction> transactions;
}
