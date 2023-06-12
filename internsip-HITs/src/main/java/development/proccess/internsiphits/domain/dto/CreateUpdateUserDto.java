package development.proccess.internsiphits.domain.dto;

import development.proccess.internsiphits.domain.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUpdateUserDto {

    private static final String MESSAGE = "Обязательное поле";

    @NotBlank(message = MESSAGE)
    private String name;

    @NotBlank(message = MESSAGE)
    private String surname;

    @NotBlank(message = MESSAGE)
    private String lastName;

    @NotBlank(message = MESSAGE)
    private String email;

    @NotBlank(message = MESSAGE)
    private String password;

    private Role role;

    private String companyName;

    private String group;
}
