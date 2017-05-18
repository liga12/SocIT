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
    public List<Friend> getFriendsByFriendAndFriendstatus(User user, FRIENDSTATUS friendstatus) {
        return friendRepository.findByFriendAndFriendstatus(user, friendstatus);
    }

    @Override
    public List<Friend> getFriendsByUserAndFriendstatus(User user) {
        return friendRepository.findByUserAndFriendstatus(user, FRIENDSTATUS.REJECTED);
    }

    @Override
    public Friend getFriendByUserAndFriend(User user, User friend) {
        return friendRepository.findByUserAndFriend(user, friend);
    }
}
