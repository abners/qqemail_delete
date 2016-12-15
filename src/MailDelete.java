import com.sun.mail.imap.IMAPFolder;
import mail.EmailSendServicImpl;
import mail.MessageSendService;
import mail.factory.IMAPStoryFactory;
import mail.factory.StoryFactory;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * 类MailDelete.java的实现描述：
 *
 * @author baoxing.peng 2016年12月14日 11:19:17
 */
public class MailDelete {

    public static void delete(String user, String password, String keyword, String folderName)
                                                                                              throws UnsupportedEncodingException {
        MessageSendService messageSendService = new EmailSendServicImpl();
        try {
            StoryFactory factory = new IMAPStoryFactory();
            Store store = factory.getInstance();

            IMAPFolder emailFolder = queryEmailFolder(user, password, folderName, messageSendService, store);
            Integer count = emailFolder.getMessageCount();
            int totalDeleted = deletEmailFromServer(keyword, emailFolder, count);
            // expunges the folder to remove messages which are marked deleted
            emailFolder.close(true);
            store.close();
            StringBuilder sb = new StringBuilder();
            sb.append("共成功删除邮件" + totalDeleted + "封");
            messageSendService.send(sb.toString(), user, password, "邮件删除完毕");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            messageSendService.send(e.toString(), user, password, "删除邮件出错");
        }
    }

    /**
     * 从服务器删除邮件
     * 
     * @param keyword
     * @param emailFolder
     * @param count
     * @return
     * @throws MessagingException
     */
    private static int deletEmailFromServer(String keyword, IMAPFolder emailFolder, Integer count)
                                                                                                  throws MessagingException {
        Calendar calendar = Calendar.getInstance();
        // 当前时间3天之前的邮件
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        Date date = calendar.getTime();
        // retrieve the messages from the folder in an array and print it
        int j = 1;
        int x = 0;
        while (j <= count) {
            if (!emailFolder.isOpen()) {
                emailFolder.open(Folder.READ_WRITE);
            }
            Message[] messages = null;
            try {
                messages = emailFolder.getMessages(j, (j + 10) > count ? count : (j + 10));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("j---" + j);
                System.out.println("count---" + count);
                break;
            }
            System.out.println("messages.length---" + messages.length);

            for (int i = 0; i < messages.length; i++) {
                if (!emailFolder.isOpen()) {
                    emailFolder.open(Folder.READ_WRITE);
                }
                Message message = messages[i];
                System.out.println("---------------------------------");

                Date receivedDate = null;
                try {
                    receivedDate = message.getSentDate();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                if (receivedDate.getTime() < date.getTime() && message.getFrom()[0].toString().contains(keyword)) {
                    x++;
                    System.out.println("Subject: " + message.getSubject());
                    message.setFlag(Flags.Flag.DELETED, true);
                }
                if (x % 100 == 0) {
                    emailFolder.close(true);
                }
            }

            j = j + 10;
        }
        return x;
    }

    /**
     * 获取指定文件夹
     *
     * @param user
     * @param password
     * @param folderName
     * @param messageSendService
     * @param store
     * @return
     * @throws MessagingException
     */
    private static IMAPFolder queryEmailFolder(String user, String password, String folderName,
                                               MessageSendService messageSendService, Store store)
                                                                                                  throws MessagingException {
        store.connect(user, password);
        Folder[] folders = store.getDefaultFolder().list("*");
        IMAPFolder emailFolder = null;
        for (Folder folder : folders) {
            if (folder.getName().contains(folderName)) {
                emailFolder = (IMAPFolder) folder;
                break;
            }
        }
        if (emailFolder == null) {
            emailFolder = (IMAPFolder) folders[0];
            messageSendService.send("不存在文件夹:" + folderName, user, password, "删除邮件出错");
            System.exit(0);
        }
        emailFolder.open(Folder.READ_WRITE);
        return emailFolder;
    }

    /**
     * @param args 用户名，密码，要删除的邮件的关键字,要删除的邮件所在的邮件组
     * @throws UnsupportedEncodingException
     */
    public static void main(String args[]) throws UnsupportedEncodingException {
        delete(args[0], args[1], args[2], args[3]);
    }
}
