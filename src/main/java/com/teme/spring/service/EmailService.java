package com.teme.spring.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendGreetingEmail(String name,String receiver){
        HtmlEmail htmlEmail = new HtmlEmail();
        try {
            htmlEmail.setHostName("smtp.googlemail.com");
            htmlEmail.setSmtpPort(465);
            htmlEmail.setAuthentication("hieu.minh9820@gmail.com", "Minhhieu98");
            htmlEmail.setSSLOnConnect(true);
            htmlEmail.setFrom("hieu.minh9820@gmail.com","BK Software");
            htmlEmail.setSubject("Hello "+name);
            String html = "<html><head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "</head><body> Hello <b>"+name+"</b></body></html>" ;
            htmlEmail.setHtmlMsg(html);
            htmlEmail.addTo(receiver);
            htmlEmail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
