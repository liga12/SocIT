package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import socit.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select case when count (u) > 0  then true else false end from User u where u.login = :login")
   Boolean existsByLogin(@Param("login") String login);

    @Query("select case when count (u) > 0  then true else false end from User u where u.email = :email")
    Boolean existsByEmail(@Param("email") String email);

    User findByLogin(String login);

    User findByEmail(String email);
}
