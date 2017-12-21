package matmic.librarymanager.services;


import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.model.Loan;

import java.util.Set;

public interface LoanService {
    LoanCommand saveLoan(LoanCommand loanCommand);
    Set<Loan> getLoans();
    void updateLoansDeadline();
    void deleteLoanById(Long loanId);
}
