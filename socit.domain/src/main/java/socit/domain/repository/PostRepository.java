package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socit.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
