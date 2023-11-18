package com.guo.learn;

import com.sun.mail.pop3.POP3SSLStore;
import com.sun.mail.util.MimeUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 使用pop3协议接收邮件
 *
 * @author guozongjun
 * @version 1.0
 */
public class ReceiveEmailWithPop3 {
    final String host;
    final String username;
    final String password;
    final boolean debug;
    ReceiveEmailWithPop3(String smtpHost, String username, String password, boolean debug){
        this.host = smtpHost;
        this.username = username;
        this.password = password;
        this.debug = debug;
    }
    //获取session 实例 ssl加密
    private Store createSSLStore() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.store.protocol", "pop3");//smtp主机名
        props.put("mail.pop3.host", host);//端口号
        props.put("mail.pop3.port", "995");//是否需要用户验证
        // 启动SSL:
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "995");

        //连接到store
        URLName urlName = new URLName("pop3", host, 995, "", username, password);
        Session session = Session.getInstance(props, null);
        //显示调试信息
        session.setDebug(this.debug);
        Store store = new POP3SSLStore(session, urlName);
        store.connect();
        return store;
    }


    private static void receiveEmail() throws MessagingException, UnsupportedEncodingException {
        //首先通过java api登录smtp服务器
        //服务器地址，我选择通过qq邮箱发送邮件
        String smtp = "pop.qq.com";
        //登录用户名
        String username = "link4839@qq.com";
        //登录口令
        String password = "dbolwvhrakglfidc";
        
        //获取session 实例
        ReceiveEmailWithPop3 se = new ReceiveEmailWithPop3(smtp,username,password,true);
        Store store = se.createSSLStore();
        se.parseMessage(store);
    }

    private void parseMessage(Store store) throws MessagingException, UnsupportedEncodingException {
        //获取收件箱
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        //打印邮件总数、新邮件数量、未读数量、已读数量
        System.out.println("Total Messages: " + folder.getMessageCount());
        System.out.println("New Messages: " + folder.getNewMessageCount());
        System.out.println("Unread Messages: " + folder.getUnreadMessageCount());
        System.out.println("Deleted Messages: " + folder.getDeletedMessageCount());
        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            //打印每一封邮件
            printMessage((MimeMessage)message);
        }
        folder.close();
        store.close();
    }

    private void printMessage(MimeMessage message) throws MessagingException, UnsupportedEncodingException {
        //邮件主题
        System.out.println("Subject: " + MimeUtility.decodeText(message.getSubject()));
        //发件人
        Address[] froms = message.getFrom();
        InternetAddress address = (InternetAddress) froms[0];
        String personal = address.getPersonal();
        String from = personal == null ? address.getAddress() : (MimeUtility.decodeText(personal) + " <" + address.getAddress() + ">");
        System.out.println("From: " + from);
    }

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        receiveEmail();
    }
}
