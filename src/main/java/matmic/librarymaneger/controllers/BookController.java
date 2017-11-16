package matmic.librarymaneger.controllers;

import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("bookpanel/showlist")
    public String showBooksPanel(Model model){
        model.addAttribute("books", bookService.getBooks());
        return "bookpanel/bookslist";
    }

    @RequestMapping("bookpanel/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("book", bookService.findById(new Long(id)));
        return "bookpanel/show";
    }

    @RequestMapping("bookpanel/newbook")
    public String newBook(Model model){
        model.addAttribute("book", new BookCommand());
        return "bookpanel/bookform";
    }

    @PostMapping
    @RequestMapping("bookpanel")
    public String saveOrUpdate(@ModelAttribute BookCommand command){
        BookCommand savedUserCommand = bookService.saveBookCommand(command);

        return "redirect:/bookpanel/" + savedUserCommand.getId() + "/show";
    }


    @RequestMapping("bookpanel/{id}/bookupdate")
    public String updateBook(@PathVariable String id, Model model){
        model.addAttribute("book", bookService.findCommandById(Long.valueOf(id)));
        return "bookpanel/bookform";
    }

    @GetMapping
    @RequestMapping("bookpanel/{id}/delete")
    public String deleteBookById(@PathVariable String id){
        bookService.deleteById(Long.valueOf(id));
        return "index";

    }

//    @RequestMapping("mainpanel")
//    public String goToMain(){
//        return "index";
//    }
}
