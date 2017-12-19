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
    @NotEmpty(message = "Please provide an email")
    private String email;


    @NotEmpty(message = "Please provide your name.")
    private String firstName;

    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @Pattern(regexp = "(?:\\d{3}-){2}\\d{3}", message = "Please provide your phone number in this pattern '123-123-123'")
    @NotEmpty(message = "Please provide your mobile phone in this way '999-999-999'")
    private String mobile;

}
