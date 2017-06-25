package socit.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import socit.domain.entity.Friend;
import socit.domain.entity.User;
import socit.domain.enums.FRIENDSTATUS;
import socit.domain.repository.FriendRepository;

import java.io.Serializable;
import java.util.List;

@Service
@Log4j
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Friend getById(Integer id) {
        log.debug("Friend: id = " + id);
        return friendRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(Friend friend) {
        return friendRepository.saveAndFlush(friend);
    }

    @Override
    @Transactional
    public void update(Friend friend) {
        friendRepository.saveAndFlush(friend);
    }

    @Override
    @Transactional
    public void remove(Friend friend) {
        friendRepository.delete(friend);
    }

    @Override
    public List<Friend> getAll() {
        log.debug("Get All Friend");
        return friendRepository.findAll();
    }

    @Override
    public List<Friend> getFriendsByUser(User user) {
        return friendRepository.findByUser(user);
    }

    @Override
    public List<Friend> getFriendsByFriendAndFriendStatus(User user, FRIENDSTATUS friendstatus) {
        return friendRepository.findByFriendAndFriendstatus(user, friendstatus);
    }

    @Override
    public Friend getFriendByUserAndFriend(User user, User friend) {
        return friendRepository.findByUserAndFriend(user, friend);
    }

    @Override
    public void rejectFriend(String id) {
        try {
            Friend friend = getById(Integer.valueOf(id));
            if (friend != null) {
                friend.setFriendstatus(FRIENDSTATUS.REJECTED);
                update(friend);
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void confirmFriend(String id) {
        try {
            Friend friend = getById(Integer.valueOf(id));
            if (friend != null) {
                friend.setFriendstatus(FRIENDSTATUS.CONFIRM);
                update(friend);
                Friend friend1 = new Friend(userService.getUserByPrincpals(), friend.getUser(), FRIENDSTATUS.CONFIRM);
                save(friend1);
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void deleteFriends(String id, String idFriend, String idUser) {
        try {
            if (idFriend == null && idUser == null){
                Friend deleteFriend = getById(Integer.valueOf(id));
                idFriend = String.valueOf(deleteFriend.getFriend().getId());
                idUser = String.valueOf(deleteFriend.getUser().getId());
            }
            remove(getById(Integer.valueOf(id)));
            if (idFriend != null && idUser != null) {
                try {
                    Friend friend = getFriendByUserAndFriend
                            (userService.getById(Integer.valueOf(idFriend)), userService.getById(Integer.valueOf(idUser)));
                    if (friend != null) {
                        friend.setFriendstatus(FRIENDSTATUS.REJECTED);
                        update(friend);
                    }
                } catch (NumberFormatException e) {
                    log.error(e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addFriends(String idUser, String idFriend) {
        if (idFriend != null) {
            try {
                Friend friend = getById(Integer.valueOf(idFriend));
                if (friend != null) {
                    friend.setFriendstatus(FRIENDSTATUS.WAIT);
                    update(friend);
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }

        } else {
            try {
                    Friend friend = getFriendByUserAndFriend(userService.getById(Integer.valueOf(idUser)), userService.getById(Integer.valueOf(userService.getUserByPrincpals().getId())));
                    if (friend != null && friend.getFriendstatus() == FRIENDSTATUS.REJECTED) {
                        remove(friend);
                }
                save(new Friend(userService.getUserByPrincpals(),
                        userService.getById(Integer.valueOf(idUser)), FRIENDSTATUS.WAIT));
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }
    }

}
