package socit.service.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class HtmlToString  {
    private static final InputStream resourcesHtml = HtmlToString.class.getClassLoader().
            getResourceAsStream("emailHtml.html");

    public String getEmailStringHtml() {
        String stringHtml = null;
        String string;
        StringBuffer stringBuffer;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(resourcesHtml))) {
            stringBuffer = new StringBuffer();
            while ((string = in.readLine()) != null) {
                stringBuffer.append(string + "\n");
            }
            stringHtml = stringBuffer.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stringHtml;
    }
}
