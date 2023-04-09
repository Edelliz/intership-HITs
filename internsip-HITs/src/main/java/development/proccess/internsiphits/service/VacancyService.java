package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CreateVacancyDto;
import development.proccess.internsiphits.domain.dto.UpdateVacancyDto;
import development.proccess.internsiphits.domain.dto.VacancyDto;
import development.proccess.internsiphits.domain.entity.VacancyEntity;
import development.proccess.internsiphits.exception.company.CompanyNotFoundException;
import development.proccess.internsiphits.exception.vacancy.VacancyNotFoundException;
import development.proccess.internsiphits.repository.CompanyRepository;
import development.proccess.internsiphits.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    private final CompanyRepository companyRepository;

    @Transactional
    public VacancyDto createVacancy(CreateVacancyDto dto, Integer companyId) {
        VacancyEntity entity = VacancyEntity.builder()
                .name(dto.getName())
                .requirements(dto.getRequirements())
                .description(dto.getDescription())
                .company(companyRepository.findById(companyId)
                        .orElseThrow(() -> new CompanyNotFoundException("Компания с таким id не найдена: " + companyId)))
                .build();

        vacancyRepository.save(entity);

        return vacancyEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public VacancyDto getVacancy(Integer id) {
        return vacancyEntityToDto(vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException("Вакансия с таким id не найдена: " + id)));
    }

    @Transactional(readOnly = true)
    public List<VacancyDto> getVacancies(Integer companyId) {
        return vacancyRepository.findByCompanyId(companyId).stream()
                .map(this::vacancyEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteVacancy(Integer id) {
        vacancyRepository.deleteById(id);
    }

    @Transactional
    public VacancyDto updateVacancy(UpdateVacancyDto dto, Integer id) {
        VacancyEntity entity = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException("Вакансия с таким id не найдена: " + id));

        if (!dto.getDescription().isEmpty()) {
            entity.setDescription(dto.getDescription());
        }

        vacancyRepository.save(entity);

        return vacancyEntityToDto(entity);
    }


    private VacancyDto vacancyEntityToDto(VacancyEntity entity) {
        return VacancyDto.builder()
                .companyId(entity.getCompany().getId())
                .companyName(entity.getCompany().getName())
                .description(entity.getDescription())
                .requirements(entity.getRequirements())
                .name(entity.getName())
                .id(entity.getId())
                .build();
    }
}
