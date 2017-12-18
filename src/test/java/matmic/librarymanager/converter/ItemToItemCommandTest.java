package matmic.librarymanager.converter;

import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.enums.DistributionType;
import matmic.librarymanager.model.enums.ItemType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemToItemCommandTest {

    private static final Long ID = 1L;
    private static final DistributionType DISTRIBUTION_TYPE = DistributionType.PAPER;
    private static final ItemType ITEM_TYPE = ItemType.BOOK;
    private static final String ISN = "2-1234-5680-2";
    private static final String TITLE = "Knowledge";
    private static final String PUBLISHER = "Best Publisher";
    private static final String RELEASE_NUMBER = "2";
    private static final String AUTHOR = "Tomas Samot";
    private static final String GENRE = "Drama";
    private static final int YEAR = 2001;

    private ItemToItemCommand converter;

    @Before
    public void setUp() throws Exception {

        converter = new ItemToItemCommand();
    }

    @Test
    public void convert() throws Exception {
        Item item = new Item();

        item.setId(ID);
        item.setYear(YEAR);
        item.setReleaseNumber(RELEASE_NUMBER);
        item.setPublisher(PUBLISHER);
        item.setInternationalSegregationNumber(ISN);
        item.setDistributionType(DISTRIBUTION_TYPE);
        item.setTitle(TITLE);
        item.setGenre(GENRE);
        item.setAuthor(AUTHOR);
        item.setItemType(ITEM_TYPE);

        ItemCommand itemCommand = converter.convert(item);

        assertNotNull(itemCommand);
        assertEquals(ID, itemCommand.getId());
        assertEquals(YEAR, itemCommand.getYear());
        assertEquals(RELEASE_NUMBER, itemCommand.getReleaseNumber());
        assertEquals(PUBLISHER, itemCommand.getPublisher());
        assertEquals(ISN, itemCommand.getInternationalSegregationNumber());
        assertEquals(DISTRIBUTION_TYPE, itemCommand.getDistributionType());
        assertEquals(TITLE, itemCommand.getTitle());
        assertEquals(GENRE, itemCommand.getGenre());
        assertEquals(AUTHOR, itemCommand.getAuthor());
        assertEquals(ITEM_TYPE, itemCommand.getItemType());

    }

}