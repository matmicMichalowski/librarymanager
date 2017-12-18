package matmic.librarymanager.converter;

import lombok.Synchronized;
import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.model.Item;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class ItemToItemCommand implements Converter<Item, ItemCommand>{

    @Synchronized
    @Nullable
    @Override
    public ItemCommand convert(Item item) {
        if(item == null){
            return null;
        }

        final ItemCommand itemCommand = new ItemCommand();

        itemCommand.setId(item.getId());
        itemCommand.setAuthor(item.getAuthor());
        itemCommand.setGenre(item.getGenre());
        itemCommand.setTitle(item.getTitle());
        itemCommand.setDistributionType(item.getDistributionType());
        itemCommand.setInternationalSegregationNumber(item.getInternationalSegregationNumber());
        itemCommand.setItemType(item.getItemType());
        itemCommand.setPublisher(item.getPublisher());
        itemCommand.setReleaseNumber(item.getReleaseNumber());
        itemCommand.setYear(item.getYear());

        return itemCommand;
    }
}
