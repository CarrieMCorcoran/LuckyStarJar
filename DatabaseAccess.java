
package luckystarjar;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class DatabaseAccess 
{
    private static final String URL = "jdbc:mysql://localhost:3306/luckystarjar";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "LuckyStarJar";
    
    /*
    void removeUserFromJar(int userJarID, Jar j) removes a user with the given userJarID
    from the given Jar.  Also removes their notes and changes the inviteCode to a 
    new unique number.
    */
    public static void removeUserFromJar(int userJarID, Jar j) throws SQLException
    {
        Connection con;
        PreparedStatement st;
        Statement state;
        String removeUser = "DELETE FROM userjar WHERE userjarID = "+ userJarID + ";";
        String removeNotes = "DELETE FROM note WHERE userjarID = " + userJarID + ";";
        String changeInviteCode= "UPDATE jar SET inviteCode = ? WHERE jarID = ?;";
        String checkInviteCode; 
        ResultSet results;
        int inviteCode;
        int count = 1;
        boolean unique = false;
        
        Random rand = new Random();
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        
        //remove user
        st = con.prepareStatement(removeUser);
        st.execute();
        
        //remove all their notes
        st = con.prepareStatement(removeNotes);
        st.execute();
        
        //generate new invite code, check for uniqueness
        do
        {
            //Generate random invite code
            inviteCode = rand.nextInt(100000);
            
            //Check to make sure jar code is unique
            state = con.createStatement();
            checkInviteCode = "select COUNT(inviteCode) from jar WHERE inviteCode = " + inviteCode + ";";
            results = state.executeQuery(checkInviteCode);

            while(results.next())
            {
                count = results.getInt(1);
            }
            if(count == 0)
                unique = true;
        }while(!unique);
        
        //Update jar with new code
        st = con.prepareStatement(changeInviteCode);
        st.setInt(1, inviteCode);
        st.setInt(2, j.getJarID());
        
        //Close database connection
        st.close();
        state.close();
        con.close();
    }
    
    /*
    void deleteJar(User u, Jar j)- deletes the given Jar along with its userjar
    relations and notes.  Sets the jar object to null and removes jar data from
    User object.
    */
    public static void deleteJar(User u, Jar j) throws SQLException
    {
        String deleteNotes = "DELETE note FROM note "+
        "JOIN userjar ON note.userJarID = userjar.userJarID "+
        "JOIN jar ON userJar.jarID = jar.jarID "+
        "WHERE jar.jarID = " + j.getJarID() + ";";
        String deleteUserJar = "DELETE userjar FROM userjar "+
        "JOIN jar ON userJar.jarID = jar.jarID "+
        "WHERE jar.jarID = " + j.getJarID() + ";";
        String deleteJar = "DELETE FROM jar WHERE jarID = " + j.getJarID() + ";";
        Connection con;
        PreparedStatement st;
        
        //Connect to database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Delete jar's notes
        st = con.prepareStatement(deleteNotes);
        st.execute();
        
        //Delete userjar entries
        st = con.prepareStatement(deleteUserJar);
        st.execute();
        
        //Delete jar
        st = con.prepareStatement(deleteJar);
        st.execute();
        
        //Set jar object to null
        j = null;
        
        //Update user object;
        u.setIsJarOwner(false);
        u.setNoteColor(-1);
        u.setUserJarID(-1);
        
        //Close database connection
        st.close();
        con.close();
    }
    
    
    /*
    void removeNote(int noteID) Removes the note at the given noteID from the database
    */
    public static void removeNote(int noteID) throws SQLException
    {
        Connection con;
        PreparedStatement st;
        String sql = "DELETE FROM note WHERE noteID = " + noteID + ";";
        
        //Connect to database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to delete note
        st = con.prepareStatement(sql);
        st.execute();
        
        //Close database connection
        con.close();
        st.close();
        
    }
    /*
    ArrayList<User> getAllUsers (int jarID) returns an ArrayList of user objects for a
    given jarID.  If the jar has no users, the ArrayList will be empty, but this
    shouldn't happen.  All jars have 1 user at creation
    */
    public static ArrayList<User> getAllUsers(int jarID) throws SQLException
    {
        ArrayList <User> userList = new ArrayList<>();
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        int userID;
        String name;
        String userG;
        String userEmail;
        int userJarID;
        int noteColor;
        boolean isOwner;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to get users for a specific jar
        sql = "SELECT user.*,userJar.userJarID, userJar.isOwner, userJar.notecolor " +
        "FROM user JOIN userJar " +
        "ON user.userID = userjar.userID " +
        "WHERE userjar.jarID = " + jarID + ";";
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //Parse results into ArrayList of Users
        while(results.next())
        {
                userID = results.getInt("userID");
                name = results.getString("userName");
                userG = results.getString("userGoogleAuthInfo");
                userEmail = results.getString("userEmail");
                userJarID = results.getInt("userJarID");
                noteColor = results.getInt("notecolor");
                if(results.getInt("isOwner") == 1)
                {
                    isOwner = true;
                }
                else
                {
                    isOwner = false;
                }
                
                userList.add(new User(userID, name, userG, userEmail, userJarID, noteColor, isOwner));
        }
        
        //Close sql connection
        st.close();
        con.close();
        
        //Return list of users
        return userList;
    }
    /*
    String getGoogleAuthInfo(String email)- returns the googleAuthInfo for a given user's
    email address, or an empty String if the email isn't in the database
    */
    public static String getGoogleAuthInfo(String email) throws SQLException
    {
        String googleAuthInfo = "";
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
       
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to get users for a specific jar
        sql = "Select userGoogleAuthInfo FROM user WHERE userEmail = " + email +";";
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //Parse results into ArrayList of Users
        while(results.next())
        {
                googleAuthInfo = results.getString("userGoogleAuthInfo");
        }
        
        //Close sql connection
        st.close();
        con.close();
        
        //Return list of users
        return googleAuthInfo;
    }
    
    
    /*
    ArrayList<Note> getAllNotes (int jarID)- returns all notes for a given jarID
    in an ArrayList.  If the ArrayList is empty, the jar has no notes yet.
    */
    public static ArrayList<Note> getAllNotes(int jarID) throws SQLException
    {
        ArrayList <Note> noteList = new ArrayList<>();
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        int noteID;
        String noteText;
        Date noteDate;
        int userJarID;
        String name;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to get notes for a specific jar
        sql = "SELECT note.noteID, note.noteDate, note.noteText, userJar.userJarID, user.userName "+
        "FROM jar JOIN userjar ON jar.jarID = userjar.jarID "+
        "JOIN note ON userjar.userJarID = note.userJarID " +
        "JOIN user ON user.userID = userJar.userID "+
        "WHERE userjar.jarID = " +jarID + ";";
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //Parse results into ArrayList of notes
        while(results.next())
        {
            noteID = results.getInt("noteID");
            noteText = results.getString("noteText");
            noteDate = results.getDate("noteDate");
            userJarID = results.getInt("userJarID");
            name = results.getString("userName");
            

            noteList.add(new Note(noteID, noteText, noteDate, userJarID, name));
        }
        
        //Close sql connection
        st.close();
        con.close();
        
        //Return list of users
        return noteList;
    }
    
    
    /*
    ArrayList <Jar> getJarsForUser (int userID)- returns an ArrayList of all
    Jars that the given user is a member of.  If the list is empty, the user
    isn't part of any Jars yet.
    */
    public static ArrayList<Jar> getJarsForUser(int userID) throws SQLException
    {
        ArrayList <Jar> jarList = new ArrayList<>();
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        int jarID;
        Date openDate;
        int remFreq;
        int inviteCode;
        String jarName;
        Date lastReminderDate;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to get all jars for a specific user
        sql = "SELECT jar.* " +
        "FROM Jar " +
        "JOIN userjar ON userjar.jarID = jar.jarID " + 
        "JOIN user ON userjar.userID = user.userID " +
        "WHERE user.userID = " + userID + ";";
        
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //Parse results into array list
        while(results.next())
        {
            jarID = results.getInt("jarID");
            openDate = results.getDate("openDate");
            remFreq = results.getInt("remFreq");
            inviteCode = results.getInt("inviteCode");
            jarName = results.getString("jarName");
            lastReminderDate = results.getDate("lastReminderDate");
            
            jarList.add(new Jar(jarID, openDate, remFreq,inviteCode, jarName, lastReminderDate));
        }


        //Close sql connection
        st.close();
        con.close();        
        
        //Return arrayList
        return jarList;
    }
    /*
    ArrayList<Jar> getAllJars()- returns an ArrayList of all Jars in the database.
    If the list is empty, there are no Jars in the database.
    */
    public static ArrayList<Jar> getAllJars() throws SQLException
    {
        ArrayList <Jar> jarList = new ArrayList<>();
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        int jarID;
        Date openDate;
        int remFreq;
        int inviteCode;
        String jarName;
        Date lastReminderDate;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to get all jars for a specific user
        sql = "SELECT * FROM jar";
        
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //Parse results into array list
        while(results.next())
        {
            jarID = results.getInt("jarID");
            openDate = results.getDate("openDate");
            remFreq = results.getInt("remFreq");
            inviteCode = results.getInt("inviteCode");
            jarName = results.getString("jarName");
            lastReminderDate = results.getDate("lastReminderDate");
            
            jarList.add(new Jar(jarID, openDate, remFreq,inviteCode, jarName, lastReminderDate));
        }


        //Close sql connection
        st.close();
        con.close();        
        
        //Return arrayList
        return jarList;
    }
    
    /*
    getNoteCount (int jarID) - returns the number of notes in a given jar.
    */
    public static int getNoteCount (int jarID) throws SQLException
    {
        int count;
        ArrayList<Note> n = DatabaseAccess.getAllNotes(jarID);
        
        count = n.size();
        
        return count;
    }
    
    /*
    ArrayList<Note> getOwnNotes(int userJarID)- returns an ArrayList of all notes a given user
    has put in a given jar, as indicated by their userJarID.  If the list is empty,
    the user hasn't put any notes in the jar yet.
    */
    public static ArrayList<Note> getOwnNotes(int userJarID) throws SQLException
    {
        ArrayList <Note> noteList = new ArrayList<>();
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        int noteID;
        String noteText;
        Date noteDate;
        String name;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute query to get notes for a specific user
        sql = "SELECT note.noteID, note.noteDate, note.noteText, userJar.userJarID, user.userName " +
        "FROM jar JOIN userjar ON jar.jarID = userjar.jarID " +
        "JOIN note ON userjar.userJarID = note.userJarID " +
        "JOIN user ON user.userID = userJar.userID " +
        "WHERE userjar.userjarID = " + userJarID + ";";
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //Parse results into ArrayList of notes
        while(results.next())
        {
            noteID = results.getInt("noteID");
            noteText = results.getString("noteText");
            noteDate = results.getDate("noteDate");
            userJarID = results.getInt("userJarID");
            name = results.getString("userName");

            noteList.add(new Note(noteID, noteText, noteDate, userJarID, name));
        }
        
        //Close sql connection
        st.close();
        con.close();
        
        //Return list of users
        return noteList;
    }
    
    
    /*
    Note getRandomNote(int jarID)- returns a random note from the jar, or null if
    the jar has no notes in it yet.
    */
    public static Note getRandomNote(int jarID) throws SQLException
    {
        Note n = null;
        Random rand = new Random();
        int randomIndex;
        
        //Get list of all notes from jar
        ArrayList <Note> noteList = getAllNotes(jarID);
        
        
        //if list of notes is not empty
        if(!noteList.isEmpty())
        {
            //Get random number to pick an index of the note list
            randomIndex = rand.nextInt(noteList.size());

            //Get random note from note list
            n = noteList.get(randomIndex);
        }
        
        //Return random note
        return n;
    }
    /*
    User getUser(String email)- returns a User object that matches the given email
    address, or null if there's no user with that email.
    */
    public static User getUser (String email) throws SQLException
    {
        User u = null;
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        int userID;
        String userGoogleAuthInfo;
        String name;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute SQL query
        sql = "SELECT * FROM user WHERE userEmail = '" + email + "';";
        
        st = con.createStatement();
        results = st.executeQuery(sql);
        
        //parse results
        while(results.next())
        {
            userID = results.getInt("userID");
            name = results.getString("userName");
            userGoogleAuthInfo = results.getString("userGoogleAuthInfo");
            
            u = new User(userID, name, userGoogleAuthInfo, email);
        }
        
        //Close sql connection
        st.close();
        con.close();
        
        //Return user object
        return u;
    }
    /*
    User createUser(String name, String userGoogleAuthInfo, String email)- adds a new
    user to the database and returns a User object for the application to use
    */
    public static User createUser(String name, String userGoogleAuthInfo, String email) throws SQLException
    {
        Connection con;
        PreparedStatement st;
        Statement state; 
        String getID= "SELECT userID FROM user "+
        "WHERE userID = (SELECT MAX(userID) FROM user);";
        String insert = "INSERT INTO user VALUES(DEFAULT, ?, ?, ?);";
        User u;
        ResultSet results;
        int userID = 0;
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Add user to database
        st = con.prepareStatement(insert);
        st.setString(1, name);
        st.setString(2, userGoogleAuthInfo);
        st.setString(3, email);
        st.execute();
        
        //Get new userID from database
        state = con.createStatement();
        results = st.executeQuery(getID);
        
        while(results.next())
        {
            userID = results.getInt("userID");
        }
        
        //Create user object
        u = new User(userID, name, userGoogleAuthInfo, email);
        
        //Close database connection
        st.close();
        state.close();
        con.close();
        
        //Return new user
        return u;
        
    }
    /*
    Note createNote(int userJarID, String noteText, String userName)- adds a 
    note with the given information to the database and returns a Note object
    */
    public static Note createNote(int userJarID, String noteText, String userName) throws SQLException
    {
        Calendar calendar = Calendar.getInstance();
        Connection con;
        PreparedStatement st;
        Statement state; 
        String getID= "SELECT noteID FROM note "+
        "WHERE noteID = (SELECT MAX(noteID) FROM note);";
        String insert = "INSERT INTO note VALUES(DEFAULT, ?, ?, ?);";
        Note n;
        ResultSet results;
        int noteID = 0;
        Date today = new Date(calendar.getTime().getTime());
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Add user to database
        st = con.prepareStatement(insert);
        st.setString(1, noteText);
        st.setDate(2, today);
        st.setInt(3, userJarID);
        st.execute();
        
        //Get new noteID from database
        state = con.createStatement();
        results = st.executeQuery(getID);
        
        while(results.next())
        {
            noteID = results.getInt("noteID");
        }
        
        //Create note object
        n = new Note(noteID, noteText, new java.sql.Date(calendar.getTime().getTime()), userJarID, userName);
        
        //Close database connection
        st.close();
        state.close();
        con.close();
        
        //Return new user
        return n;
    }
    /*
    void getUserJarData(User u, int jarID)- updates a given User object with data
    specific to the chosen Jar (userJarID, noteColor, and isOwner)
    */
    public static void getUserJarData(User u, int jarID) throws SQLException
    {
        Connection con;
        Statement st;
        ResultSet results;
        String sql;
        
         //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        st =con.createStatement();
        
        //Run sql query
        sql = "SELECT * FROM userjar WHERE userID = " + u.getUserID() +
        " AND jarID = "+ jarID + ";";
        
        results = st.executeQuery(sql);
        
        while(results.next())
        {
            if(results.getInt("isOwner") == 1)
                u.setIsJarOwner(true);
            else
                u.setIsJarOwner(false);
            u.setUserJarID(results.getInt("userJarID"));
            u.setNoteColor(results.getInt("notecolor"));
        }
        
        //Close sql connection
        st.close();
        con.close();
    }
    /*
    boolean updateGoogleAuthInfo(User u, String auth)- updates the database and the given
    User object with the given Google Authentification information.  Returns true
    if information was updated, otherwise false.
    */
    public static boolean updateGoogleAuthInfo(User u, String auth) throws SQLException
    {
        Connection con;
        String sql;
        boolean updated = false;
        PreparedStatement st;
        int rows;
        
        //Update user object
        u.setUserGoogleAuthInfo(auth);
        
        //Connect to Database
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Execute sql statement
        sql = "UPDATE user SET userGoogleAuthInfo = ? WHERE userID = ?;";
        st =con.prepareStatement(sql);
        st.setString(1, auth);
        st.setInt(2, u.getUserID());
        
        rows = st.executeUpdate();
        
        if(rows == 1)
            updated = true;
        
        //Close connection
        con.close();
        st.close();
        
        return updated;
    }
    
    
    /*
    Jar createJar(User u, int noteColor, Date openDate, int remFreq, String jarName)-
    creates a new Jar in the database with the given information, creates and
    sets a unique invite code, sets date of last reminder to date of creation,
    sets the given user as the jar owner and updates 
    the User object with the information specific to the new Jar (userJarID, note
    color, and isOwner).  Returns the Jar object of the newly created Jar.
    */
    public static Jar createJar(User u, int noteColor, Date openDate, int remFreq, String jarName) throws SQLException
    {
        Connection con;
        String createJar ="INSERT into jar VALUES(DEFAULT, ?, ?, ? ,? ,?);";
        String getJarInfo = "SELECT jarID from jar WHERE jarID = (SELECT MAX(jarID) FROM jar);";
        String addUserToJar = "INSERT INTO userjar VALUES (DEFAULT, ?, ?, ?, ?);";
        String getUserJarData = "SELECT userjarID from userjar " + 
                "WHERE userjarID = (SELECT MAX(userjarID) FROM userjar);";
        String checkJarCode;
        PreparedStatement st;
        Statement state;
        ResultSet results;
        Jar j = null;
        int inviteCode;
        int jarID = 0;
        int userJarID = 0;
        int count = 1;
        boolean unique = true;
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTime().getTime());
        
        Random rand = new Random();
        
        

        
        //Create connection
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        

        do
        {
            //Generate random invite code
            inviteCode = rand.nextInt(100000);
            
            //Check to make sure jar code is unique
            state = con.createStatement();
            checkJarCode = "select COUNT(inviteCode) from jar WHERE inviteCode = " + inviteCode + ";";
            results = state.executeQuery(checkJarCode);

            while(results.next())
            {
                count = results.getInt(1);
            }
            if(count == 0)
                unique = true;
        }while(!unique);
        
        //Execute query to create jar
        st = con.prepareStatement(createJar);
        st.setDate(1, openDate);
        st.setInt(2, remFreq);
        st.setInt(3, inviteCode);
        st.setString(4, jarName);
        st.setDate(5, today);
        st.execute();
        
        
        //Get full jar data from database and create Jar object
        state = con.createStatement();
        results = state.executeQuery(getJarInfo);
        
        while(results.next())
        {
            jarID = results.getInt("jarID");
        }
        
        j = new Jar(jarID, openDate, remFreq, inviteCode, jarName,today);
        
        //Set user as jar owner in database
        st = con.prepareStatement(addUserToJar);
        st.setInt(1, jarID);
        st.setInt(2, noteColor);
        st.setInt(3, u.getUserID());
        st.setInt(4, 1);
        st.execute();
        
        //Get new userjarID
        state = con.createStatement();
        results = state.executeQuery(getUserJarData);
        
        while(results.next())
        {
            userJarID = results.getInt("userjarID");
        }

        //Update User object
        u.setIsJarOwner(true);
        u.setNoteColor(noteColor);
        u.setUserJarID(userJarID);
        
        //Close connection
        st.close();
        state.close();
        con.close();
        
        
        
        return j;
    }
    
    /*
    boolean checkInviteCode(int inviteCode)- returns true if the given inviteCode is
    in the database, else false
    */
    public static boolean checkInviteCode(int inviteCode) throws SQLException
    {
        Connection con;
        Statement state;
        String check;
        ResultSet results;
        int count = 0;
        boolean approved = false;
        
        //Create connection
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Check to make sure jar code matches a jar
        state = con.createStatement();
        check = "select COUNT(inviteCode) from jar WHERE inviteCode = " + inviteCode + ";";
        results = state.executeQuery(check);

        while(results.next())
        {
            count = results.getInt(1);
        }
        if(count == 1)
            approved = true;
        
        //Close connection
        con.close();
        state.close();
        
        return approved;
    }
    
    /*
    User addUserToJar(User u, int inviteCode, int noteColor)- adds the given user to a
    jar in the database specified by the invite code.  Updates and returns the 
    given User object with the data specific to the given jar (isOwner, userJarID,
    and given noteColor)
    */
    public static User addUserToJar(User u, int inviteCode, int noteColor) throws SQLException
    {
        
        Connection con;
        PreparedStatement st;
        Statement state;
        ResultSet results;
        int userJarID = 0;
        String addUserJar ="INSERT INTO userjar " + 
        "VALUES(Default, (select jarID from jar where inviteCode = ?)," +
                "?, ?, 0);";
        String getUserJarData = "SELECT userjarID FROM userjar " +
        "WHERE userjarID = (SELECT MAX(userjarID) FROM userjar);";
        
        //Create connection
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Run SQL to add user to jar (create new entry in userjar table)
        st = con.prepareStatement(addUserJar);
        st.setInt(1, inviteCode);
        st.setInt(2, noteColor);
        st.setInt(3, u.getUserID());
        st.execute();
        
        //Get userjarID from database
        state = con.createStatement();
        results = state.executeQuery(getUserJarData);
        
        while(results.next())
        {
            userJarID = results.getInt("userjarID");
        }
        
        //Update user object
        u.setIsJarOwner(false);
        u.setNoteColor(noteColor);
        u.setUserJarID(userJarID);
        
        
        //Close connection
        st.close();
        state.close();
        con.close();
        
        //Return user object
        return u;
    }
    /*
    Jar updateReminderDate(Jar j)- updates the given Jar in the database
    and the Jar object by setting the reminderDate to the current date.
    */
    public static Jar updateReminderDate(Jar j) throws SQLException
    {
        Connection con;
        String sql;
        Calendar calendar = Calendar.getInstance();
        PreparedStatement st;
        int rows;
        Date today = new Date(calendar.getTime().getTime());
        
        //Create connection
        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        
        //Call update statement
        sql = "UPDATE jar " +
        "SET lastReminderDate = ?" +
        " WHERE jarID = " + j.getJarID() + ";";
        
        st = con.prepareStatement(sql);
        st.setDate(1, today);
        rows = st.executeUpdate();
        
        //Update jar object
        j.setLastReminderDate(today);
        
        //Close connection
        st.close();
        con.close();
        
        
        return j;
    }

}
