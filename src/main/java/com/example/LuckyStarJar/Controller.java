package com.example.LuckyStarJar;

import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 */
@Path("controller")

public class Controller {

    @GET
    @Path("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByEmail(@PathParam("email") String email)
    {
        User u = null;
        
        try
        {
            u = DatabaseAccess.getUser(email);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        return u;
    }
    
    @GET
    @Path("/jars/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jar [] getJarsByUser(@PathParam("userID") int userID)
    {
        Jar [] jars = null;
        ArrayList<Jar> jarList;
        
        try
        {
            jarList = DatabaseAccess.getJarsForUser(userID);
            jars = new Jar[jarList.size()];
            jars = jarList.toArray(jars);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        
        return jars;
    }
    
    @GET
    @Path("/userjar/?user={userID}&jar={jarID}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserJarData (@PathParam("userID") int userID, @PathParam("jarID") int jarID)
    {
        User u = null;
        try
        {
            u = DatabaseAccess.getUserJarData(userID, jarID);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        return u;
    }
    
}