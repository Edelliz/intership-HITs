package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.VacancyDto;
import development.proccess.internsiphits.domain.dto.CreateVacancyDto;
import development.proccess.internsiphits.domain.dto.UpdateVacancyDto;
import development.proccess.internsiphits.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companies/{companyId}/vacancies")
public class VacancyController {
    private static final String ID = "/{id}";

    private final VacancyService service;

    @Operation(summary = "Создание вакансии")
    @PostMapping
    @PreAuthorize("hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public VacancyDto createVacancy(@RequestBody CreateVacancyDto dto, @PathVariable Integer companyId) {
        return service.createVacancy(dto, companyId);
    }

    @Operation(summary = "Получение вакансии")
    @GetMapping(ID)
    @PreAuthorize("hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public VacancyDto getVacancy(@PathVariable Integer id, @PathVariable Integer companyId) {
        return service.getVacancy(id);
    }

    @Operation(summary = "Получение всех вакансий компании")
    @GetMapping()
    @PreAuthorize("hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public List<VacancyDto> getVacancies(@PathVariable Integer companyId) {
        return service.getVacancies(companyId);
    }

    @Operation(summary = "Удаление вакансии")
    @DeleteMapping(ID)
    @PreAuthorize("hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public void deleteVacancy(@PathVariable Integer id, @PathVariable Integer companyId) {
        service.deleteVacancy(id);
    }

    @Operation(summary = "Обновление вакансии")
    @PatchMapping(ID)
    @PreAuthorize("hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public VacancyDto updateVacancy(
            @RequestBody UpdateVacancyDto dto,
            @PathVariable Integer id,
            @PathVariable Integer companyId) {
        return service.updateVacancy(dto, id);
    }
}
