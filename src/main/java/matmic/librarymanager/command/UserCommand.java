package matmic.librarymanager.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {

    private Long id;

    @NotEmpty(message = "Please provide user First Name")
    private String firstName;

    @NotEmpty(message = "Please provide user Last Name")
    private String lastName;

    @NotEmpty(message = "Please provide user Phone Number")
    private String phoneNumber;

    @Email
    @NotEmpty(message = "Please provide user Email")
    private String email;
    private String city;

    @NotEmpty(message = "Please provide user Address")
    private String address;
    private String postCode;
}
