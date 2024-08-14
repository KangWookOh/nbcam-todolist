package com.example.todolist.Dto.Schedule;

import com.example.todolist.Entity.Manager;
import com.example.todolist.Entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class ScheduleResponseDto {
    private Long sid;
    private String task;
    private String Name;
    private String regDate;
    private String modDate;


    public static ScheduleResponseDto from(Schedule schedule, Manager manager) {
        return new ScheduleResponseDto(
                schedule.getSid(),
                schedule.getTask(),
                manager.getName(),
                schedule.getRegDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")),
                schedule.getModDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        );
    }

}

