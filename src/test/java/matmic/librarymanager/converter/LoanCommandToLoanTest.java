package matmic.librarymanager.converter;


import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.Loan;
import matmic.librarymanager.model.User;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.UserRepository;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.ItemServiceImpl;
import matmic.librarymanager.services.UserService;
import matmic.librarymanager.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class LoanCommandToLoanTest {

    private static final Long ID = 5L;
    private static final Long ITEM_ID = 4L;
    private static final Long USER_ID = 6L;


    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;


    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;


    private LoanCommandToLoan converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, new UserToUserCommand(), new UserCommandToUser(userRepository));
        itemService = new ItemServiceImpl(itemRepository, new ItemCommandToItem(itemRepository), new ItemToItemCommand());
        converter = new LoanCommandToLoan(itemService, userService);

    }


    @Test
    public void convert() throws Exception {

        LoanCommand loanCommand = new LoanCommand();
        loanCommand.setId(ID);
        loanCommand.setUserId(USER_ID);
        loanCommand.setItemId(ITEM_ID);

        User user = new User();
        user.setId(USER_ID);

        Optional<User> optional = Optional.of(user);

        Item item = new Item();
        item.setId(ITEM_ID);

        Optional<Item> itemOptional = Optional.of(item);

        when(userRepository.findUserById(anyLong())).thenReturn(optional);
        when(itemRepository.findItemById(anyLong())).thenReturn(itemOptional);


        Loan loan = converter.convert(loanCommand);

        assertNotNull(loan);
        assertEquals(ID, loan.getId());
        assertEquals(USER_ID, loan.getUser().getId());
        assertEquals(ITEM_ID, loan.getItem().getId());
    }

}