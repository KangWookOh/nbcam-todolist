package com.example.todolist.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends  BaseTime{


    private Long mid;

    private String name;

    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String uuid;

    @Builder
    public Manager(Long mid, String name, String email, String uuid, LocalDateTime regDate, LocalDateTime modDate) {
        this.mid = mid;
        this.name = name;
        this.email = email;
        this.uuid = uuid;
        this.setRegDate(regDate);
        this.setModDate(modDate);
    }
}
