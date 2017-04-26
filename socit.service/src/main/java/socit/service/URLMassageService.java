package socit.service;

import socit.domain.entity.URLMassage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface URLMassageService extends BaseService<URLMassage, Integer> {

    Boolean existsByUrl(String url);

    String getURL(HttpServletRequest req);

    List<URLMassage> getAll();

    void remove(Integer id);

}
