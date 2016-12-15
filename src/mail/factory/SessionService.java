package mail.factory;

import javax.mail.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 类SessionService.java的实现描述：获取回话
 *
 * @author baoxing.peng 2016年12月15日 15:15:35
 */
public class SessionService {
    public static Properties prop;
    static {
        prop = System.getProperties();
        try {
            prop.load(ClassLoader.getSystemResourceAsStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param debug 是否开启debug模式
     * @return
     */
    public static Session createSession(boolean debug){
        Session session = Session.getInstance(prop);

        session.setDebug(debug);

        return  session;
    }
}
