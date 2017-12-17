package matmic.librarymaneger.converter;

import matmic.librarymaneger.command.ItemCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.model.enums.DistributionType;
import matmic.librarymaneger.model.enums.ItemType;
import matmic.librarymaneger.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemCommandToItemTest {

    private static final Long ID = 2L;
    private static final DistributionType DISTRIBUTION_TYPE = DistributionType.PAPER;
    private static final ItemType ITEM_TYPE = ItemType.MAGAZINE;
    private static final String ISN = "0317-8471";
    private static final String TITLE = "Deep Science";
    private static final String PUBLISHER = "Great Publisher";
    private static final String RELEASE_NUMBER = "12/2017";
    private static final String AUTHOR = "Editor-in-chief";
    private static final String GENRE = "Science";
    private static final int YEAR = 2017;


    @Mock
    private ItemRepository itemRepository;

    private ItemCommandToItem converter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new ItemCommandToItem(itemRepository);
    }

    @Test
    public void convert() throws Exception {
        ItemCommand itemCommand = new ItemCommand();

        itemCommand.setId(ID);
        itemCommand.setYear(YEAR);
        itemCommand.setReleaseNumber(RELEASE_NUMBER);
        itemCommand.setPublisher(PUBLISHER);
        itemCommand.setInternationalSegregationNumber(ISN);
        itemCommand.setDistributionType(DISTRIBUTION_TYPE);
        itemCommand.setTitle(TITLE);
        itemCommand.setGenre(GENRE);
        itemCommand.setAuthor(AUTHOR);
        itemCommand.setItemType(ITEM_TYPE);

        Item item = converter.convert(itemCommand);

        assertNotNull(item);
        assertEquals(ID, item.getId());
        assertEquals(YEAR, item.getYear());
        assertEquals(RELEASE_NUMBER, item.getReleaseNumber());
        assertEquals(PUBLISHER, item.getPublisher());
        assertEquals(ISN, item.getInternationalSegregationNumber());
        assertEquals(DISTRIBUTION_TYPE, item.getDistributionType());
        assertEquals(TITLE, item.getTitle());
        assertEquals(GENRE, item.getGenre());
        assertEquals(AUTHOR, item.getAuthor());
        assertEquals(ITEM_TYPE, item.getItemType());
    }
}