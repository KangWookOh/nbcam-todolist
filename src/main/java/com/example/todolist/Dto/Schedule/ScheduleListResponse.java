package com.example.todolist.Dto.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
public class ScheduleListResponse {
    private Page<ScheduleResponseDto> scheduleResponses;

    public static ScheduleListResponse of(Page<ScheduleResponseDto> scheduleResponses) {
        return new ScheduleListResponse(scheduleResponses);
    }
}
