package socit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import socit.domain.entity.URLMassage;

public interface URLMassageRepository extends JpaRepository<URLMassage, Integer> {

   URLMassage findByUrl(String url);
}
