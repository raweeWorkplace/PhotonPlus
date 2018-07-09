/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Dao.DataBase_Connection;
import Panels.homeFrame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import beans.log_in_pojo;

/**
 *
 * @author idiotbox
 */
public class login_controller {
    Connection conInstance;
    Statement smtInstance;
    DataBase_Connection dao;
    homeFrame hf;
    private String UserName, UserId, Password, masterPassword;
    
    

    public login_controller() {
        dao = new DataBase_Connection();
        hf = new homeFrame();
        
    }
    
    public boolean is_user_present(log_in_pojo pojo){
        String str = pojo.getUserId();
        try {
            String sql = "select * from Login_tbl where UserId = '"+str+"'";
            conInstance = dao.getConnectionInstance();
            smtInstance =  conInstance.createStatement();
            ResultSet rs = smtInstance.executeQuery(sql);
            if(rs.next()){
                this.Password=rs.getString("Password");
                this.UserId=rs.getString("UserId");
                this.UserName=rs.getString("UserName");
                this.masterPassword=rs.getString("masterPassword");
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(login_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public void register_user(log_in_pojo pojo){
        
        try {
            
            if((pojo.getPassword().equals(pojo.getCnfPassword())) && !"".equals(pojo.getPassword())){
                String Userpassword = Encryption.SHA1(pojo.getPassword());
                String Masterpassword = Encryption.SHA1(pojo.getMasterPassword());
                String insertUser = "insert ignore into Login_tbl values ('"+pojo.getUserId()+"','"+pojo.getUserName()+"','"+Userpassword+"','"+Masterpassword+"')";
                conInstance = dao.getConnection();
                smtInstance = conInstance.createStatement();
                int r = smtInstance.executeUpdate(insertUser);
                if(r!=0){
                    JOptionPane.showMessageDialog(null,"User Registered");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(login_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public boolean forgot(log_in_pojo pojo){
        if(is_user_present(pojo)){
            
            if (UserId.equals(pojo.getUserId()) && masterPassword.equals(Encryption.SHA1(pojo.getMasterPassword()))&&(pojo.getPassword().equals(pojo.getCnfPassword()))) {

                try {
                    String sqlUpdate = "update Login_tbl set Password = '"+Encryption.SHA1(pojo.getPassword())+"'where UserId = '" + pojo.getUserId() + "'";
                    smtInstance = conInstance.createStatement();
                    smtInstance.executeUpdate(sqlUpdate);
                    JOptionPane.showMessageDialog(null,"Password Updated");
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(login_controller.class.getName()).log(Level.SEVERE, null, ex);
                }

                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect login or master password",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
        }

        return false;
    }
    
    
    public boolean login(log_in_pojo pojo){
        conInstance = dao.getConnection();

        if(is_user_present(pojo)){
            if (UserId.equals(pojo.getUserId()) && Password.equals(Encryption.SHA1(pojo.getPassword())) && !"".equals(pojo.getPassword())) {
                   hf.setGlobalVariableCashier(UserName);
                    
                    if("Admin".equals(UserName)){
                        hf.labelMaster.setEnabled(true);
                        hf.labelReport.setEnabled(true);
                    }
                    hf.setVisible(true);
                return true;
            }
        }
        return false;
    }
    
}
