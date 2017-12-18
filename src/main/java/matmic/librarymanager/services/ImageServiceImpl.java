package matmic.librarymanager.services;

import matmic.librarymanager.model.ImageSuperclass;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.User;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemService itemService;

    public ImageServiceImpl(UserRepository userRepository, ItemRepository itemRepository, UserService userService, ItemService itemService) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    @Transactional
    public void saveImageFile(String imageOwnerClass, Long imageOwnerId, MultipartFile file) {

        try{
            ImageSuperclass owner;
            if(imageOwnerClass.equalsIgnoreCase("user")){
                owner = userService.findUserById(imageOwnerId);
            }else{
                owner = itemService.findItemById(imageOwnerId);
            }

            Byte[] bytesObject = new Byte[file.getBytes().length];

            int i = 0;

            for(byte b: file.getBytes()){
                bytesObject[i++] = b;
            }
            owner.setImage(bytesObject);

            if(imageOwnerClass.equals("user")){
                userRepository.save((User) owner);
            }else{
                itemRepository.save((Item) owner);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
