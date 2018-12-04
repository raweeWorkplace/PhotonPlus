/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.billingPojo;
import beans.clientPojo;
import beans.journal_pojo;
import beans.rate_table_pojo;
import beans.sales_pojo;
import beans.size_entry_pojo;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
    List<clientPojo>  r_list;
    InputStream url7;
    

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
        clientPojo c_pojo = (clientPojo)query.getSingleResult();
        List<journal_pojo> list = s.createQuery("from journal_pojo where client_id = "+c_pojo.getId()+"").getResultList();
        for(journal_pojo pojo :list){
            j_pojo = pojo;
        }
        return j_pojo;
    }
    
    public void fill_company_name(JTable table, String sql){
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        s= sf.openSession();
        Query query = s.createQuery(sql);
        List<clientPojo> list = query.getResultList();
        fnTools.remove_table_data(table_model, table);
        int j=0;
        for(clientPojo rs_pojo :list){
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
    
    public void billDetails(billingPojo b_pojo){
        s = sf.openSession();
        t=s.beginTransaction();
        s.saveOrUpdate(b_pojo);
        t.commit();
        ireport(getBillNo());
        
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
    String sql = "select max(j.bill_no) from billingPojo j";
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
    
    private static Map getParameter(Session session){
        Map params = new HashMap();
        params.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,session);
        return params;
    }
    
    public void ireport(int bill_no){
        try {
           // s = sf.openSession();
             Class.forName("com.mysql.jdbc.Driver");
            Connection connectionInstance = DriverManager.getConnection("jdbc:mysql://localhost:3306/photon", "root", "");
            
            //Map params = getParameter(s);
            String reportSql ="select * from bill_table b,sales_table s,photo_size_detail_table p where s.bill_no =b.bill_no and p.size_id=s.item_code and b.bill_no ='"+bill_no+"'";
            System.out.println(reportSql);
            //List list = s.createQuery(reportSql).getResultList();
            
            //url7 = getClass().getResourceAsStream("/report/hicola.jrxml");
           // System.out.println(url7);
            JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream("/report/hicola.jrxml"));
            if(jd==null){
               jd = JRXmlLoader.load(new File("/report/hicola.jrxml"));
            }
            if(jd==null){
               jd = JRXmlLoader.load(getClass().getResourceAsStream("/report/hicola.jrxml"));
            }
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(reportSql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,null, connectionInstance);
            //JasperPrint jp = JasperFillManager.fillReport(jr,params);
            JasperViewer.viewReport(jp,false);
            int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to print Bill?","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                JasperPrintManager.printReport(jp, true);
            }
        } catch (JRException ex) {
            Logger.getLogger(billing_controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(billing_controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(billing_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }



        public void fill_report_table(JTable table, String sql){
        s = sf.openSession();
        DefaultTableModel table_model = (DefaultTableModel)table.getModel();
        List<billingPojo> list =s.createQuery(sql).getResultList();
        fnTools.remove_table_data(table_model, table);
        int j=0;
        for(billingPojo rs_pojo : list){
            table_model.insertRow(j, new Object[]{rs_pojo.getDate(),rs_pojo.getBill_no(),rs_pojo.getCust_name(),rs_pojo.getTotal()});
            j++;
        }
    }

}
