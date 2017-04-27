package socit.service;

import org.springframework.web.multipart.MultipartFile;
import socit.domain.entity.PhotoPost;

import java.io.IOException;

public interface PhotoPostService extends BaseService<PhotoPost, Integer> {

    void savePhotoPost(String description, String[] allUser, MultipartFile[] files) throws IOException;

    String extractFileName(String contentDisp);
}
