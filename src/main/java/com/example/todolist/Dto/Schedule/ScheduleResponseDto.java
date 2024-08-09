package com.example.todolist.Dto.Schedule;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleResponseDto {

    private Long sid;

    private String task;

    private String writer;

    private String password;

    @Builder
    public ScheduleResponseDto(Long sid, String task, String writer, String password) {
        this.sid = sid;
        this.task = task;
        this.writer = writer;
        this.password = password;
    }
}
