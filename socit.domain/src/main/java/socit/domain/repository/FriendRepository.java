package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socit.domain.entity.Friend;
import socit.domain.entity.User;
import socit.domain.enums.FRIENDSTATUS;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> findByUser(User user);

    List<Friend> findByFriendAndFriendstatus(User user, FRIENDSTATUS friendstatus);

    List<Friend> findByUserAndFriendstatus(User user, FRIENDSTATUS friendstatus);

    Friend findByUserAndFriend(User user, User friend);
}
