package tr.org.lkd.lyk2015.camp.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EmailConfirmationService implements EmailService {

	@Override
	public boolean sendConfirmation(String to, String subject, String context) {

		final String username = "lyk2015java@gmail.com";
		final String from = "lyk2015java@gmail.com";
		final String password = "510B619-J1|#!rD";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject("LYK - Aktivasyon Mesajı"); // subject
			message.setText("Dear " + "," + "\n\n!" + context);

			Transport.send(message);

			System.out.println("Done");
			System.out.println("Sent message successfully....");

			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();

		}

		return false;

	}

}
