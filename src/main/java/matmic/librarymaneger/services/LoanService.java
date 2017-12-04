package matmic.librarymaneger.services;


import matmic.librarymaneger.model.Loan;

public interface LoanService {
    Loan saveLoan(Loan loanCommand);
    void deleteLoanById(Long loanId);
}
