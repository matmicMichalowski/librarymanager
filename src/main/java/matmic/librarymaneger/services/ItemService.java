package matmic.librarymaneger.services;


import matmic.librarymaneger.model.Item;

import java.util.Set;

public interface ItemService {
    Item findItemById(Long id);
    Item saveItem(Item item);
    Set<Item> getItems();
    Item findById(Long id);
    void deleteById(Long id);
}
