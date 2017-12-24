package matmic.librarymanager.controllers;


import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("item/list")
    public String showItemsList (Model model){
        model.addAttribute("items", itemService.getItems());
        return "item/itemlist";
    }

    @GetMapping("item/{id}/display")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findById(new Long(id)));
        return "item/itemdisplay";
    }

    @GetMapping("item/new")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "item/itemform";
    }

    @PostMapping("saveitem")
    public String saveOrUpdate(@Valid @ModelAttribute("item") ItemCommand itemCommand, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "item/itemform";
        }
            ItemCommand savedItem = itemService.saveItem(itemCommand);
            return "redirect:/item/" + savedItem.getId() + "/display";
    }

    @GetMapping("item/{id}/update/details")
    public String updateItemForm(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findItemCommandById(Long.valueOf(id)));
        return "item/itemform";
    }

    @GetMapping("item/{id}/delete")
    public String deleteItemById(@PathVariable String id, Model model){
        if (!itemService.deleteById(Long.valueOf(id))){
            model.addAttribute("activeLoanError", "You can not delete the borrowed item.");
            model.addAttribute("items", itemService.getItems());
            return "item/itemlist";
        }
        return "redirect:/item/list";
    }

}
