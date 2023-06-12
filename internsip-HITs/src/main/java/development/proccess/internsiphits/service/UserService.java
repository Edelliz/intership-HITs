package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CreateUpdateUserDto;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.domain.entity.enums.Role;
import development.proccess.internsiphits.exception.user.UserAlreadyExistsException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_EXISTS_MESSAGE;
import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    @Transactional
    public UserEntity getUserById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE)
        );
    }

    @Transactional
    public UserEntity createUser(CreateUpdateUserDto dto) throws Exception {
        Optional<UserEntity> user = repository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException(USER_EXISTS_MESSAGE);
        }
        return repository.save(
                UserEntity.builder()
                        .name(dto.getName())
                        .surname(dto.getSurname())
                        .lastName(dto.getLastName())
                        .role(dto.getRole())
                        .email(dto.getEmail())
                        .password(encoder.encode(dto.getPassword()))
                        .companyName(dto.getCompanyName())
                        .groupName(dto.getGroupName())
                        .build()
        );
    }

    @Transactional
    public UserEntity updateUser(Integer id, CreateUpdateUserDto dto) throws Exception {
        try {
            UserEntity user = repository.findById(id).orElseThrow(
                    () -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE)
            );
            user.setName(dto.getName()); //TODO: написать маппер
            user.setLastName(dto.getLastName());
            user.setSurname(dto.getSurname());
            user.setRole(dto.getRole());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setCompanyName(dto.getCompanyName());
            return repository.save(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }

    @Transactional
    public List<UserEntity> uploadListOfUsers(final MultipartFile file) {
        List<UserEntity> users = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheet("users");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                CreateUpdateUserDto userDto = new CreateUpdateUserDto();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> userDto.setName(cell.getStringCellValue());
                        case 1 -> userDto.setSurname(cell.getStringCellValue());
                        case 2 -> userDto.setLastName(cell.getStringCellValue());
                        case 3 -> userDto.setEmail(cell.getStringCellValue());
                        case 4 -> userDto.setPassword(cell.getStringCellValue());
                        case 5 -> userDto.setRole(Role.valueOf(cell.getStringCellValue()));
                        case 6 -> userDto.setCompanyName(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                users.add(createUser(userDto));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        repository.saveAll(users);
        return users;
    }
}
