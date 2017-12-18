package matmic.librarymanager.controllers;

import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.User;
import matmic.librarymanager.services.ImageService;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    private ImageController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageService, userService, itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showUploadFormForUser() throws Exception {
        User user = new User();
        user.setId(2L);

        when(userService.findUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/user/2/update/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void showUploadFormForItem() throws Exception {
        Item item = new Item();
        item.setId(1L);

        when(itemService.findItemById(anyLong())).thenReturn(item);

        mockMvc.perform(get("/item/1/update/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"));
    }

    @Test
    public void handleUserImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testfile.txt", "text/plain", "Test user".getBytes());

        mockMvc.perform(multipart("/user/1/save/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/1/display"));

        verify(imageService,times(1)).saveImageFile(any(), anyLong(), any());
    }
    @Test
    public void handleItemImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testfile.txt", "text/plain", "Test item".getBytes());

        mockMvc.perform(multipart("/item/1/save/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/item/1/display"));

        verify(imageService,times(1)).saveImageFile(any(), anyLong(), any());
    }

    @Test
    public void renderUserImageFromDB() throws Exception {
        User user = new User();
        user.setId(3L);

        String test = "user image test";
        Byte[] imgBytes = new Byte[test.getBytes().length];

        int i = 0;

        for (byte nextByte : test.getBytes()){
            imgBytes[i++] = nextByte;
        }

        user.setImage(imgBytes);

        when(userService.findUserById(anyLong())).thenReturn(user);

        MockHttpServletResponse response = mockMvc.perform(get("/user/3/showimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(test.getBytes().length, responseBytes.length);
    }

    @Test
    public void renderItemImageFromDB() throws Exception {
        Item item = new Item();
        item.setId(2L);

        String test = "item image test";
        Byte[] imgBytes = new Byte[test.getBytes().length];

        int i = 0;

        for (byte nextByte : test.getBytes()){
            imgBytes[i++] = nextByte;
        }

        item.setImage(imgBytes);

        when(itemService.findItemById(anyLong())).thenReturn(item);

        MockHttpServletResponse response = mockMvc.perform(get("/item/2/showimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(test.getBytes().length, responseBytes.length);
    }

}