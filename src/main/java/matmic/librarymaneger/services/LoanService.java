package matmic.librarymaneger.services;


import matmic.librarymaneger.command.LoanCommand;
import matmic.librarymaneger.model.Loan;

import java.util.Set;

public interface LoanService {
    LoanCommand saveLoan(LoanCommand loanCommand);
    Set<Loan> getLoans();
    void deleteLoanById(Long loanId);
}
