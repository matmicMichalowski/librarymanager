package matmic.librarymanager.controllers;

import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.services.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest {

    @Mock
    private Model model;

    @Mock
    private ItemService itemService;

    private ItemController controller;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ItemController(itemService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showItemsList() throws Exception {
        Set<Item> items  = new HashSet<>();

        items.add(new Item());
        items.add(new Item());

        when(itemService.getItems()).thenReturn(items);

        ArgumentCaptor<Set<Item>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.showItemsList(model);

        assertEquals("item/itemlist", viewName);
        verify(itemService, times(1)).getItems();
        verify(model, times(1)).addAttribute(eq("items"), argumentCaptor.capture());
        Set<Item> controllerSet = argumentCaptor.getValue();
        assertEquals(2, controllerSet.size());

    }

    @Test
    public void showById() throws Exception {
        Item item = new Item();
        item.setId(1L);

        when(itemService.findById(anyLong())).thenReturn(item);

        mockMvc.perform(get("/item/1/display"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/itemdisplay"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    public void newItem() throws Exception {

        mockMvc.perform(get("/item/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/itemform"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        ItemCommand itemCommand = new ItemCommand();
        itemCommand.setId(2L);

        when(itemService.saveItem(any())).thenReturn(itemCommand);

        mockMvc.perform(post("/saveitem")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("title", "item title")
                .param("year", "1990"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/item/2/display"));

    }

    @Test
    public void updateItemForm() throws Exception {
        ItemCommand itemCommand = new ItemCommand();
        itemCommand.setId(1L);

        when(itemService.findItemCommandById(anyLong())).thenReturn(itemCommand);

        mockMvc.perform(get("/item/1/update/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/itemform"))
                .andExpect(model().attributeExists("item"));

    }

    @Test
    public void deleteItemById() throws Exception {

        mockMvc.perform(get("/item/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/item/itemlist"));

        verify(itemService, times(1)).deleteById(anyLong());
    }

}