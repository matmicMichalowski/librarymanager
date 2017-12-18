package matmic.librarymanager.services;


import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.converter.ItemCommandToItem;
import matmic.librarymanager.converter.ItemToItemCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final ItemCommandToItem itemCommandToItem;
    private final ItemToItemCommand itemToItemCommand;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemCommandToItem itemCommandToItem, ItemToItemCommand itemToItemCommand) {
        this.itemRepository = itemRepository;
        this.itemCommandToItem = itemCommandToItem;
        this.itemToItemCommand = itemToItemCommand;
    }

    @Override
    public Item findItemById(Long id) {
        Optional<Item> itemToFind = itemRepository.findItemById(id);
        if(!itemToFind.isPresent()){
            return null;
        }

        return itemToFind.get();
    }

    @Override
    @Transactional
    public ItemCommand findItemCommandById(Long id){
        return itemToItemCommand.convert(findItemById(id));
    }

    @Override
    public ItemCommand saveItem(ItemCommand itemToSave) {
        Item detachedItem = itemCommandToItem.convert(itemToSave);

        Item savedItem = itemRepository.save(detachedItem);
        return itemToItemCommand.convert(savedItem);
    }

    @Override
    public Set<Item> getItems() {
        Set<Item> items = new HashSet<>();
        itemRepository.findAll().iterator().forEachRemaining(items::add);
        return items;
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);

        if(!itemOptional.isPresent()){
            throw new RuntimeException("No item with give id");
        }

        return itemOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
