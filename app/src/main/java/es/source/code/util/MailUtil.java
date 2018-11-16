package es.source.code.util;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import es.source.code.AppGlobal;
import es.source.code.callback.OnDataFinishedListener;

/**
 * Created by sail on 2018/10/21.
 */

public class MailUtil {


    public static class InstanceHolder{
        private static MailUtil instance = new MailUtil();
    }

    public static MailUtil getInstance(){
        return InstanceHolder.instance;
    }

    private MailUtil(){}

    class MailTask extends AsyncTask<Void,Void,Boolean>{
        private Transport transport;
        private MimeMessage message;
        private String title;
        private String content;
        private Session session;

        public MailTask(String title,String content){
            this.title =title;
            this.content=content;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                //加载配置文件
                Properties properties = System.getProperties();
                //设置邮件服务器的信息
                properties.put(AppGlobal.Mail.HOST_KEY,AppGlobal.Mail.HOST_VALUE);
                //设置验证通过
                properties.put(AppGlobal.Mail.AUTH_KEY,AppGlobal.Mail.AUTH_VALUE);
                //根据配置新建会话
                Session session = Session.getInstance(properties,getAuthenticator());
                message = new MimeMessage(session);
                //设置发件人地址
                message.setFrom(new InternetAddress(AppGlobal.Mail.Name));
                //设置收件人地址
                InternetAddress[] addresses = new InternetAddress[]{new InternetAddress(AppGlobal.Mail.Name)};
                //设置收件人
                message.setRecipients(Message.RecipientType.TO,addresses);
                //设置标题
                message.setSubject(title);
                //设置信件内容
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart);
                message.setContent(multipart);
                //设置发送时间
                //发送邮件
                message.setSentDate(new Date());
                //保存修改
                message.saveChanges();
                //获得发邮件服务器
                transport = session.getTransport("smtp");
                //发送者的账号连接到smtp服务器上
                transport.connect(AppGlobal.Mail.HOST_VALUE,AppGlobal.Mail.Name,AppGlobal.Mail.AUTHORIZATION);
                transport.sendMessage(message,message.getAllRecipients());
                transport.close();
                return Boolean.TRUE;
            } catch (MessagingException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //if(onDataFinishedListener!=null) {
                System.out.println(aBoolean);
                if (aBoolean) {
                    onDataFinishedListener.onDataSuccess(aBoolean);
                } else {
                    onDataFinishedListener.onDataFail();
                }
           // }
        }
    }


    private Authenticator getAuthenticator() {
        return new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppGlobal.Mail.Name,AppGlobal.Mail.PASSWORD);
            }
        };
    }



    public void sendMail(String title,String content){

            MailTask mailTask = new MailTask(title,content);
            mailTask.execute();

    }

    OnDataFinishedListener onDataFinishedListener;

    public void setOnDataFinishedListener(OnDataFinishedListener onDataFinishedListener) {
        this.onDataFinishedListener = onDataFinishedListener;
    }
}
