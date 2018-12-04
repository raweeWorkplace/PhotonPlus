/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.clientPojo;
import beans.size_entry_pojo;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author idiotbox
 */
public class functionTools {
    
    Session s;
    SessionFactory sf;
    Transaction t;
    Configuration conf;
    List<clientPojo>  r_list;
    InputStream url7;
    

    public functionTools() {
        conf = new Configuration();
        conf.configure();
        sf = conf.buildSessionFactory();
    }
    
    
    public String getData(String sql){
        s = sf.openSession();
        String pojo=null;
        Query query = s.createQuery(sql);
        try{
          pojo= query.getSingleResult().toString();
              return pojo;
        }catch(Exception ex){         
        }
         if((pojo == null || pojo.equals("")) || pojo.length()<=0)return "0.0";else {
             return pojo;
         }
    }
    
        public List getAllData(String sql){
        s = sf.openSession();
        List pojo=null;
        Query query = s.createQuery(sql);
        try{
          pojo= query.getResultList();
              return pojo;
        }catch(Exception ex){         
        }
         if((!pojo.isEmpty())){
             return pojo;
         }else{
             return null;
         }
    }
    
    
    
     public void remove_table_data(DefaultTableModel tableModel, JTable table) {
          if(!isEmpty(table)){

        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        }
    }
     
     public static boolean isEmpty(JTable table) {
        if (table != null && table.getModel() != null) {
            return table.getModel().getRowCount()<=0;
        }
        return false;
    }
     
     public void close(JPanel panel) {
        WindowEvent winClosingEvent = new WindowEvent(SwingUtilities.getWindowAncestor(panel), WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
     
     public static void enter(java.awt.event.MouseEvent evt){
         JPanel parent = (JPanel)evt.getSource();
        parent.setBackground(Color.decode("#f0be8b"));
        parent.revalidate();
     }
     
     public static void exit(java.awt.event.MouseEvent evt){
         JPanel parent = (JPanel)evt.getSource();
        parent.setBackground(Color.DARK_GRAY);
        parent.revalidate();
     }
    
    
}
