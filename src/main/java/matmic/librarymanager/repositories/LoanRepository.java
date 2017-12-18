package matmic.librarymanager.repositories;

import matmic.librarymanager.model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long>{
    Loan findLoanById(Long id);
}
