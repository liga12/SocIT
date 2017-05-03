package socit.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import socit.domain.entity.PhotoPost;
import socit.domain.entity.Post;
import socit.domain.entity.User;
import socit.domain.repository.PhotoPostRepository;
import socit.service.util.UploadFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class PhotoPostServiceImpl implements PhotoPostService {

    @Autowired
    private PhotoPostRepository photoPostRepository;

    @Autowired
    private UploadFiles uploadFile;

    @Autowired
    private PostService postService;

    @Override
    @Transactional
    public PhotoPost getById(Integer id) {
        log.debug("User: id = " + id);
        return photoPostRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(PhotoPost photoPost) {
        log.debug("PhotoPost:  location = " + photoPost.getLocation() + ", date = " + photoPost.getDate()
                + ", status = " + photoPost.getStatus());
        return photoPostRepository.saveAndFlush(photoPost);
    }

    @Override
    @Transactional
    public void update(PhotoPost photoPost) {
        log.debug("PhotoPost:  location = " + photoPost.getLocation() + ", date = " + photoPost.getDate()
                + ", status = " + photoPost.getStatus());
        photoPostRepository.saveAndFlush(photoPost);
    }

    @Override
    @Transactional
    public void remove(PhotoPost photoPost) {
        log.debug("PhotoPost:  location = " + photoPost.getLocation() + ", date = " + photoPost.getDate()
                + ", status = " + photoPost.getStatus());
        photoPostRepository.delete(photoPost);
    }

    @Override
    @Transactional
    public List<PhotoPost> getAll() {
        log.debug("Get all PhotoPost");
        return photoPostRepository.findAll();
    }

    @Override
    public void savePhotoPost(String description, String[] allUser, MultipartFile[] files) throws IOException {
        List<String> urlImages = new ArrayList<>();
        urlImages.clear();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getId().toString();

        Post post = new Post(description, true, user);
        if (allUser != null) {
            for (String s : allUser) {
                if (s != null) {
                    post.setAllUser(true);
                    break;
                }
            }
        }
        postService.save(post);
        String postId = post.getId().toString();
        try {
            // Part list (multi files).
            int counter = 1;
            for (MultipartFile part : files) {
                String fileName = part.getOriginalFilename();
                if (fileName != null && fileName.length() > 0) {
                    String filePath = uploadFile.getFullSavePath("userId_" + userId + "/post_" + postId) + File.separator + counter + fileName;
                    // Write to file
                    byte[] bytes = part.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(filePath));
                    stream.write(bytes);
                    log.debug("Save photo = "+filePath);
                    stream.close();
                    urlImages.add("/st/userId_" + userId + "/post_" + postId + "/" + counter + fileName);
                    counter++;
                }
            }
            for (String urlImage : urlImages) {
                PhotoPost photoPost = new PhotoPost(urlImage, true, post);
                save(photoPost);
            }
        } catch (Exception e) {
            throw new IOException("Error while parse images");
        }
    }

    @Override
    public String extractFileName(String contentDisp) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }
}
