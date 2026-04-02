package com.example.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Todo Entity — mapped to the "todos" collection in MongoDB.
 *
 * Fields:
 *  - id      : auto-generated MongoDB ObjectId (String)
 *  - taskId  : integer business-key (required, unique)
 *  - title   : string title (required)
 *  - status  : boolean, defaults to false (not completed)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "todos")
public class Todo {

    /** MongoDB document id (ObjectId) */
    @Id
    private String id;

    /** Integer business id — required and unique */
    @NotNull(message = "taskId is required")
    @Indexed(unique = true)
    private Integer taskId;

    /** Title of the todo task — required */
    @NotBlank(message = "title is required")
    private String title;

    /** Completion status — false by default */
    private boolean status = false;
}
