package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long>{
}
