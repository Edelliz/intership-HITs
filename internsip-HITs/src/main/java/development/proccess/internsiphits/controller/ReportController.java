package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.ReportResponse;
import development.proccess.internsiphits.domain.dto.UpdateReportDto;
import development.proccess.internsiphits.domain.entity.ReportEntity;
import development.proccess.internsiphits.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping
    public List<ReportResponse> getAllReports() {
        return service.getAllReports();
    }

    @PostMapping("/{userId}")
    public ReportResponse createReport(
            @PathVariable Integer userId,
            @RequestParam String supervisorName,
            @RequestParam String characteristic,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        return service.createReport(userId, supervisorName, characteristic, file);
    }

    @GetMapping("/{userId}")
    public ReportResponse getReportInfo(@PathVariable Integer userId) {
        return service.getReportByUserId(userId);
    }

    @GetMapping("/{reportId}/download")
    public ResponseEntity<byte[]> getReportFile(@PathVariable Integer reportId) {
        Optional<ReportEntity> fileEntityOptional = service.getReportById(reportId);
        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ReportEntity report = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getName() + "\"")
                .contentType(MediaType.valueOf(report.getContentType()))
                .body(report.getData());
    }

    @PutMapping("/{userId}")
    public ReportResponse updateReport(@PathVariable Integer userId, @RequestBody UpdateReportDto dto) {
        return service.updateReport(userId, dto);
    }

    @DeleteMapping("/{userId}")
    public void deleteReport(@PathVariable Integer userId) {
        service.deleteReport(userId);
    }
}
