package socit.service;

import socit.domain.entity.Post;

public interface PostService extends BaseService<Post, Integer> {

    void remove(Integer id);

    void deletePost(String id);

    void editPost(String id, String comment);
}
