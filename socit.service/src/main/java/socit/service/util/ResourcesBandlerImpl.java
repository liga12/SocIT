package socit.service.util;


import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

@Component
public class ResourcesBandlerImpl implements ResourcesBandler {

    private static final String encodingFrom = "ISO-8859-1";
    private static final String encodingTo = "UTF-8";
    private static final String emailFile = "Email";

    @Override
    public String getResourcesEmail(String key) {
        ResourceBundle emailResources = ResourceBundle.getBundle(emailFile);
        String value = emailResources.getString(key);
        String recoded = null;
        try {
            recoded = new String(value.getBytes(encodingFrom), encodingTo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return recoded;
    }
}
