package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CharacteristicsDto;
import development.proccess.internsiphits.service.CharacteristicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students/{studentId}/characteristic")
@RequiredArgsConstructor
public class CharacteristicsController {

    private final CharacteristicsService service;

    @PostMapping
    public void create(
            @PathVariable Integer studentId,
            @RequestBody CharacteristicsDto dto
    ) {
        service.createCharacteristics(dto, studentId);
    }

    @GetMapping
    public CharacteristicsDto get(@PathVariable Integer studentId) {
        return service.getByUserId(studentId);
    }

    @DeleteMapping
    public void delete(@PathVariable Integer studentId) {
        service.delete(studentId);
    }
}
