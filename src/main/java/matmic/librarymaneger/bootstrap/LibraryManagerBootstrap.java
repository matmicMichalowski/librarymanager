package matmic.librarymaneger.bootstrap;

import matmic.librarymaneger.model.Book;
import matmic.librarymaneger.model.BookLoan;
import matmic.librarymaneger.model.LibraryAccount;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.model.rolemodel.Role;
import matmic.librarymaneger.repositories.BookRepository;
import matmic.librarymaneger.repositories.RoleRepository;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LibraryManagerBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;




    public LibraryManagerBootstrap(UserRepository userRepository, BookRepository bookRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        bookRepository.saveAll(getBooks());
        userRepository.saveAll(getUsers());
    }

    private List<Book> getBooks(){
        List<Book> books = new ArrayList<>();

        Book novelBook = new Book();
        novelBook.setAuthor("Jhon Novy");
        novelBook.setGenre("Horror");
        novelBook.setIsbn("78-987-678-00");
        novelBook.setPublisher("PwN");
        novelBook.setPublishYear(1999);
        novelBook.setTitle("In the wild");
        novelBook.setReleaseNumber(3);
        books.add(novelBook);

        Book book1 = new Book();
        book1.setAuthor("Thomas Doe");
        book1.setGenre("Drama");
        book1.setIsbn("78-987-777-00");
        book1.setPublisher("NWP");
        book1.setPublishYear(1909);
        book1.setTitle("No Man's Land");
        book1.setReleaseNumber(1);
        books.add(book1);

        Book book2 = new Book();
        book2.setAuthor("Britany Beloved");
        book2.setGenre("Sci-Fi");
        book2.setIsbn("99-877-678-00");
        book2.setPublisher("No-name");
        book2.setPublishYear(2000);
        book2.setTitle("Milenial Sky");
        book2.setReleaseNumber(1);
        books.add(book2);

        return books;
    }

    private List<User> getUsers(){

        List<User> users = new ArrayList<>();


        Optional<Book> bookToLoan = bookRepository.findByTitle("No Man's Land");

        if(!bookToLoan.isPresent()){
            throw new RuntimeException("Expected Book Not Found");
        }

        BookLoan loan1 = new BookLoan();
        bookToLoan.get().setBookLoan(loan1);
        loan1.setBook(bookToLoan.get());
        bookRepository.save(bookToLoan.get());



        Optional<Book> bookToLoan2 = bookRepository.findByTitle("Milenial Sky");

        if(!bookToLoan2.isPresent()){
            throw new RuntimeException("Expected Book Not Found");
        }

        BookLoan loan2 = new BookLoan();
        loan2.setBook(bookToLoan2.get());

        Role role1 = new Role();
        role1.setName("ADMIN");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setName("EMPLOYEE");
        roleRepository.save(role2);


        User user1 = new User();
        user1.setFirstName("Tom");
        user1.setLastName("Jhonas");
        user1.setCity("Warsaw");
        user1.setEmail("tom.jhonas@hm.com");
        user1.setPhoneNumber("788-888-888");
        user1.setAddress("Uliczna 23/23");
        user1.setPostCode("00-001");
        user1.setUserLibraryAccount(new LibraryAccount(user1));
        user1.getUserLibraryAccount().addBookLoan(loan1);
        users.add(user1);


        User user2 = new User();
        user2.setFirstName("Jason");
        user2.setLastName("Nuggat");
        user2.setCity("Warsaw");
        user2.setEmail("jason.nuggat@hm.com");
        user2.setPhoneNumber("222-388-588");
        user2.setAddress("Miejska 23/23");
        user2.setPostCode("00-001");
        user2.setUserLibraryAccount(new LibraryAccount(user2));
        user2.getUserLibraryAccount().addBookLoan(loan2);
        users.add(user2);

        User user3 = new User();
        user3.setFirstName("Rob");
        user3.setLastName("Thompson");
        user3.setCity("Warsaw");
        user3.setEmail("rob.thompson@hm.com");
        user3.setPhoneNumber("111-555-877");
        user3.setAddress("Uliczna 23/23");
        user3.setPostCode("00-001");
        users.add(user3);
        user3.setUserLibraryAccount(new LibraryAccount(user3));

        return users;

    }

}
