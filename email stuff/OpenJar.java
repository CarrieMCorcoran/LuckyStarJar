package luckystaremailapi.javamail;

import java.sql.SQLException;

public class OpenJar {
    // OpenJar opens the jar of a specific jar owner whcih emails all of the notes
    // contained within the jar
    
    public static void OpenJarTimer(int jarID) throws SQLException, Exception
    {
        // Pull the user information to use for email
        User userEmail = DatabaseAccess.getJarOwner(jarID);
        
        // Call the openJar Email
        JavaMailUtil.sendOpenMail(userEmail.getUserEmail(), jarID);
        
        // Call openJar to close the jar
        DatabaseAccess.openJar(jarID);
    }
}
