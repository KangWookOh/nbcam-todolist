package com.example.todolist.Service.Manager;

import com.example.todolist.Dto.Manager.ManagerRequestDto;
import com.example.todolist.Entity.Manager;

public interface ManagerService {

    Manager createManager(ManagerRequestDto managerRequestDto);
}
