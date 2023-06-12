package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CreateUpdateUserDto;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.exception.user.UserAlreadyExistsException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_EXISTS_MESSAGE;
import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE)
        );
    }

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
                        .group(dto.getGroup())
                        .build()
        );
    }

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

    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }
}
