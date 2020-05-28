package com.tll.controllers;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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

public class EmailController {

	private static final String HOST = "smtp.office365.com";
	private static final String PORT = "587";
	private static final String REQUIRE_ENCRYPTION = "Y";
	public int ERROR_CODE = 0;
	Transport transport;
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		EmailController sendEmail = new EmailController();
		String to = "pranay@carprosystems.com;";
		String cc = "swapnil@carprosystems.com;amit@carprosystems.com";
		String bcc = "";
		String username = "carprotest@hotmail.com";
		String from = "carprotest@hotmail.com";
		String password = "CarPro$321";
		String subject = "New Email. sent with java";
		String body = "Hi testing,\n\nThis   is the body of the email\n\nRegards,\ntest";
		String fileAttachment = "";
		System.out.println("PORT: " + PORT);
		sendEmail.sendMail(HOST, PORT, to, cc, bcc, username, from, password, subject, body, fileAttachment,
				REQUIRE_ENCRYPTION);
		System.out.println("took: " + (System.currentTimeMillis() - start));
	}
	
	public int sendMail(String host, String port, String to, String cc, String bcc, String username, String from,
			String password, String subject, String body, String fileAttachment, String requireEncryption) {
		this.execute(host, port, to, cc, bcc, username, from, password, subject, body, fileAttachment,
				requireEncryption);
		System.out.println(ERROR_CODE);
		return (ERROR_CODE);
	}

	public void execute(String host, String port, String to, String cc, String bcc, String username, String from,
			String password, String subject, String body, String fileAttachment, String requireEncryption) {
		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "false");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.trust", host);
		properties.put("mail.smtp.auth", false);
		properties.put("mail.smtp.socketFactory.port", port);
		properties.put("mail.smtp.starttls.enable", true);
		properties.setProperty("charset", "UTF-8");

		if (requireEncryption.equals("Y")) {
			properties.put("mail.smtp.socketFactory.port", port);
			// properties.put("mail.smtp.socketFactory.class",
			// "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			// properties.put("mail.smtp.starttls.enable", "true");
		}

		Authenticator authenticator = new MyAuthenticator(from, password);

		Session session = Session.getInstance(properties, authenticator);

		try {

			transport = session.getTransport("smtp");
			transport.connect(username, password);
			MimeMessage message = new MimeMessage(session);
			// Set from
			message.setFrom(new InternetAddress(from));
			// Set subject
			message.setSubject(subject, "UTF-8");
			System.out.println("subject---" + subject);
			// Set time
			message.setSentDate(new Date());
			// Set content
			// In this example multipart of
			// part1 is email body
			// part2 is the attachement
			MimeBodyPart bodyPart1 = new MimeBodyPart();
			bodyPart1.setText(body, "UTF-8");
			System.out.println("body---" + body);
			MimeBodyPart bodyPart2;
			Multipart multipart = new MimeMultipart();
			FileDataSource fileDataSource;
			multipart.addBodyPart(bodyPart1);
			String fileName;
			int start = 0;
			int end = fileAttachment.length();
			if (!fileAttachment.equals("")) {
				while (end != -1 && start < fileAttachment.length()) {
					end = fileAttachment.indexOf(",", start);
					if (end == -1 && start >= 0) {
						end = fileAttachment.length();
					}
					fileName = fileAttachment.substring(start, end);
					// System.out.println(fileName);
					start = end + 1;
					fileDataSource = new FileDataSource(fileName);
					bodyPart2 = new MimeBodyPart();
					bodyPart2.setDataHandler(new DataHandler(fileDataSource));
					bodyPart2.setFileName(fileDataSource.getName());
					multipart.addBodyPart(bodyPart2);
				}
			}
			message.setContent(multipart);
			// Set complete
			message.saveChanges();
			// Attache the one or multiple "To recipients" address to the
			// "message".
			String mailTO[] = to.split(";");
			InternetAddress[] address = new InternetAddress[mailTO.length];
			for (int i = 0; i < mailTO.length; i++) {
				address[i] = new InternetAddress(mailTO[i]);
			}
			message.setRecipients(Message.RecipientType.TO, address);
			transport.sendMessage(message, address);
			if (!cc.equals("")) {
				// Attache the one or multiple "CC recipients" address to the
				// "message".
				String mailCC[] = cc.split(";");
				InternetAddress[] addressCC = new InternetAddress[mailCC.length];
				for (int i = 0; i < mailCC.length; i++) {
					addressCC[i] = new InternetAddress(mailCC[i]);
				}
				// InternetAddress[] addressCC = { new InternetAddress(cc) };
				message.setRecipients(Message.RecipientType.CC, addressCC);
				transport.sendMessage(message, addressCC);
			}
			if (!bcc.equals("")) {
				// Attache the one or multiple "BCC recipients" address to the
				// "message".
				String mailBCC[] = bcc.split(";");
				InternetAddress[] addressBCC = new InternetAddress[mailBCC.length];
				for (int i = 0; i < mailBCC.length; i++) {
					addressBCC[i] = new InternetAddress(mailBCC[i]);
				}
				// InternetAddress[] addressBCC = { new InternetAddress(bcc) };
				message.setRecipients(Message.RecipientType.BCC, addressBCC);
				transport.sendMessage(message, addressBCC);
			}
			// transport.close();
		} catch (MessagingException e) {
			// e.printStackTrace();
			ERROR_CODE = -1;
			stackTraceToFile(e);
		}
		try {
			System.out.println("Transport Closed");
			transport.close();
		} catch (Exception e) {
		}
	}

	public void stackTraceToFile(MessagingException e) {
		try {
			Calendar cal = Calendar.getInstance();
			FileOutputStream fo = new FileOutputStream("Email.log", true);
			PrintStream ps = new PrintStream(fo);
			ps.println("------------------------------------------------------");
			ps.print("Log Date & Time:");
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy hh:mm:ss z");
			ps.println(sdf.format(cal.getTime()));
			e.printStackTrace(ps);
			ps.println("------------------------------------------------------");
			ps.close();
			fo.close();
		} catch (Exception ex) {
			ERROR_CODE = -1;
			ex.printStackTrace();
		}
	}	
}

class MyAuthenticator extends Authenticator {
	protected PasswordAuthentication passwordAuthentication;
	public MyAuthenticator(String user, String password) {
		this.passwordAuthentication = new PasswordAuthentication(user, password);
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		return passwordAuthentication;
	}
}