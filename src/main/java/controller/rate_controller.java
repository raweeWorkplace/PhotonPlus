/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.client_table_pojo;
import beans.rate_table_pojo;
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
public class rate_controller {
    Session s;
    SessionFactory sf;
    Transaction t;
    Configuration conf;
    functionTools fnTools;
    List<client_table_pojo>  r_list;

    public rate_controller() {
        conf = new Configuration();
        fnTools = new functionTools();
        conf.configure();
        sf = conf.buildSessionFactory();
    }
    
    public boolean isDetail(rate_table_pojo pojo){
        Query query = s.createQuery("from rate_table_pojo where client_id ='"+pojo.getClient_id().getId()+"' and size_id = "+pojo.getSize_id().getSize_id()+"");
        r_list = query.getResultList();
        return !r_list.isEmpty();
    }
    
    public void register_detail(rate_table_pojo pojo){
        s= sf.openSession();
        t=s.beginTransaction();
        if(isDetail(pojo)){
            String sql = "update rate_table_pojo set rate="+pojo.getRate()+" where client_id ='"+pojo.getClient_id().getId()+"' and size_id = "+pojo.getSize_id().getSize_id()+"";
            if(s.createQuery(sql).executeUpdate()!=0){
                JOptionPane.showMessageDialog(null, " Detail Updated ");
            }
            
        }else{
                s.save(pojo);
                JOptionPane.showMessageDialog(null, "Detail Submitted");
            }
        t.commit();
        s.close();
    }
    
    public size_entry_pojo get_size(String sql){
        s = sf.openSession();
        Query query = s.createQuery(sql);
         size_entry_pojo pojo = (size_entry_pojo)query.getSingleResult();
         return pojo;
    }
    
    
    public client_table_pojo get_client(String sql){
        s = sf.openSession();
        Query query = s.createQuery(sql);
         client_table_pojo pojo = (client_table_pojo)query.getSingleResult();
         return pojo;
    }
    
    public void fill_company_name(JTable table, String str){
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        s= sf.openSession();
        Query query = s.createQuery("from client_table_pojo where company_name like '"+str+"%'");
        List<client_table_pojo> list = query.getResultList();
        fnTools.remove_table_data(table_model, table);
        int j=0;
        for(client_table_pojo rs_pojo :list){
            table_model.insertRow(j, new Object[]{rs_pojo.getCompany_name()});
            j++;
        }
        s.close();
    }
    
    public void fill_size_name(JTable table, String sql){
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        s= sf.openSession();
        Query query = s.createQuery(sql);
        List<size_entry_pojo> list = query.getResultList();
        fnTools.remove_table_data(table_model, table);
        int j=0;
        for(size_entry_pojo rs_pojo :list){
            table_model.insertRow(j, new Object[]{rs_pojo.getSize()});
            j++;
        }
        s.close();
        
    }
    
    public void fill_detils(JTable table){
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        s = sf.openSession();
        Query query = s.createQuery("from rate_table_pojo");
        List <rate_table_pojo> list = query.getResultList();
        int j = 0;
        fnTools.remove_table_data(table_model, table);
        for(rate_table_pojo rs_pojo : list){
            table_model.insertRow(j, new Object[]{ rs_pojo.getId(),rs_pojo.getClient_id().getCompany_name(),rs_pojo.getSize_id().getSize(),rs_pojo.getRate()});
            j++;
        }
        s.close();
    }
}
