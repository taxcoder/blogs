package com.tanx.blogs.service.mail;


import com.tanx.blogs.pojo.model.FriendLink;
import com.tanx.blogs.pojo.model.User;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
@PropertySource({"classpath:application-prod.yaml"})
public class MailSend {
    @Value("${spring.mail.username}")
    private String username;
    private String nick = "";

    private JavaMailSenderImpl sender;

    @Autowired
    public void setSender(JavaMailSenderImpl sender) {
        this.sender = sender;
    }

    @Async
    public void send(User user, String title, String content) throws MessagingException {
        MimeMessage message = this.sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        try {
            this.nick = MimeUtility.encodeText("Tree");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        helper.setFrom(new InternetAddress(this.nick + "<" + this.username + ">"));
        helper.setTo(user.getEmail());
        helper.setSubject(title);
        helper.setText(content);
        this.sender.send(message);
    }

    @Async
    public void errorSend(String email) throws MessagingException {
        MimeMessage message = this.sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom(this.username);

        helper.setTo(email);

        helper.setSubject("\u7F51\u7AD9\u88AB\u9ED1\u8B66\u544A\uFF01");

        helper.setText("\u7F51\u7AD9\u7BA1\u7406\u5458\u4EBA\u6570\u589E\u52A0\u6216\u51CF\u5C11\uFF0C\u6570\u636E\u5E93\u53EF\u80FD\u88AB\u4FEE\u6539\uFF0C\u9700\u8981\u5BF9\u7F51\u7AD9\u8FDB\u884C\u6F0F\u6D1E\u6E05\u67E5\u53CA\u6253\u8865\u4E01\uFF01");
        this.sender.send(message);
    }

    @Async
    public void send(FriendLink friendLink) throws MessagingException {
        MimeMessage message = this.sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        try {
            this.nick = MimeUtility.encodeText("Tree");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        helper.setFrom(new InternetAddress(this.nick + "<" + this.username + ">"));
        helper.setTo(username);
        helper.setSubject("增加友链");
        String content = "网址："+friendLink.getFriendAddress()+"\n"+
                         "图片链接："+friendLink.getFriendImage()+"\n"+
                         "名称："+friendLink.getFriendName()+"\n"+
                         "描述："+friendLink.getFriendBrief()+"\n"+
                         "邮箱："+friendLink.getEmail();
        helper.setText(content);
        this.sender.send(message);
    }

}
