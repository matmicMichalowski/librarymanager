package matmic.librarymaneger.controllers;

import matmic.librarymaneger.commands.ItemCommand;
import matmic.librarymaneger.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("items/showitems")
    public String showItemsPanel (Model model){
        model.addAttribute("item", itemService.getItems());
        return "items/list";
    }

    @GetMapping("items/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findById(new Long(id)));
        return "items/show";
    }

    @GetMapping("items/newitem")
    public String newItem(Model model){
        model.addAttribute("item", new ItemCommand());
        return "itempanel/itemform";
    }

    @PostMapping("items")
    public String saveOrUpdate(@ModelAttribute ItemCommand command){
        ItemCommand savedItemCommand = itemService.saveBookCommand(command);
        return "redirect:/items/" + savedItemCommand.getId() + "/show";
    }

    @GetMapping
    public String updateItem(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findCommandById(Long.valueOf(id)));
        return "item/itemform";
    }

    @GetMapping("items/{id}/delete")
    public String deleteItemById(@PathVariable String id){
        itemService.deleteById(Long.valueOf(id));
        return "items/list";
    }

}
