package luckystarjar;

public class User 
{
    private int userID;
    private String name;
    private String userGoogleAuthInfo;
    private String userEmail;
    private int userJarID;
    private int noteColor;
    private boolean isJarOwner;

    public User(int userID, String name, String userGoogleAuthInfo, String userEmail) {
        this.userID = userID;
        this.name = name;
        this.userGoogleAuthInfo = userGoogleAuthInfo;
        this.userEmail = userEmail;
        userJarID = -1;
        noteColor = -1;
        isJarOwner = false;
    }

    
    
    
    public User(int userID, String name, String userGoogleAuthInfo, String userEmail, int userJarID, int noteColor, boolean isJarOwner) {
        this.userID = userID;
        this.name = name;
        this.userGoogleAuthInfo = userGoogleAuthInfo;
        this.userEmail = userEmail;
        this.userJarID = userJarID;
        this.noteColor = noteColor;
        this.isJarOwner = isJarOwner;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getUserGoogleAuthInfo() {
        return userGoogleAuthInfo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserJarID() {
        return userJarID;
    }

    public boolean isIsJarOwner() {
        return isJarOwner;
    }

    public int getNoteColor() {
        return noteColor;
    }

    public void setUserJarID(int userJarID) {
        this.userJarID = userJarID;
    }

    public void setNoteColor(int noteColor) {
        this.noteColor = noteColor;
    }

    public void setIsJarOwner(boolean isJarOwner) {
        this.isJarOwner = isJarOwner;
    }

    public void setUserGoogleAuthInfo(String userGoogleAuthInfo) {
        this.userGoogleAuthInfo = userGoogleAuthInfo;
    }

    
    
    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", name=" + name + ", userGoogleAuthInfo=" + userGoogleAuthInfo + ", userEmail=" + userEmail + ", userJarID=" + userJarID + ", noteColor=" + noteColor + ", isJarOwner=" + isJarOwner + '}';
    }


    
}
