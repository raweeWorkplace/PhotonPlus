/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import controller.functionTools;
import Dao.DataBase_Connection;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ranjan
 */
public class clientRateManagementPanel extends javax.swing.JPanel {
    
    protected Connection conInstance;
    protected Statement smtInstance;
    ResultSet rs;
    DataBase_Connection dao;
    DefaultTableModel size_table_model, size_detail_table_model,companyTableModel;
    private String sql,item_code = "0",display = "";
    functionTools fnTools;

    /**
     * Creates new form purchaseVouchrePanel
     */
    public clientRateManagementPanel() {
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        txtDiscAmt.setEditable(true);
        fnTools = new functionTools();
        fill_client_rate_table("select * from client_rate_table");
        
    }
    
    
    private void reset_details(){
        txtsize.setText("");
        txtClientRate.setText("");
        txtRate.setText("");
        txtDiscAmt.setText("0");
        txtDiscRate.setText("0");
        
    }
    
    private void fill_client_rate_table(String sql){
        size_detail_table_model  =(DefaultTableModel)clientRateTable.getModel();
        try {
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql);
                fnTools.remove_table_data(size_detail_table_model,clientRateTable);
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
                        String size = rs1.getString("company_name");
                        String sub_size = rs1.getString("size");
                        String dispaly = rs1.getString("rate");
                        
                        size_detail_table_model.insertRow(j,new Object[]{id,size,sub_size,dispaly});
                        j++;
                    }
                }
                
            
             
        } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void fill_size_entry_table(String sql,String output, JTable table){
       size_table_model  =(DefaultTableModel)table.getModel();
       
        try {
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql);
                fnTools.remove_table_data(size_table_model,table);
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
    
    private void fill_company_name_table(){
       companyTableModel  =(DefaultTableModel)companyTable.getModel();
       String values = txtCompanyName.getText();
        try {
                    
            String  sql1= "SELECT * FROM client_detail_table where company_name Like '"+values+"%'";
            //System.out.println(sql1);
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
                fnTools.remove_table_data(companyTableModel,companyTable);
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
        if(txtsize.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Photo Size");
         txtsize.requestFocus();
        }
       else if(txtRate.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Original Rate");
         txtRate.requestFocus();
        }
        else if(txtCompanyName.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Company Name");
         txtCompanyName.requestFocus();
        }else {
            try {
            String search = "select id from client_rate_table where company_name = '"+txtCompanyName.getText()+"' and size = '"+txtsize.getText()+"'";
            smtInstance = conInstance.createStatement();
             ResultSet searchVend = smtInstance.executeQuery(search);
             int i=0;
             while(searchVend.next()){
                 i++;
             }
             if(i>0){
                 String queryUpdate = "UPDATE client_rate_table SET rate='"+txtClientRate.getText()+"' where company_name = '"+txtCompanyName.getText()+"' and size = '"+txtsize.getText()+"'";
                 Statement smtInstance1 = conInstance.createStatement();
                     int result = smtInstance1.executeUpdate(queryUpdate);
                     if (result != 0) {
                     JOptionPane.showMessageDialog(null, " Detail Updated ");
                 }
              }else {

                 String sqlQuery = "Insert into client_rate_table(company_name,size,rate) values ('"+txtCompanyName.getText()+"','"+txtsize.getText()+"','"+txtClientRate.getText()+"')";
                smtInstance= conInstance.createStatement();
                int rs1 = smtInstance.executeUpdate(sqlQuery);
                if(rs1==1){
                   JOptionPane.showMessageDialog(null,"Data Submitted");
                    reset_details(); 
                }
             }
            } catch (SQLException ex) {
             
            Logger.getLogger(clientRateManagementPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
    private void calculateTotal(){
        String total = Double.toString(Double.parseDouble(txtRate.getText())-Double.parseDouble(txtDiscAmt.getText()));
        txtClientRate.setText(total);
               
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
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtsize = new javax.swing.JTextField();
        txtRate = new javax.swing.JTextField();
        txtClientRate = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        sizeTable = new javax.swing.JTable();
        btnSubmit = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        companyTable = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtDiscRate = new javax.swing.JTextField();
        txtDiscAmt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientRateTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(java.awt.Color.lightGray);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel4.setBackground(java.awt.Color.lightGray);

        jPanel5.setBackground(java.awt.Color.lightGray);

        jLabel12.setBackground(java.awt.Color.lightGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setText("Item :");

        jLabel18.setBackground(java.awt.Color.lightGray);
        jLabel18.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel18.setText("Rate :");

        jLabel20.setBackground(java.awt.Color.lightGray);
        jLabel20.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel20.setText("Nw Rate");

        txtsize.setBackground(java.awt.Color.lightGray);
        txtsize.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtsize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsizeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsizeKeyReleased(evt);
            }
        });

        txtRate.setEditable(false);
        txtRate.setBackground(java.awt.Color.lightGray);
        txtRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRateActionPerformed(evt);
            }
        });
        txtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRateKeyReleased(evt);
            }
        });

        txtClientRate.setEditable(false);
        txtClientRate.setBackground(java.awt.Color.lightGray);
        txtClientRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtClientRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClientRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientRateKeyReleased(evt);
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

        btnSubmit.setBackground(java.awt.Color.lightGray);
        btnSubmit.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel14.setBackground(java.awt.Color.lightGray);
        jLabel14.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel14.setText("Company :");

        txtCompanyName.setBackground(java.awt.Color.lightGray);
        txtCompanyName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtCompanyName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCompanyNameFocusGained(evt);
            }
        });
        txtCompanyName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyReleased(evt);
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
        jScrollPane4.setViewportView(companyTable);

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel21.setText("Disc. % :");

        jLabel23.setBackground(java.awt.Color.white);
        jLabel23.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel23.setText("Disc.Rs. :");

        txtDiscRate.setBackground(java.awt.Color.lightGray);
        txtDiscRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtDiscRate.setText("0");
        txtDiscRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscRateFocusLost(evt);
            }
        });
        txtDiscRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscRateKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscRateKeyReleased(evt);
            }
        });

        txtDiscAmt.setBackground(java.awt.Color.lightGray);
        txtDiscAmt.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
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

        clientRateTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        clientRateTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Company Name", "Size", "Rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        clientRateTable.setRowHeight(24);
        jScrollPane1.setViewportView(clientRateTable);
        if (clientRateTable.getColumnModel().getColumnCount() > 0) {
            clientRateTable.getColumnModel().getColumn(0).setResizable(false);
            clientRateTable.getColumnModel().getColumn(0).setPreferredWidth(15);
            clientRateTable.getColumnModel().getColumn(1).setResizable(false);
            clientRateTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            clientRateTable.getColumnModel().getColumn(2).setResizable(false);
            clientRateTable.getColumnModel().getColumn(3).setResizable(false);
            clientRateTable.getColumnModel().getColumn(3).setPreferredWidth(15);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDiscAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtClientRate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDiscRate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCompanyName))
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDiscAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(67, 67, 67))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiscRate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtClientRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel14, jLabel18, jLabel20});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtClientRate, txtDiscRate, txtRate});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
       validate_and_save_data();
       fill_client_rate_table("select * from client_rate_table");
       reset_details();
       txtsize.requestFocus();
        
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void sizeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sizeTableMouseClicked
       
    }//GEN-LAST:event_sizeTableMouseClicked

    private void txtsizeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyReleased
        String values = txtsize.getText();
        sql = "SELECT Distinct size FROM photo_size_detail_table where size Like '"+values+"%'";
        fill_size_entry_table(sql,"size",sizeTable);
    }//GEN-LAST:event_txtsizeKeyReleased

    private void txtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRateKeyReleased

    private void txtClientRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientRateKeyReleased
          
    }//GEN-LAST:event_txtClientRateKeyReleased

    private void txtRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            //txtRate.setEditable(true);
        }
        else if ((key == KeyEvent.VK_ENTER)&&(!txtRate.getText().isEmpty())) {
            txtClientRate.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtRate.setText("");
        }
    }//GEN-LAST:event_txtRateKeyPressed

    private void txtClientRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientRateKeyPressed
       int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtClientRate.setEditable(true);
        }
        else if ((key == KeyEvent.VK_ENTER)&&(!txtClientRate.getText().isEmpty())) {
            btnSubmit.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtClientRate.setText("");
        }
    }//GEN-LAST:event_txtClientRateKeyPressed

    private void txtsizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyPressed
       int key = evt.getKeyCode();
        
       if ((key == KeyEvent.VK_ENTER)&&(!txtsize.getText().isEmpty())) {
            
            if(!fnTools.isEmpty(sizeTable)){
                size_table_model  =(DefaultTableModel)sizeTable.getModel();
                String Name = size_table_model.getValueAt(0, 0).toString();
                txtsize.setText(Name);
            try {
                String itemcode = "Select id,rate from client_rate_table where size = '"+txtsize.getText()+"' and company_name = '"+txtCompanyName.getText()+"'";
                smtInstance = conInstance.createStatement();
                ResultSet itemcodeRs = smtInstance.executeQuery(itemcode);
                int i =1;
                while(itemcodeRs.next()){
                    i++;
                    item_code = itemcodeRs.getString(1);
                    display = itemcodeRs.getString("rate");
                }
                
                if(i==1){
                    
                   String item = "Select id,display from photo_size_detail_table where size = '"+txtsize.getText()+"'";
                   
                smtInstance = conInstance.createStatement();
                ResultSet itemcodeRss = smtInstance.executeQuery(item);
                while(itemcodeRss.next()){
                    item_code = itemcodeRss.getString(1);
                    display = itemcodeRss.getString("display");
                }
                
                }
            } catch (SQLException ex) {
                Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            txtRate.setText(display);
            txtDiscRate.requestFocus();
         }else if ((key == KeyEvent.VK_DOWN)) {
            if(!fnTools.isEmpty(sizeTable)){
            size_table_model  =(DefaultTableModel)sizeTable.getModel();
            sizeTable.setRowSelectionInterval(0, 0);
            sizeTable.requestFocus();
            
            }
            //txtRate.requestFocus();
        }else if (key == KeyEvent.VK_ESCAPE) {
            
            txtCompanyName.requestFocus();
            txtCompanyName.setText("");
        }
       
        
        
    }//GEN-LAST:event_txtsizeKeyPressed

    private void txtCompanyNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtCompanyName.getText().isEmpty())) {
         if(!fnTools.isEmpty(companyTable)){   
        companyTableModel  =(DefaultTableModel)companyTable.getModel();
        String Name = companyTableModel.getValueAt(0, 0).toString();
        txtCompanyName.setText(Name);
        txtsize.requestFocus();
        }
        }
        else if (key == KeyEvent.VK_DOWN) {
            if(!fnTools.isEmpty(companyTable)){
            companyTableModel  =(DefaultTableModel)companyTable.getModel();
            companyTable.setRowSelectionInterval(0, 0);
            companyTable.requestFocus();
            
            }
        }
    }//GEN-LAST:event_txtCompanyNameKeyPressed

    private void txtCompanyNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyReleased
        fill_company_name_table();
        
    }//GEN-LAST:event_txtCompanyNameKeyReleased

    private void companyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_companyTableMouseClicked
        
    }//GEN-LAST:event_companyTableMouseClicked

    private void txtRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRateActionPerformed

    private void txtCompanyNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyNameFocusGained
        
    }//GEN-LAST:event_txtCompanyNameFocusGained

    private void txtDiscRateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscRateFocusLost
        //jButton2FocusGained(evt);
    }//GEN-LAST:event_txtDiscRateFocusLost

    private void txtDiscRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscRateKeyTyped

    }//GEN-LAST:event_txtDiscRateKeyTyped

    private void txtDiscRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscRateKeyPressed
        int key = evt.getKeyCode();
        double total = 0;
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtDiscRate.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!"0".equals(txtDiscRate.getText()))&&(!txtDiscRate.getText().isEmpty())) {
            total = Double.parseDouble(txtRate.getText())*Double.parseDouble(txtDiscRate.getText())*0.01;
            txtDiscAmt.setText(new DecimalFormat("##.##").format(total));
            txtDiscAmt.setEditable(false);
            calculateTotal();
            btnSubmit.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&("0".equals(txtDiscRate.getText()))) {
            txtDiscAmt.setEditable(true);
            txtDiscAmt.requestFocus();
        } else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtDiscRate.setText("");
        }
    }//GEN-LAST:event_txtDiscRateKeyPressed

    private void txtDiscRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscRateKeyReleased

    }//GEN-LAST:event_txtDiscRateKeyReleased

    private void txtDiscAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscAmtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtFocusLost

    private void txtDiscAmtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtKeyTyped

    private void txtDiscAmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyPressed
        int key = evt.getKeyCode();
        if((key == KeyEvent.VK_ENTER)&&(!txtRate.getText().isEmpty())){
            calculateTotal();
            btnSubmit.requestFocus();
        }

    }//GEN-LAST:event_txtDiscAmtKeyPressed

    private void txtDiscAmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtKeyReleased

    private void companyTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_companyTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = companyTable.getSelectedRow();
            String value = companyTable.getModel().getValueAt(row_index,0).toString();
            txtCompanyName.setText(value);
            txtsize.requestFocus();
        }
    }//GEN-LAST:event_companyTableKeyPressed

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
    private javax.swing.JButton btnSubmit;
    private javax.swing.JTable clientRateTable;
    private javax.swing.JTable companyTable;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable sizeTable;
    private javax.swing.JTextField txtClientRate;
    public javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtDiscAmt;
    private javax.swing.JTextField txtDiscRate;
    private javax.swing.JTextField txtRate;
    public javax.swing.JTextField txtsize;
    // End of variables declaration//GEN-END:variables
}
