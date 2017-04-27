package socit.service.util;

import socit.domain.entity.Post;
import socit.service.BaseService;

public interface PostService extends BaseService<Post, Integer> {

    void removeById(Integer id);
}
