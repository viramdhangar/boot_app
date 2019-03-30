package com.waio.email.api;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.waio.verification.model.OTPSystem;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("EmailService")
public class EmailService implements IEmailService {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	private final static String FTL= ".ftl";
	
	public MailResponse sendEmail(MailRequest request, Map<String, Object> model, String templateName) {
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
	
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
			//helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

			Template t = config.getTemplate(templateName+FTL);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(request.getTo());
			helper.setText(html, true);
			helper.setSubject(request.getSubject());
			helper.setFrom(request.getFrom());
			sender.send(message);

			response.setMessage("Email send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		return response;
	}
	public boolean sendEmailUser(String subject,String body,String toAdd){
		
		try
		  {
		  Properties props=new Properties();
		   props.put("mail.smtp.host", "waiotech.com");
		  // props.put("mail.smtp.host","localhost");   //  'localhost' for testing
		   Session   session1  =  Session.getDefaultInstance(props);
		   String s1 = "hr@waiotech.com"; //sender (from)
		   String s2 = toAdd;
		  // String s3 = request.getParameter("name");
		  // String s4 = request.getParameter("messagevalue");
		   MimeMessage message =new MimeMessage(session1);
		   message.setFrom(new InternetAddress(s1));
		   message.addRecipient(Message.RecipientType.TO,
                 new InternetAddress(s2));
		   message.setSubject(subject);
		   message.setText(body);  
		   Transport.send(message);
		  }
		  catch(Exception ex)
		  {
		   System.out.println("ERROR....."+ex);
		  }
		return true;
	}
	
	@Override
	public MailResponse sendResetEmail(OTPSystem request, Map<String, Object> model, String templateName){
		
		String host = "waiosystem.in";
		MailResponse response = new MailResponse();
		try {

			boolean sessionDebug = true;
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");

			props.put("mail.smtp.host", host);
			props.put("mail.smtp.ssl.trust", host);
			props.put("mail.transport.protocol.", "smtp");
			props.put("mail.smtp.user", "hr@waiotech.com"); // User name
			props.put("mail.smtp.password", "Ramkishan!123"); // password

			props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.", "true");
			props.put("mail.smtp.port", "587");
			
		//	Template t = config.getTemplate(templateName+FTL);
		//	String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("hr@waiotech.com", "Ramkishan!123");
				}
			});
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress("hr@waiotech.com"));
			InternetAddress[] address = { new InternetAddress(request.getEmail()) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(request.getSubject());
			Multipart multipart = new MimeMultipart();
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(request.getOtp());
		//	MimeBodyPart htmlPart = new MimeBodyPart();
		//    htmlPart.setContent( html, "text/html; charset=utf-8" );
		    
			// multipart.addBodyPart( htmlPart ); // add it when need template
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			msg.setContent(multipart, "text/html"); // use setText if you want
													// to send text
			Transport.send(msg);
			System.out.println("sent");
			// WasEmailSent = true; // assume it was sent
			response.setMessage("Email send to : " + request.getEmail());
			response.setStatus(Boolean.TRUE);
		} catch (Exception err) {
			// WasEmailSent = false; // assume it's a fail
			System.out.println("Error" + err.getMessage());
			response.setMessage("Mail Sending failure : "+err.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		return response;
	}
	 	private String SMTP_HOST = "smtp.gmail.com";
	    private String FROM_ADDRESS = "viramdhangar786@gmail.com";
	    private String PASSWORD = "ramkishan";
	    private String FROM_NAME = "Sameera";

	    public boolean sendMailss(String[] recipients, String[] bccRecipients, String subject, String message) {
	        try {
	            Properties props = new Properties();
	            props.put("192.168.0.102", SMTP_HOST);
	            props.put("mail.smtp.auth", "true");
	            props.put("mail.debug", "false");
	            props.put("mail.smtp.ssl.enable", "true");
	            //props.put("mail.smtp.port", "457");

	            Session session = Session.getInstance(props, new SocialAuth());
	            Message msg = new MimeMessage(session);

	            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
	            msg.setFrom(from);

	            InternetAddress[] toAddresses = new InternetAddress[recipients.length];
	            for (int i = 0; i < recipients.length; i++) {
	                toAddresses[i] = new InternetAddress(recipients[i]);
	            }
	            msg.setRecipients(Message.RecipientType.TO, toAddresses);


	            InternetAddress[] bccAddresses = new InternetAddress[bccRecipients.length];
	            for (int j = 0; j < bccRecipients.length; j++) {
	                bccAddresses[j] = new InternetAddress(bccRecipients[j]);
	            }
	            msg.setRecipients(Message.RecipientType.BCC, bccAddresses);

	            msg.setSubject(subject);
	            msg.setContent(message, "text/plain");
	            Transport.send(msg);
	            return true;
	        } catch (UnsupportedEncodingException ex) {
	            //Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
	            return false;

	        } catch (MessagingException ex) {
	            //Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }

	    class SocialAuth extends Authenticator {

	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {

	            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

	        }
	    }
}
