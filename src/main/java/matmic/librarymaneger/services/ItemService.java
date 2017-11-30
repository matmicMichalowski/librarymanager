package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.ItemCommand;
import matmic.librarymaneger.model.Item;

import java.util.Set;

public interface ItemService {
    ItemCommand findCommandById(Long id);
    ItemCommand saveBookCommand(ItemCommand command);
    Set<Item> getItems();
    Item findById(Long id);
    void deleteById(Long id);
}
