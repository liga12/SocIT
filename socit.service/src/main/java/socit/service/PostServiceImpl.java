package socit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import socit.domain.entity.Post;
import socit.domain.repository.PostRepository;

import java.io.Serializable;
import java.util.List;

@Service
@EnableTransactionManagement
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    @Transactional
    public Post getById(Integer id) {
        return postRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(Post post){
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void update(Post post) {
        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void remove(Post post) {
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        postRepository.delete(id);
    }

    @Override
    public List<Post> getAll() {
        return  postRepository.findAll();
    }
}
