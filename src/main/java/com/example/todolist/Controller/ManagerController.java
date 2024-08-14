package com.example.todolist.Controller;

import com.example.todolist.Dto.Manager.ManagerRequestDto;
import com.example.todolist.Entity.Manager;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Service.Manager.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;
    @PostMapping("/register")
    public ResponseEntity<Manager> managerRegister(@Valid @RequestBody ManagerRequestDto managerRequestDto){
        return ResponseEntity.ok(managerService.createManager(managerRequestDto));
    }
}
