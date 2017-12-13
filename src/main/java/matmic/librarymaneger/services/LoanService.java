package matmic.librarymaneger.services;


import matmic.librarymaneger.command.LoanCommand;

public interface LoanService {
    LoanCommand saveLoan(LoanCommand loanCommand);
    void deleteLoanById(Long loanId);
}
