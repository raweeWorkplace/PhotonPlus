/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.instock_entry_pojo;
import java.util.List;
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
public class expences_controller {
    Configuration conf;
    SessionFactory sf;
    Session s;
    Transaction t;
    functionTools fnTools;
    
    public expences_controller() {
        fnTools = new functionTools();
        conf = new Configuration();
        conf.configure();
        sf = conf.buildSessionFactory();}
    
    public void enter_expences(instock_entry_pojo pojo){
        s= sf.openSession();
        s.saveOrUpdate(pojo);
        t=s.beginTransaction();
        t.commit();
        JOptionPane.showMessageDialog(null, "Expences Entered");
        s.close();
    }
    public void fill_table(JTable table, String sql){
        s = sf.openSession();
        DefaultTableModel table_model = (DefaultTableModel)table.getModel();
        List<instock_entry_pojo> list =s.createQuery(sql).getResultList();
        fnTools.remove_table_data(table_model, table);
        int j=0;
        for(instock_entry_pojo rs_pojo : list){
            table_model.insertRow(j, new Object[]{rs_pojo.getDate(),rs_pojo.getCategory(),rs_pojo.getItem_name(),rs_pojo.getUnit(),rs_pojo.getTotal_exp()});
            j++;
        }
    }
}
