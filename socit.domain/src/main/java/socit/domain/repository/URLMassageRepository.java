package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import socit.domain.entity.URLMassage;

public interface URLMassageRepository extends JpaRepository<URLMassage, Integer> {

    @Query("select case when count (u) > 0  then true else false end from URLMassage u where u.url = :url")
    Boolean existsByUrl(@Param("url")String url);
}
