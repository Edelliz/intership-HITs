package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CreateUpdateUserDto;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final String ID = "/{id}";

    private final UserService service;

    @Operation(summary = "Получение списка пользователей")
    @GetMapping
    public List<UserEntity> getAllStudents() {
        return service.getAllUsers();
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping(ID)
    public UserEntity getStudentById(
            @PathVariable Integer id
    ) {
        return service.getUserById(id);
    }

    @Operation(summary = "Создание пользователя")
    @PostMapping
    public UserEntity createStudent(
            @RequestBody @Valid CreateUpdateUserDto dto
    ) throws Exception {
        return service.createUser(dto);
    }

    @Operation(summary = "Редактирование пользователя")
    @PutMapping(ID)
    public UserEntity updateUser(
            @PathVariable Integer id,
            @RequestBody @Valid CreateUpdateUserDto dto
    ) throws Exception {
        return service.updateUser(id, dto);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping(ID)
    public void deleteUser(
            @PathVariable Integer id
    ) {
        service.deleteUser(id);
    }

    @Operation(summary = "Метод импорта пользователей")
    @PostMapping("/upload")
    public List<UserEntity> uploadListOfUsers(@RequestParam final MultipartFile file) throws IOException {
        return service.uploadListOfUsers(file);
    }
}