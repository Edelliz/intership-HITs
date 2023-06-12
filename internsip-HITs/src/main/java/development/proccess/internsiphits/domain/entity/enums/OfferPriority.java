package development.proccess.internsiphits.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OfferPriority {
    HIGH("Высокий", 3),
    MIDDLE("Средний", 2),
    UNIDENTIFIED("Неустановленный", 1),
    LOW("Низкий", 0);

    private final String name;
    private final Integer sequenceNumber;
}
