/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Kostya
 */
public class SqlDatabase {
    public void createDB()
    {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void createTable()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE AVLData " +
                    "(Datetime TEXT NOT NULL, " +
                    " CarID TEXT NOT NULL, " +
                    " Lat REAL, " +
                    " Long REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
    
    public List getList(){
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "select * from tbl_list";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                HashMap<String, Object> map = new HashMap<String,Object>();
                map.put("id", rs.getInt("id"));
                map.put("title", rs.getString("title"));
                map.put("description", rs.getString("description"));
                map.put("deadline", rs.getInt("deadline"));
                map.put("high_per", rs.getInt("high_per"));
                map.put("a_time", rs.getString("a_time"));
                
//                System.out.println(rs.getInt("id") +  "\t" + 
//                                   rs.getString("title") + "\t" +
//                                   rs.getString("description"));
                list.add(map);
            }
        }catch(Exception e){
            
        }
        
        return list;
    }
    
    public List getSearchList(String first_date, String last_date, int performance){
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            
            String sql = "select * from tbl_list";
            String where = " where 1=1 ";
            if(!first_date.equals("")){
                where += " and a_time >= '" + first_date + "'";
            }
            if(!last_date.equals("")){
                where += " and a_time <= '" + last_date + "'";
            }
            if(performance > 0){
                where += " and high_per == 1 " ;
            }
            sql = sql + where;
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                HashMap<String, Object> map = new HashMap<String,Object>();
                map.put("id", rs.getInt("id"));
                map.put("title", rs.getString("title"));
                map.put("description", rs.getString("description"));
                map.put("deadline", rs.getInt("deadline"));
                map.put("high_per", rs.getInt("high_per"));
                map.put("a_time", rs.getString("a_time"));
                
//                System.out.println(rs.getInt("id") +  "\t" + 
//                                   rs.getString("title") + "\t" +
//                                   rs.getString("description"));
                list.add(map);
            }
        }catch(Exception e){
            
        }
        
        return list;
    }
    
    public int insert(String title, String description, int deadline, int performence, String date){
        int result = 0;
        Connection c = null;
        String sql = "INSERT INTO tbl_list(title,description, deadline, high_per, a_time) VALUES(?,?,?,?,?)";
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, deadline);
            pstmt.setInt(4, performence);
            pstmt.setString(5, date);
            pstmt.executeUpdate();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "You have alread maked your to-do List");
        }
        return result;
    }
}
