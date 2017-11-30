package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.ItemCommand;
import matmic.librarymaneger.model.Item;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



@Component
public class ItemToItemCommand implements Converter<Item, ItemCommand>{


    @Synchronized
    @Nullable
    @Override
    public ItemCommand convert(Item item) {
        if(item == null) {
            return null;
        }

        final ItemCommand itemCommand = new ItemCommand();
        itemCommand.setId(item.getId());
        itemCommand.setAuthor(item.getAuthor());
        itemCommand.setDistributionType(item.getDistributionType());
        itemCommand.setGenre(item.getGenre());
        itemCommand.setInternationalSegregationNumber(item.getInternationalSegregationNumber());
        itemCommand.setItemImage(item.getItemImage());
        itemCommand.setPublisher(item.getPublisher());
        itemCommand.setIsAvailable(item.getIsAvailable());
        itemCommand.setReleaseNumber(item.getReleaseNumber());
        itemCommand.setTitle(item.getTitle());
        itemCommand.setItemType(item.getItemType());
        itemCommand.setYear(item.getYear());
        itemCommand.setLoanId(item.getLoan().getId());

        return itemCommand;
    }
}
