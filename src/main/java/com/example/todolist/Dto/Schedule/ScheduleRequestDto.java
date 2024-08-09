package com.example.todolist.Dto.Schedule;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleRequestDto {

    private String task;

    private String writer;

    private String password;

    @Builder
    public ScheduleRequestDto(String task, String writer, String password) {
        this.task = task;
        this.writer = writer;
        this.password = password;
    }
}
