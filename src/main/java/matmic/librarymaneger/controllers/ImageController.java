package matmic.librarymaneger.controllers;

import matmic.librarymaneger.model.ImageSuperclass;
import matmic.librarymaneger.services.ImageService;
import matmic.librarymaneger.services.ItemService;
import matmic.librarymaneger.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;
    private final ItemService itemService;

    public ImageController(ImageService imageService, UserService userService, ItemService itemService) {
        this.imageService = imageService;
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/{imageOwner}/{id}/uploadimage")
    public String showUploadFormForUser (@PathVariable String  imageOwner, @PathVariable String id, Model model){

        if (imageOwner.equalsIgnoreCase("userpanel")) {
            model.addAttribute("user", userService.findUserById(Long.valueOf(id)));
            return imageOwner +  "/imageuploadform";
        }else{
            model.addAttribute("item", itemService.findItemById(Long.valueOf(id)));
            return imageOwner + "/imageuploadform";
        }

    }



    @PostMapping("{imageOwner}/{id}/image")
    public String handleItemImagePost(@PathVariable String imageOwner, @PathVariable String id, @RequestParam("imagefile")MultipartFile imageFile){
        imageService.saveImageFile(imageOwner, Long.valueOf(id), imageFile);

        return "redirect:/" + imageOwner + "/" + id + "/show";
    }

    @GetMapping("{imageOwner}/{id}/showimage")
    public void renderImageFromDB (@PathVariable String imageOwner, @PathVariable String id, HttpServletResponse response) throws IOException {
        ImageSuperclass owner;

        if (imageOwner.equalsIgnoreCase("userpanel")){
            owner = userService.findUserById(Long.valueOf(id));
        }else{
             owner = itemService.findItemById(Long.valueOf(id));
        }

        if(owner.getImage() != null){
            byte[] byteArray = new byte[owner.getImage().length];

            int i = 0;

            for(Byte wrappedByte : owner.getImage()){
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
