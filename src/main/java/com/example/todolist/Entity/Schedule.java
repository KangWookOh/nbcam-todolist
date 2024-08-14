package com.example.todolist.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class Schedule extends BaseTime{


    private Long sid;


    @Size(max = 200)
    private String task;

    @NotBlank
    @JsonIgnore
    private String password;

    private Long manager_id;

    @Builder
    public Schedule(Long sid,String task, String password,Long manager_id,LocalDateTime regDate,LocalDateTime modDate) {
        this.sid=sid;
        this.task = task;
        this.password = password;
        this.manager_id = manager_id;
        this.setRegDate(regDate);
        this.setModDate(modDate);
    }



}
