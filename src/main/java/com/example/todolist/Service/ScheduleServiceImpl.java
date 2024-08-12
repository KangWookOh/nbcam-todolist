package com.example.todolist.Service;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    LocalDate currentDate = LocalDate.now();

    @Override
    public Schedule createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = Schedule.builder()
                .task(scheduleRequestDto.getTask())
                .writer(scheduleRequestDto.getWriter())
                .password(scheduleRequestDto.getPassword())
                .build();
        return scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleResponseDto readByScheduleId(Long sid) {
        Schedule schedule = scheduleRepository.findById(sid);
        if(schedule == null) {
            throw new NoSuchElementException("일정이 존재 하지 않습니다");
        }
        ScheduleResponseDto scheduleResponseDto = ScheduleResponseDto.builder()
                .sid(sid)
                .task(schedule.getTask())
                .writer(schedule.getWriter())
                .password(schedule.getPassword())
                .regDate(schedule.getRegDate())
                .modDate(schedule.getModDate())
                .build();
        return scheduleResponseDto;
    }

    @Override
    public List<Schedule> getSchedule(String writer, LocalDate modDate) {
        return scheduleRepository.findByScheduleByWriterAndModDate(writer,modDate);
    }

    @Override
    public Schedule updateTaskAndWriter(Long sid, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(sid);
        if(!(schedule.getPassword().equals(scheduleRequestDto.getPassword()))){
            throw new RuntimeException("비밀번호가 일치 하지 않습니다.");
        }
        Schedule update = Schedule.builder()
                .sid(sid)
                .task(scheduleRequestDto.getTask())
                .writer(scheduleRequestDto.getWriter())
                .modDate(LocalDate.now())
                .build();
        return scheduleRepository.update(update);
    }

    @Override
    public void deleteBySid(Long sid) {
        Schedule schedule = scheduleRepository.findById(sid);
        if(schedule == null){
            throw new IllegalStateException("일정이 존재 하지 않습니다.");
        }
        scheduleRepository.deleteById(sid);


    }
}