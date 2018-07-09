/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Dao.DataBase_Connection;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ranjan
 */
public class clientRegPanel extends javax.swing.JPanel {
        protected Connection conInstance;
        protected Statement smtInstance;
        functionTools fnTools;
        ResultSet rs;
        DataBase_Connection dao;
        DefaultTableModel client_detail_table_model;
        LineBorder lbd;
        String sql1 ="",sql2="",sql3="";
        
    /**
     * Creates new form clientRegPanel
     */
    public clientRegPanel() {
        
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        fnTools = new functionTools();
        lbd = new LineBorder(Color.RED);
        txtAddress.setText("JANAKPUR DHAM");
        txtOldDue.setText("0");
    }
    
    private void validate_and_save_form(){
        if(txtCompanyName.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Company Name");
         txtCompanyName.requestFocus();
        }
       else if(txtClientName.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Client Name");
         txtClientName.requestFocus();
        }
        else if(txtContact.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Contact");
         txtContact.requestFocus();
        }
        else if(!txtOldDue.getText().equals(txtOldDue1.getText())){
         JOptionPane.showMessageDialog(null,"Old due Doesnot match");
         txtOldDue.requestFocus();
        }
        else {
            
            String obj = "";
            obj = cmbCliebtType.getSelectedItem().toString();
            if("Dealer".equals(obj)){
                sql1 = "Select client_name from client_detail_table where company_name ='"+txtCompanyName.getText()+"'";
                sql3 = "INSERT INTO client_detail_table(client_name, company_name, contact, old_due, address) VALUES ('" + txtClientName.getText() + "','" + txtCompanyName.getText() + "','" + txtContact.getText() + "','" + txtOldDue.getText() + "','" + txtAddress.getText() + "')";
                sql2 = "UPDATE client_detail_table SET client_name='"+txtClientName.getText()+"',contact='"+txtContact.getText()+"',old_due='"+txtOldDue.getText()+"',address='"+txtAddress.getText()+"' Where company_name = '"+txtCompanyName.getText()+"'";
            }else{
                sql1 = "Select cust_name from special_customer_table where contact ='"+txtContact.getText()+"'";
                sql3 = "INSERT INTO special_customer_table(cust_name, contact, old_due) VALUES ('" + txtCompanyName.getText() + "','" + txtContact.getText() + "','" + txtOldDue.getText() + "')";
                sql2 = "UPDATE special_customer_table SET old_due='"+txtOldDue.getText()+"' Where contact = '"+txtContact.getText()+"'";
            }
            saveVendor(sql1,sql2,sql3);
            resetVendorDetails();
            fnTools.remove_table_data(client_detail_table_model, client_detail_table);
            if("Dealer".equals(obj)){
                
                fill_client_detail_table();
            }else{
                fill_customer_detail_table();
            }
            txtCompanyName.requestFocus();
        }
    }
        
    private  void resetVendorDetails(){
        txtCompanyName.setText("");
        txtClientName.setText("");
        txtContact.setText("");
        txtOldDue.setText("0");
        txtOldDue1.setText("");
        txtAddress.setText("JANAKPUR DHAM");
        txtClientName.setEditable(true);
        txtCompanyName.setEditable(true);
        txtContact.setEditable(true);
        txtAddress.setEditable(true);
        txtOldDue.setEditable(true);
                
    }

    private void saveVendor(String searchVendor,String queryUpdate,String queryInsert) {

     try {
             //
             smtInstance = conInstance.createStatement();
             ResultSet searchVend = smtInstance.executeQuery(searchVendor);
             int i=0;
             while(searchVend.next()){
                 i++;
             }
             if(i>0){
                 
                 Statement smtInstance1 = conInstance.createStatement();
                     int result = smtInstance1.executeUpdate(queryUpdate);
                     if (result != 0) {
                     JOptionPane.showMessageDialog(null, " Detail Updated ");
                 }
              }else {

                 Statement smtInstance2 = conInstance.createStatement();
                 int result = smtInstance2.executeUpdate(queryInsert);
                 if (result != 0) {
                     DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String todayDate =dateformat.format(cal.getTime());
                     String sql = "Insert into bill_table(date,cust_name,contact,total,due,status)values('"+todayDate+"','"+txtCompanyName.getText()+"', '"+txtContact.getText()+"','"+txtOldDue.getText()+"','"+txtOldDue.getText()+"','Old Entry')";
                        smtInstance = conInstance.createStatement();
                        smtInstance.executeUpdate(sql);
                     JOptionPane.showMessageDialog(null, " Detail Submitted ");
                 }
             }
     }catch (SQLException ex) {
     }
 }     

    private void fill_client_detail_table() {
     client_detail_table_model = (DefaultTableModel) client_detail_table.getModel();
     String name = txtCompanyName.getText();
     try {
         conInstance = dao.getConnection();
         String sql1 = "SELECT * FROM client_detail_table where company_name Like'" + name + "%'";
         Statement st = conInstance.createStatement();
         ResultSet rs1 = st.executeQuery(sql1);

         if (rs1 != null) { //if the result set exists
             int i = 0;
             while (rs1.next()) //step through the result set
             {
                 i++;//count raws
             }

             int j = 0;
             fnTools.remove_table_data(client_detail_table_model,client_detail_table);
             rs1.beforeFirst();

             while (rs1.next()) {
                 String id = rs1.getString("id");
                 String A = rs1.getString("company_name");
                 String B = rs1.getString("client_name");
                 String D = rs1.getString("contact");
                 String E = rs1.getString("old_due");
                 String F = rs1.getString("address");

                 client_detail_table_model.insertRow(j, new Object[]{id,A, B, D,E,F});
                 
//                    tableshow.getColumnModel().getColumn(0).setHeaderValue("Madicin Name");

                 j++;

             }
         }//end if
     } catch (SQLException ex) {
         Logger.getLogger(clientRegPanel.class.getName()).log(Level.SEVERE, null, ex);
     }

 }
    
    private void fill_customer_detail_table() {
     client_detail_table_model = (DefaultTableModel) client_detail_table.getModel();
     String name = txtCompanyName.getText();
     try {
         conInstance = dao.getConnection();
         String sql1 = "SELECT * FROM special_customer_table where cust_name Like'" + name + "%'";
         Statement st = conInstance.createStatement();
         ResultSet rs1 = st.executeQuery(sql1);

         if (rs1 != null) { //if the result set exists
             int i = 0;
             while (rs1.next()) //step through the result set
             {
                 i++;//count raws
             }

             int j = 0;
             fnTools.remove_table_data(client_detail_table_model,client_detail_table);
             rs1.beforeFirst();

             while (rs1.next()) {
                 String id = rs1.getString("id");
                 String A = rs1.getString("cust_name");
                 String D = rs1.getString("contact");
                 String E = rs1.getString("old_due");

                 client_detail_table_model.insertRow(j, new Object[]{id,A,"NA", D,E,"NA"});
                 
//                    tableshow.getColumnModel().getColumn(0).setHeaderValue("Madicin Name");

                 j++;

             }
         }//end if
     } catch (SQLException ex) {
         Logger.getLogger(clientRegPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        client_detail_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        txtContact = new javax.swing.JTextField();
        txtCompanyName = new javax.swing.JTextField();
        txtClientName = new javax.swing.JTextField();
        cmbCliebtType = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtOldDue = new javax.swing.JTextField();
        txtOldDue1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        client_detail_table.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        client_detail_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Company", "Client Name", "Contact No.", "Old Dues", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        client_detail_table.setRowHeight(24);
        client_detail_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                client_detail_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(client_detail_table);
        if (client_detail_table.getColumnModel().getColumnCount() > 0) {
            client_detail_table.getColumnModel().getColumn(0).setResizable(false);
            client_detail_table.getColumnModel().getColumn(0).setPreferredWidth(8);
            client_detail_table.getColumnModel().getColumn(1).setResizable(false);
            client_detail_table.getColumnModel().getColumn(1).setPreferredWidth(150);
            client_detail_table.getColumnModel().getColumn(2).setResizable(false);
            client_detail_table.getColumnModel().getColumn(2).setPreferredWidth(150);
            client_detail_table.getColumnModel().getColumn(3).setResizable(false);
            client_detail_table.getColumnModel().getColumn(3).setPreferredWidth(15);
            client_detail_table.getColumnModel().getColumn(4).setResizable(false);
            client_detail_table.getColumnModel().getColumn(4).setPreferredWidth(15);
            client_detail_table.getColumnModel().getColumn(5).setResizable(false);
            client_detail_table.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        jPanel1.setBackground(new java.awt.Color(72, 89, 253));

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 36)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Client Registration/Updation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(315, 315, 315))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(java.awt.Color.lightGray);

        jLabel8.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel8.setText("Address  :");

        btnSubmit.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        btnSubmit.setText("SUBMIT");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel5.setText("Contact No :");

        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel1.setText("Company Name :");

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel3.setText("Client Name :");

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Century Schoolbook L", 0, 16)); // NOI18N
        txtAddress.setRows(5);
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtAddress);

        txtContact.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContactActionPerformed(evt);
            }
        });
        txtContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContactKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContactKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContactKeyReleased(evt);
            }
        });

        txtCompanyName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtCompanyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompanyNameActionPerformed(evt);
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

        txtClientName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtClientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientNameActionPerformed(evt);
            }
        });
        txtClientName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClientNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientNameKeyReleased(evt);
            }
        });

        cmbCliebtType.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        cmbCliebtType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dealer", "Customer" }));
        cmbCliebtType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCliebtTypeKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel7.setText("Old due :");

        txtOldDue.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtOldDue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOldDueActionPerformed(evt);
            }
        });
        txtOldDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOldDueKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOldDueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOldDueKeyReleased(evt);
            }
        });

        txtOldDue1.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtOldDue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOldDue1ActionPerformed(evt);
            }
        });
        txtOldDue1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOldDue1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOldDue1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOldDue1KeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel9.setText("Confirm :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbCliebtType, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(405, 405, 405))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtClientName, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                .addComponent(txtContact))
                            .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOldDue, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(152, 152, 152))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtOldDue1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbCliebtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtOldDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtContact)
                        .addComponent(btnSubmit)
                        .addComponent(txtOldDue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel5, jLabel7, txtOldDue});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtClientName, txtCompanyName, txtContact});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1208, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCompanyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompanyNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyNameActionPerformed

    private void txtCompanyNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtCompanyName.getText().isEmpty())&&(!cmbCliebtType.getSelectedItem().toString().equals("Customer"))) {
            if(!fnTools.isEmpty(client_detail_table)){
            TableModel model2 = client_detail_table.getModel();
            String Name = model2.getValueAt(0, 1).toString();
            txtCompanyName.setText(Name);
            }
            txtClientName.requestFocus();
            
        }else if(((key == KeyEvent.VK_ENTER)&&(!txtCompanyName.getText().isEmpty())&&(cmbCliebtType.getSelectedItem().toString().equals("Customer")))){
            if(!fnTools.isEmpty(client_detail_table)){
            TableModel model2 = client_detail_table.getModel();
            String Name = model2.getValueAt(0, 1).toString();
            String nm = model2.getValueAt(0, 2).toString();
            txtClientName.setText(nm);
            txtClientName.setEditable(false);
            String con = model2.getValueAt(0, 3).toString();
            txtContact.setText(con);
            txtContact.setEditable(false);
            String old_due = model2.getValueAt(0, 4).toString();
            txtOldDue.setText(old_due);
            String add = model2.getValueAt(0, 5).toString();
            txtAddress.setText(add);
            txtAddress.setEditable(false);
            txtCompanyName.setText(Name);
            txtOldDue.requestFocus();
            }
            txtClientName.setText(txtCompanyName.getText());
            txtClientName.setEditable(false);
            txtContact.requestFocus();
        }else if(key==KeyEvent.VK_ESCAPE){
            cmbCliebtType.requestFocus();
        }
    }//GEN-LAST:event_txtCompanyNameKeyPressed

    private void txtCompanyNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyReleased
            String obj = "";
            obj = cmbCliebtType.getSelectedItem().toString();
            fnTools.remove_table_data(client_detail_table_model, client_detail_table);
            if("Dealer".equals(obj)){
                fill_client_detail_table();
            }else{
                fill_customer_detail_table();
            }
        
    }//GEN-LAST:event_txtCompanyNameKeyReleased

    private void txtContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactActionPerformed

    private void txtContactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyPressed
       int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtContact.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!txtContact.getText().isEmpty())&&(!cmbCliebtType.getSelectedItem().toString().equals("Customer"))) {
            txtAddress.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(!txtContact.getText().isEmpty())&&(cmbCliebtType.getSelectedItem().toString().equals("Customer"))) {
            txtOldDue.requestFocus();
        }
        else if ((key == KeyEvent.VK_ENTER)&&(txtContact.getText().isEmpty())&&(!cmbCliebtType.getSelectedItem().toString().equals("Customer"))) {
            TableModel model2 = client_detail_table.getModel();
            String con = model2.getValueAt(0, 3).toString();
            txtContact.setText(con);
            txtAddress.requestFocus();
        }else if(((key == KeyEvent.VK_ENTER)&&(txtContact.getText().isEmpty())&&(cmbCliebtType.getSelectedItem().toString().equals("Customer")))){
            if(!fnTools.isEmpty(client_detail_table)){
            TableModel model2 = client_detail_table.getModel();
            String Name = model2.getValueAt(0, 1).toString();
            String pan = model2.getValueAt(0, 3).toString();
            txtContact.setText(pan);
            txtContact.setEditable(false);
            }
            txtOldDue.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtContact.setText("");
        }
    }//GEN-LAST:event_txtContactKeyPressed

    private void txtContactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactKeyReleased

    private void txtOldDueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOldDueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOldDueActionPerformed

    private void txtOldDueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldDueKeyPressed
       int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9)||(key >= KeyEvent.VK_DECIMAL) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtOldDue.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!txtOldDue.getText().isEmpty())) {
            txtOldDue1.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtOldDue.getText().isEmpty())) {
            TableModel model2 = client_detail_table.getModel();
            String Name = model2.getValueAt(0, 4).toString();
            txtOldDue.setText(Name);
            txtOldDue1.requestFocus();
        }
        
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtOldDue.setText("0");
        }
    }//GEN-LAST:event_txtOldDueKeyPressed

    private void txtOldDueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldDueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOldDueKeyReleased

    private void txtClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientNameActionPerformed

    private void txtClientNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtClientName.getText().isEmpty())) {
            txtContact.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtClientName.getText().isEmpty())) {
            TableModel model2 = client_detail_table.getModel();
            String Name = model2.getValueAt(0, 2).toString();
            txtClientName.setText(Name);
            txtContact.requestFocus();
        }
        
            
    }//GEN-LAST:event_txtClientNameKeyPressed

    private void txtClientNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientNameKeyReleased

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        validate_and_save_form();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void client_detail_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_client_detail_tableMouseClicked
        
    }//GEN-LAST:event_client_detail_tableMouseClicked

    private void txtContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyTyped
       if ((txtContact.getText().length() >9)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtContactKeyTyped

    private void txtOldDueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldDueKeyTyped
       if ((txtOldDue.getText().length() >9)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtOldDueKeyTyped

    private void txtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtOldDue.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtAddress.getText().isEmpty())) {
            TableModel model2 = client_detail_table.getModel();
            String Name = model2.getValueAt(0, 5).toString();
            txtAddress.setText(Name);
            txtOldDue.selectAll();
            txtOldDue.requestFocus();
            
        }
    }//GEN-LAST:event_txtAddressKeyPressed

    private void cmbCliebtTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCliebtTypeKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtCompanyName.requestFocus();
        }
    }//GEN-LAST:event_cmbCliebtTypeKeyPressed

    private void txtOldDue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOldDue1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOldDue1ActionPerformed

    private void txtOldDue1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldDue1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOldDue1KeyTyped

    private void txtOldDue1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldDue1KeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9)||(key >= KeyEvent.VK_DECIMAL)  || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtOldDue1.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!txtOldDue1.getText().isEmpty())) {
            btnSubmit.requestFocus();
        }else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtOldDue1.setText("");
        }
    }//GEN-LAST:event_txtOldDue1KeyPressed

    private void txtOldDue1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldDue1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOldDue1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JTable client_detail_table;
    public javax.swing.JComboBox<String> cmbCliebtType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtClientName;
    public javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtOldDue;
    private javax.swing.JTextField txtOldDue1;
    // End of variables declaration//GEN-END:variables
}
