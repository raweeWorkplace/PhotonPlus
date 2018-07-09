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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ranjan
 */
public class photoSizeDetailPanel extends javax.swing.JPanel {
    
    protected Connection conInstance;
    protected Statement smtInstance;
    functionTools fntools;
    ResultSet rs;
    DataBase_Connection dao;
    DefaultTableModel size_table_model, size_detail_table_model;

    /**
     * Creates new form purchaseVouchrePanel
     */
    public photoSizeDetailPanel() {
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        fntools = new functionTools();
        fill_photo_size_table();
        
        
    }
    
    private void reset_details(){
        txtsize.setText("");
        txtDisplay.setText("");
        txtRate.setText("");
        fntools.remove_table_data((DefaultTableModel)sizeTable.getModel(), sizeTable);
                
        
    }
    
    private void fill_photo_size_table(){
        size_detail_table_model  =(DefaultTableModel)sizeDetailTable.getModel();
        try {
            
            String  sql1= "SELECT * FROM photo_size_detail_table";
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
                fntools.remove_table_data(size_detail_table_model,sizeDetailTable);
                int i = 0;
                while ( rs1.next() ){
                    i++;//count raws
                }
                if (i>0){
                    int j= 0;
                    rs1.beforeFirst();
                    while (rs1.next()) {
                        //`voucherNo`, `date`, `company_name`, `total`, `paid`, `due`, `reference_bill_no`
                        String id = rs1.getString("id");
                        String size = rs1.getString("size");
                        String rate = rs1.getString("rate");
                        String dispaly = rs1.getString("display");
                        
                        size_detail_table_model.insertRow(j,new Object[]{id,size,rate,dispaly});
                        j++;
                    }
                }
                
            
             
        } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void fill_size_entry_table(String input, String output, JTable table){
       size_table_model  =(DefaultTableModel)table.getModel();
       
        try {
                    
            String  sql1= "SELECT DISTINCT "+output+" FROM photo_size_detail_table where "+output+" Like '"+input+"%'";
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
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
                            //System.out.println(size);
                    size_table_model.insertRow(j,new Object[]{size});
                    j++;
                }
                }    
         } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
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
        else if(txtDisplay.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Display Rate or '0'");
         txtDisplay.requestFocus();
        }else {
            try {
                String searchVendor = "Select id from photo_size_detail_table where size ='"+txtsize.getText()+"'";
                smtInstance = conInstance.createStatement();
                ResultSet searchVend = smtInstance.executeQuery(searchVendor);
                int i=0;
                while(searchVend.next()){
                    i++;
                }
             if(i>0){
                 String queryUpdate = "UPDATE photo_size_detail_table SET rate='"+txtRate.getText()+"',display='"+txtDisplay.getText()+"' where size ='"+txtsize.getText()+"'";
                 Statement smtInstance1 = conInstance.createStatement();
                     int result = smtInstance1.executeUpdate(queryUpdate);
                     if (result != 0) {
                     JOptionPane.showMessageDialog(null, " Detail Updated ");
                 }
              }else {
                
                
                String sqlQuery = "Insert into photo_size_detail_table(size,rate,display) values ('"+txtsize.getText()+"','"+txtRate.getText()+"','"+txtDisplay.getText()+"')";
                smtInstance= conInstance.createStatement();
                int rs1 = smtInstance.executeUpdate(sqlQuery);
                if(rs1==1){
                   JOptionPane.showMessageDialog(null,"Data Submitted");
                    reset_details(); 
                }else{
                    JOptionPane.showMessageDialog(null,"Data Failed");
                }
            }

            } catch (SQLException ex) {
            Logger.getLogger(photoSizeDetailPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        sizeDetailTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtsize = new javax.swing.JTextField();
        txtRate = new javax.swing.JTextField();
        txtDisplay = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        sizeTable = new javax.swing.JTable();
        submitButton = new javax.swing.JButton();

        setBackground(java.awt.Color.darkGray);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel4.setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(new java.awt.Color(72, 89, 253));

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 36)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Photo Size Detail Entry");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(387, 387, 387))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        sizeDetailTable.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        sizeDetailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Size", "Rate", "Display Rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sizeDetailTable.setRowHeight(20);
        jScrollPane1.setViewportView(sizeDetailTable);
        if (sizeDetailTable.getColumnModel().getColumnCount() > 0) {
            sizeDetailTable.getColumnModel().getColumn(0).setResizable(false);
            sizeDetailTable.getColumnModel().getColumn(0).setPreferredWidth(15);
            sizeDetailTable.getColumnModel().getColumn(1).setResizable(false);
            sizeDetailTable.getColumnModel().getColumn(2).setResizable(false);
            sizeDetailTable.getColumnModel().getColumn(2).setPreferredWidth(15);
            sizeDetailTable.getColumnModel().getColumn(3).setResizable(false);
            sizeDetailTable.getColumnModel().getColumn(3).setPreferredWidth(15);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel5.setBackground(java.awt.Color.lightGray);

        jLabel12.setBackground(java.awt.Color.lightGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setText("Item :");

        jLabel18.setBackground(java.awt.Color.lightGray);
        jLabel18.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel18.setText("Rate :");

        jLabel20.setBackground(java.awt.Color.lightGray);
        jLabel20.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel20.setText("Disp. Rate :");

        txtsize.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtsize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsizeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsizeKeyReleased(evt);
            }
        });

        txtRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRateKeyReleased(evt);
            }
        });

        txtDisplay.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDisplayKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDisplayKeyReleased(evt);
            }
        });

        sizeTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
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

        submitButton.setBackground(java.awt.Color.lightGray);
        submitButton.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel18)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDisplay, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtRate, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(submitButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(submitButton)
                .addGap(87, 87, 87))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
       validate_and_save_data();
       fill_photo_size_table();
       reset_details();
       txtsize.requestFocus();
        
    }//GEN-LAST:event_submitButtonActionPerformed

    private void sizeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sizeTableMouseClicked
       
    }//GEN-LAST:event_sizeTableMouseClicked

    private void txtsizeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyReleased
        String values = txtsize.getText();
        fill_size_entry_table(values,"size",sizeTable);
    }//GEN-LAST:event_txtsizeKeyReleased

    private void txtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRateKeyReleased

    private void txtDisplayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDisplayKeyReleased
          
    }//GEN-LAST:event_txtDisplayKeyReleased

    private void txtRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) ||(key >= KeyEvent.VK_DECIMAL) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtRate.setEditable(true);
        }
        else if ((key == KeyEvent.VK_ENTER)&&(!txtRate.getText().isEmpty())) {
            txtDisplay.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtRate.setText("");
        }
    }//GEN-LAST:event_txtRateKeyPressed

    private void txtDisplayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDisplayKeyPressed
       int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) ||(key >= KeyEvent.VK_DECIMAL) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtDisplay.setEditable(true);
        }
        else if ((key == KeyEvent.VK_ENTER)&&(!txtDisplay.getText().isEmpty())) {
            submitButton.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtDisplay.setText("");
        }
    }//GEN-LAST:event_txtDisplayKeyPressed

    private void txtsizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyPressed
       int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtsize.getText().isEmpty())) {
            if(!fntools.isEmpty(sizeTable)){
            size_table_model  =(DefaultTableModel)sizeTable.getModel();
            String Name = size_table_model.getValueAt(0, 0).toString();
            txtsize.setText(Name);
            }
            txtRate.requestFocus();
        }
        
        else if ((key == KeyEvent.VK_DOWN)) {
            if(!fntools.isEmpty(sizeTable)){
            size_table_model  =(DefaultTableModel)sizeTable.getModel();
            
                sizeTable.setRowSelectionInterval(0, 0);
                sizeTable.requestFocus();
            
            }
            //txtRate.requestFocus();
        }
    }//GEN-LAST:event_txtsizeKeyPressed

    private void sizeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizeTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = sizeTable.getSelectedRow();
            String value = sizeTable.getModel().getValueAt(row_index,0).toString();
            txtsize.setText(value);
            txtRate.requestFocus();
        }
    }//GEN-LAST:event_sizeTableKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable sizeDetailTable;
    private javax.swing.JTable sizeTable;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField txtDisplay;
    private javax.swing.JTextField txtRate;
    public javax.swing.JTextField txtsize;
    // End of variables declaration//GEN-END:variables
}
