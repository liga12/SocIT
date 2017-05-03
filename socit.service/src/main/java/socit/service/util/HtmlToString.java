package socit.service.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Log4j
public class HtmlToString{

    @Getter
    @Setter
    private String pathToHTML;

    public HtmlToString(String pathToHTML) {
        this.pathToHTML = pathToHTML;
        log.debug("Set value pathToHTML = "+pathToHTML);
    }

    public String getEmailStringHtml() {
        String stringHtml = null;
        String string;
        StringBuffer stringBuffer;
        InputStream resourcesHtml = HtmlToString.class.getClassLoader().
                getResourceAsStream(pathToHTML);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(resourcesHtml))) {
            stringBuffer = new StringBuffer();
            while ((string = in.readLine()) != null) {
                stringBuffer.append(string).append("\n");
            }
            stringHtml = stringBuffer.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        log.debug("html parse success");
        return stringHtml;
    }
}
