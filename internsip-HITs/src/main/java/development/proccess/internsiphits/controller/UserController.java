package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CreateUpdateUserDto;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final String ID = "/{id}";

    private final UserService service;

    @GetMapping
    public List<UserEntity> getAllStudents() {
        return service.getAllUsers();
    }

    @GetMapping(ID)
    public UserEntity getStudentById(
            @PathVariable Long id
    ) {
        return service.getUserById(id);
    }

    @PostMapping
    public UserEntity createStudent(
            @RequestBody @Valid CreateUpdateUserDto dto
    ) throws Exception {
        return service.createUser(dto);
    }

    @PutMapping(ID)
    public UserEntity updateUser(
            @PathVariable Long id,
            @RequestBody @Valid CreateUpdateUserDto dto
    ) throws Exception {
        return service.updateUser(id, dto);
    }

    @DeleteMapping(ID)
    public void deleteUser(
            @PathVariable Long id
    ) {
        service.deleteUser(id);
    }
}