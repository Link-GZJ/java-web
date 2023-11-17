package com.guo.learn;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 练习发送html邮件
 *
 * @author guozongjun
 * @version 1.0
 */
public class SendHtmlEmail {
    final String smtpHost;
    final String username;
    final String password;
    final boolean debug;
    SendHtmlEmail(String smtpHost, String username, String password, boolean debug){
        this.smtpHost = smtpHost;
        this.username = username;
        this.password = password;
        this.debug = debug;
    }
    //获取session 实例 ssl加密
    private Session createSSLSession(){
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost);//smtp主机名
        props.put("mail.smtp.port", "465");//端口号
        props.put("mail.smtp.auth", "true");//是否需要用户验证
        // 启动SSL:
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //用户名、口令认证
                return new PasswordAuthentication(username, password);
            }
        });
        //显示调试信息
        session.setDebug(this.debug);
        return session;
    }
    //获取session 实例 tls加密
    private Session createTLSSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost); // SMTP主机名
        props.put("mail.smtp.port", "587"); // 主机端口号
        props.put("mail.smtp.auth", "true"); // 是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //这里使用内部类的关系，写成class.this.field,
                return new PasswordAuthentication(SendHtmlEmail.this.username, password);
            }
        });
        session.setDebug(this.debug); // 显示调试信息
        return session;
    }

    private static void sendEmail() {
        //首先通过java api登录smtp服务器
        //服务器地址，我选择通过qq邮箱发送邮件
        String smtp = "smtp.qq.com";
        //登录用户名
        String username = "link4839@qq.com";
        //登录口令
        String password = "dbolwvhrakglfidc";
        
        //获取session 实例
        SendHtmlEmail se = new SendHtmlEmail(smtp,username,password,true);
        Session session = se.createSSLSession();
        se.createTextMessage(username, session);
    }

    private void createTextMessage(String username, Session session) {
        MimeMessage message = new MimeMessage(session);
        //设置发送方地址，一般要求和登录用户名一致
        try {
            message.setFrom(new InternetAddress(username,"林克","UTF-8"));
            //设置接收方地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("link20231002@163.com"));
            //设置邮件主题
            message.setSubject("Hello", "UTF-8");

            //设置邮件正文
            message.setText("<h1>Hello</h1><p>这是一封Html邮件</p>", "UTF-8","html");
            //发送
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        sendEmail();
    }
}
