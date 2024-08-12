package com.example.todolist.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Schedule extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;


    @Size(max = 200)
    private String task;

    private String writer;

    @NotBlank
    @JsonIgnore
    private String password;

    @Builder
    public Schedule(Long sid,String task, String writer, String password,LocalDate regDate,LocalDate modDate) {
        this.sid=sid;
        this.task = task;
        this.writer = writer;
        this.password = password;
        this.setRegDate(regDate);
        this.setModDate(modDate);
    }

    public void timeSchedule(Long sid, String task, String writer, String password, LocalDate regDate,LocalDate modeDate){
        this.sid=sid;
        this.task=task;
        this.writer=writer;
        this.password=password;

    }

    public Schedule(long sid, String task, String writer, String password) {
        super();
    }


    public void updateSchedule(String task,String writer){
       this.task = task;
       this.writer = writer;
    }

}
