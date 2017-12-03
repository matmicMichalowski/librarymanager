package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoanCommand {

    private Long id;
    private String loanDate;
    private UserCommand user;
    private ItemCommand item;

}
