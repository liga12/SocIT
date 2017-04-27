package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socit.domain.entity.PhotoPost;

public interface PhotoPostRepository extends JpaRepository<PhotoPost, Integer> {

}
