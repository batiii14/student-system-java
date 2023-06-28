/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

import java.sql.DriverManager;

/**
 *
 * @author BATUN
 */
public class Connectionz {
        static java.sql.Connection con;
     
     public static java.sql.Connection getConnection(){
         try {
             Class.forName("com.mysql.jdbc.Driver");
             con=DriverManager.getConnection("jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=CONVERT_TO_NULL [dbuser on Default schema]");
         } catch (Exception e) {
             System.out.println(""+e.getMessage());
         }
         return con;
     }
    
}
