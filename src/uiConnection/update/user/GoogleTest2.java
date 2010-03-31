package uiConnection.update.user;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class GoogleTest2{


private static final String SMTP_HOST_NAME = "smtp.gmail.com";

private static final String SMTP_AUTH_USER = "noorin671@gmail.com";

private static final String SMTP_AUTH_PWD = "alghazaly";

private static final String emailMsgTxt = "Please visit my project ";

private static final String emailSubjectTxt = "Ticket Tracker";

private static final String[] emailSendToList = { "noorin671@gmail.com" };

public static void main(String args[]) throws Exception {
GoogleTest2 smtpMailSender = new GoogleTest2();
smtpMailSender.postMail(emailSendToList, emailSubjectTxt, emailMsgTxt);
System.out.println("Sucessfully Sent mail to All Users");
}

public void postMail(String recipients[], String subject, String message) throws MessagingException {

java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

//Set the host smtp address
Properties props = new Properties();
props.put("mail.transport.protocol", "smtp");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host", SMTP_HOST_NAME);
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.port", "587"); 

Authenticator auth = new SMTPAuthenticator();
//auth = null;
Session session = Session.getDefaultInstance(props, auth);

session.setDebug(false);

// create a message
Message msg = new MimeMessage(session);

InternetAddress[] addressTo = new InternetAddress[recipients.length];
for (int i = 0; i < recipients.length; i++) {
addressTo[i] = new InternetAddress(recipients[i]);
}
msg.setRecipients(Message.RecipientType.TO, addressTo);
msg.setSubject(subject);
msg.setContent(message, "text/plain");
Transport.send(msg);
}

/**
SimpleAuthenticator is used to do simple authentication when the SMTP
server requires it.
*/
private class SMTPAuthenticator extends javax.mail.Authenticator {

public PasswordAuthentication getPasswordAuthentication() {
String username = SMTP_AUTH_USER;
String password = SMTP_AUTH_PWD;
return new PasswordAuthentication(username, password);
}
}


}



