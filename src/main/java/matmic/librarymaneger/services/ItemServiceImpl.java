package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.ItemCommand;
import matmic.librarymaneger.converters.ItemCommandToItem;
import matmic.librarymaneger.converters.ItemToItemCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.repositories.ItemRepository;
import matmic.librarymaneger.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final ItemCommandToItem commandToItem;
    private final ItemToItemCommand itemToItemCommand;
    private final LoanRepository loanRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ItemCommandToItem commandToItem,
                           ItemToItemCommand itemToItemCommand, LoanRepository loanRepository) {
        this.itemRepository = itemRepository;
        this.commandToItem = commandToItem;
        this.itemToItemCommand = itemToItemCommand;
        this.loanRepository = loanRepository;
    }

    @Override
    public ItemCommand findCommandById(Long id) {
        Optional<Item> itemToFind = itemRepository.findItemById(id);
        if(!itemToFind.isPresent()){
            return null;
        }

        return itemToItemCommand.convert(itemToFind.get());
    }

    @Override
    public ItemCommand saveBookCommand(ItemCommand command) {
        Item toSave = commandToItem.convert(command);
        Item savedItem = itemRepository.save(toSave);
        return itemToItemCommand.convert(savedItem);
    }

    @Override
    public Set<Item> getItems() {
        Set<Item> items = new HashSet<>();

        itemRepository.findAll().iterator().forEachRemaining(items::add);
        System.out.println("hoplla!!" + items.size());
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
