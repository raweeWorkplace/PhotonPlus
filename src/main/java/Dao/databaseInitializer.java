/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author idiotbox
 */
public class databaseInitializer {
    protected static Connection connectionInstance;
    protected String dbUserName ="root";
    protected String dbPassWord = "";
    protected String dbUrl ="jdbc:mysql://localhost:3306/";
    protected String dbDriver ="com.mysql.jdbc.Driver";
    protected Statement smtInstance2;
    DataBase_Connection dao = new DataBase_Connection();
    
    
    
    
    public void createDataBase(){
        try {
            Class.forName(dbDriver);
            connectionInstance = DriverManager.getConnection(dbUrl,dbUserName,dbPassWord);
            String sql = "create database if not exists photon";
            smtInstance2 = connectionInstance.createStatement();
            int result = smtInstance2.executeUpdate(sql);
            createTable();
            
        } catch (ClassNotFoundException | SQLException ex) {
        }
        
    }
    
    public void createTable(){
        try {
            connectionInstance = dao.getConnection();
            connectionInstance.setAutoCommit(false);
            Statement pst = connectionInstance.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS bill_table (bill_no int(11) NOT NULL AUTO_INCREMENT,  date date NOT NULL,  cust_name varchar(50) DEFAULT 'Customer',  total double NOT NULL,  contact varchar(10) DEFAULT NULL,  disc double DEFAULT '0',  paid double DEFAULT '0',  due double DEFAULT '0',  status varchar(15) DEFAULT 'Pending',  old_due double DEFAULT '0', PRIMARY KEY (bill_no)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql2 = "CREATE TABLE IF NOT EXISTS client_detail_table (id int(11) NOT NULL AUTO_INCREMENT,  company_name varchar(50) NOT NULL,  client_name varchar(50) NOT NULL,  contact varchar(10) NOT NULL,  old_due varchar(15) DEFAULT '0.00',  address varchar(100) DEFAULT NULL, PRIMARY KEY (id), UNIQUE KEY company_name (company_name),  UNIQUE KEY contact (contact)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql3 = "CREATE TABLE IF NOT EXISTS client_rate_table (id int(11) NOT NULL AUTO_INCREMENT,  company_name varchar(50) NOT NULL,  size varchar(15) NOT NULL, rate double NOT NULL, PRIMARY KEY (id), UNIQUE KEY client_rate (company_name,size)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql4 = "CREATE TABLE IF NOT EXISTS instock_details (item_code int(11) NOT NULL AUTO_INCREMENT,  item_name varchar(50) NOT NULL,  instock int(11) DEFAULT '0',  sold int(11) DEFAULT '0', PRIMARY KEY (item_code)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql5 = "CREATE TABLE IF NOT EXISTS instock_entry_table (id int(11) NOT NULL AUTO_INCREMENT,  date date NOT NULL,  item_name varchar(50) NOT NULL,  total double NOT NULL,  item_code int(11) NOT NULL,  unit int(11) NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql6 = "CREATE TABLE IF NOT EXISTS Login_tbl (  UserId varchar(15) NOT NULL DEFAULT '',  UserName varchar(50) NOT NULL,  Password varchar(50) NOT NULL, masterPassword varchar(50) NOT NULL, PRIMARY KEY (UserId)) ENGINE=InnoDB DEFAULT CHARSET=latin1";
            String sql7 = "CREATE TABLE IF NOT EXISTS company_detail_table (id int(11) NOT NULL AUTO_INCREMENT,  company_name varchar(50) NOT NULL,  pan_no varchar(15) NOT NULL,  contact varchar(10) NOT NULL,  email varchar(25),  address varchar(100) DEFAULT NULL, PRIMARY KEY (id), UNIQUE KEY company_name (company_name),  UNIQUE KEY contact (contact)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql8 = "CREATE TABLE IF NOT EXISTS photo_size_detail_table (id int(11) NOT NULL AUTO_INCREMENT,  size varchar(15) NOT NULL,  rate double DEFAULT '0',  display double DEFAULT '0', PRIMARY KEY (id), UNIQUE KEY size (size)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql9 = "CREATE TABLE IF NOT EXISTS sales_table (id int(11) NOT NULL AUTO_INCREMENT,  bill_no int(11) NOT NULL,  item_code int(11) NOT NULL,  item_name varchar(25) NOT NULL,  qty int(11) NOT NULL,  cost double NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            String sql10 ="CREATE TABLE IF NOT EXISTS special_customer_table (id int(11) NOT NULL AUTO_INCREMENT,  cust_name varchar(30) NOT NULL,  contact varchar(10) NOT NULL, old_due double DEFAULT '0', PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
            
            
            pst.addBatch(sql1);
            pst.addBatch(sql2);
            pst.addBatch(sql3);
            pst.addBatch(sql4);
            pst.addBatch(sql5);
            pst.addBatch(sql6);
            pst.addBatch(sql7);
            pst.addBatch(sql8);
            pst.addBatch(sql9);
            pst.addBatch(sql10);
            pst.executeBatch();
            connectionInstance.commit();
        } catch (SQLException ex) {
            Logger.getLogger(databaseInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public boolean checkDBExists(String dbName){

    try{
        Class.forName(dbDriver);
        connectionInstance = DriverManager.getConnection(dbUrl,dbUserName,dbPassWord);
        
        ResultSet resultSet = connectionInstance.getMetaData().getCatalogs();
        
        while (resultSet.next()) {
            
          String databaseName = resultSet.getString(1);
            if(databaseName.equals(dbName)){
                return true;
            }
        }
        resultSet.close();

    }
    catch(Exception e){
        e.printStackTrace();
    }

    return false;
}
    
}


