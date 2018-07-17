/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.billing_pojo;
import beans.client_table_pojo;
import beans.journal_pojo;
import beans.rate_table_pojo;
import beans.sales_pojo;
import beans.size_entry_pojo;
import java.util.List;
import javax.persistence.Query;
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
public class billing_controller {
    Session s;
    SessionFactory sf;
    Transaction t;
    Configuration conf;
    functionTools fnTools;
    List<client_table_pojo>  r_list;

    public billing_controller() {
        conf = new Configuration();
        fnTools = new functionTools();
        conf.configure();
        sf = conf.buildSessionFactory();
    }
    
    public size_entry_pojo get_size(String sql){
        s = sf.openSession();
        Query query = s.createQuery(sql);
         size_entry_pojo pojo = (size_entry_pojo)query.getSingleResult();
         return pojo;
    }
    
    public boolean isData(String sql){
        s = sf.openSession();
        List list = s.createQuery(sql).getResultList();
        return !list.isEmpty();
    }
    
    public rate_table_pojo get_rate(String sql){
        s = sf.openSession();
        Query query = s.createQuery(sql);
        rate_table_pojo pojo = (rate_table_pojo)query.getSingleResult();
        return pojo;
    }
        
    public journal_pojo get_client(String sql){
        s = sf.openSession();
        Query query = s.createQuery(sql);
        journal_pojo j_pojo = new journal_pojo();
        client_table_pojo c_pojo = (client_table_pojo)query.getSingleResult();
        List<journal_pojo> list = s.createQuery("from journal_pojo where client_id = "+c_pojo.getId()+"").getResultList();
        for(journal_pojo pojo :list){
            j_pojo = pojo;
        }
        return j_pojo;
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
       
        
    }
    
    public void billDetails(billing_pojo b_pojo){
        s = sf.openSession();
        t=s.beginTransaction();
        s.saveOrUpdate(b_pojo);
        t.commit();
        
    }
    
    public void fill_order(int bill_no, int item_code, int qty, double cost){
        
        s= sf.openSession();
        t = s.beginTransaction();
            sales_pojo pojo = new sales_pojo(bill_no, item_code, qty, cost);
            s.save(pojo);
            t.commit();
            s.close();
    }
        
    public int getBillNo(){
    String sql = "select max(j.bill_no) from billing_pojo j";
    s= sf.openSession();
    org.hibernate.query.Query query =s.createQuery(sql);
    return (int) query.getSingleResult();
    }
    
    private void fillTable(String queryUsingSelection){
         //billTableModel= (DefaultTableModel)billTable.getModel();
//        try
//        {
//                Statement smtUsingDate = conInstance.createStatement();
//                rs1 = (ResultSet) smtUsingDate.executeQuery(queryUsingSelection);
//                
//                if (rs1 != null)
//                {
//                    
//                    int i = 0;
//                    while ( rs1.next() ) //step through the result set
//                    {
//                        i++;//count raws
//                    }
//                    int j = 0;
//                    rs1.beforeFirst();
//                    
//                    fnTools.remove_table_data(billTableModel, billTable);
//                    while (rs1.next()) 
//                    {
//                        item_code = rs1.getString("item_code");
//                        String item_name = rs1.getString("item_name");
//                        String qty = rs1.getString("qty");
//                        String rate = "NA";
//                        String cost = rs1.getString("cost");
//                        
//
//                        billTableModel.insertRow(j,new Object[]{item_code,item_name,qty,rate,cost});
//                        j++;
//                    }
//                }
//              } catch (SQLException ex) {
//             Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
//         }
    }
    
    public void updateDue(journal_pojo j_pojo){
        s = sf.openSession();
        t = s.beginTransaction();
        s.saveOrUpdate(j_pojo);
        t.commit();
        }
    
    private void updateBill(){
//        try {
//            String query3 = "Update bill_table set paid = '"+(Double.parseDouble(lblPaid.getText())+Double.parseDouble(txtPayment.getText()))+"', due = '"+(Double.parseDouble(lblTotalDue.getText())-Double.parseDouble(txtPayment.getText()))+"', disc='"+txtDiscAmt.getText()+"' where bill_no = '"+txtBillId.getText()+"'";
//            smtInstance = conInstance.createStatement();
//            int r = smtInstance.executeUpdate(query3);
//            if(r==1){
//                updateDue();
//                JOptionPane.showMessageDialog(null, "Bill Updated");
//                resetBill();
//                resetTables();
//            }
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public double getBalance(int str){
    String sql = "select sum(j.credit)-sum(j.debit) from journal_pojo j where j.client_id = "+str+"";
    s= sf.openSession();
    org.hibernate.query.Query query =s.createQuery(sql);
    return (double) query.getSingleResult();
    } 
}
