package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long>{
    Optional<Item> findItemByTitle(String title);
}
