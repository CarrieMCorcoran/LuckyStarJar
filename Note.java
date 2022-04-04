package luckystarjar;

import java.util.Date;

public class Note 
{
    int noteID;
    String noteText;
    java.sql.Date noteDate;
    int userJarID;
    String userName;

    public Note(int noteID, String noteText, java.sql.Date noteDate, int userJarID, String userName) {
        this.noteID = noteID;
        this.noteText = noteText;
        this.noteDate = noteDate;
        this.userJarID = userJarID;
        this.userName = userName;
    }

    public int getNoteID() {
        return noteID;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public int getUserJarID() {
        return userJarID;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Note{" + "noteID=" + noteID + ", noteText=" + noteText + ", noteDate=" + noteDate + ", userJarID=" + userJarID + ", userName=" + userName + '}';
    }
    

    
    
}
