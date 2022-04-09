package com.example.LuckyStarJar;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 */
@Path("/test")
@Singleton
public class StartPoint {

    @GET
    public String thisIsKindOfLikeMain() 
    {
        ArrayList<Jar> jars = new ArrayList<>();
        User user;
        Jar jar;
        ArrayList<Note>notes = new ArrayList<>();
        String x = "";
        try
        {
            
            //Pulling user data from db based on email address
            user = DatabaseAccess.getUser("ccorcor3@oswego.edu");
            
            //Getting a list of jars user is in and selecting one from the arrayList
            jars = DatabaseAccess.getJarsForUser(user.getUserID());
            jar = jars.get(0);
            DatabaseAccess.getUserJarData(user, jar.getJarID());
            
            //Get and print all notes user put in database
            notes = DatabaseAccess.getAllNotes(jar.getJarID());
            x = notes.get(0) + "\n";
            for(int count = 1; count < notes.size(); count++)
                x+= notes.get(count) + "\n";
            
            
        }
        catch (SQLException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        return x;
    }
}
