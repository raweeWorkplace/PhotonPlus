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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
    private String todayDate, sql,bill_no,item_code,display,BillId,inv_item_code, 
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
    
    private void reset_details(){
        txtsize.setText("");
        txtDiscAmt.setText("0");
        txtRate.setText("");
        txtPaid.setText("0");
        txtQty.setText("");
        lblDue.setText("");
        lblSubTotal.setText("");
        lblTotal.setText("");
        
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

    private void ireport() throws SQLException{
        try {
            
            String reportSql ="select * from bill_table as b, sales_table as s where b.bill_no= s.bill_no and b.bill_no = '"+bill_no+"'";  

                url7 = getClass().getResourceAsStream("/report/report.jrxml");
                        
                JasperDesign jd = JRXmlLoader.load(url7);
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(reportSql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, conInstance);
                JasperViewer.viewReport(jp,false);
        } catch (JRException ex) {
            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void validate_and_save_data(){
        if(txtsize.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Photo Size");
         txtsize.requestFocus();
        }
       else if(txtRate.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Original Rate");
         txtRate.requestFocus();
        }
        else if(txtQty.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Quantity");
         txtQty.requestFocus();
        }else if(txtDiscAmt.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Discount Amount or 0");
         txtDiscAmt.requestFocus();
        }
       else if(txtPaid.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Advance paid or 0");
         txtPaid.requestFocus();
        } else {
            try {
                
                
                String sqlQuery = "Insert into bill_table(date,total,disc,paid,due) values ('"+todayDate+"','"+lblSubTotal.getText()+"','"+txtDiscAmt.getText()+"','"+txtPaid.getText()+"','"+lblDue.getText()+"')";
                smtInstance= conInstance.createStatement();
                int rs1 = smtInstance.executeUpdate(sqlQuery);
                if(rs1==1){
                    
                    String maxId = "Select max(bill_no) from bill_table";
                    smtInstance = conInstance.createStatement();
                    ResultSet max = smtInstance.executeQuery(maxId);
                    while(max.next()){
                        bill_no = max.getString(1);
                    }
                    
                   String item_name = txtsize.getText();
                    String sql = "Insert into sales_table(bill_no,item_code,item_name,qty,cost) values ('"+bill_no+"','"+item_code+"','"+item_name+"','"+txtQty.getText()+"','"+lblTotal.getText()+"')";
                    smtInstance= conInstance.createStatement();
                    int rs2 = smtInstance.executeUpdate(sql);
                    if(rs2==1){
                    JOptionPane.showMessageDialog(null,"Data Submitted");
                    reset_details();
                    ireport();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Data Failed");
                }

            } catch (SQLException ex) {
            Logger.getLogger(homePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtsize = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        sizeTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtRate = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        txtDiscAmt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtPaid = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        lblDue = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setBackground(java.awt.Color.white);

        jPanel1.setBackground(java.awt.Color.darkGray);
        jPanel1.setBorder(null);

        jPanel3.setBackground(java.awt.Color.lightGray);
        jPanel3.setBorder(null);

        txtBilNo.setBackground(java.awt.Color.lightGray);
        txtBilNo.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
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
        jLabel5.setForeground(java.awt.Color.black);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/icons8-search-25.png"))); // NOI18N
        jLabel5.setText("Bill No. ");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jPanel2.setBackground(java.awt.Color.lightGray);
        jPanel2.setBorder(null);

        jLabel12.setBackground(java.awt.Color.lightGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel12.setForeground(java.awt.Color.black);
        jLabel12.setText("Item :");

        txtsize.setBackground(java.awt.Color.lightGray);
        txtsize.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        txtsize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsizeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsizeKeyReleased(evt);
            }
        });

        sizeTable.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        sizeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sizeTable.setRowHeight(24);
        sizeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sizeTableMouseClicked(evt);
            }
        });
        sizeTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sizeTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(sizeTable);
        if (sizeTable.getColumnModel().getColumnCount() > 0) {
            sizeTable.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel10.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel10.setText("Quan. :");

        txtQty.setBackground(java.awt.Color.lightGray);
        txtQty.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel11.setText("Rate  :");

        txtRate.setBackground(java.awt.Color.lightGray);
        txtRate.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        txtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRateKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel15.setText("Disc. :");

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel21.setText("Sub.:");

        lblSubTotal.setBackground(java.awt.Color.white);
        lblSubTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        lblSubTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtDiscAmt.setBackground(java.awt.Color.lightGray);
        txtDiscAmt.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        txtDiscAmt.setText("0");
        txtDiscAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscAmtFocusLost(evt);
            }
        });
        txtDiscAmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscAmtKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscAmtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscAmtKeyReleased(evt);
            }
        });

        jLabel22.setBackground(java.awt.Color.white);
        jLabel22.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel22.setText("Total :");

        lblTotal.setBackground(java.awt.Color.white);
        lblTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        lblTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel27.setBackground(java.awt.Color.white);
        jLabel27.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel27.setText("Adv. :");

        txtPaid.setBackground(java.awt.Color.lightGray);
        txtPaid.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        txtPaid.setText("0");
        txtPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaidFocusLost(evt);
            }
        });
        txtPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaidKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidKeyReleased(evt);
            }
        });

        jLabel26.setBackground(java.awt.Color.white);
        jLabel26.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        jLabel26.setText("Due :");

        lblDue.setBackground(java.awt.Color.white);
        lblDue.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        lblDue.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblDueKeyTyped(evt);
            }
        });

        btnSubmit.setFont(new java.awt.Font("Century Schoolbook L", 0, 36)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDue, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtRate, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txtDiscAmt, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txtPaid, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel15, jLabel21, jLabel22, jLabel26, jLabel27, lblDue, lblSubTotal, lblTotal, txtDiscAmt, txtPaid, txtQty, txtRate});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiscAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDue, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSubmit)))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblDue, lblSubTotal, lblTotal, txtDiscAmt, txtPaid, txtQty, txtRate, txtsize});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel12, jLabel15});

        jSeparator2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBilNo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jScrollPane4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBilNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(23, 23, 23)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1076, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
      if ((key == KeyEvent.VK_SHIFT)){
          txtsize.requestFocus();
      }else if ((key == KeyEvent.VK_END)){
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

    private void txtsizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtsize.getText().isEmpty())) {
            
            if(!fntools.isEmpty(sizeTable)){
                size_table_model  =(DefaultTableModel)sizeTable.getModel();
                String Name = size_table_model.getValueAt(0, 0).toString();
                txtsize.setText(Name);
            
                            
            try {
                String item = "Select id,display from photo_size_detail_table where size = '"+txtsize.getText()+"'";
                   
                smtInstance = conInstance.createStatement();
                ResultSet itemcodeRss = smtInstance.executeQuery(item);
                while(itemcodeRss.next()){
                    item_code = itemcodeRss.getString(1);
                    display = itemcodeRss.getString("display");
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           txtQty.requestFocus();
            
        }else if ((key == KeyEvent.VK_DOWN)) {
            if(!fntools.isEmpty(sizeTable)){
            size_table_model  =(DefaultTableModel)sizeTable.getModel();
            sizeTable.setRowSelectionInterval(0, 0);
            sizeTable.requestFocus();
            
            }
            //txtRate.requestFocus();
        }
    }//GEN-LAST:event_txtsizeKeyPressed

    private void txtsizeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyReleased
        String values = txtsize.getText();
        sql = "SELECT Distinct size FROM photo_size_detail_table where size Like '%"+values+"%'";
        fill_size_entry_table(sql,"size",sizeTable);
    }//GEN-LAST:event_txtsizeKeyReleased

    private void sizeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sizeTableMouseClicked
        int index = sizeTable.getSelectedRow();
        size_table_model  =(DefaultTableModel)sizeTable.getModel();
        String Name = size_table_model.getValueAt(index, 0).toString();
        txtsize.setText(Name);
        txtsize.requestFocus();
    }//GEN-LAST:event_sizeTableMouseClicked

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtQty.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!txtQty.getText().isEmpty())) {
            txtRate.requestFocus();
        }else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtQty.setText("");
        }
    }//GEN-LAST:event_txtQtyKeyPressed

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
            float cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
            lblSubTotal.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtRate.getText().isEmpty())) {
            txtDiscAmt.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtRate.getText().isEmpty())) {
            txtRate.setText(display);
            txtDiscAmt.requestFocus();
        }
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
            float cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
            lblSubTotal.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtRateKeyPressed

    private void txtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyReleased
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
            float cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
            lblSubTotal.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtRateKeyReleased

    private void txtDiscAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscAmtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtFocusLost

    private void txtDiscAmtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtKeyTyped

    private void txtDiscAmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyPressed
        int key = evt.getKeyCode();
        if((key == KeyEvent.VK_ENTER)&&(!txtDiscAmt.getText().isEmpty())){
            txtPaid.requestFocus();
        }

    }//GEN-LAST:event_txtDiscAmtKeyPressed

    private void txtDiscAmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyReleased
        if ((!"".equals(lblSubTotal.getText())) && (!"".equals(txtDiscAmt.getText()))) {
            float cost = Float.parseFloat(lblSubTotal.getText()) - Float.parseFloat(txtDiscAmt.getText());
            lblTotal.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtDiscAmtKeyReleased

    private void txtPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidFocusGained
        
    }//GEN-LAST:event_txtPaidFocusGained

    private void txtPaidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidFocusLost

    private void txtPaidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidKeyTyped

    private void txtPaidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyPressed
        int key = evt.getKeyCode();
        if((key == KeyEvent.VK_ENTER)&&(!txtPaid.getText().isEmpty())){
            btnSubmit.requestFocus();
        }
    }//GEN-LAST:event_txtPaidKeyPressed

    private void txtPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyReleased
        if ((!"".equals(lblTotal.getText())) && (!"".equals(txtPaid.getText()))) {
            if (Float.parseFloat(lblTotal.getText()) > Float.parseFloat(txtPaid.getText())) {
                float due = Float.parseFloat(lblTotal.getText()) - Float.parseFloat(txtPaid.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText(new DecimalFormat("##.##").format(due));
                
            } else {
                float due = Float.parseFloat(txtPaid.getText()) - Float.parseFloat(lblTotal.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText("0.0");
                
            }
        }
    }//GEN-LAST:event_txtPaidKeyReleased

    private void lblDueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblDueKeyTyped
        
    }//GEN-LAST:event_lblDueKeyTyped

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        validate_and_save_data();
       //fill_photo_size_table();
       reset_details();
       txtsize.requestFocus();
    }//GEN-LAST:event_btnSubmitActionPerformed

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

    private void sizeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizeTableKeyPressed
       int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = sizeTable.getSelectedRow();
            String value = sizeTable.getModel().getValueAt(row_index,0).toString();
            txtsize.setText(value);
            txtsize.requestFocus();
        }
    }//GEN-LAST:event_sizeTableKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable billTable;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JTable detailTable;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblDue;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable sizeTable;
    public javax.swing.JTextField txtBilNo;
    private javax.swing.JTextField txtDiscAmt;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtRate;
    public javax.swing.JTextField txtsize;
    // End of variables declaration//GEN-END:variables
}
