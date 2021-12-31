package com.dbay.teddy.manager;

import com.dbay.teddy.utils.TeddyConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author AlexanderGuo
 */
@Component
public class EmailSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Transport transport = null;
    private Session session = null;


    public EmailSender() {
    }

    private void init() {
        if (transport == null || session == null) {
            Properties properties = System.getProperties();
            // 设置邮件服务器
            properties.setProperty("mail.host", TeddyConf.get("mail.host"));
            properties.setProperty("mail.port", TeddyConf.get("mail.port"));
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.timeout", "10000");
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "false");

            try {
                session = Session.getInstance(properties);
                transport = session.getTransport();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void send(String emails, String subject, String text) {

        init();

        try {
            String[] emailArr = emails.split(";");
            InternetAddress[] addresses = new InternetAddress[emailArr.length];
            for (int i = 0; i < emailArr.length; i++) {
                addresses[i] = new InternetAddress(emailArr[i]);
            }
            // create message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(TeddyConf.get("mail.from")));
            message.setSubject(subject);
            message.setText(text);
            message.addRecipients(Message.RecipientType.TO, addresses);
            // transport send
//            transport.connect(TeddyConf.get("mail.host"), TeddyConf.get("mail.from"), TeddyConf.get("mail.passwd"));
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
            Transport.send(message);
            logger.info("email success：-->" + emails + "[" + subject + "]" + text);
          

        } catch (Exception e) {
            logger.error("email error，" + e.getMessage());
            e.printStackTrace();
        }
    }
}
