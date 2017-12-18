package matmic.librarymanager.converter;

import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserToUserCommandTest {

    private static final Long ID = 2L;
    private static final String FIRST_NAME = "Jordan";
    private static final String LAST_NAME = "Nadroj";
    private static final String PHONE_NUMBER = "888-999-898";
    private static final String EMAIL = "jnad@mail.com";
    private static final String CITY = "Big City";
    private static final String ADDRESS = "Straight St. 108";
    private static final String POST_CODE = "00-00";


    private UserToUserCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UserToUserCommand();
    }

    @Test
    public void convert() throws Exception {
        User user = new User();
        user.setId(ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setEmail(EMAIL);
        user.setCity(CITY);
        user.setAddress(ADDRESS);
        user.setPostCode(POST_CODE);

        UserCommand command = converter.convert(user);

        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(FIRST_NAME, command.getFirstName());
        assertEquals(LAST_NAME, command.getLastName());
        assertEquals(PHONE_NUMBER, command.getPhoneNumber());
        assertEquals(EMAIL, command.getEmail());
        assertEquals(CITY, command.getCity());
        assertEquals(ADDRESS, command.getAddress());
        assertEquals(POST_CODE, command.getPostCode());
    }

}