package luckystaremailapi.javamail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
    
       // Invite methods for adding a user to the jar.
    
    public static void sendInviteMail(String recepient, String jarOwner, float code) throws Exception
    {
        System.out.println("Preparing automated message...");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "benjaminjmelby@gmail.com"; // Replace "*" with luckystarjar gmail
        String password = "**********"; // Replace "*" with google luckystarjar password
        
        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        
        Message invite_message = prepareInviteMessage(session, myAccountEmail, recepient, jarOwner, code);
        
        Transport.send(invite_message);
        System.out.println("Message sent successfully to the user.");
    }
    
    private static Message prepareInviteMessage(Session session, String 
            myAccountEmail, String recepient, String jarOwner, float code)
    {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new 
                InternetAddress(recepient));
            message.setSubject("You have been invited to " + jarOwner
                    + "'s positivity jar!");
            message.setText(jarOwner + " would like to invite you to their "
            + "positivity jar!\nCalled the Lucky Star Jar, the aim for this "
            + "jar is to collabarate, create and look back on the good "
            + " memories that have happened in your life.\nDid you win your "
            + "hockey game today in spectacular fassion?\nDid you do something"
            + " incredible for someone out of the goodness of your heart?\n"
            + " Did you beat your Father in a battle of wits?\nWell, write that "
            + " all down and remeber that moment forever with the Lucky Star Jar!\n"
            + "We are so glad you have chosen such a positive thing in your life!\n"
            + "\n Invite Code: " + code + "\n\nType or copy this into the 'Join Jar"
            + " button on the main page of the Lucky Star Jar.\n Enjoy and have fun!");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }      
        return null;
    }
}
