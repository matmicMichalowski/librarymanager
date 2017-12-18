package matmic.librarymanager.repositories;

import matmic.librarymanager.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long>{
    Optional<Item> findItemByTitle(String title);
    Optional<Item> findItemById(Long id);
}
