package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.ReportResponse;
import development.proccess.internsiphits.domain.dto.CreateReportDto;
import development.proccess.internsiphits.domain.entity.ReportEntity;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.ReportRepository;
import development.proccess.internsiphits.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String TEMPLATE_PATH = "classpath:templates/input.docx";
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public List<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    public void deleteReport(Integer userId) {
        reportRepository.deleteByUserId(userId);
    }

    public ReportResponse createReport(Integer userId, CreateReportDto body) throws Exception {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE)
        );
        try (FileInputStream template = new FileInputStream(ResourceUtils.getFile(TEMPLATE_PATH))) {
            XWPFDocument document = new XWPFDocument(template);
            setDataIntoTable(document, "StudentFullName", (user.getSurname() + " " + user.getName() + " " + user.getLastName()));
            setDataIntoTable(document, "CompanyFullName", user.getCompanyName());
            setDataIntoRow(document, "CompanyFullName", user.getCompanyName());
            setDataIntoTable(document, "StudentFullCharacteristic", body.getStudentCharacteristic());
            setDataIntoTable(document, "SupervisorFullName", body.getSupervisorName().getFullName());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();
            byte[] bytes = outputStream.toByteArray();
            ReportEntity fileEntity = new ReportEntity();
            fileEntity.setUserId(userId);
            fileEntity.setName("test.docx");
            fileEntity.setContentType("application/msword");
            fileEntity.setData(bytes);
            fileEntity.setSize((long) bytes.length);
            fileEntity = reportRepository.save(fileEntity);
            return mapToReportResponse(fileEntity);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public Optional<ReportEntity> getReportById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    public ReportResponse getReportByUserId(Integer userId) {
        return mapToReportResponse(reportRepository.findByUserId(userId));
    }

    private ReportResponse mapToReportResponse(ReportEntity reportEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/reports/")
                .path(String.valueOf(reportEntity.getId()))
                .path("/download")
                .toUriString();
        ReportResponse fileResponse = new ReportResponse();
        fileResponse.setId(reportEntity.getId());
        fileResponse.setUserId(reportEntity.getUserId());
        fileResponse.setName(reportEntity.getName());
        fileResponse.setContentType(reportEntity.getContentType());
        fileResponse.setSize(reportEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    private static void setDataIntoRow(XWPFDocument document, String target, String data) {
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(target)) {
                        text = text.replace(target, data);
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

    private static void setDataIntoTable(XWPFDocument document, String target, String data) {
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        String tmp = paragraph.getText();
                        if (tmp.contains(target)) {
                            paragraph.getRuns().forEach(t -> t.setText("", 0));
                            XWPFRun run = paragraph.createRun();
                            run.setText(data);
                        }
                    }
                }
            }
        }
    }
}
