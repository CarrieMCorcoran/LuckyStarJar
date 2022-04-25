package luckystaremailapi.javamail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;  

public class Reminder 
{
    // Vairables

     public static void ReminderTimer() throws SQLException, Exception
     {
        // Local variables
        int openOrNot;

        // Create the format of the date and time
        Calendar calendar = Calendar.getInstance();

        // Create a new instance of the date
        Date date = new Date(calendar.getTime().getTime());

        // Pull all the jars from the database
        ArrayList<Jar> jarList = DatabaseAccess.getAllJars();
        
        Date currentDate = DatabaseAccess.getDate();

        // For loop to loop through all of the jars
        for (int index = 0; index < jarList.size(); index++)
        {            
            // Grab the reminder frequency and last reminder date from the jar
            Jar jarReminder = jarList.get(index);

            int frequency = jarReminder.getRemFrequency();
            Date reminderDate = jarReminder.getLastReminderDate();

            // Check if the last Reminder Date is past the current date
            openOrNot = date.compareTo(reminderDate);

            // Check for each reminder frequency of the jar
            if (openOrNot >= 0 && frequency == 2) // Monthly reminder
            {
                // Call the reminder email method
                JavaMailUtil.sendReminderNote(jarReminder.getJarName(), jarReminder.getJarID());
                
                // Update the next reminder date
                DatabaseAccess.updateReminderDate(jarReminder);
            }

            else if (openOrNot >= 0 && frequency == 1) // Weekly reminder
            {
                // Call the reminder email method
                JavaMailUtil.sendReminderNote(jarReminder.getJarName(), jarReminder.getJarID());
                
                // Update the next reminder date
                DatabaseAccess.updateReminderDate(jarReminder);
            }

            else if (openOrNot >= 0 && frequency == 0) // Daily reminder
            {
                // Call the reminder email method
                JavaMailUtil.sendReminderNote(jarReminder.getJarName(), jarReminder.getJarID());
                
                // Update the next reminder date
                DatabaseAccess.updateReminderDate(jarReminder);
            }
        }
     }
    
}
