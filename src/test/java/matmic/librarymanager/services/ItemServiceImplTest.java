package matmic.librarymanager.services;

import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.converter.ItemCommandToItem;
import matmic.librarymanager.converter.ItemToItemCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        Item item = new Item();
        item.setId(1L);

        Optional<Item> itemOptional = Optional.of(item);

        when(itemRepository.findItemById(anyLong())).thenReturn(itemOptional);

        Item found = itemService.findItemById(1L);

        assertNotNull(found);
        verify(itemRepository, times(1)).findItemById(anyLong());
    }

    @Test
    public void findItemCommandById() throws Exception {
        ItemCommand item = new ItemCommand();
        item.setId(2L);


        when(itemToItemCommand.convert(any())).thenReturn(item);
        ItemCommand found = itemService.findItemCommandById(2L);

        assertNotNull(found);
        verify(itemToItemCommand, times(1)).convert(any());
    }

    @Test
    public void saveItem() throws Exception {
        Item item = new Item();
        item.setId(3L);

        when(itemCommandToItem.convert(any())).thenReturn(item);
        when(itemRepository.save(any())).thenReturn(item);

        ItemCommand toSave = new ItemCommand();
        toSave.setId(3L);

        when(itemToItemCommand.convert(any())).thenReturn(toSave);

        ItemCommand command = itemService.saveItem(toSave);

        assertNotNull(command);
        assertEquals(Long.valueOf(3L), command.getId());
        verify(itemCommandToItem, times(1)).convert(any());
        verify(itemRepository, times(1)).save(any());
        verify(itemRepository, never()).saveAll(anyIterable());

    }

    @Test
    public void getItems() throws Exception {
        Set<Item> items = new HashSet<>();

        items.add(new Item());
        items.add(new Item());

        when(itemRepository.findAll()).thenReturn(items);

        Set<Item> itemsFound = itemService.getItems();

        assertEquals(2, itemsFound.size());
        verify(itemRepository, times(1)).findAll();
        verify(itemRepository, never()).findById(anyLong());
    }

    @Test
    public void findById() throws Exception {
        Item item = new Item();
        item.setId(4L);
        Optional<Item> optional = Optional.of(item);

        when(itemRepository.findById(anyLong())).thenReturn(optional);

        Item found = itemService.findById(4L);

        assertNotNull(found);
        verify(itemRepository, times(1)).findById(anyLong());
    }

    @Test
    public void deleteById() throws Exception {
        Long deleted = 1L;

        itemService.deleteById(deleted);

        verify(itemRepository, times(1)).deleteById(anyLong());

    }

}