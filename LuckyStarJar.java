package luckystarjar;

import java.sql.*;
import java.util.ArrayList;

public class LuckyStarJar {

    
    
    public static void main(String[] args) 
    {
        ArrayList<Jar> jars = new ArrayList<>();
        User user;
        Jar jar;
        ArrayList<Note>notes = new ArrayList<>();
        
        try
        {
            //Pulling user data from db based on email address
            user = DatabaseAccess.getUser("ccorcor3@oswego.edu");
            
            //Getting a list of jars user is in and selecting one from the arrayList
            jars = DatabaseAccess.getJarsForUser(user.getUserID());
            jar = jars.get(0);
            DatabaseAccess.getUserJarData(user, jar.getJarID());
            
            //Get and print all notes user put in database
            notes = DatabaseAccess.getOwnNotes(user.getUserJarID());
            for(int count = 0; count < notes.size(); count++)
                System.out.println(notes.get(0));
            
        }
        catch (SQLException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    
}
