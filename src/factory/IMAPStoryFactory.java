package factory;

import com.sun.mail.imap.IMAPStore;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import java.util.Properties;

/**
 * 类IMAPStoryFactory.java的实现描述：生成imapstore
 *
 * @author baoxing.peng 2016年12月15日 13:49:58
 */
public class IMAPStoryFactory implements StoryFactory {

    @Override
    public IMAPStore getInstance(){
        Properties prop = System.getProperties();
        prop.setProperty("mail.store.protocol", "imap");
        prop.setProperty("mail.imap.host", "imap.qq.com");
        prop.setProperty("mail.imap.port", "143");
        Session mailsession =Session.getInstance(prop);

        mailsession.setDebug(false);

        try {
            return (IMAPStore)mailsession.getStore("imap");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return null;
    }
}
