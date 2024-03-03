package com.bookingtable.servicies.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bookingtable.servicies.IMailService;

import jakarta.mail.internet.MimeMessage;

@ComponentScan("org.springframework.mail.javamail.JavaMailSender")
@Service
public class MailService implements IMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public boolean send(String from, String to, String subject, String content) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			javaMailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
