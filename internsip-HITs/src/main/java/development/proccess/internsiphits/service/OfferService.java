package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CreateOfferDto;
import development.proccess.internsiphits.domain.dto.OfferDto;
import development.proccess.internsiphits.domain.dto.UserShortDto;
import development.proccess.internsiphits.domain.entity.CompanyEntity;
import development.proccess.internsiphits.domain.entity.OfferEntity;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.domain.entity.enums.OfferPriority;
import development.proccess.internsiphits.exception.company.CompanyNotFoundException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.CompanyRepository;
import development.proccess.internsiphits.repository.OfferRepository;
import development.proccess.internsiphits.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public OfferDto createOffer(CreateOfferDto dto) {
        UserEntity userEntity = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден: " + dto.getUserId()));
        CompanyEntity companyEntity = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Компания с таким id не найдена: " + dto.getCompanyId()));
        OfferEntity offerEntity = OfferEntity.builder()
                .student(userEntity)
                .company(companyEntity)
                .priority(OfferPriority.UNIDENTIFIED)
                .build();

        return entityToDto(offerRepository.save(offerEntity));
    }

    public List<OfferDto> getUserOffers() {

    }

    private OfferDto entityToDto(OfferEntity entity) {
        UserEntity user = entity.getStudent();
        return OfferDto.builder()
                .id(entity.getId())
                .companyName(entity.getCompany().getName())
                .priority(entity.getPriority().getName())
                .student(UserShortDto.builder()
                        .fullName(user.getSurname() + " " + user.getName() + " " + user.getLastName())
                        .id(user.getId()
                        ).build())
                .build();
    }

}