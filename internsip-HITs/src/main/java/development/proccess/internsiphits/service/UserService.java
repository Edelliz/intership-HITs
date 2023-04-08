package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CreateUpdateUserDto;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    public UserEntity getUserById(Long id) throws Exception {
        try {
            return repository.findById(id).orElseThrow(); //TODO: добавить нормальную обработку ошибок
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public UserEntity updateUser(CreateUpdateUserDto dto) throws Exception {
        try {
            return repository.save( //TODO: обработать ситуацию когда email существует
                    UserEntity.builder()
                            .name(dto.getName())
                            .surname(dto.getSurname())
                            .lastName(dto.getLastName())
                            .role(dto.getRole())
                            .email(dto.getEmail())
                            .password(dto.getPassword())
                            .companyName(dto.getCompanyName())
                            .build()
            );
        } catch (Exception e) {
            throw new Exception(e);//TODO: добавить нормальную обработку ошибок
        }
    }

    public UserEntity updateUser(Long id, CreateUpdateUserDto dto) throws Exception {
        try {
            UserEntity user = repository.findById(id).orElseThrow(); //TODO: работа с ошибками
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

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
