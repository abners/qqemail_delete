package mail.factory;

import com.sun.mail.imap.IMAPStore;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;

/**
 * 类IMAPStoryFactory.java的实现描述：生成imapstore
 *
 * @author baoxing.peng 2016年12月15日 13:49:58
 */
public class IMAPStoryFactory implements StoryFactory {

    @Override
    public IMAPStore getInstance(){
        Session mailsession = SessionService.createSession(false);
        try {
            return (IMAPStore)mailsession.getStore("imap");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return null;
    }
}
