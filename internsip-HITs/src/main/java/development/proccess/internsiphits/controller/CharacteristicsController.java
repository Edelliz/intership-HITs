package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.CharacteristicsDto;
import development.proccess.internsiphits.domain.dto.StringDto;
import development.proccess.internsiphits.service.CharacteristicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/{studentId}/characteristic")
@RequiredArgsConstructor
public class CharacteristicsController {

    private final CharacteristicsService service;

    @PostMapping
    public void create(
            @PathVariable Integer studentId,
            @RequestBody StringDto dto
    ) {
        service.createCharacteristics(dto, studentId);
    }

    @GetMapping
    public List<CharacteristicsDto> get(@PathVariable Integer studentId) {
        return service.getByUserId(studentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacteristicsDto> getById(@PathVariable Integer id) {
        return service.getByReportId(id).map(
                item -> ResponseEntity.ok(
                        CharacteristicsDto.builder()
                                .content(item.getContent())
                                .id(item.getId())
                                .userId(item.getUserId())
                                .build()
                )
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @DeleteMapping
    public void delete(@PathVariable Integer studentId) {
        service.delete(studentId);
    }
}
