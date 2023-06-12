package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CharacteristicsDto;
import development.proccess.internsiphits.domain.dto.StringDto;
import development.proccess.internsiphits.domain.entity.CharacteristicsEntity;
import development.proccess.internsiphits.exception.characteristics.CharacteristicsException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.CharacteristicsRepository;
import development.proccess.internsiphits.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class CharacteristicsService {

    private final UserRepository userRepository;
    private final CharacteristicsRepository characteristicsRepository;

    public Optional<CharacteristicsEntity> getByReportId(Integer reportId) {
        return characteristicsRepository.findById(reportId);
    }

    public List<CharacteristicsDto> getByUserId(Integer userId) {
        List<CharacteristicsEntity> entities = characteristicsRepository.findAllByUserId(userId);
        return !entities.isEmpty()
                ? entities.stream()
                .map(item ->
                        CharacteristicsDto
                                .builder()
                                .id(item.getId())
                                .userId(item.getUserId())
                                .content(item.getContent())
                                .build()
                )
                .collect(Collectors.toList()
                )
                : Collections.emptyList();
    }

    @SneakyThrows
    public void createCharacteristics(StringDto dto, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
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
