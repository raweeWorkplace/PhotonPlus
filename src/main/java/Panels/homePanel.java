/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import controller.functionTools;
import Dao.DataBase_Connection;
import controller.MyIntFilter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ranjan
 */
public class homePanel extends javax.swing.JPanel {
    
    protected Connection conInstance;
    protected Statement smtInstance;
    functionTools fntools;
    ResultSet rs;
    DataBase_Connection dao;
    InputStream url7;
    private String todayDate, 
        sql1= "SELECT * FROM bill_table where status != 'Old Entry'";
    DefaultTableModel size_table_model, bill_table_model, detail_model;
    

    /**
     * Creates new form homePanel
     */
    public homePanel() {
        
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        fntools = new functionTools();
        txtBilNo.requestFocus();
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        todayDate =dateformat.format(cal.getTime());
        fill_bill_table(sql1);
        //itemPanel.setVisible(false);
    }
    
 
    private void fill_size_entry_table(String sql,String output, JTable table){
       size_table_model  =(DefaultTableModel)table.getModel();
       
        try {
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql);
                fntools.remove_table_data(size_table_model,table);
                int i = 0;
                while ( rs1.next() ) //step through the result set
                {
                    i++;//count raws
                }
                if (i>0){
                        int j= 0;
                        rs1.beforeFirst();
                        
                        while (rs1.next()) {
                    String size = rs1.getString(output); 
                    size_table_model.insertRow(j,new Object[]{size});
                    j++;
                }
                }    
         } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
              
}
    
    private void fill_bill_table(String sql1){
       bill_table_model  =(DefaultTableModel)billTable.getModel();
       try {
            
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
            fntools.remove_table_data(bill_table_model,billTable);
                int i = 0;
                while ( rs1.next() ) //step through the result set
                {
                    i++;//count raws
                }
                if (i>0){
                        int j= 0;
                        rs1.beforeFirst();
                        
                        while (rs1.next()) {
                    String bill_no = rs1.getString("bill_no");
                    String item_name = rs1.getString("cust_name");
                    String total = rs1.getString("total");
                    String adv = rs1.getString("paid");
                    String due = rs1.getString("due");
                    String status = rs1.getString("status");
                    bill_table_model.insertRow(j,new Object[]{bill_no,item_name,total,adv,due,status});
                    j++;
                }                       
                }
                
            } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
              
}
    
    private void fill_detail_table(String sql1){
       detail_model  =(DefaultTableModel)detailTable.getModel();
       try {
            
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
            fntools.remove_table_data(detail_model,detailTable);
                int i = 0;
                while ( rs1.next() ) //step through the result set
                {
                    i++;//count raws
                }
                if (i>0){
                        int j= 0;
                        rs1.beforeFirst();
                        
                        while (rs1.next()) {
                    String bill_no = rs1.getString("bill_no");
                    String item_name = rs1.getString("item_name");
                    String total = rs1.getString("qty");
                    detail_model.insertRow(j,new Object[]{bill_no,item_name,total});
                    j++;
                }                       
                }
            } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
              
}      
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtBilNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        detailTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();

        setBackground(java.awt.Color.white);

        jPanel1.setBackground(java.awt.Color.darkGray);
        jPanel1.setBorder(null);

        jPanel3.setBackground(java.awt.Color.darkGray);
        jPanel3.setBorder(null);

        txtBilNo.setBackground(java.awt.Color.darkGray);
        txtBilNo.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtBilNo.setForeground(java.awt.Color.white);
        txtBilNo.setBorder(null);
        txtBilNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBilNoFocusGained(evt);
            }
        });
        txtBilNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBilNoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBilNoKeyReleased(evt);
            }
        });

        detailTable.setBackground(new java.awt.Color(164, 140, 140));
        detailTable.setBorder(null);
        detailTable.setFont(new java.awt.Font("Century Schoolbook L", 0, 16)); // NOI18N
        detailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill No.", "Item", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        detailTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
        detailTable.setRowHeight(22);
        detailTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(detailTable);
        if (detailTable.getColumnModel().getColumnCount() > 0) {
            detailTable.getColumnModel().getColumn(0).setResizable(false);
            detailTable.getColumnModel().getColumn(0).setPreferredWidth(6);
            detailTable.getColumnModel().getColumn(1).setResizable(false);
            detailTable.getColumnModel().getColumn(2).setResizable(false);
            detailTable.getColumnModel().getColumn(2).setPreferredWidth(4);
        }

        billTable.setBorder(null);
        billTable.setFont(new java.awt.Font("Century Schoolbook L", 0, 16)); // NOI18N
        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill No.", "Name", "Total", "Adv", "Due", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        billTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
        billTable.setRowHeight(22);
        billTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billTableMouseClicked(evt);
            }
        });
        billTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                billTableKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(billTable);
        if (billTable.getColumnModel().getColumnCount() > 0) {
            billTable.getColumnModel().getColumn(0).setResizable(false);
            billTable.getColumnModel().getColumn(0).setPreferredWidth(6);
            billTable.getColumnModel().getColumn(1).setResizable(false);
            billTable.getColumnModel().getColumn(2).setResizable(false);
            billTable.getColumnModel().getColumn(2).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(2).setHeaderValue("Total");
            billTable.getColumnModel().getColumn(3).setResizable(false);
            billTable.getColumnModel().getColumn(3).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(3).setHeaderValue("Adv");
            billTable.getColumnModel().getColumn(4).setResizable(false);
            billTable.getColumnModel().getColumn(4).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(4).setHeaderValue("Due");
            billTable.getColumnModel().getColumn(5).setResizable(false);
            billTable.getColumnModel().getColumn(5).setPreferredWidth(15);
            billTable.getColumnModel().getColumn(5).setHeaderValue("Status");
        }

        jLabel5.setBackground(java.awt.Color.white);
        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/icons8-search-25.png"))); // NOI18N
        jLabel5.setText("Bill No. ");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jSeparator2.setBackground(java.awt.Color.darkGray);
        jSeparator2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(java.awt.Color.darkGray);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 155, Short.MAX_VALUE)
        );

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBilNo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 11, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(txtBilNo)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(207, 207, 207))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
        
    private void txtBilNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilNoKeyReleased
        if(!txtBilNo.getText().isEmpty()){
        String  sql1= "SELECT * FROM bill_table where bill_no ='"+txtBilNo.getText()+"'";
        fill_bill_table(sql1);
        String  sql2= "SELECT * FROM sales_table where bill_no ='"+txtBilNo.getText()+"'";
        fill_detail_table(sql2);
        }else{
            fill_bill_table(sql1);
        }
    }//GEN-LAST:event_txtBilNoKeyReleased

    private void detailTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailTableMouseClicked
       
    }//GEN-LAST:event_detailTableMouseClicked

    private void txtBilNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBilNoFocusGained
       
    }//GEN-LAST:event_txtBilNoFocusGained

    private void txtBilNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilNoKeyPressed
      ((AbstractDocument) txtBilNo.getDocument()).setDocumentFilter(new MyIntFilter());
      int key = evt.getKeyCode();
      if ((key == KeyEvent.VK_END)){
          try {
              bill_table_model  =(DefaultTableModel)billTable.getModel();
              String adv = bill_table_model.getValueAt(0, 3).toString();
              String due = bill_table_model.getValueAt(0, 4).toString();
              String paid = Double.toString(Double.parseDouble(adv) + Double.parseDouble(due));
              String sql = "update bill_table set status = 'Delivered', paid = '"+paid+"', due = '0.0' where bill_no = '"+txtBilNo.getText()+"' ";
              smtInstance = conInstance.createStatement();
              smtInstance.executeUpdate(sql);
              fill_bill_table(sql1);
          } catch (SQLException ex) {
              Logger.getLogger(homePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
      } else if ((key == KeyEvent.VK_HOME)){
          try {
              bill_table_model  =(DefaultTableModel)billTable.getModel();
              String adv = bill_table_model.getValueAt(0, 3).toString();
              String due = bill_table_model.getValueAt(0, 4).toString();
              String paid = Double.toString(Double.parseDouble(adv) + Double.parseDouble(due));
              String sql = "update bill_table set status = 'Delivered'where bill_no = '"+txtBilNo.getText()+"' ";
              smtInstance = conInstance.createStatement();
              smtInstance.executeUpdate(sql);
              fill_bill_table(sql1);
          } catch (SQLException ex) {
              Logger.getLogger(homePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      else if ((key == KeyEvent.VK_DOWN)) {
            if(!fntools.isEmpty(billTable)){
            bill_table_model  =(DefaultTableModel)billTable.getModel();
            billTable.setRowSelectionInterval(0, 0);
            billTable.requestFocus();
            
            }
            
        }
    }//GEN-LAST:event_txtBilNoKeyPressed

    private void billTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_billTableMouseClicked

    private void billTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = billTable.getSelectedRow();
            String value = billTable.getModel().getValueAt(row_index,0).toString();
            txtBilNo.setText(value);
            txtBilNo.requestFocus();
        }
    }//GEN-LAST:event_billTableKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable billTable;
    private javax.swing.JTable detailTable;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public javax.swing.JTextField txtBilNo;
    // End of variables declaration//GEN-END:variables
}
