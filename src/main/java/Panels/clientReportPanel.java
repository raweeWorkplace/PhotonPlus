/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import controller.functionTools;
import Dao.DataBase_Connection;
import beans.billingPojo;
import controller.billing_controller;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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
public class clientReportPanel extends javax.swing.JPanel {

        protected String queryUsingSelection,queryPaid, queryTotal,reportSql;
        protected String excelFilePath = null,billId= null;
        DataBase_Connection dao;
        InputStream url7;
        DefaultTableModel salesTableModel,purchaseTableModel,companyTableModel;
        private final JTextField filename = new JTextField(), dir = new JTextField();
        functionTools fnTools;
       

    /**
     * Creates new form salesSummaryPanel
     */
    public clientReportPanel() {
        initComponents();
        dir.setEditable(false);
        filename.setEditable(false);
        fnTools= new functionTools();
        ButtonGroup();
        dateFrom.setDate(Calendar.getInstance().getTime());
        dateTo.setDate(Calendar.getInstance().getTime());
        companyTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 16));
        salesTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 16));
    }
    
    private void ButtonGroup() {
        ButtonGroup sb = new ButtonGroup();
        sb.add(rbnDate);
        sb.add(rbnMonthly);   
    }


    
    private void fillTable(){
         
             java.sql.Date dFrom = new java.sql.Date(dateFrom.getDate().getTime());
            java.sql.Date dTo = new java.sql.Date(dateTo.getDate().getTime());
            if(rbnMonthly.isSelected() ||rbnDate.isSelected()){
            if(rbnMonthly.isSelected()){
            
            queryUsingSelection = "from billingPojo where (date between '"+dFrom+"' And '"+dTo+"') and cust_name = '"+txtCompanyName.getText()+"'order by date";
            queryTotal ="Select sum(total) as total from billingPojo where (date between '"+dFrom+"' And '"+dTo+"')  and cust_name = '"+txtCompanyName.getText()+"'";
           queryPaid ="Select sum(paid) as paid from billingPojo where (date between '"+dFrom+"' And '"+dTo+"')  and cust_name = '"+txtCompanyName.getText()+"'";
           
        }
            else if(rbnDate.isSelected()){
            
            queryUsingSelection = "from billingPojo where date ='" + dFrom + "'  and cust_name = '"+txtCompanyName.getText()+"' order by date";
            queryTotal ="Select sum(total) as total from billingPojo where date ='" + dFrom + "'  and cust_name = '"+txtCompanyName.getText()+"'";
            queryPaid ="Select sum(paid) as paid from billingPojo where date ='" + dFrom + "'  and cust_name = '"+txtCompanyName.getText()+"'";
           
        }
            }
            else{ 
                queryUsingSelection = "from billingPojo  where cust_name = '"+txtCompanyName.getText()+"' order by date";
                queryTotal ="Select sum(total) as total from billingPojo  where cust_name = '"+txtCompanyName.getText()+"'";
                queryPaid ="Select sum(paid) as paid from billingPojo  where cust_name = '"+txtCompanyName.getText()+"'";
                
                txtTotalSales.setText("0.0");
                txtRecieved.setText("0.0");
        }
            
                String value =  fnTools.getData(queryTotal);
                Double sum = Double.parseDouble(value);
                txtTotalSales.setText(new DecimalFormat("##.##").format(sum));
                String paid_value =  fnTools.getData(queryPaid);
                Double paid = Double.parseDouble(paid_value);
                 txtRecieved.setText(new DecimalFormat("##.##").format(paid));
                 if(txtRecieved.getText().isEmpty()){
                     txtRecieved.setText("0.0"); 

            }

            
            List<billingPojo> pojo = fnTools.getAllData(queryUsingSelection);
            if(pojo!=null){
                 int j=0;
                for(billingPojo rs:pojo){
                    Date dbDate = rs.getDate();
                    DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                    String todayDate =dateformat.format(dbDate);
                    int billId = rs.getBill_no();
                    String NAME = rs.getCust_name();
                    Double total = rs.getTotal();
                    Double pay = rs.getPaid();
    //                        String rate = rs1.getString("product_sales.rate");
    //                        Double amt = (Double.parseDouble(total));
    //                        String amount = Double.toString(amt);

                    salesTableModel.insertRow(j,new Object[]{todayDate,billId,NAME,total,pay});
                    j++;
            }
            }
        }
    
    private void fill_company_name_table(){
       companyTableModel  =(DefaultTableModel)companyTable.getModel();
       String values = txtCompanyName.getText();
       String  sql1= "FROM clientPojo where company_name Like '"+values+"%'";
       
       new billing_controller().fill_company_name(companyTable, sql1);
            
              
}
    
    private void calculate(){
        if((!txtTotalSales.getText().isEmpty()) && (!txtRecieved.getText().isEmpty())){
            double due = Double.parseDouble(txtRecieved.getText())- Double.parseDouble(txtTotalSales.getText());
            txtBalance.setText(new DecimalFormat("##.##").format(due));
            
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

        jPanel3 = new javax.swing.JPanel();
        rbnDate = new javax.swing.JRadioButton();
        btnSearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        salesTable = new javax.swing.JTable();
        txtTotalSales = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        dateTo = new com.toedter.calendar.JDateChooser();
        rbnMonthly = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtRecieved = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        companyTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(java.awt.Color.darkGray);
        jPanel3.setBorder(null);
        jPanel3.setForeground(java.awt.Color.white);

        rbnDate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        rbnDate.setForeground(java.awt.Color.white);
        rbnDate.setText("Daily");
        rbnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnDateActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        salesTable.setBackground(java.awt.Color.darkGray);
        salesTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        salesTable.setForeground(java.awt.Color.white);
        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Invoice", "Customer Name", "Amount", "Adv. Paid", "Due"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        salesTable.setFillsViewportHeight(true);
        salesTable.setGridColor(java.awt.Color.white);
        salesTable.setRowHeight(24);
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(salesTable);
        if (salesTable.getColumnModel().getColumnCount() > 0) {
            salesTable.getColumnModel().getColumn(0).setResizable(false);
            salesTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            salesTable.getColumnModel().getColumn(1).setResizable(false);
            salesTable.getColumnModel().getColumn(1).setPreferredWidth(8);
            salesTable.getColumnModel().getColumn(2).setResizable(false);
            salesTable.getColumnModel().getColumn(2).setPreferredWidth(125);
            salesTable.getColumnModel().getColumn(3).setResizable(false);
            salesTable.getColumnModel().getColumn(3).setPreferredWidth(8);
            salesTable.getColumnModel().getColumn(4).setResizable(false);
            salesTable.getColumnModel().getColumn(4).setPreferredWidth(8);
            salesTable.getColumnModel().getColumn(5).setResizable(false);
            salesTable.getColumnModel().getColumn(5).setPreferredWidth(8);
        }

        txtTotalSales.setEditable(false);
        txtTotalSales.setBackground(java.awt.Color.darkGray);
        txtTotalSales.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtTotalSales.setForeground(java.awt.Color.white);

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Sales :");

        dateFrom.setBackground(java.awt.Color.white);
        dateFrom.setDateFormatString("dd-MM-yyyy");
        dateFrom.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        dateTo.setBackground(java.awt.Color.white);
        dateTo.setDateFormatString("dd-MM-yyyy");
        dateTo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        rbnMonthly.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        rbnMonthly.setForeground(java.awt.Color.white);
        rbnMonthly.setText("Monthly");
        rbnMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMonthlyActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Recieved :");

        txtRecieved.setEditable(false);
        txtRecieved.setBackground(java.awt.Color.darkGray);
        txtRecieved.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtRecieved.setForeground(java.awt.Color.white);

        jLabel8.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("Balance :");

        txtBalance.setEditable(false);
        txtBalance.setBackground(java.awt.Color.darkGray);
        txtBalance.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtBalance.setForeground(java.awt.Color.white);

        jLabel12.setBackground(java.awt.Color.lightGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("Company  :");

        txtCompanyName.setBackground(java.awt.Color.darkGray);
        txtCompanyName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtCompanyName.setForeground(java.awt.Color.white);
        txtCompanyName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyReleased(evt);
            }
        });

        companyTable.setBackground(java.awt.Color.darkGray);
        companyTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        companyTable.setForeground(java.awt.Color.white);
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
        if (companyTable.getColumnModel().getColumnCount() > 0) {
            companyTable.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRecieved, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rbnMonthly)
                                            .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbnDate)
                            .addComponent(rbnMonthly))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRecieved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel4, jLabel8, txtTotalSales});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, txtCompanyName});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnDateActionPerformed
        if(rbnDate.isSelected()){
            dateTo.setEnabled(false);
            
        }
    }//GEN-LAST:event_rbnDateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        txtBalance.setText("0.0");
        salesTableModel= (DefaultTableModel)salesTable.getModel();
        fnTools.remove_table_data(salesTableModel, salesTable);
        fillTable();
        calculate();
        
        txtCompanyName.setText("");
        txtCompanyName.requestFocus();

    }//GEN-LAST:event_btnSearchActionPerformed

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
        int index = salesTable.getSelectedRow();
        salesTableModel  =(DefaultTableModel)salesTable.getModel();
        int billId = Integer.parseInt(salesTableModel.getValueAt(index, 1).toString());
        new billing_controller().ireport(billId);
    }//GEN-LAST:event_salesTableMouseClicked

    private void rbnMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnMonthlyActionPerformed
        dateTo.setEnabled(true);
    }//GEN-LAST:event_rbnMonthlyActionPerformed

    private void txtCompanyNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtCompanyName.getText().isEmpty())) {
         
        companyTableModel  =(DefaultTableModel)companyTable.getModel();
        String Name = companyTableModel.getValueAt(0, 0).toString();
        txtCompanyName.setText(Name);
            btnSearch.requestFocus();
        }else if ((key == KeyEvent.VK_DOWN)) {
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
    if(!fnTools.isEmpty(companyTable)){
            int row_index = companyTable.getSelectedRow();
            String value = companyTable.getModel().getValueAt(row_index,0).toString();
            txtCompanyName.setText(value);
            btnSearch.requestFocus();
    }
    }//GEN-LAST:event_companyTableMouseClicked

    private void companyTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_companyTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = companyTable.getSelectedRow();
            String value = companyTable.getModel().getValueAt(row_index,0).toString();
            txtCompanyName.setText(value);
            btnSearch.requestFocus();
        }
    }//GEN-LAST:event_companyTableKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSearch;
    private javax.swing.JTable companyTable;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton rbnDate;
    private javax.swing.JRadioButton rbnMonthly;
    private javax.swing.JTable salesTable;
    private javax.swing.JTextField txtBalance;
    public javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtRecieved;
    private javax.swing.JTextField txtTotalSales;
    // End of variables declaration//GEN-END:variables
}
