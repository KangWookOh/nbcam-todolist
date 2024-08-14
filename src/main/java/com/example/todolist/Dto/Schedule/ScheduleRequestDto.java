package com.example.todolist.Dto.Schedule;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleRequestDto {

    private String task;

    private String password;

    private Long manager_id;

    @Builder
    public ScheduleRequestDto(String task,String password,Long manager_id) {
        this.task = task;
        this.password = password;
        this.manager_id = manager_id;
    }
}
