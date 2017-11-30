package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.ItemCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.repositories.LoanRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ItemCommandToItem implements Converter<ItemCommand, Item>{

    private final LoanRepository loanRepository;

    public ItemCommandToItem(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Item convert(ItemCommand itemCommand) {
        if(itemCommand == null) {
            return null;
        }

        final Item item = new Item();
        item.setId(itemCommand.getId());
        item.setAuthor(itemCommand.getAuthor());
        item.setDistributionType(itemCommand.getDistributionType());
        item.setGenre(itemCommand.getGenre());
        item.setInternationalSegregationNumber(itemCommand.getInternationalSegregationNumber());
        item.setItemImage(itemCommand.getItemImage());
        item.setPublisher(itemCommand.getPublisher());
        item.setIsAvailable(itemCommand.getIsAvailable());
        item.setReleaseNumber(itemCommand.getReleaseNumber());
        item.setTitle(itemCommand.getTitle());
        item.setItemType(itemCommand.getItemType());
        item.setYear(itemCommand.getYear());
        item.setLoan(loanRepository.findLoanById(itemCommand.getLoanId()));

        return item;
    }
}
