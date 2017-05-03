package socit.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import socit.domain.entity.URLMassage;
import socit.domain.repository.URLMassageRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Service
@Log4j
public class URLMassageServiceImpl implements URLMassageService {

    @Autowired
    private URLMassageRepository urlMessageRepository;

    @Override
    @Transactional
    public URLMassage getById(Integer id) {
        log.debug("URLMassage: id = " + id);
        return urlMessageRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(URLMassage urlMassage) {
        log.debug("URLMassage: url = " + urlMassage.getUrl());
        return urlMessageRepository.saveAndFlush(urlMassage);
    }

    @Override
    @Transactional
    public void update(URLMassage urlMassage) {
        log.debug("URLMassage: url = " + urlMassage.getUrl());
        urlMessageRepository.saveAndFlush(urlMassage);
    }

    @Override
    @Transactional
    public void remove(URLMassage urlMassage) {
        log.debug("URLMassage: url = " + urlMassage.getUrl());
        urlMessageRepository.delete(urlMassage);
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        log.debug("URLMassage: id = " + id);
        urlMessageRepository.delete(id);
    }

    @Override
    public List<URLMassage> getAll() {
        log.debug("URLMassage all User");
        return urlMessageRepository.findAll();
    }

    @Override
    @Transactional
    public URLMassage getByUrl(String url) {
        log.debug("URLMassage: url = " + url);
        return urlMessageRepository.findByUrl(url);
    }

    @Override
    public String getURL(HttpServletRequest req) {

        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
        String contextPath = req.getContextPath();   // /mywebapp
        String servletPath = req.getServletPath();   // /servlet/MyServlet
        String pathInfo = req.getPathInfo();         // /a/b;c=123
        String queryString = req.getQueryString();          // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(req.getScheme()).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath).append(servletPath);
        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        log.debug("Url = "+url.toString());
        return url.toString();
    }


}
