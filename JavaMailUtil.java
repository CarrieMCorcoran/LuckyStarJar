package luckystaremailapi.javamail;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMailUtil {
    
       // Invite methods for adding a user to the jar.
    
    public static void sendInviteEmail(String recepient, String jarOwner, int inviteCode) throws Exception
    {      
        System.out.println("Preparing automated message...");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "luckystarjarowsego@gmail.com";
        String password = "coffeeisoverrated";
        
        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        
        Message invite_message = prepareInviteMessage(session, myAccountEmail, recepient, jarOwner, inviteCode);
        
        Transport.send(invite_message);
        System.out.println("Message sent successfully to the user.");
    }
    
    private static Message prepareInviteMessage(Session session, String 
            myAccountEmail, String recepient, String jarOwner, int inviteCode)
    {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new 
                InternetAddress(recepient));
            message.setSubject("You have been invited to " + jarOwner
                    + "'s Positivity Jar!");
            message.setText(jarOwner + " would like to invite you to their "
            + "positivity jar!\nCalled the Lucky Star Jar, the aim for this "
            + "jar is to collabarate, create and look back on the good "
            + " memories that have happened in your life.\nDid you win your "
            + "hockey game today in spectacular fassion?\nDid you do something"
            + " incredible for someone out of the goodness of your heart?\n"
            + " Did you beat your Father in a battle of wits?\nWell, write that "
            + " all down and remeber that moment forever with the Lucky Star Jar!\n"
            + "We are so glad you have chosen such a positive thing in your life!\n"
            + "\n Invite Code: " + inviteCode + "\n\nType or copy this into the 'Join Jar"
            + " button on the main page of the Lucky Star Jar.\n Enjoy and have fun!");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }      
        return null;
    }
    
    // Email for returning a random note in the jar
    public static void sendReminderNote(String recepient, int jarID) throws Exception
    {
        // Grab a random note
        // Access database
        ArrayList <Note> Notes = DatabaseAccess.openJar(jarID);
        
        // Random number generator from 0 to note size
        int amountOfNotes = Notes.size();
        Random rand = new Random();
        
        // Get the random index of a string positive note
        int randomJarNote = rand.nextInt(amountOfNotes);
        
        // Grab the note text
        Note n = Notes.get(randomJarNote);
        
        // Convert the note into a string and get the text layout
        String positiveNote = n.getNoteText();
        
        System.out.println("Preparing automated message...");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "luckystarjarowsego@gmail.com";
        String password = "coffeeisoverrated";
        
        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        
        Message reminder_message = prepareReminderMessage(session, 
                myAccountEmail, recepient, positiveNote);
        
        Transport.send(reminder_message);
        System.out.println("Message sent successfully to the user.");
    }
    
    private static Message prepareReminderMessage(Session session, String 
            myAccountEmail, String recepient, String randomNote)
    {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new 
                InternetAddress(recepient));
            if (randomNote == null)
            {
                message.setSubject("You Have no Messages in your Lucky Star Jar!");
                message.setText("We wanted to send you a random note from your "
                + "Lucky star Jar, but per the frequency of your reminder,\n"
                + "We found no notes in your jar!\nHurry up and put some positive"
                + " notes in that jar so you can share the memories with yourself"
                + ", your friends and your family!\n");
            }
            
            else
            {
                message.setSubject("Your Timer is up on your Lucky Star Jar!");
                message.setText("Here is a random message within your very own"
                        + " Lucky Star Jar!: \n\n" + randomNote);
            }
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }      
        return null;
    }
    
    // Email for when the jar opens
    public static void sendOpenMail(String recepient, int jarID) throws Exception
    {
        ArrayList <Note> Notes = DatabaseAccess.openJar(jarID); // Test on jarID 1
        String totalNotes = "You have opened your jar!\n Time to look at all the "
                + "great moments you had!\n Thank you for using Lucky Star and "
                + "we hope that you continue to use our product to elevate your"
                + " positive and happy life!\n\n";
        
        // For loop to grab every note within the given jarID
        for(int index = 0; index < Notes.size(); index++)
        {
            Note n = Notes.get(index);
            
            String positiveNote = n.getNoteText();
            String name = n.getUserName();
            java.util.Date d = n.getNoteDate();
            
            String noteLine = "On " + d + ", " + name + " wrote: " + positiveNote + "\n\n";
            
            totalNotes += noteLine;
        }
        System.out.println("Preparing automated message...");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "luckystarjarowsego@gmail.com";
        String password = "coffeeisoverrated";
        
        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        
        Message open_message = prepareOpenMessage(session, myAccountEmail, recepient, totalNotes);
        
        Transport.send(open_message);
        System.out.println("Message sent successfully to the user.");
    }
    
    private static Message prepareOpenMessage(Session session, String 
            myAccountEmail, String recepient, String allMessages)
    {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new 
                InternetAddress(recepient));
            message.setSubject("Jar Opening Session for your Lucky Star!");
            message.setText(allMessages);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }      
        return null;
    }
}
