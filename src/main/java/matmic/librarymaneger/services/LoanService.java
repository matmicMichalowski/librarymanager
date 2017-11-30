package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.LoanCommand;

public interface LoanService {
    LoanCommand saveLoanCommand(LoanCommand loanCommand, Long itemId);
    void deleteLoanById(Long userId, Long loanId);
}
