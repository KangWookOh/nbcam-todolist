package com.example.todolist.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseTime {

    @CreatedDate
    @Timestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    @CreatedDate
    @Timestamp
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modDate;

    public BaseTime() {
    }


    public void updateModDate(LocalDate modDate) {
        this.modDate = modDate;
    }

    public BaseTime(LocalDate regDate, LocalDate modDate) {
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
