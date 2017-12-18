package matmic.librarymanager.services;


import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.User;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    private ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(userRepository, itemRepository, userService, itemService);
    }

    @Test
    public void saveUserImageFile() throws Exception {
        Long id = 3L;

        MultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "user upload test".getBytes());

        User user = new User();
        user.setId(id);

        when(userService.findUserById(anyLong())).thenReturn(user);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        imageService.saveImageFile("user", id, multipartFile);

        verify(userService, times(1)).findUserById(anyLong());
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        User userWithSavedImage = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, userWithSavedImage.getImage().length);
    }
    @Test
    public void saveItemImageFile() throws Exception {
        Long id = 1L;

        MultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "item upload test".getBytes());

        Item item = new Item();
        item.setId(id);

        when(itemService.findItemById(anyLong())).thenReturn(item);

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);

        imageService.saveImageFile("item", id, multipartFile);

        verify(itemService, times(1)).findItemById(anyLong());
        verify(itemRepository, times(1)).save(argumentCaptor.capture());
        Item itemWithSavedImage = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, itemWithSavedImage.getImage().length);
    }

}