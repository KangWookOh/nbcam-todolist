package com.example.todolist.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Pattern;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public abstract class BaseTime {

    @Timestamp
    private LocalDateTime regDate;

    @Timestamp
    private LocalDateTime modDate;




}
