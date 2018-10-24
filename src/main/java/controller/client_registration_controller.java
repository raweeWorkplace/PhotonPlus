/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.clientPojo;
import beans.journal_pojo;
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
    List<clientPojo>  cl_list;
    functionTools fnTools;

    public client_registration_controller() {
        fnTools = new functionTools();
        cnf = new Configuration();
        cnf.configure();
        sf = cnf.buildSessionFactory();
        s = sf.openSession();
    }
   
   
    public void register_client(clientPojo pojo, journal_pojo j_pojo) {
        s = sf.openSession();
        t = s.beginTransaction();
        if(isClient(pojo)){
          String  sql2 = "UPDATE clientPojo s SET s.address = '"+pojo.getAddress()+"', s.contact = '"+pojo.getContact()+"', s.client_name = '"+pojo.getClient_name()+"' Where s.company_name = '"+pojo.getCompany_name()+"'";
            if ( s.createQuery(sql2).executeUpdate() != 0) {
                 JOptionPane.showMessageDialog(null, " Detail Updated ");
             }
        
        }else{
            s.save(pojo);
            j_pojo.setClient_id(pojo);
            s.save(j_pojo);
            JOptionPane.showMessageDialog(null, "Detail Submitted");
            
        }
        t.commit();
        s.close();
        
             
             
 } 
    
    public boolean isClient(clientPojo pojo){
        Query query = s.createQuery("From clientPojo s where s.company_name = '"+pojo.getCompany_name()+"'");
        cl_list = query.getResultList();
            return !cl_list.isEmpty();
        }
         
    public void fill_client_detail_table(JTable table, String sql1) {
        
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        s = sf.openSession();
        Query query = s.createQuery(sql1);
        List<clientPojo> list = query.getResultList();
        fnTools.remove_table_data(table_model,table);
        int j =0;
        for(clientPojo pojo : list) {

        table_model.insertRow(j, new Object[]{pojo.getId(),pojo.getCompany_name(),pojo.getClient_name(),pojo.getContact(),pojo.getAddress()});
        j++;

    }
    s.close();
 }
    
}
