package matmic.librarymanager.command;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeCommand {

    private Long id;


    @Email
    @NotEmpty(message = "{required}")
    private String email;


    @NotEmpty(message = "{required}")
    private String firstName;

    @NotEmpty(message = "{required}")
    private String lastName;

    @Pattern(regexp = "(?:\\d{3}-){2}\\d{3}", message = "Please provide your phone number in this pattern '123-123-123'")
    private String mobile;

}
