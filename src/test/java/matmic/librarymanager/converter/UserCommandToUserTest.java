package matmic.librarymanager.converter;

import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.User;
import matmic.librarymanager.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserCommandToUserTest {
    private static final Long ID = 4L;
    private static final String FIRST_NAME = "Tomas";
    private static final String LAST_NAME = "Somat";
    private static final String PHONE_NUMBER = "123-456-789";
    private static final String EMAIL = "samtam@mail.com";
    private static final String CITY = "Big City";
    private static final String ADDRESS = "Good Street 53";
    private static final String POST_CODE = "01-011";


    @Mock
    private UserRepository userRepository;

    private UserCommandToUser converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new UserCommandToUser(userRepository);
    }

    @Test
    public void convert() throws Exception {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(ID);
        userCommand.setFirstName(FIRST_NAME);
        userCommand.setLastName(LAST_NAME);
        userCommand.setPhoneNumber(PHONE_NUMBER);
        userCommand.setEmail(EMAIL);
        userCommand.setCity(CITY);
        userCommand.setAddress(ADDRESS);
        userCommand.setPostCode(POST_CODE);

        User user = converter.convert(userCommand);

        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(PHONE_NUMBER, user.getPhoneNumber());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(CITY, user.getCity());
        assertEquals(ADDRESS, user.getAddress());
        assertEquals(POST_CODE, user.getPostCode());
    }

}