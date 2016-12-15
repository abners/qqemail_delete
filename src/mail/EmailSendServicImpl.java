package mail;

import mail.factory.SessionService;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 类EmailSend.java的实现描述：邮件发送
 *
 * @author baoxing.peng 2016年12月15日 15:08:11
 */
public class EmailSendServicImpl implements MessageSendService {
    @Override
    public Boolean send(String message, String userName, String password, String title) {
        Session session = SessionService.createSession(false);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(userName));
            mimeMessage.setSubject(title);
            mimeMessage.setText(message);
            Transport transport = session.getTransport();
            transport.connect(userName,password);

            //发送邮件
            transport.sendMessage(mimeMessage,new Address[]{new InternetAddress(userName)});
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
