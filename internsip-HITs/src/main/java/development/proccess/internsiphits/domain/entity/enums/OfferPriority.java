package development.proccess.internsiphits.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OfferPriority {
    HIGH("Высокий"),
    MIDDLE("Средний"),
    UNIDENTIFIED("Неустановленный"),
    LOW("Низкий");

    private final String name;
}
