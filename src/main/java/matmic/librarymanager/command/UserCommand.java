package matmic.librarymanager.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {

    private Long id;

    @NotEmpty(message = "{required}")
    private String firstName;

    @NotEmpty(message = "{required}")
    private String lastName;


    @Pattern(regexp = "(?:\\d{3}-){2}\\d{3}", message = "{pattern.phoneNumber}")
    private String phoneNumber;

    @Email
    @NotEmpty(message = "{required}")
    private String email;

    @NotEmpty(message = "{required}")
    private String city;

    @NotEmpty(message = "{required}")
    private String address;


    @Pattern(regexp = "(?:\\d{2}-)\\d{3}", message = "{pattern.postCode}")
    private String postCode;
}
