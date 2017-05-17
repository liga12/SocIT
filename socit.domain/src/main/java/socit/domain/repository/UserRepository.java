package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import socit.domain.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select case when count (u) > 0  then true else false end from User u where u.login = :login")
   Boolean existsByLogin(@Param("login") String login);

    @Query("select case when count (u) > 0  then true else false end from User u where u.email = :email")
    Boolean existsByEmail(@Param("email") String email);

    @Query("select case when count (u) > 0  then true else false end from User u where u.password = :password")
    Boolean existsByPassword(@Param("password") String password);

    User findByLogin(String login);

    User findByEmail(String email);

    List<User> findByFirstNameContainingAndStatusEqualsOrLastNameContainingAndStatusEquals(String s1,boolean b1, String s2, boolean b2);
}
