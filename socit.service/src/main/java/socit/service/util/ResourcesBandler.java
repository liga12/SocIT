package socit.service.util;

import lombok.extern.log4j.Log4j;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

@Log4j
public class ResourcesBandler{

    private static final String ENCODINGFROM = "ISO-8859-1";
    private static final String ENCODINGTO = "UTF-8";
    private  String file;

    public ResourcesBandler(String file) {
        this.file = file;
        log.debug("Set value: file = "+file);
    }

    public String getResources(String key) {
        ResourceBundle emailResources = ResourceBundle.getBundle(file);
        String value = emailResources.getString(key);
        String recoded = null;
        try {
            recoded = new String(value.getBytes(ENCODINGFROM), ENCODINGTO);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException"+e.getMessage());
        }
        log.debug("value = "+recoded);
        return recoded;
    }
}
