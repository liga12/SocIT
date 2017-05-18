package socit.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import socit.domain.entity.Post;
import socit.domain.repository.PostRepository;

import java.io.Serializable;
import java.util.List;

@Service
@Log4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public Post getById(Integer id) {
        log.debug("Post: id = " + id);
        return postRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(Post post) {
        log.debug("Post: like = " + post.getLike() + ", date = " + post.getDate()
                + ", status = " + post.getStatus() + ", comment = " + post.getComment() + ", allUser = " + post.getAllUser());
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void update(Post post) {
        log.debug("Post: like = " + post.getLike() + ", date = " + post.getDate()
                + ", status = " + post.getStatus() + ", comment = " + post.getComment() + ", allUser = " + post.getAllUser());
        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void remove(Post post) {
        log.debug("Post: like = " + post.getLike() + ", date = " + post.getDate()
                + ", status = " + post.getStatus() + ", comment = " + post.getComment() + ", allUser = " + post.getAllUser());
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        log.debug("Post: id = " + id);
        postRepository.delete(id);
    }

    @Override
    public List<Post> getAll() {
        log.debug("Get All Post");
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Integer id) {
        try {
            Post post = getById(id);
            post.setStatus(false);
            update(post);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editPost(String id, String comment) {
        try {
            Post post = getById(Integer.valueOf(id));
            post.setComment(comment);
            update(post);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }

    }
}
