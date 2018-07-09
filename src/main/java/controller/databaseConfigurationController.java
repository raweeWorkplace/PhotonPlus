/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import beans.dataConfigurationPojo;


/**
 *
 * @author idiotbox
 */
public class databaseConfigurationController {
    dataConfigurationPojo pojo = new dataConfigurationPojo();
    private String productKey, name, email, contact, address,pan;
    Connection conInstance;
    Statement smtInstance;
    ResultSet resInstance;
    
    
    
    public databaseConfigurationController(){
        
    }
    
       
    public void init(dataConfigurationPojo pojo){
        this.productKey = pojo.getProductKey();
        this.pan = pojo.getPan();
        this.name = pojo.getName();
        this.contact = pojo.getContact();
        this.email = pojo.getEmail();
        this.address = pojo.getAddress();
        
        
    }
    
    public boolean validate(){
         if(("".equals(productKey))||(!"1111111111111111".equals(productKey)&&!"512A40FM84Q783FQ".equals(productKey)&&!"512A32NR72S542MP".equals(productKey))){
             JOptionPane.showMessageDialog(null, "Product Key Invalid","Error", JOptionPane.ERROR_MESSAGE);
             return false;
         }else if ("".equals(pan)){
             JOptionPane.showMessageDialog(null, "Please Enter PAN Details","Error", JOptionPane.ERROR_MESSAGE);
             return false;
         }else if ("".equals(name)){
             JOptionPane.showMessageDialog(null, "Please Enter Details","Error", JOptionPane.ERROR_MESSAGE);
             return false;
         }else if ("".equals(contact)){
             JOptionPane.showMessageDialog(null, "Please Enter Contact Details","Error", JOptionPane.ERROR_MESSAGE);
             return false;
         }else if ("".equals(email)){
             JOptionPane.showMessageDialog(null, "Email Address Invalid","Error", JOptionPane.ERROR_MESSAGE);
             return false;
         }else if ("".equals(address)){
             return false;
         }
        return true;
         
     }
    
    
    
    
    public void entry_data(){
        try {
            String sql = "INSERT INTO company_detail_table(company_name, pan_no, contact, email, address) VALUES('"+name+"','"+pan+"','"+contact+"','"+email+"','"+address+"')";
            
            conInstance = Dao.DataBase_Connection.getConnectionInstance();
            smtInstance = conInstance.createStatement();
            smtInstance.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(databaseConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   }
