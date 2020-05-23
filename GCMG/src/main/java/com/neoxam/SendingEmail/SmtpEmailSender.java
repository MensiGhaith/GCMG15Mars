package com.neoxam.SendingEmail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class SmtpEmailSender {
	@Autowired
	private JavaMailSender javaMailSender;	
	
	public void send(String to, String subject, String body) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		helper = new MimeMessageHelper(message, true); // true indicates
													   // multipart message
		helper.setSubject(subject);
		helper.setTo(to);
		String htmlCode="<html>" + 
				"<body>" + 
				"<h1 style=\"color:#09c0a3;\" >Bienvenue sur  notre plateforme</h1>\r\n" + 
				"<p><h4>vous pouvez maintenant vous connecter </h4><a href=\"\">LOGIN PAGE</a>\r\n" + 
				"</body>\r\n" + 
				"</html>";
	message.setContent(htmlCode,"text/html");
		// true indicates html
		// continue using helper object for more functionalities like adding attachments, etc.  
		
		javaMailSender.send(message);
		
		
	}
public void SendToAdmin( String subject, String body,String username,String FileName,String dep) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		helper = new MimeMessageHelper(message, true); // true indicates
													   // multipart message
		helper.setSubject(subject);
		helper.setTo("mensighaith98@gmail.com");
		String htmlCode="<html>" + 
				"<body>" + 
				"<h1 style=\"color:#09c0a3;\" >Upload d'un nouveau fichier</h1>\r\n" + 
				"<p><h4>L'utilisateur "+ username +" a ajouté le fichier  "+ FileName +" dans le département "+ dep  +" </h4></p>\r\n" + 
				"</body>\r\n" + 
				"</html>";
	message.setContent(htmlCode,"text/html");
		// true indicates html
		// continue using helper object for more functionalities like adding attachments, etc.  
		
		javaMailSender.send(message);
		
		
	}
}
