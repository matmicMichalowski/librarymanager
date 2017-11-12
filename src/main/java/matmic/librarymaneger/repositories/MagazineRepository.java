package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.Magazine;
import org.springframework.data.repository.CrudRepository;

public interface MagazineRepository extends CrudRepository<Magazine, Long> {
}
