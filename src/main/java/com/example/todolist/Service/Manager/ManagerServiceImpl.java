package com.example.todolist.Service.Manager;

import com.example.todolist.Dto.Manager.ManagerRequestDto;
import com.example.todolist.Entity.Manager;
import com.example.todolist.Repository.Manager.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService{
    private final ManagerRepository managerRepository;
    @Override
    public Manager createManager(ManagerRequestDto managerRequestDto) {
        Manager manager = Manager.builder()
                .name(managerRequestDto.getName())
                .email(managerRequestDto.getEmail())
                .uuid(managerRequestDto.getUuid())
                .build();

        return managerRepository.createManager(manager);

    }
}
