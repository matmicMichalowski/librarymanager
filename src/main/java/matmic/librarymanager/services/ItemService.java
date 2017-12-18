package matmic.librarymanager.services;


import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.model.Item;

import java.util.Set;

public interface ItemService {
    Item findItemById(Long id);
    ItemCommand findItemCommandById(Long id);
    ItemCommand saveItem(ItemCommand itemToSave);
    Set<Item> getItems();
    Item findById(Long id);
    void deleteById(Long id);
}
