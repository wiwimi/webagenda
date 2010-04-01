package uiConnection.update.user;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class GoogleTest3
{
	
	String SMTP_HOST_NAME = "smtp.gmail.com";
	String SMTP_AUTH_USER = "noorin671@gmail.com";
	String SMTP_AUTH_PWD = "alghazaly";
	String emailMsgTxt = "Please visit my project ";
	String emailSubjectTxt = "Ticket Tracker";
	String[] emailSendToList = null;
	
	public GoogleTest3()
	{
		
	}
	
	public GoogleTest3(String SMTP_HOST_NAME, String SMTP_AUTH_USER, String SMTP_AUTH_PWD)
	{
		
	}
		
	
	public void postMail(String recipients[], String subject, String message) throws MessagingException 
	{
		
		java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	
		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587"); 
		
		Authenticator auth = new SMTPAuthenticator();
		
		Session session = Session.getInstance(props, auth);
		
		session.setDebug(false);
		
		// create a message
		Message msg = new MimeMessage(session);
		
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) 
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}
		
	public String getSMTP_HOST_NAME() {
		return SMTP_HOST_NAME;
	}

	public void setSMTP_HOST_NAME(String sMTPHOSTNAME) {
		SMTP_HOST_NAME = sMTPHOSTNAME;
	}

	public String getSMTP_AUTH_USER() {
		return SMTP_AUTH_USER;
	}

	public void setSMTP_AUTH_USER(String sMTPAUTHUSER) {
		SMTP_AUTH_USER = sMTPAUTHUSER;
	}

	public String getSMTP_AUTH_PWD() {
		return SMTP_AUTH_PWD;
	}

	public void setSMTP_AUTH_PWD(String sMTPAUTHPWD) {
		SMTP_AUTH_PWD = sMTPAUTHPWD;
	}

	public String getEmailMsgTxt() {
		return emailMsgTxt;
	}

	public void setEmailMsgTxt(String emailMsgTxt) {
		this.emailMsgTxt = emailMsgTxt;
	}

	public String getEmailSubjectTxt() {
		return emailSubjectTxt;
	}

	public void setEmailSubjectTxt(String emailSubjectTxt) {
		this.emailSubjectTxt = emailSubjectTxt;
	}

	public String[] getEmailSendToList() {
		return emailSendToList;
	}

	public void setEmailSendToList(String[] emailSendToList) {
		this.emailSendToList = emailSendToList;
	}

	/**
	SimpleAuthenticator is used to do simple authentication when the SMTP
	server requires it.
	*/
	private class SMTPAuthenticator extends javax.mail.Authenticator 
	{
	
		public PasswordAuthentication getPasswordAuthentication() 
		{
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}
	
	

