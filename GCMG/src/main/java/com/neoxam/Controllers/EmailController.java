package com.neoxam.Controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoxam.SendingEmail.SmtpEmailSender;

@RestController
public class EmailController {
	@Autowired
	private SmtpEmailSender smtpMailSender;

	@RequestMapping("/send-mail")
	public void sendMail() throws MessagingException {
		
		smtpMailSender.send("ghaith.mensi@neoxam.com", "Test mail from Spring", "Howdy");
		
	}
}
