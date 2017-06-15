package nl.hu.ipass.vergelijkNL.webservices;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nl.hu.ipass.vergelijkNL.persistance.UserDAO;

public class EmailController {
	
	UserDAO ud = new UserDAO();
	
	public boolean sendMail(String user, String pass, String email) {    
		final String username = "vergelijknlipass@gmail.com";
		final String password = "Welkom123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vergelijknlipass@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("Account Creatie VergelijkNL");
			message.setText("Beste " + user + ","
				+ "\n\nU heeft een account aangemaakt bij VergelijkNL met deze informatie:" +
					"\n\nUsername: " + user +
					"\nPassword: " + pass +
					"\n\nU kunt nu inloggen bij deze link: https://vergelijknl.herokuapp.com/login.html");
			
			Transport.send(message);
			
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String sendRecovery(String user, String email) {
		final String username = "vergelijknlipass@gmail.com";
		final String password = "Welkom123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			String pass = ud.getUser(email, user);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vergelijknlipass@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("Account Creatie VergelijkNL");
			message.setText("Beste " + user + ","
				+ "\n\nU heeft uw wachtwoord aangevraagd bij VergelijkNL, hier is uw informatie:" +
					"\n\nUsername: " + user +
					"\nPassword: " + pass +
					"\n\nU kunt nu inloggen bij deze link: https://vergelijknl.herokuapp.com/login.html");
			
			Transport.send(message);
			
			return "Email verzonden naar " + email;
		} catch (Exception e) {
			e.printStackTrace();
			return "Er is iets fout gegaan";
		}
	}
}
