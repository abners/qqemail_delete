package mail;
/**
 * 类EmailSendServic.java的实现描述：邮件发送
 *
 * @author baoxing.peng 2016年12月15日 15:09:41
 */
public interface MessageSendService {

    /**
     * 发送消息
     * @param message 消息体
     * @param userName
     * @param password
     * @param title 邮件标题
     * @return
     */
    Boolean send(String message, String userName, String password, String title);
}
