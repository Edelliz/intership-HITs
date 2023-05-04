package development.proccess.internsiphits.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportStatus {
    CREATED("Создан"),
    REVIEW("На ревью"),
    FIX("На доработке"),
    APPROVED("Одобрен");

    private final String status;
}
