package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.*;
import development.proccess.internsiphits.service.CompanyService;
import development.proccess.internsiphits.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private static final String USER_ID = "/{userId}";

    private final OfferService service;

    @PostMapping
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public OfferDto createOffer(@RequestBody CreateOfferDto dto) {
        return service.createOffer(dto);
    }

    @GetMapping()
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public CompanyDto getOffer(@PathVariable Integer id) {
        return service.getCompany(id);
    }

    @GetMapping(USER_ID + "/user")
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public List<OfferDto> getUserOffers(@PathVariable Integer userId) {
        return service.getUserOffers();
    }

    @DeleteMapping(ID)
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public void deleteOffer(@PathVariable Integer id) {
        service.deleteCompany(id);
    }

    @PatchMapping(ID)
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public CompanyDto updateOffery(@RequestBody UpdateCompanyDto dto, @PathVariable Integer id) {
        return service.updateCompany(dto, id);
    }
}
