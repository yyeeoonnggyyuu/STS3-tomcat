package com.office.library.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {

	@Value("#{commInfoProperty['mail.host']}")
	private String mailHost;
	
	@Value("#{commInfoProperty['mail.port']}")
	private int mailPort;
	
	@Value("#{commInfoProperty['mail.username']}")
	private String mailUsername;
	
	@Value("#{commInfoProperty['mail.password']}")
	private String mailPassword;
	
	@Value("#{commInfoProperty['mail.smtp.auth']}")
	private String mailSmtpAuth;
	
	@Value("#{commInfoProperty['mail.smtp.starttls.enable']}")
	private String mailSmtpStarttlsEnalbe;
	
	@Bean
	public JavaMailSenderImpl mailSender() {
		System.out.println("[MailSenderConfig] mailSender()");
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);
		
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", mailSmtpAuth);
		properties.setProperty("mail.smtp.starttls.enable", mailSmtpStarttlsEnalbe);
		
		mailSender.setJavaMailProperties(properties);
		
		return mailSender;
	}
}
