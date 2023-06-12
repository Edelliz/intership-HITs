package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CompanyDto;
import development.proccess.internsiphits.domain.dto.CreateCompanyDto;
import development.proccess.internsiphits.domain.dto.UpdateCompanyDto;
import development.proccess.internsiphits.service.CompanyService;
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
@RequestMapping("/api/companies")
public class CompanyController {

    private static final String ID = "/{id}";

    private final CompanyService service;

    @Operation(summary = "Создание компании")
    @PostMapping
    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public CompanyDto createCompany(@RequestBody CreateCompanyDto dto) {
        return service.createCompany(dto);
    }

    @Operation(summary = "Получение компании")
    @GetMapping(ID)
    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public CompanyDto getCompany(@PathVariable Integer id) {
        return service.getCompany(id);
    }

    @Operation(summary = "Получение списка компаний")
    @GetMapping()
    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public List<CompanyDto> getCompanies() {
        return service.getCompanies();
    }

    @Operation(summary = "Удаление компании")
    @DeleteMapping(ID)
    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public void deleteCompany(@PathVariable Integer id) {
        service.deleteCompany(id);
    }

    @Operation(summary = "Обновление компании")
    @PatchMapping(ID)
    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public CompanyDto updateCompany(@RequestBody UpdateCompanyDto dto, @PathVariable Integer id) {
        return service.updateCompany(dto, id);
    }

}
