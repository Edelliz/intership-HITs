package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CompanyDto;
import development.proccess.internsiphits.domain.dto.CreateOfferDto;
import development.proccess.internsiphits.domain.dto.OfferDto;
import development.proccess.internsiphits.domain.dto.UpdateOfferDto;
import development.proccess.internsiphits.service.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/offers")
public class OfferController {

    private static final String USER_ID = "/{userId}";
    private static final String OFFER_ID = "/{offerId}";

    private final OfferService service;

    @Operation(summary = "Создание оффера")
    @PostMapping
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public OfferDto createOffer(@RequestBody CreateOfferDto dto) {
        return service.createOffer(dto);
    }

    @Operation(summary = "Получение оффера")
    @GetMapping(OFFER_ID)
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public OfferDto getOffer(@PathVariable Integer offerId) {
        return service.getOffer(offerId);
    }

    @Operation(summary = "Получение всех офферов пользователя")
    @GetMapping(USER_ID + "/user")
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE', 'STUDENT')")
    public List<OfferDto> getUserOffers(@PathVariable Integer userId) {
        return service.getUserOffers(userId);
    }

    @Operation(summary = "Удаление оффера")
    @DeleteMapping(OFFER_ID)
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public void deleteOffer(
            @PathVariable Integer offerId
    ) {
        service.deleteCompany(offerId);
    }

    @Operation(summary = "Обновление оффера(смена приоритета)")
    @PatchMapping(OFFER_ID)
//    @PreAuthorize(value = "hasAnyRole('UNIVERSITY_EMPLOYEE', 'COMPANY_EMPLOYEE')")
    public OfferDto updateOffer(@RequestBody UpdateOfferDto dto, @PathVariable Integer offerId) {
        return service.updateOffer(dto, offerId);
    }
}
