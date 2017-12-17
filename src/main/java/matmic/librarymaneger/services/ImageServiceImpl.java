package matmic.librarymaneger.services;

import matmic.librarymaneger.model.ImageSuperclass;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.repositories.ItemRepository;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;

    public ImageServiceImpl(UserRepository userRepository, ItemRepository itemRepository, UserService userService) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void saveImageFile(String imageOwnerClass, Long imageOwnerId, MultipartFile file) {

        try{
            ImageSuperclass owner;
            if(imageOwnerClass.equalsIgnoreCase("user")){
                owner = userService.findUserById(imageOwnerId);
            }else{
                owner = itemRepository.findItemById(imageOwnerId).get();
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
