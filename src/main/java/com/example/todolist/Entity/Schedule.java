package com.example.todolist.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @NotBlank
    @Size(max = 200)
    private String task;

    @NotBlank
    private String writer;

    @NotBlank
    private String password;

    @Builder
    public Schedule(String task, String writer, String password) {
        this.task = task;
        this.writer = writer;
        this.password = password;
    }

}
