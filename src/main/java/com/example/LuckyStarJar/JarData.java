/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.LuckyStarJar;

import java.sql.Date;

/**
 *
 * @author Carrie
 */
public class JarData 
{
    public int userID;
    public int noteColor; 
    public Date openDate;
    public int remFreq;
    public String jarName;

    public JarData() {
    }

    
    
    public int getUserID() {
        return userID;
    }

    public int getNoteColor() {
        return noteColor;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public int getRemFreq() {
        return remFreq;
    }

    public String getJarName() {
        return jarName;
    }
    
    
}
