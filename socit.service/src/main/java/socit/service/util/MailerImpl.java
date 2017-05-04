package socit.service.util;


import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.PropertyResourceBundle;


@Service
@Log4j
public class MailerImpl implements Mailer {

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_TARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_DEBUG = "mail.smtp.debug";
    private static final String FROM = "from";

    @Override
    public void send(String to, String URLLocalhost, String pathToHTML) {
        log.debug("Email to = " + to);
        log.debug("Email Link to confirmed = " + URLLocalhost);

        ResourcesBandler bandler = new ResourcesBandler("email.properties");
        Properties props = new Properties();
        log.debug("Set properties for email: " + MAIL_SMTP_HOST);
        props.put(MAIL_SMTP_HOST, bandler.getResources(MAIL_SMTP_HOST));
        log.debug("Set properties for email: " + MAIL_SMTP_SOCKET_FACTORY_PORT);
        props.put(MAIL_SMTP_SOCKET_FACTORY_PORT, bandler.getResources(MAIL_SMTP_SOCKET_FACTORY_PORT));
        log.debug("Set properties for email: " + MAIL_SMTP_SOCKET_FACTORY_CLASS);
        props.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, bandler.getResources(MAIL_SMTP_SOCKET_FACTORY_CLASS));
        log.debug("Set properties for email: " + MAIL_SMTP_AUTH);
        props.put(MAIL_SMTP_AUTH, bandler.getResources(MAIL_SMTP_AUTH));
        log.debug("Set properties for email: " + MAIL_SMTP_PORT);
        props.put(MAIL_SMTP_PORT, bandler.getResources(MAIL_SMTP_PORT));
        log.debug("Set properties for email: " + MAIL_SMTP_DEBUG);
        props.put(MAIL_SMTP_DEBUG, bandler.getResources(MAIL_SMTP_DEBUG));
        log.debug("Set properties for email: " + MAIL_SMTP_TARTTLS_ENABLE);
        props.put(MAIL_SMTP_TARTTLS_ENABLE, bandler.getResources(MAIL_SMTP_TARTTLS_ENABLE));


        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        log.debug("Email from = " + bandler.getResources(FROM));
                        return new PasswordAuthentication(bandler.getResources(FROM),
                                bandler.getResources("password"));
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(bandler.getResources(FROM)));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setSubject(bandler.getResources("sub"));

            MimeMultipart mp = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            String str = new HtmlToString(pathToHTML).getEmailStringHtml();
            String htmlString = MessageFormat.format(str, URLLocalhost);

            messageBodyPart.setContent(htmlString, "text/html; charset=utf-8");
            mp.addBodyPart(messageBodyPart);

            log.debug("Get html");
            InputStream resourceAsStream = HtmlToString.class.getClassLoader()
                    .getResourceAsStream("email.jpg");
            DataHandler dataHandler = new DataHandler(new InputStreamDataSource(resourceAsStream, "t"));
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(dataHandler);
            messageBodyPart.setHeader("Content-ID", "<image>");
            mp.addBodyPart(messageBodyPart);

            message.setContent(mp);
            Transport.send(message);
            log.debug("Message sent successfully");
        } catch (MessagingException e) {
            log.error("Error while send message for: " + e.getMessage());
            throw new RuntimeException("Error while send message for: " + URLLocalhost, e);
        }
    }
}

