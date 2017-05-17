package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socit.domain.entity.Friend;
import socit.domain.entity.User;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> getFriendsByUser(User user);
}
