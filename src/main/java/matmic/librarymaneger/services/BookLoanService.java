package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.BookLoanCommand;

public interface BookLoanService {
    BookLoanCommand saveBookLoanCommand(BookLoanCommand loanCommand);
    void deleteLoanById(Long userId, Long loanId);
}
