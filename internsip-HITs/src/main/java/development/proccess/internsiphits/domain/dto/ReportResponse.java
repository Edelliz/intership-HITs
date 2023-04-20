package development.proccess.internsiphits.domain.dto;

import development.proccess.internsiphits.domain.entity.enums.Mark;
import lombok.Data;

@Data
public class ReportResponse {

    private Integer id;

    private Integer userId;

    private String name;

    private Long size;

    private String url;

    private String contentType;

    private Mark mark;
}
