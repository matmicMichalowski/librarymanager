package matmic.librarymaneger.services;


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
    private final LoanRepository loanRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                            LoanRepository loanRepository) {
        this.itemRepository = itemRepository;
        this.loanRepository = loanRepository;
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
    public Item saveItem(Item item) {

        Item savedItem = itemRepository.save(item);
        return savedItem;
    }

    @Override
    public Set<Item> getItems() {
        Set<Item> items = new HashSet<>();
        itemRepository.findAll().iterator().forEachRemaining(items::add);
        System.out.println(items.size() + " _");
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
