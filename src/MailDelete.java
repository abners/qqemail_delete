/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */

import com.sun.mail.imap.IMAPFolder;
import factory.IMAPStoryFactory;
import factory.StoryFactory;

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
 * 类MailDelete.java的实现描述：TODO 类实现描述
 *
 * @author baoxing.peng 2016年12月14日 11:19:17
 */
public class MailDelete {
    public static void delete(String user,
                              String password,String keyword,String folderName) throws UnsupportedEncodingException {
        try
        {
            StoryFactory factory = new IMAPStoryFactory();
            Store        store   = factory.getInstance();
            store.connect(user,password);
            Folder[] folders = store.getDefaultFolder().list("*");
            IMAPFolder emailFolder = null;
            for(Folder folder:folders){
                if(folder.getName().contains(folderName)) {
                    emailFolder = (IMAPFolder) folder;
                    break;
                }
            }
            if(folders==null){
                emailFolder = (IMAPFolder) folders[0];
                emailFolder.open(Folder.READ_WRITE);
//                emailFolder.get
            }
            emailFolder.open(Folder.READ_WRITE);
            System.out.println("收件箱中共" + emailFolder.getMessageCount() + "封邮件!");
            System.out.println("收件箱中共" + emailFolder.getUnreadMessageCount() + "封未读邮件!");
            System.out.println("收件箱中共" + emailFolder.getNewMessageCount() + "封新邮件!");
            System.out.println("收件箱中共" + emailFolder.getDeletedMessageCount() + "封已删除邮件!");
            Integer count = emailFolder.getMessageCount();
            Calendar calendar = Calendar.getInstance();
            //当前时间3天之前的邮件
            calendar.add(Calendar.DAY_OF_MONTH,-3);
            Date date = calendar.getTime();
            // retrieve the messages from the folder in an array and print it
            int j=1;
            int x = 0;
            while(j<=count) {
                if(!emailFolder.isOpen()){
                    emailFolder.open(Folder.READ_WRITE);
                }
                Message[] messages = null;
                try {
                    messages = emailFolder.getMessages(j, (j + 10) > count ? count : (j + 10));
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("j---"+j);
                    System.out.println("count---"+count);
                    break;
                }
                System.out.println("messages.length---" + messages.length);

                for (int i = 0; i < messages.length; i++) {
                    if(!emailFolder.isOpen()){
                        emailFolder.open(Folder.READ_WRITE);
                    }
                    Message message = messages[i];
                    System.out.println("---------------------------------");

                    Date receivedDate = null;
                    try {
                        receivedDate = message.getSentDate();
                    }catch (Exception e){
                        e.printStackTrace();
                        continue;
                    }
                    if (receivedDate.getTime() < date.getTime()
                        && message.getFrom()[0].toString().contains(keyword)) {
                        x++;
                        System.out.println("Subject: " + message.getSubject());
                        message.setFlag(Flags.Flag.DELETED, true);
                    }
                    if(x%100==0){
                        emailFolder.close(true);
                    }
                }

                j = j + 10;
            }
            // expunges the folder to remove messages which are marked deleted
            emailFolder.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
//        catch (IOException io) {
//            io.printStackTrace();
//        }
    }

    /**
     *
     * @param args 用户名，密码，要删除的邮件的关键字,要删除的邮件所在的邮件组
     * @throws UnsupportedEncodingException
     */
    public static  void main(String args[]) throws UnsupportedEncodingException {
        delete(args[0],
               args[1],args[2],args[3]);
    }
}
