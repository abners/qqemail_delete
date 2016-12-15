package mail.factory;

import javax.mail.NoSuchProviderException;
import javax.mail.Store;

/**
 * 类StoryFactory.java的实现描述：store工厂
 *
 * @author baoxing.peng 2016年12月15日 13:48:06
 */
public interface StoryFactory {

    public Store getInstance() throws NoSuchProviderException;
}
