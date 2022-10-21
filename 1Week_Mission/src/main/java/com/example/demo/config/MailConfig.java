package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration   //클래스를 빈으로 등록해줘야 함
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");  //메인 도메인 서버 주소 >> smtp서버주소
        javaMailSender.setUsername("llfinal2book");
        javaMailSender.setPassword("lldj1234");

        javaMailSender.setPort(465); // 메일 인증서버 포트

        javaMailSender.setJavaMailProperties(getMailProperties()); //메일 인증서버 가져오기

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");  //프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true");  //smtp인증
        properties.setProperty("mail.smtp.starttls.enable", "true"); //smtp srarttles 사용
        properties.setProperty("mail.debug", "true"); // 디버그 사용
        properties.setProperty("mail.smtp.ssl.trust", "smtp.google.com");  //ssl 인증 서버
        properties.setProperty("mail.smtp.ssl.enable", "true");  //ssl 사용
        return properties;

    }
}
