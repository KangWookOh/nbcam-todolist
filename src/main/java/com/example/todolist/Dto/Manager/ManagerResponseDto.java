package com.example.todolist.Dto.Manager;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerResponseDto {

    private Long mid;

    private String name;

    private String email;

    private String uuid;

    LocalDate regDate;

    LocalDate modDate;

    @Builder
    public ManagerResponseDto(Long mid, String name, String email, String uuid, LocalDate regDate, LocalDate modDate) {
        this.mid = mid;
        this.name = name;
        this.email = email;
        this.uuid = uuid;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
