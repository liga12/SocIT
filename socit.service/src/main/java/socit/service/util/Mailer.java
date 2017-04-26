package socit.service.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;


@Component
public class Mailer {

    @Autowired
    ResourcesBandler bandler;

    @Autowired
    HtmlToString htmlToString;

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";

    private static final String mail_smtp_tarttls_enable = "mail.smtp.starttls.enable";
    private static final String mail_smtp_debug = "mail.smtp.debug";
    private static final String mail_smtp_socketFactory_fallback = "mail.smtp.port";

    public void send(String to, String URLLocalhost) {
        //Get properties object
        Properties props = new Properties();
        props.put(MAIL_SMTP_HOST, bandler.getResourcesEmail(MAIL_SMTP_HOST));
        props.put(MAIL_SMTP_SOCKET_FACTORY_PORT, bandler.getResourcesEmail(MAIL_SMTP_SOCKET_FACTORY_PORT));
        props.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, bandler.getResourcesEmail(MAIL_SMTP_SOCKET_FACTORY_CLASS));
        props.put(MAIL_SMTP_AUTH, bandler.getResourcesEmail(MAIL_SMTP_AUTH));
        props.put(MAIL_SMTP_PORT, bandler.getResourcesEmail(MAIL_SMTP_PORT));

        props.put(mail_smtp_debug, bandler.getResourcesEmail(mail_smtp_debug));
        props.put(mail_smtp_socketFactory_fallback, bandler.getResourcesEmail(mail_smtp_socketFactory_fallback));
        props.put(mail_smtp_tarttls_enable, bandler.getResourcesEmail(mail_smtp_tarttls_enable));
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(bandler.getResourcesEmail("from"),
                                bandler.getResourcesEmail("password"));
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(bandler.getResourcesEmail("from")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setSubject(bandler.getResourcesEmail("sub"));

            MimeMultipart mp = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            String str = htmlToString.getEmailStringHtml();
            String htmlString = MessageFormat.format(str, URLLocalhost);

            messageBodyPart.setContent(htmlString, "text/html; charset=utf-8");
            mp.addBodyPart(messageBodyPart);

            InputStream resourceAsStream = HtmlToString.class.getClassLoader()
                    .getResourceAsStream("images.jpg");
            DataHandler dataHandler = new DataHandler(new InputStreamDataSource(resourceAsStream, "t"));

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(dataHandler);
            messageBodyPart.setHeader("Content-ID", "<image>");
            mp.addBodyPart(messageBodyPart);

            message.setContent(mp);
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException("Error while send message for: " + URLLocalhost , e);
        }
    }
}

