/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;


import Dao.DataBase_Connection;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ranjan
 */
public class purchaseVoucherPanel extends javax.swing.JPanel {
    
    protected Connection conInstance;
    protected Statement smtInstance;
    functionTools fntools;
    ResultSet rs;
    DataBase_Connection dao;
    DefaultTableModel companyTableModel, voucherTableModel;

    /**
     * Creates new form purchaseVouchrePanel
     */
    public purchaseVoucherPanel() {
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        purchaseDateChooser.setDate(Calendar.getInstance().getTime());
        fntools = new functionTools();
        fill_voucher_table();
        
        
    }
    
    private void resetvoucher(){
        txtCompanyName.setText("");
        txtPaid.setText("");
        txtpurchase.setText("");
        txtdue.setText("");
        txtReferenceBill.setText("");
        
    }
    
    private void fill_voucher_table(){
        voucherTableModel  =(DefaultTableModel)voucherTable.getModel();
        try {
            
            String  sql1= "SELECT * FROM voucher_table";
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
                fntools.remove_table_data(voucherTableModel,voucherTable);
                int i = 0;
                while ( rs1.next() ){
                    i++;//count raws
                }
                if (i>0){
                    int j= 0;
                    rs1.beforeFirst();
                    while (rs1.next()) {
                        //`voucherNo`, `date`, `company_name`, `total`, `paid`, `due`, `reference_bill_no`
                        String voucherNo = rs1.getString("voucherNo");
                        String date = rs1.getString("date");
                        String reference_bill_no = rs1.getString("reference_bill_no");
                        String company_name = rs1.getString("company_name");
                        String C = rs1.getString("total");
                        String D = rs1.getString("paid");
                        String E = rs1.getString("due");
                        voucherTableModel.insertRow(j,new Object[]{voucherNo,date,reference_bill_no,company_name,C,D,E});
                        
                        j++;
                    }
                }
                
            
             
        } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void fill_company_name_table(){
       companyTableModel  =(DefaultTableModel)companyTable.getModel();
       String values = txtCompanyName.getText();
        try {
                    
            String  sql1= "SELECT * FROM client_detail_table where company_name Like '"+values+"%'";
            //System.out.println(sql1);
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
                fntools.remove_table_data(companyTableModel,companyTable);
                int i = 0;
                while ( rs1.next() ) //step through the result set
                {
                    i++;//count raws
                }
                if (i>0){
                        int j= 0;
                        rs1.beforeFirst();
                        
                        while (rs1.next()) {
                    String C = rs1.getString("company_name");                  
                    companyTableModel.insertRow(j,new Object[]{C});
                 
                    j++;
                }
                }    
         } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
              
}
    
    private void validate_and_save_data(){
        if(txtCompanyName.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Company Name");
         txtCompanyName.requestFocus();
        }
       else if(txtpurchase.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Purchase Amount");
         txtpurchase.requestFocus();
        }
        else if(txtPaid.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Amount Paid or '0'");
         txtPaid.requestFocus();
        }else {
            try {
            java.sql.Date d = new java.sql.Date(purchaseDateChooser.getDate().getTime());
            String sqlQuery = "Insert into voucher_table (date,company_name,reference_bill_no,total,paid,due) values ('"+d+"','"+txtCompanyName.getText()+"','"+txtReferenceBill.getText()+"','"+txtpurchase.getText()+"','"+txtPaid.getText()+"','"+txtdue.getText()+"')";
            smtInstance= conInstance.createStatement();
            int rs1 = smtInstance.executeUpdate(sqlQuery);
            if(rs1==1){
               JOptionPane.showMessageDialog(null,"Data Submitted");
                resetvoucher(); 
            }else{
                JOptionPane.showMessageDialog(null,"Data Failed");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(purchaseVoucherPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        voucherTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        purchaseDateChooser = new com.toedter.calendar.JDateChooser();
        txtCompanyName = new javax.swing.JTextField();
        txtpurchase = new javax.swing.JTextField();
        txtPaid = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtdue = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        companyTable = new javax.swing.JTable();
        submitButton = new javax.swing.JButton();
        txtReferenceBill = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel4.setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(new java.awt.Color(72, 89, 253));

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 36)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Purchase Voucher");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(420, 420, 420)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        voucherTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        voucherTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Voucher No.", "Pur. Date", "Bill No", "Client Name", "Total", "Paid", "Due"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        voucherTable.setRowHeight(24);
        jScrollPane1.setViewportView(voucherTable);
        if (voucherTable.getColumnModel().getColumnCount() > 0) {
            voucherTable.getColumnModel().getColumn(0).setResizable(false);
            voucherTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            voucherTable.getColumnModel().getColumn(1).setResizable(false);
            voucherTable.getColumnModel().getColumn(1).setPreferredWidth(15);
            voucherTable.getColumnModel().getColumn(2).setResizable(false);
            voucherTable.getColumnModel().getColumn(2).setPreferredWidth(8);
            voucherTable.getColumnModel().getColumn(3).setResizable(false);
            voucherTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            voucherTable.getColumnModel().getColumn(4).setResizable(false);
            voucherTable.getColumnModel().getColumn(4).setPreferredWidth(8);
            voucherTable.getColumnModel().getColumn(5).setResizable(false);
            voucherTable.getColumnModel().getColumn(5).setPreferredWidth(8);
            voucherTable.getColumnModel().getColumn(6).setResizable(false);
            voucherTable.getColumnModel().getColumn(6).setPreferredWidth(8);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(java.awt.Color.lightGray);

        jLabel12.setBackground(java.awt.Color.lightGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setText("Company Name :");

        jLabel16.setBackground(java.awt.Color.lightGray);
        jLabel16.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel16.setText("Date :");

        jLabel18.setBackground(java.awt.Color.lightGray);
        jLabel18.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel18.setText("Total Cost :");

        jLabel20.setBackground(java.awt.Color.lightGray);
        jLabel20.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel20.setText("Paid :");

        purchaseDateChooser.setDateFormatString("yyyy-MM-dd");
        purchaseDateChooser.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        purchaseDateChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                purchaseDateChooserKeyPressed(evt);
            }
        });

        txtCompanyName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtCompanyName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyReleased(evt);
            }
        });

        txtpurchase.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtpurchase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpurchaseKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpurchaseKeyReleased(evt);
            }
        });

        txtPaid.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidKeyReleased(evt);
            }
        });

        jLabel21.setBackground(java.awt.Color.lightGray);
        jLabel21.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel21.setText("Due :");

        txtdue.setEditable(false);
        txtdue.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtdue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdueActionPerformed(evt);
            }
        });
        txtdue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdueKeyReleased(evt);
            }
        });

        companyTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        companyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        companyTable.setRowHeight(24);
        companyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                companyTableMouseClicked(evt);
            }
        });
        companyTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                companyTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(companyTable);
        if (companyTable.getColumnModel().getColumnCount() > 0) {
            companyTable.getColumnModel().getColumn(0).setResizable(false);
        }

        submitButton.setBackground(java.awt.Color.lightGray);
        submitButton.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        txtReferenceBill.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtReferenceBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtReferenceBillKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReferenceBillKeyReleased(evt);
            }
        });

        jLabel19.setBackground(java.awt.Color.lightGray);
        jLabel19.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel19.setText("Reference No :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(7, 7, 7)
                        .addComponent(txtReferenceBill, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(purchaseDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel21))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtdue, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(txtpurchase))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtReferenceBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(purchaseDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(submitButton)))
                        .addContainerGap(18, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtpurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtdue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel16, jLabel18, jLabel19, jLabel20, jLabel21, purchaseDateChooser, submitButton, txtPaid, txtReferenceBill, txtdue, txtpurchase});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
       validate_and_save_data();
       fill_voucher_table();
       resetvoucher();
       txtCompanyName.requestFocus();
        
    }//GEN-LAST:event_submitButtonActionPerformed

    private void txtdueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdueActionPerformed

    private void companyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_companyTableMouseClicked
       
    }//GEN-LAST:event_companyTableMouseClicked

    private void txtCompanyNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyReleased
        fill_company_name_table();
    }//GEN-LAST:event_txtCompanyNameKeyReleased

    private void txtpurchaseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpurchaseKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpurchaseKeyReleased

    private void txtPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyReleased
          if ((!"".equals(txtpurchase.getText())) && (!"".equals(txtPaid.getText()))) {
              float due = Float.parseFloat(txtpurchase.getText()) - Float.parseFloat(txtPaid.getText());
                txtdue.setText(new DecimalFormat("##.##").format(due));
                
            } 
    }//GEN-LAST:event_txtPaidKeyReleased

    private void txtdueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdueKeyReleased
        if ((!"".equals(txtpurchase.getText())) && (!"".equals(txtPaid.getText()))) {
              float due = Float.parseFloat(txtpurchase.getText()) - Float.parseFloat(txtPaid.getText());
                txtdue.setText(new DecimalFormat("##.##").format(due));
                
            }
    }//GEN-LAST:event_txtdueKeyReleased

    private void txtpurchaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpurchaseKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtpurchase.setEditable(true);
        }
        else if ((key == KeyEvent.VK_ENTER)&&(!txtpurchase.getText().isEmpty())) {
            txtPaid.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtpurchase.setText("");
        }
    }//GEN-LAST:event_txtpurchaseKeyPressed

    private void txtPaidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyPressed
       int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtPaid.setEditable(true);
        }
        else if ((key == KeyEvent.VK_ENTER)&&(!txtPaid.getText().isEmpty())) {
            submitButton.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtPaid.setText("");
        }
    }//GEN-LAST:event_txtPaidKeyPressed

    private void txtCompanyNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyPressed
       int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtCompanyName.getText().isEmpty())) {
            if(!fntools.isEmpty(companyTable)){
                companyTableModel  =(DefaultTableModel)companyTable.getModel();
                String Name = companyTableModel.getValueAt(0, 0).toString();
                txtCompanyName.setText(Name);
                txtReferenceBill.requestFocus();
            }else if ((key == KeyEvent.VK_DOWN)&&(!txtCompanyName.getText().isEmpty())) {
            if(!fntools.isEmpty(companyTable)){
            companyTableModel  =(DefaultTableModel)companyTable.getModel();
            companyTable.setRowSelectionInterval(0, 0);
            companyTable.requestFocus();
            
            }
        }
            
        }
    }//GEN-LAST:event_txtCompanyNameKeyPressed

    private void txtReferenceBillKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReferenceBillKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReferenceBillKeyReleased

    private void txtReferenceBillKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReferenceBillKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtCompanyName.getText().isEmpty())) {
            txtpurchase.requestFocus();
        }
    }//GEN-LAST:event_txtReferenceBillKeyPressed

    private void purchaseDateChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchaseDateChooserKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)||key == KeyEvent.VK_SHIFT) {
            txtpurchase.requestFocus();
        }
    }//GEN-LAST:event_purchaseDateChooserKeyPressed

    private void companyTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_companyTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = companyTable.getSelectedRow();
            String value = companyTable.getModel().getValueAt(row_index,0).toString();
            txtCompanyName.setText(value);
            txtReferenceBill.requestFocus();
        }
    }//GEN-LAST:event_companyTableKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable companyTable;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser purchaseDateChooser;
    private javax.swing.JButton submitButton;
    public javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtReferenceBill;
    private javax.swing.JTextField txtdue;
    private javax.swing.JTextField txtpurchase;
    private javax.swing.JTable voucherTable;
    // End of variables declaration//GEN-END:variables
}
