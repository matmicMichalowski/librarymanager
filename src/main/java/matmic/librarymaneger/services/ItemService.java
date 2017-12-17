package matmic.librarymaneger.services;


import matmic.librarymaneger.command.ItemCommand;
import matmic.librarymaneger.model.Item;

import java.util.Set;

public interface ItemService {
    Item findItemById(Long id);
    ItemCommand findItemCommandById(Long id);
    ItemCommand saveItem(ItemCommand itemToSave);
    Set<Item> getItems();
    Item findById(Long id);
    void deleteById(Long id);
}
