package socit.service;

import socit.domain.entity.Friend;
import socit.domain.entity.User;
import socit.domain.enums.FRIENDSTATUS;

import java.util.List;

public interface FriendService extends BaseService<Friend, Integer> {

    List<Friend> getFriendsByUser(User user);

    List<Friend> getFriendsByFriendAndFriendstatus(User user, FRIENDSTATUS friendstatus);

    Friend getFriendByUserAndFriend(User user, User friend);

    void rejectFriend(String id);

    void confirmFriend(String id);

    void deleteFriends(String id, String idFriend, String idUser);

    void addFriends(String id, String idFriend);
}
