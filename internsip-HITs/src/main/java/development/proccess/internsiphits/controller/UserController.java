package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CreateUpdateUserDto;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private static final String STUDENTS = "/students";
    private static final String ID = "/{id}";
    private static final String EMPLOYEES = "/employees";

    private final UserService service;

    @GetMapping(STUDENTS)
    public List<UserEntity> getAllStudents() {
        return service.getAllUsers();
    }

    @GetMapping(STUDENTS + ID)
    public UserEntity getStudentById(
            @PathVariable Long id
    ) throws Exception {
        return service.getUserById(id);
    }

    @PostMapping(STUDENTS)
    public UserEntity createStudent(
            @RequestBody CreateUpdateUserDto dto
    ) throws Exception {
        return service.updateUser(dto);
    }

    @PutMapping(STUDENTS + ID)
    public UserEntity updateUser(
            @PathVariable Long id,
            @RequestBody CreateUpdateUserDto dto
    ) throws Exception {
        return service.updateUser(id, dto);
    }

    @DeleteMapping(STUDENTS + ID)
    public void deleteUser(
            @PathVariable Long id
    ) {
        service.deleteUser(id);
    }
}