package matmic.librarymanager.services;

import matmic.librarymanager.converter.ItemCommandToItem;
import matmic.librarymanager.converter.ItemToItemCommand;
import matmic.librarymanager.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemCommandToItem itemCommandToItem;

    @Mock
    private ItemToItemCommand itemToItemCommand;

    private ItemService itemService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        itemService = new ItemServiceImpl(itemRepository, itemCommandToItem, itemToItemCommand);
    }

    @Test
    public void findItemById() throws Exception {
    }

    @Test
    public void findItemCommandById() throws Exception {
    }

    @Test
    public void saveItem() throws Exception {
    }

    @Test
    public void getItems() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

}