package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MagazineLoanCommand {

    private Long id;
    private String loanDate;
    private MagazineCommand magazine;
}
