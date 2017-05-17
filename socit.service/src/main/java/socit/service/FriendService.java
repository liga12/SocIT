package socit.service;

import socit.domain.entity.Friend;
import socit.domain.entity.User;

import java.util.List;

public interface FriendService extends BaseService<Friend, Integer> {

    List<Friend> getFriendsByUser(User user);
}
