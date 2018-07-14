/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.size_entry_pojo;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author idiotbox
 */
public class size_entry_controller {
    Configuration cnf ;
    SessionFactory sf;
    Session s;
    Transaction t;
    List<size_entry_pojo>  size_list;
    functionTools fnTools;
    
    
    public void save(size_entry_pojo pojo){
        
            s = sf.openSession();
            t = s.beginTransaction();
             if(isSize(pojo)){
                 String queryUpdate = "UPDATE size_entry_pojo SET rate='"+pojo.getRate()+"',display='"+pojo.getDisplay()+"' where size ='"+pojo.getSize()+"'";
                 Query query = s.createQuery(queryUpdate);
                 if (query.executeUpdate() != 0) {
                     JOptionPane.showMessageDialog(null, " Detail Updated ");
                 }
              }else {
                
                s.save(pojo);
                   JOptionPane.showMessageDialog(null,"Data Submitted");
                }
             t.commit();
             s.close();
            }
            
    
    public boolean isSize(size_entry_pojo pojo){
        Query query = s.createQuery("from size_entry_pojo where size = '"+pojo.getSize()+"'");
        size_list = query.getResultList();
        return !size_list.isEmpty();
    }

    public size_entry_controller() {
        fnTools = new functionTools();
        cnf = new Configuration();
        cnf.configure();
        sf = cnf.buildSessionFactory();
        s = sf.openSession();
    }
    
    public void fill_photo_size_table(JTable table){
        DefaultTableModel table_model  =(DefaultTableModel)table.getModel();
        String  sql1= "FROM size_entry_pojo";
        s=sf.openSession();
        List<size_entry_pojo> list = s.createQuery(sql1).getResultList();
        fnTools.remove_table_data(table_model,table);
        int j = 0;
        for(size_entry_pojo pojo_rs:list) {
                table_model.insertRow(j,new Object[]{pojo_rs.getSize_id(),pojo_rs.getSize(),pojo_rs.getRate(),pojo_rs.getDisplay()});
                j++;
            }
        }

    
    public void fill_table(JTable table, String sql){
        
        DefaultTableModel size_table_model  =(DefaultTableModel)table.getModel();
        s=sf.openSession();
        //String  sql1= "FROM size_entry_pojo where size Like '"+input+"%'";
        fnTools.remove_table_data(size_table_model,table);
        List<size_entry_pojo> list = s.createQuery(sql).getResultList();
        fnTools.remove_table_data(size_table_model,table);
        int j = 0;
        for(size_entry_pojo pojo_rs:list) {
                size_table_model.insertRow(j,new Object[]{pojo_rs.getSize()});
                j++;
            }
        }   
         
              

}
