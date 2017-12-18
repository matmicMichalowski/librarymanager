package matmic.librarymanager.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(String imageOwnerClass, Long imageOwnerId, MultipartFile file);
}
