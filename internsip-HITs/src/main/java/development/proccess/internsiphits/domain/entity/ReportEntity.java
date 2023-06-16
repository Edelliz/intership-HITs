package development.proccess.internsiphits.domain.entity;

import development.proccess.internsiphits.domain.entity.enums.Mark;
import development.proccess.internsiphits.domain.entity.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "REPORT")
@Data
public class ReportEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;

    private String name;

    private String contentType;

    private Long size;

    @Enumerated(value = EnumType.STRING)
    private Mark mark;

    @Enumerated(value = EnumType.STRING)
    private ReportStatus status;

    @Lob
    private byte[] data;

    private String username;

    private String group;
}
