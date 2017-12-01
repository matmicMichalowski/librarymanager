package matmic.librarymaneger.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeCommand {

    private Long id;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @NotBlank
    //@Size(min=8, max=25)
    private String password;

    @NotBlank(message = "*Please provide your name")
    @Size(min=3, max=20)
    private String firstName;

    @NotBlank(message = "*Please provide your last name")
    @Size(min=3, max=30)
    private String lastName;

    private boolean isActive;

    private Set<EmployeeRoleCommand> employeeRoles = new HashSet<>();
}
