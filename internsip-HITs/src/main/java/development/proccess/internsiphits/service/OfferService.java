package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CreateOfferDto;
import development.proccess.internsiphits.domain.dto.OfferDto;
import development.proccess.internsiphits.domain.dto.UpdateOfferDto;
import development.proccess.internsiphits.domain.dto.UserShortDto;
import development.proccess.internsiphits.domain.entity.CompanyEntity;
import development.proccess.internsiphits.domain.entity.OfferEntity;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.domain.entity.VacancyEntity;
import development.proccess.internsiphits.domain.entity.enums.OfferPriority;
import development.proccess.internsiphits.exception.company.CompanyNotFoundException;
import development.proccess.internsiphits.exception.offer.OfferNotFoundException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.exception.vacancy.VacancyNotFoundException;
import development.proccess.internsiphits.repository.CompanyRepository;
import development.proccess.internsiphits.repository.OfferRepository;
import development.proccess.internsiphits.repository.UserRepository;
import development.proccess.internsiphits.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public OfferDto createOffer(CreateOfferDto dto) {
        UserEntity userEntity = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден: " + dto.getUserId()));
        CompanyEntity companyEntity = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Компания с таким id не найдена: " + dto.getCompanyId()));
        VacancyEntity vacancyEntity = vacancyRepository.findById(dto.getVacancyId())
                .orElseThrow(() -> new VacancyNotFoundException("Вакансия с таким id не найдена: " + dto.getVacancyId()));
        OfferEntity offerEntity = OfferEntity.builder()
                .student(userEntity)
                .company(companyEntity)
                .vacancy(vacancyEntity)
                .priority(offerRepository.findAllByStudent(userEntity).size())
                .build();

        return entityToDto(offerRepository.save(offerEntity));
    }

    @Transactional
    public OfferDto updateOffer(UpdateOfferDto dto, Integer offerId) {
        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new OfferNotFoundException("Оффер с таким id не найден " + offerId));
        if (Objects.nonNull(dto.getPriority())) {
            offer.setPriority(dto.getPriority());
        }

        offerRepository.save(offer);

        return entityToDto(offer);
    }

    @Transactional
    public void deleteCompany(Integer offerId) {
        offerRepository.deleteById(offerId);
    }

    @Transactional
    public OfferDto getOffer(Integer offerId) {
        return entityToDto(offerRepository.findById(offerId)
                .orElseThrow(() -> new OfferNotFoundException("Оффер с таким id не найден " + offerId)));
    }

    @Transactional(readOnly = true)
    public List<OfferDto> getUserOffers(Integer userId) {
        List<OfferEntity> offers = offerRepository.findAllByStudent(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден: " + userId)));

        return   offers.stream()
                .map(this::entityToDto)
                .sorted(Comparator.comparing(OfferDto::getPriority).reversed())
                .collect(Collectors.toList());
    }

    private OfferDto entityToDto(OfferEntity entity) {
        UserEntity user = entity.getStudent();
        return OfferDto.builder()
                .id(entity.getId())
                .companyName(entity.getCompany().getName())
                .priority(entity.getPriority())
                .student(UserShortDto.builder()
                        .fullName(user.getSurname() + " " + user.getName() + " " + user.getLastName())
                        .id(user.getId()
                        ).build())
                .build();
    }

}