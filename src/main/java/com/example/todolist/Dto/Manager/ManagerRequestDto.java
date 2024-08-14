package com.example.todolist.Dto.Manager;

import lombok.Builder;
import lombok.Data;

@Data
public class ManagerRequestDto {

    private String name;

    private String email;

    private String uuid;

    @Builder
    public ManagerRequestDto(String name, String email, String uuid) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
    }
}
