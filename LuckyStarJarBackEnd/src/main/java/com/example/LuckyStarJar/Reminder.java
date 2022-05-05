package com.example.LuckyStarJar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;  

public class Reminder 
{
     public static void ReminderTimer() throws SQLException, Exception
     {
        // Local variables
        long daysPassed;
        long difference;
        ArrayList<User>allUsers;

        // Create the format of the date and time
        Calendar calendar = Calendar.getInstance();

        // Create a new instance of the date
        Date date = new Date(calendar.getTime().getTime());

        // Pull all the jars from the database
        ArrayList<Jar> jarList = DatabaseAccess.getAllJars();
        
        // For loop to loop through all of the jars
        for (int index = 0; index < jarList.size(); index++)
        { 
            // Pull the user information to use for email
            allUsers = DatabaseAccess.getAllUsers(jarList.get(index).getJarID());
            
            System.out.println(allUsers.size());
            
            // Grab the reminder frequency and last reminder date from the jar
            Jar jarReminder = jarList.get(index);

            int frequency = jarReminder.getRemFrequency();
            
            Date reminderDate = jarReminder.getLastReminderDate();

            // Check if the last Reminder Date is past the current date
            difference = date.getTime() - reminderDate.getTime();
            daysPassed = difference / (1000*60*60*24);
            System.out.println(daysPassed);

            // Check for each reminder frequency of the jar
            // OpenOrNot being greater than or equal to zero means the current
            // date is past or equal to the reminder date
            if (daysPassed >=30 && frequency == 2) // Monthly reminder
            {
                //Call reminder email method for each user
                for(int count = 0; count < allUsers.size(); count++)
                {
                    JavaMailUtil.sendReminderNote(allUsers.get(count).getUserEmail(), jarReminder.getJarID());
                }

                
                
                // Update the next reminder date
                DatabaseAccess.updateReminderDate(jarReminder);
            }

            else if (daysPassed >= 7 && frequency == 1) // Weekly reminder
            {
                //Call reminder email method for each user
                for(int count = 0; count < allUsers.size(); count++)
                {
                    JavaMailUtil.sendReminderNote(allUsers.get(count).getUserEmail(), jarReminder.getJarID());
                }
                
                // Update the next reminder date
                DatabaseAccess.updateReminderDate(jarReminder);
            }

            else if (daysPassed>=1 && frequency == 0) // Daily reminder
            {
                //Call reminder email method for each user
                for(int count = 0; count < allUsers.size(); count++)
                {
                    JavaMailUtil.sendReminderNote(allUsers.get(count).getUserEmail(), jarReminder.getJarID());
                }
                
                // Update the next reminder date
                DatabaseAccess.updateReminderDate(jarReminder);
            }
        }
     }
    
}