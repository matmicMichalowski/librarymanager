package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.LoanCommand;

public interface LoanService {
    LoanCommand saveLoanCommand(LoanCommand loanCommand);
    void deleteLoanById(Long loanId);
}
