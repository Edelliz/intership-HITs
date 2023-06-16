package development.proccess.internsiphits.service;

import development.proccess.internsiphits.domain.dto.ReportResponse;
import development.proccess.internsiphits.domain.dto.UpdateReportDto;
import development.proccess.internsiphits.domain.entity.CharacteristicsEntity;
import development.proccess.internsiphits.domain.entity.ReportEntity;
import development.proccess.internsiphits.domain.entity.UserEntity;
import development.proccess.internsiphits.domain.entity.enums.ReportStatus;
import development.proccess.internsiphits.domain.entity.enums.SupervisorName;
import development.proccess.internsiphits.exception.characteristics.CharacteristicsException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import development.proccess.internsiphits.repository.CharacteristicsRepository;
import development.proccess.internsiphits.repository.ReportRepository;
import development.proccess.internsiphits.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static development.proccess.internsiphits.exception.user.UserExceptionText.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private static final String TEMPLATE_PATH = "classpath:templates/input.docx";
    private final ReportRepository reportRepository;
    private final CharacteristicsRepository characteristicsRepository;
    private final UserRepository userRepository;

    public ReportResponse updateReport(Integer reportId, UpdateReportDto dto) {
        ReportEntity entity = reportRepository.findById(reportId).orElseThrow(
                () -> new IllegalArgumentException("There's no report with such id")
        );
        entity.setStatus(dto.getStatus());
        entity.setMark(dto.getMark());
        reportRepository.save(entity);
        return mapToReportResponse(entity);
    }

    public List<ReportResponse> getAllReports() {
        List<ReportEntity> reports = reportRepository.findAll();
        List<ReportResponse> responses = new ArrayList<>();
        for (ReportEntity entity : reports) {
            responses.add(mapToReportResponse(entity));
        }
        return responses;
    }

    public void deleteReport(Integer reportId) {
        reportRepository.deleteById(reportId);
    }

    public ReportResponse createReport(
            Integer userId,
            String supervisorName,
            Integer characteristicId,
            MultipartFile file
    ) throws Exception {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE)
        );
        Optional<CharacteristicsEntity> characteristic = characteristicsRepository.findById(characteristicId);
        if (characteristic.isEmpty()) {
            throw new CharacteristicsException("There's no characteristics for this user");
        }
        try (FileInputStream template = new FileInputStream(ResourceUtils.getFile(TEMPLATE_PATH))) {
            byte[] bytes = setData(supervisorName, characteristic.get().getContent(), user, template, file);
            ReportEntity fileEntity = new ReportEntity();
            fileEntity.setUserId(userId);
            fileEntity.setName("test.docx");
            fileEntity.setContentType("application/msword");
            fileEntity.setData(bytes);
            fileEntity.setSize((long) bytes.length);
            fileEntity.setStatus(ReportStatus.REVIEW);
            fileEntity.setUsername(user.getSurname() + " " + user.getName() + " " + user.getLastName());
            fileEntity.setGroup(user.getGroupName());
            fileEntity = reportRepository.save(fileEntity);
            return mapToReportResponse(fileEntity);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private byte[] setData(
            String supervisorName,
            String characteristic,
            UserEntity user,
            FileInputStream template,
            MultipartFile file
    ) throws IOException {
        XWPFDocument document = new XWPFDocument(template);
        setDataIntoTable(document, "StudentFullName", (user.getSurname() + " " + user.getName() + " " + user.getLastName()));
        setDataIntoTable(document, "CompanyFullName", user.getCompanyName());
        setDataIntoTable(document, "StudentFullCharacteristic", characteristic);
        setDataIntoTable(document, "SupervisorFullName", SupervisorName.valueOf(supervisorName).getFullName());

        String fileName = UUID.randomUUID() + Objects.requireNonNull(file.getOriginalFilename());
        File convFile = new File(fileName);
        if (convFile.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        }
        FileInputStream fis = new FileInputStream(convFile.getAbsolutePath());
        XWPFDocument tasks = new XWPFDocument(fis);

        String marker = "AllTasksList";

        XWPFParagraph resultParagraph = null;
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            boolean found = false;
            List<XWPFRun> runs = paragraph.getRuns();
            if (runs != null && !runs.isEmpty()) {
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null && text.contains(marker)) {
                        found = true;
                        text = text.replace(marker, "");
                        run.setText(text, 0);
                    }
                }
            }
            if (found) {
                resultParagraph = paragraph;
                break;
            }
        }
        if (resultParagraph != null) {
            copyAllRunsToAnotherParagraph(tasks.getParagraphs().get(0), resultParagraph);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        tasks.close();
        Path fileToDelete = Paths.get(fileName);
        Files.delete(fileToDelete);
        return outputStream.toByteArray();
    }

    private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar) {
        final int DEFAULT_FONT_SIZE = 10;

        for (XWPFRun run : oldPar.getRuns()) {
            String textInRun = run.getText(0);
            if (textInRun == null || textInRun.isEmpty()) {
                continue;
            }

            int fontSize = run.getFontSize();

            XWPFRun newRun = newPar.createRun();

            // Copy text
            newRun.setText(textInRun);

            // Apply the same style
            newRun.setFontSize((fontSize == -1) ? DEFAULT_FONT_SIZE : run.getFontSize());
            newRun.setFontFamily(run.getFontFamily());
            newRun.setBold(run.isBold());
            newRun.setItalic(run.isItalic());
            newRun.setStrike(run.isStrike());
            newRun.setColor(run.getColor());
        }
    }


    public Optional<ReportEntity> getReportById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    public ReportResponse getReportByUserId(Integer userId) {
        return mapToReportResponse(reportRepository.findByUserId(userId));
    }

    public ReportResponse mapToReportResponse(ReportEntity reportEntity) {
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
        fileResponse.setMark(reportEntity.getMark());
        fileResponse.setStatus(reportEntity.getStatus());
        fileResponse.setGroup(reportEntity.getGroup());
        fileResponse.setUsername(reportEntity.getUsername());

        return fileResponse;
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
