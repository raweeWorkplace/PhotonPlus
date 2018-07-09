/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Dao.DataBase_Connection;
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
import java.util.Date;
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
        protected Connection conInstance;
        protected Statement smtInstance,smtUsingDate;
        ResultSet rs,rs1, rsOpen,rsClose;
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
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        fnTools= new functionTools();
        ButtonGroup();
        dateFrom.setDate(Calendar.getInstance().getTime());
        dateTo.setDate(Calendar.getInstance().getTime());
        
    }
    
    private void ButtonGroup() {
        ButtonGroup sb = new ButtonGroup();
        sb.add(rbnDate);
        sb.add(rbnMonthly);   
    }


    
    private void fillTable(String selectedTable, String selectedName, DefaultTableModel tableModel,JTextField txtTotal,JTextField txtPaid){
         
        try{
          java.sql.Date dFrom = new java.sql.Date(dateFrom.getDate().getTime());
            java.sql.Date dTo = new java.sql.Date(dateTo.getDate().getTime());
            if(rbnMonthly.isSelected() ||rbnDate.isSelected()){
            if(rbnMonthly.isSelected()){
            
            queryUsingSelection = "select * from "+selectedTable+" where (date between '"+dFrom+"' And '"+dTo+"') and "+selectedName+" = '"+txtCompanyName.getText()+"'order by date";
            queryTotal ="Select sum(total), sum(paid) from "+selectedTable+" where (date between '"+dFrom+"' And '"+dTo+"')  and "+selectedName+" = '"+txtCompanyName.getText()+"'";
            //queryPaid ="Select sum(paid) from "+selectedTable+" where (date between '"+dFrom+"' And '"+dTo+"')  and "+selectedName+" = '"+txtCompanyName.getText()+"'";
            
        }
            else if(rbnDate.isSelected()){
            
            queryUsingSelection = "select * from "+selectedTable+" where date ='" + dFrom + "'  and "+selectedName+" = '"+txtCompanyName.getText()+"' order by date";
            queryTotal ="Select sum(total),sum(paid) from "+selectedTable+" where date ='" + dFrom + "'  and "+selectedName+" = '"+txtCompanyName.getText()+"'";
            //queryPaid ="Select sum(paid) from "+selectedTable+" where date ='" + dFrom + "'  and "+selectedName+" = '"+txtCompanyName.getText()+"'";
            
        }
            }
            else{
            //queryOpen ="Select sum(product_sales.amount) from product_sales, productBills where product_sales.BillNo = productBills.BillNo and productBills.date <'" + dFrom + "'";
            queryUsingSelection = "select * from "+selectedTable+"  where "+selectedName+" = '"+txtCompanyName.getText()+"' order by date";
            queryTotal ="Select sum(total),sum(paid) from "+selectedTable+"  where "+selectedName+" = '"+txtCompanyName.getText()+"'";
            txtTotal.setText("0.0");
            txtPaid.setText("0.0");
        }
            
            smtInstance = conInstance.createStatement();
            rsClose = smtInstance.executeQuery(queryTotal);
            while ( rsClose.next() ) //step through the result set
                    {
                        Double sum = rsClose.getDouble(1);
                        txtTotal.setText(new DecimalFormat("##.##").format(sum));
                        Double paid = rsClose.getDouble(2);
                        txtPaid.setText(new DecimalFormat("##.##").format(paid));
                        if(txtPaid.getText().isEmpty()){
                            txtPaid.setText("0.0"); 
                        }
                    }
            
            smtUsingDate = conInstance.createStatement();
            
            rs1 = smtUsingDate.executeQuery(queryUsingSelection);
            
            
            if (rs1 != null){
                    
                int i = 0;
                while ( rs1.next() ) //step through the result set
                    {
                        i++;//count raws
                    }
                    int j = 0;
                    rs1.beforeFirst();
                    while (rs1.next()) 
                    {
                        Date dbDate = rs1.getDate(2);
                        DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                        String todayDate =dateformat.format(dbDate);
                        String billId = rs1.getString(1);
                        String NAME = rs1.getString(3);
                        String total = rs1.getString("total");
                        String paid = rs1.getString("paid");
                        String due = rs1.getString("due");
//                        String rate = rs1.getString("product_sales.rate");
//                        Double amt = (Double.parseDouble(total));
//                        String amount = Double.toString(amt);

                        tableModel.insertRow(j,new Object[]{todayDate,billId,NAME,total,paid,due});
                        j++;
                    }
                }
              } catch (SQLException ex) {
             Logger.getLogger(clientReportPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
        finally
        {
             try {
                 rs1.close();
             } catch (SQLException ex) {
                 Logger.getLogger(clientReportPanel.class.getName()).log(Level.SEVERE, null, ex);
             }
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(java.awt.Color.lightGray);
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        rbnDate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
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

        salesTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
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
        txtTotalSales.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel2.setText("Total Sales :");

        dateFrom.setDateFormatString("dd-MM-yyyy");
        dateFrom.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        dateTo.setDateFormatString("dd-MM-yyyy");
        dateTo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        rbnMonthly.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        rbnMonthly.setText("Monthly");
        rbnMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMonthlyActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel4.setText("Total Recieved :");

        txtRecieved.setEditable(false);
        txtRecieved.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel8.setText("Credit Balance :");

        txtBalance.setEditable(false);
        txtBalance.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N

        jLabel12.setBackground(java.awt.Color.lightGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setText("Company Name :");

        txtCompanyName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRecieved, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rbnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)
                                .addComponent(rbnMonthly)
                                .addGap(68, 68, 68))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))))
                        .addGap(45, 45, 45))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateFrom, dateTo});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtBalance, txtRecieved, txtTotalSales});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbnDate)
                            .addComponent(rbnMonthly))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch))
                            .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRecieved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel8)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel4, jLabel8, txtTotalSales});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, txtCompanyName});

        jPanel1.setBackground(new java.awt.Color(72, 89, 253));
        jPanel1.setForeground(java.awt.Color.white);

        jLabel1.setBackground(new java.awt.Color(254, 58, 79));
        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Client Report");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(458, 458, 458)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        fillTable("bill_table","cust_name",salesTableModel,txtTotalSales,txtRecieved);
        calculate();
        
        txtCompanyName.setText("");
        txtCompanyName.requestFocus();

    }//GEN-LAST:event_btnSearchActionPerformed

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
            try {
                int index = salesTable.getSelectedRow();
                salesTableModel  =(DefaultTableModel)salesTable.getModel();
                String billId = salesTableModel.getValueAt(index, 1).toString();
                    
                        reportSql = "select * from bill_table as b, sales_table as s where b.bill_no= s.bill_no and b.bill_no = '"+billId+"'";
                            url7 = getClass().getResourceAsStream("/report/report.jrxml");
                        
                JasperDesign jd = JRXmlLoader.load(url7);
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(reportSql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, conInstance);
                JasperViewer.viewReport(jp,false);
                //JasperExportManager.exportReportToPdfFile(jp,"sample_report.pdf");
            } catch (JRException ex) {
                Logger.getLogger(clientReportPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rbnDate;
    private javax.swing.JRadioButton rbnMonthly;
    private javax.swing.JTable salesTable;
    private javax.swing.JTextField txtBalance;
    public javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtRecieved;
    private javax.swing.JTextField txtTotalSales;
    // End of variables declaration//GEN-END:variables
}
