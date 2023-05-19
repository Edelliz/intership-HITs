package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CharacteristicsDto;
import development.proccess.internsiphits.domain.entity.CharacteristicsEntity;
import development.proccess.internsiphits.exception.characteristics.CharacteristicsException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.CharacteristicsRepository;
import development.proccess.internsiphits.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class CharacteristicsService {

    private final UserRepository userRepository;
    private final CharacteristicsRepository characteristicsRepository;
    private static final String CHARACTERISTICS_EXISTS_MESSAGE = "Characteristics for such user is already exists";

    public CharacteristicsDto getByUserId(Integer userId) {
        CharacteristicsEntity entity = characteristicsRepository.findByUserId(userId);
        return entity != null
                ? CharacteristicsDto.builder().content(entity.getContent()).build()
                : null;
    }

    @SneakyThrows
    public void createCharacteristics(CharacteristicsDto dto, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
        } else if (characteristicsRepository.existsByUserId(userId)) {
            throw new CharacteristicsException(CHARACTERISTICS_EXISTS_MESSAGE);
        }

        CharacteristicsEntity entity = CharacteristicsEntity.builder()
                .userId(userId)
                .content(dto.getContent())
                .build();
        characteristicsRepository.save(entity);
    }

    public void delete(Integer userId) {
        characteristicsRepository.deleteByUserId(userId);
    }
}
