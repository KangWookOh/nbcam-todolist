package com.example.todolist.Dto.Schedule;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ScheduleResponseDto {

    private Long sid;

    private String task;

    private String writer;

    private String password;

    private LocalDate regDate;

    private LocalDate modDate;

    @Builder
    public ScheduleResponseDto(Long sid, String task, String writer, String password, LocalDate regDate, LocalDate modDate) {
        this.sid = sid;
        this.task = task;
        this.writer = writer;
        this.password = password;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
