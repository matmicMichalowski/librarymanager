package matmic.librarymaneger.services;


import matmic.librarymaneger.commands.MagazineLoanCommand;


public interface MagazineLoanService {
    MagazineLoanCommand saveMagazineLoanCommand(MagazineLoanCommand loanCommand);
    void deleteLoanById(Long userId, Long loanId);
}
