package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long>{
    Optional<Item> findItemByTitle(String title);
    Optional<Item> findItemById(Long id);
    List<Item> findItemByTitleOrderByTitleAsc(String title);
    List<Item> findItemByAuthorOrderByAuthorAsc(String author);
    List<Item> findItemByPublisherOrderByPublisherAsc(String publisher);
}
