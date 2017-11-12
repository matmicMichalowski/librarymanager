package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.BookLoan;
import org.springframework.data.repository.CrudRepository;

public interface BookLoanRepository extends CrudRepository<BookLoan, Long>{
}
