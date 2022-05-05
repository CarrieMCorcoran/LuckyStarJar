package com.example.LuckyStarJar;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
@Path("controller")

public class Controller {

    @GET
    @Path("invite/{email}/{jarID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String inviteUser(@PathParam("email") String email, @PathParam("jarID")int jarID)
    {
        Jar j;
        User u;
        String inviterName;
        int inviteCode;
        String success = "no";
        
        try 
        {
            j = DatabaseAccess.getjar(jarID);
            success = j.getJarName();
            u = DatabaseAccess.getJarOwner(jarID);
            inviteCode = j.getInviteCode();
            inviterName = u.getName();
            success = "yes";
            JavaMailUtil.sendInviteEmail(email, inviterName, inviteCode);

            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
            success = e.getMessage();
        }
        return success;
    }
    
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
    @Path("/userjar/{userID}/{jarID}")
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
    
    @GET
    @Path("/notes/user/{userJarID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note [] getNotesForUser(@PathParam("userJarID")int userJarID)
    {
        Note [] notes = null;
        ArrayList<Note> noteList;
        try
        {
            noteList = DatabaseAccess.getOwnNotes(userJarID);
            notes = new Note[noteList.size()];
            notes = noteList.toArray(notes);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        return notes;
    }
    
    @GET
    @Path("/notes/jar/{jarID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note [] getNotesForJar(@PathParam("jarID")int jarID)
    {
        Note [] notes = null;
        ArrayList<Note> noteList;
        try
        {
            noteList = DatabaseAccess.getAllNotes(jarID);
            notes = new Note[noteList.size()];
            notes = noteList.toArray(notes);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        return notes;
    }
    
    @GET
    @Path("/code/{inviteCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkInviteCode(@PathParam("inviteCode") int inviteCode)
    {
        boolean valid = false;
        try
        {
            valid = DatabaseAccess.checkInviteCode(inviteCode);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        
        return valid;
    }
    
    @GET
    @Path("/notecount/{jarID}")
    @Produces(MediaType.APPLICATION_JSON)
    public int getNoteCount(@PathParam("jarID") int jarID)
    {
        int count = 0;
        
        try 
        {
            count = DatabaseAccess.getNoteCount(jarID);
        }
        catch (SQLException ex) 
        {
            System.out.println(ex);
        }
        
        
        return count;
    }
    
    @GET
    @Path("/users/jar/{jarID}")
    @Produces(MediaType.APPLICATION_JSON)
    public User [] getUsersForJar(@PathParam("jarID")int jarID)
    {
        User [] users = null;
        ArrayList<User> userList;
        try
        {
            userList = DatabaseAccess.getAllUsers(jarID);
            users = new User[userList.size()];
            users = userList.toArray(users);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return users;
    }
    
    @POST
    @Path("user/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(String json)
    {
        Jsonb jsonb = JsonbBuilder.create();
        User u = null;
        Person read = jsonb.fromJson(json, Person.class);
        String name = read.getName();
        String email = read.getEmail();
        
        try
        {
            u = DatabaseAccess.createUser(name, "none", email);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        return u;
    }
    
    @POST
    @Path("user/jar/add/{inviteCode}/{noteColor}/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public User addUserToJar(@PathParam("inviteCode")int inviteCode, @PathParam("noteColor")int noteColor, @PathParam("userID")int userID)
    {
        User u = null;
        
        try
        {
            u = DatabaseAccess.getUser(userID);
            u = DatabaseAccess.addUserToJar(u, inviteCode, noteColor);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        
        return u;
    }
    
    
    @POST
    @Path("note/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Note createNote(String json)
    {
        Note n = null;
        Jsonb jsonb = JsonbBuilder.create();
        NoteText read = jsonb.fromJson(json,NoteText.class);
        String text = read.getNote();
        String name = read.getName();
        int userJarID = read.getUserJarID();
        try
        {
            n = DatabaseAccess.createNote(userJarID, text, name);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        
        
        
        return n;
    }
    
    @POST
    @Path("jar/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Jar createJar(String json)
    {
        Jar j = null;
        User u;
        Jsonb jsonb = JsonbBuilder.create();
        JarData read = jsonb.fromJson(json, JarData.class);
        int userID = read.getUserID();
        int noteColor = read.getNoteColor();
        int remFreq = read.getRemFreq();
        Date open = read.getOpenDate();
        String name = read.getJarName();
        
        try
        {
            u = DatabaseAccess.getUser(userID);
            j = DatabaseAccess.createJar(u, noteColor, open, remFreq, name);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return j;
    }
    
    @DELETE
    @Path("note/delete/{noteID}")
    public void deleteNote(@PathParam("noteID") int noteID)
    {
        try
        {
            DatabaseAccess.removeNote(noteID);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    @DELETE
    @Path("userjar/delete/{userJarID}")
    public void removeUserFromJar(@PathParam("userJarID")int userJarID)
    {
        try
        {
            DatabaseAccess.removeUserFromJar(userJarID);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jar/delete/{userID}/{jarID}")
    public User deleteJar(@PathParam("userID") int userID,@PathParam ("jarID") int jarID)
    {
        User u = null;
        Jar j;
        try
        {
            u = DatabaseAccess.getUser(userID);
            j = DatabaseAccess.getjar(jarID);
            DatabaseAccess.deleteJar(u, j);
            
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return u;
    }
    
}
