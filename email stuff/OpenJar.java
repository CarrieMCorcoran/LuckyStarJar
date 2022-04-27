package luckystaremailapi.javamail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OpenJar {
    // OpenJar opens the jar of a specific jar owner whcih emails all of the notes
    // contained within the jar
    
     public static void OpenJarTimer() throws Exception
    {
        // Create the format of the date and time
        Calendar calendar = Calendar.getInstance();

        // Create a new instance of the date
        Date date = new Date(calendar.getTime().getTime());
        
        // Pull all the jars from the database
        ArrayList<Jar> jarList = DatabaseAccess.getAllJars();
        
        // For loop to loop through all the jars
        for (int index = 0; index < jarList.size(); index++)
        {
            // Index the jars
            Jar jarOpen = jarList.get(index);
            
            // Compare the open date to the current date
            int openOrNot = date.compareTo(jarOpen.getOpenDate());
            
            // if statement to determine if the jar needs to be opened or not
            // OpenOrNot being greater than or equal to zero means the current
            // date is past or equal to the open date
            if (openOrNot >= 0)
            {
                // Pull the user information to use for email
                User userEmail = DatabaseAccess.getJarOwner(jarOpen.getJarID());
        
                // Call the openJar Email
                JavaMailUtil.sendOpenMail(userEmail.getUserEmail(), jarOpen.getJarID());
        
            }
        }
    }
}
