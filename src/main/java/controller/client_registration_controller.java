/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Dao.hibernateConfiguration;
import beans.client_table_pojo;
import beans.journal_pojo;
import beans.spcl_customer_pojo;
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
 * @author idiotbox/ranjan
 */
public class client_registration_controller {
    Configuration cnf ;
    SessionFactory sf;
    Session s;
    Transaction t;
    hibernateConfiguration con;
    List<spcl_customer_pojo>  spcl_list;
    List<client_table_pojo>  cl_list;
    functionTools fnTools;

    public client_registration_controller() {
        con = new hibernateConfiguration();
        fnTools = new functionTools();
        cnf = new Configuration();
        cnf.configure();
        sf = cnf.buildSessionFactory();
        s = sf.openSession();
    }
   
    public void register_customer(spcl_customer_pojo pojo, journal_pojo j_pojo) {
        s = sf.openSession();
        t = s.beginTransaction();
        if(isCustomer(pojo)){
         String sql2 = "UPDATE spcl_customer_pojo s SET s.address = '"+pojo.getAddress()+"' Where s.contact = '"+pojo.getContact()+"'";
            if ( s.createQuery(sql2).executeUpdate() != 0) {
                 JOptionPane.showMessageDialog(null, " Detail Updated ");
             }
        
        }else{
            s.save(pojo);
            if(isCustomer(pojo)){
                for(spcl_customer_pojo rs_pojo:spcl_list){
                j_pojo.setClient_id(rs_pojo.getId());
            }
                s.save(j_pojo);
                JOptionPane.showMessageDialog(null, "Detail Submitted");
            }
        }
        t.commit();
        s.close();
        
             
             
 } 
    
    public boolean isCustomer(spcl_customer_pojo pojo){
        Query query = s.createQuery("From spcl_customer_pojo s where s.contact = '"+pojo.getContact()+"'");
        spcl_list = query.getResultList();
            return !spcl_list.isEmpty();
        }
    
    public void register_client(client_table_pojo pojo, journal_pojo j_pojo) {
        s = sf.openSession();
        t = s.beginTransaction();
        if(isClient(pojo)){
          String  sql2 = "UPDATE client_table_pojo s SET s.address = '"+pojo.getAddress()+"', s.contact = '"+pojo.getContact()+"', s.client_name = '"+pojo.getClient_name()+"' Where s.company_name = '"+pojo.getCompany_name()+"'";
            if ( s.createQuery(sql2).executeUpdate() != 0) {
                 JOptionPane.showMessageDialog(null, " Detail Updated ");
             }
        
        }else{
            s.save(pojo);
            if(isClient(pojo)){
                for(client_table_pojo rs_pojo:cl_list){
                j_pojo.setClient_id(rs_pojo.getId());
            }
                s.save(j_pojo);
                JOptionPane.showMessageDialog(null, "Detail Submitted");
            }
        }
        t.commit();
        s.close();
        
             
             
 } 
    
    public boolean isClient(client_table_pojo pojo){
        Query query = s.createQuery("From client_table_pojo s where s.company_name = '"+pojo.getCompany_name()+"'");
        cl_list = query.getResultList();
            return !cl_list.isEmpty();
        }
    
    public void fill_customer_detail_table(JTable table, String name) {
        
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        String sql1 = "FROM spcl_customer_pojo s where s.cust_name Like'" + name + "%'";
        s = sf.openSession();
        Query query = s.createQuery(sql1);
        List<spcl_customer_pojo> list = query.getResultList();
        fnTools.remove_table_data(table_model,table);
        int j =0;
        for(spcl_customer_pojo pojo : list) {

        table_model.insertRow(j, new Object[]{pojo.getId(),pojo.getCust_name(),"NA",pojo.getContact(),pojo.getAddress()});
        j++;

    }
    s.close();
 }
    
    public void fill_client_detail_table(JTable table, String name) {
        
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        String sql1 = "FROM client_table_pojo s where s.company_name Like'" + name + "%'";
        s = sf.openSession();
        Query query = s.createQuery(sql1);
        List<client_table_pojo> list = query.getResultList();
        fnTools.remove_table_data(table_model,table);
        int j =0;
        for(client_table_pojo pojo : list) {

        table_model.insertRow(j, new Object[]{pojo.getId(),pojo.getCompany_name(),pojo.getClient_name(),pojo.getContact(),pojo.getAddress()});
        j++;

    }
    s.close();
 }
    
}
