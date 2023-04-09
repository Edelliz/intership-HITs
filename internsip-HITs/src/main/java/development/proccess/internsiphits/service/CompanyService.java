package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.CompanyDto;
import development.proccess.internsiphits.domain.dto.CreateCompanyDto;
import development.proccess.internsiphits.domain.dto.UpdateCompanyDto;
import development.proccess.internsiphits.domain.dto.VacancyDto;
import development.proccess.internsiphits.domain.entity.CompanyEntity;
import development.proccess.internsiphits.domain.entity.VacancyEntity;
import development.proccess.internsiphits.exception.company.CompanyNotFoundException;
import development.proccess.internsiphits.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyDto createCompany(CreateCompanyDto dto) {
        CompanyEntity entity = CompanyEntity.builder()
                .name(dto.getName())
                .fullName(dto.getFullName())
                .info(dto.getInfo())
                .vacancies(new ArrayList<>())
                .build();

        companyRepository.save(entity);

        return companyEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public CompanyDto getCompany(Integer id) {
        return companyEntityToDto(companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Компания с таким id не найдена: " + id))
        );
    }

    @Transactional(readOnly = true)
    public List<CompanyDto> getCompanies() {
        return companyRepository.findAll().stream().map(this::companyEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public CompanyDto updateCompany(UpdateCompanyDto dto, Integer id) {
        CompanyEntity entity = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Компания с таким id не найдена: " + id));

        if (!dto.getName().isEmpty()) {
            entity.setName(dto.getName());
        }

        companyRepository.save(entity);

        return companyEntityToDto(entity);
    }

    private CompanyDto companyEntityToDto(CompanyEntity entity) {
        return CompanyDto.builder()
                .name(entity.getName())
                .fullName(entity.getFullName())
                .info(entity.getInfo())
                .id(entity.getId())
                .vacancies(entity.getVacancies().stream().map(this::vacancyEntityToDto).collect(Collectors.toList()))
                .build();
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
