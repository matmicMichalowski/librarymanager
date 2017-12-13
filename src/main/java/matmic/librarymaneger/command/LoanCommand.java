package matmic.librarymaneger.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanCommand {
    private Long id;

    private Long itemId;
    private Long userId;

}
