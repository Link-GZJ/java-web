package com.guo.learn;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 练习发送邮件
 *
 * @author guozongjun
 * @version 1.0
 */
public class SendEmail {
    public static void main(String[] args) {
        sendEmail();
    }
    private static void sendEmail() {
        //首先通过java api登录smtp服务器
        //服务器地址，我选择通过qq邮箱发送邮件
        String smtp = "smtp.qq.com";
        //登录用户名
        String username = "link4839@qq.com";
        //登录口令
        String password = "dbolwvhrakglfidc";
        Properties pt = new Properties();
        pt.put("mail.smtp.host", smtp);
        pt.put("mail.smtp.port", "465");
        pt.put("mail.smtp.auth", "true");//是否需要用户验证
        pt.put("mail.smtp.ssl.enable","true");//启用ssl加密
        //获取session 实例
        Session session = Session.getInstance(pt, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //设置debug便于调试
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        //设置发送方地址，一般要求和登录用户名一致
        try {
            message.setFrom(new InternetAddress(username,"林克","UTF-8"));
            //设置接收方地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("link20231002@163.com"));
            //设置邮件主题
            message.setSubject("Hello", "UTF-8");

            //设置邮件正文
            message.setText("Hi Link...", "UTF-8");
            //发送
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
