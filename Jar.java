

import java.sql.Date;


public class Jar {


    
    private int jarID;
    private Date openDate;
    private int remFrequency;
    private int inviteCode;
    private String jarName;
    private Date lastReminderDate;
    
    public Jar(int jarID, Date openDate, int remFrequency, int inviteCode, String jarName, Date lastReminderDate) 
    {
        
        this.jarID = jarID;
        this.openDate = openDate;
        this.remFrequency = remFrequency;
        this.inviteCode = inviteCode;
        this.jarName = jarName;
        this.lastReminderDate = lastReminderDate;
    }

    public int getJarID() {
        return jarID;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public int getRemFrequency() {
        return remFrequency;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public String getJarName() {
        return jarName;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Date getLastReminderDate() {
        return lastReminderDate;
    }

    public void setLastReminderDate(Date lastReminderDate) {
        this.lastReminderDate = lastReminderDate;
    }

    @Override
    public String toString() {
        return "Jar{" + "jarID=" + jarID + ", openDate=" + openDate + ", remFrequency=" + remFrequency + ", inviteCode=" + inviteCode + ", jarName=" + jarName + ", lastReminderDate=" + lastReminderDate + '}';
    }

    
    

    
    
    
}
