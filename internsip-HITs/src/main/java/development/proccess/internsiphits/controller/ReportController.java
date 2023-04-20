package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.ReportResponse;
import development.proccess.internsiphits.domain.dto.CreateReportDto;
import development.proccess.internsiphits.domain.entity.ReportEntity;
import development.proccess.internsiphits.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping
    public List<ReportEntity> getAllReports() {
        return service.getAllReports();
    }

    @PostMapping("/{userId}")
    public ReportResponse createReport(
            @PathVariable Integer userId,
            @RequestBody CreateReportDto body
    ) throws Exception {
        return service.createReport(userId, body);
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

    @DeleteMapping("/{userId}")
    public void deleteReport(@PathVariable Integer userId) {
        service.deleteReport(userId);
    }
}
