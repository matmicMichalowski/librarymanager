package matmic.librarymaneger.converter;

import lombok.Synchronized;
import matmic.librarymaneger.command.ItemCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.repositories.ItemRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemCommandToItem  implements Converter<ItemCommand, Item>{


    private final ItemRepository itemRepository;

    public ItemCommandToItem(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Item convert(ItemCommand itemCommand) {

        if(itemCommand == null){
            return null;
        }

        Optional<Item> itemOptional = itemRepository.findItemById(itemCommand.getId());

        Item item;

        if(itemOptional.isPresent()){
            item = itemOptional.get();
        }else{
            item = new Item();
        }

        item.setId(itemCommand.getId());
        item.setAuthor(itemCommand.getAuthor());
        item.setGenre(itemCommand.getGenre());
        item.setTitle(itemCommand.getTitle());
        item.setDistributionType(itemCommand.getDistributionType());
        item.setInternationalSegregationNumber(itemCommand.getInternationalSegregationNumber());
        item.setItemType(itemCommand.getItemType());
        item.setPublisher(itemCommand.getPublisher());
        item.setReleaseNumber(itemCommand.getReleaseNumber());
        item.setYear(itemCommand.getYear());

        return item;
    }
}
