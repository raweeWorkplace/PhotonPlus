/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import controller.functionTools;
import Dao.DataBase_Connection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
public class dailyReportPanel extends javax.swing.JPanel {
        protected Connection conInstance;
        protected Statement smtInstance,smtUsingDate;
        ResultSet rs,rs1, rsOpen,rsClose;
        protected String queryUsingSelection,queryOpen, queryClose,reportSql;
        protected String excelFilePath = null,billId= null;
        DataBase_Connection dao;
        InputStream url7;
        DefaultTableModel salesTableModel,purchaseTableModel;
        private final JTextField filename = new JTextField(), dir = new JTextField();
        functionTools fnTools;
       

    /**
     * Creates new form salesSummaryPanel
     */
    public dailyReportPanel() {
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
    
    public void excelPerformed() {
      JFileChooser c = new JFileChooser();
      // Demonstrate "Save" dialog:
      int rVal = c.showSaveDialog(dailyReportPanel.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
        filename.setText(c.getSelectedFile().getName());
        dir.setText(c.getCurrentDirectory().toString());
        
          excelFilePath = dir.getText()+"/"+filename.getText();
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {
        filename.setText("You pressed cancel");
        dir.setText("");
      }
    }
    
    private void ButtonGroup() {
        ButtonGroup sb = new ButtonGroup();
        sb.add(rbnDate);
        sb.add(rbnMonthly);   
    }


    
    private void fillTable(String selectedTable, DefaultTableModel tableModel,JTextField txt){
         
        try
        {
          
            
            java.sql.Date dFrom = new java.sql.Date(dateFrom.getDate().getTime());
            java.sql.Date dTo = new java.sql.Date(dateTo.getDate().getTime());
            if(rbnMonthly.isSelected() ||rbnDate.isSelected()){
            if(rbnMonthly.isSelected()){
            
            queryUsingSelection = "select * from "+selectedTable+" where (date between '"+dFrom+"' And '"+dTo+"')order by date";
            queryClose ="Select sum(total) from "+selectedTable+" where (date between '"+dFrom+"' And '"+dTo+"')";
            
        }
            else if(rbnDate.isSelected()){
            
            queryUsingSelection = "select * from "+selectedTable+" where date ='" + dFrom + "' order by date";
            queryClose ="Select sum(total) from "+selectedTable+" where date ='" + dFrom + "'";
            
        }
//            smtInstance = conInstance.createStatement();
//            rsOpen = smtInstance.executeQuery(queryClose);
//            while ( rsOpen.next() ) //step through the result set
//                    {
//                      Double sum = rsOpen.getDouble(1);
//                        txtTotalSales.setText(new DecimalFormat("##.##").format(sum));
//                        if(txtTotalSales.getText().isEmpty()){
//                            txtTotalSales.setText("0.0"); 
//                        }
//                    }
            }
            else{
            //queryOpen ="Select sum(product_sales.amount) from product_sales, productBills where product_sales.BillNo = productBills.BillNo and productBills.date <'" + dFrom + "'";
            queryUsingSelection = "select * from "+selectedTable+" order by date";
            queryClose ="Select sum(total) from "+selectedTable+"";
            txt.setText("0.0");    
        }
            
            smtInstance = conInstance.createStatement();
            rsClose = smtInstance.executeQuery(queryClose);
            while ( rsClose.next() ) //step through the result set
                    {
                        Double sum = rsClose.getDouble(1);
                        txt.setText(new DecimalFormat("##.##").format(sum));
                        if(txt.getText().isEmpty()){
                            txt.setText("0.0"); 
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
                        String total = rs1.getString(4);
                        //String rate = rs1.getString("product_sales.rate");
                        Double amt = (Double.parseDouble(total));
                        String amount = Double.toString(amt);

                        tableModel.insertRow(j,new Object[]{todayDate,billId,NAME,amount});
                        j++;
                    }
                }
              } catch (SQLException ex) {
             Logger.getLogger(dailyReportPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
        finally
        {
             try {
                 rs1.close();
             } catch (SQLException ex) {
                 Logger.getLogger(dailyReportPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        txtTotalPurchase = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        purchaseTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();

        setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(java.awt.Color.lightGray);
        jPanel3.setBorder(null);

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
                "Date", "Invoice", "Customer Name", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        }

        txtTotalSales.setEditable(false);
        txtTotalSales.setBackground(java.awt.Color.lightGray);
        txtTotalSales.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel2.setText("Total Sales :");

        dateFrom.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        dateTo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        rbnMonthly.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        rbnMonthly.setText("Monthly");
        rbnMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMonthlyActionPerformed(evt);
            }
        });

        txtTotalPurchase.setEditable(false);
        txtTotalPurchase.setBackground(java.awt.Color.lightGray);
        txtTotalPurchase.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N

        purchaseTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        purchaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Invoice", "Customer Name", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchaseTable.setFillsViewportHeight(true);
        purchaseTable.setGridColor(java.awt.Color.white);
        purchaseTable.setRowHeight(24);
        purchaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchaseTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(purchaseTable);
        if (purchaseTable.getColumnModel().getColumnCount() > 0) {
            purchaseTable.getColumnModel().getColumn(0).setResizable(false);
            purchaseTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            purchaseTable.getColumnModel().getColumn(1).setResizable(false);
            purchaseTable.getColumnModel().getColumn(1).setPreferredWidth(8);
            purchaseTable.getColumnModel().getColumn(2).setResizable(false);
            purchaseTable.getColumnModel().getColumn(2).setPreferredWidth(125);
            purchaseTable.getColumnModel().getColumn(3).setResizable(false);
            purchaseTable.getColumnModel().getColumn(3).setPreferredWidth(8);
        }

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel3.setText("Total Purchase :");

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel4.setText("Balance :");

        txtBalance.setEditable(false);
        txtBalance.setBackground(java.awt.Color.lightGray);
        txtBalance.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(rbnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rbnMonthly))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)))
                .addGap(22, 22, 22))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateFrom, dateTo});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtBalance, txtTotalPurchase, txtTotalSales});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbnDate)
                    .addComponent(rbnMonthly))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, txtTotalPurchase, txtTotalSales});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, txtBalance});

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
        
        salesTableModel= (DefaultTableModel)salesTable.getModel();
        fnTools.remove_table_data(salesTableModel, salesTable);
        purchaseTableModel = (DefaultTableModel)purchaseTable.getModel();
        fnTools.remove_table_data(purchaseTableModel, purchaseTable);
        fillTable("bill_table",salesTableModel,txtTotalSales);
        fillTable("instock_entry_table",purchaseTableModel,txtTotalPurchase);
        if((!txtTotalSales.getText().isEmpty()) && (!txtTotalPurchase.getText().isEmpty())){
            double due = Double.parseDouble(txtTotalSales.getText())- Double.parseDouble(txtTotalPurchase.getText());
            txtBalance.setText(new DecimalFormat("##.##").format(due));
            
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
            try {
                int index = salesTable.getSelectedRow();
                salesTableModel  =(DefaultTableModel)salesTable.getModel();
                String billId = salesTableModel.getValueAt(index, 1).toString();
                    
                        reportSql = "select * from bill_table as b, sales_table as s where s.bill_no = b.bill_no and s.bill_no ='"+billId+"'";  
                            url7 = getClass().getResourceAsStream("/report/report.jrxml");
                        
                JasperDesign jd = JRXmlLoader.load(url7);
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(reportSql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, conInstance);
                JasperViewer.viewReport(jp,false);
                //JasperPrintManager.printReport(jp, true);
                //JasperExportManager.exportReportToPdfFile(jp,"sample_report.pdf");
            } catch (JRException ex) {
                Logger.getLogger(dailyReportPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_salesTableMouseClicked

    private void rbnMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnMonthlyActionPerformed
        dateTo.setEnabled(true);
    }//GEN-LAST:event_rbnMonthlyActionPerformed

    private void purchaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_purchaseTableMouseClicked
public void exportTable(JTable table, File file) throws IOException {
            TableModel model2 = table.getModel();
            
        try (FileWriter out = new FileWriter(file)) {
            
            for(int i=0; i < model2.getColumnCount(); i++) {
                out.write(model2.getColumnName(i) + "\t");
                
            }
            out.write("\n");

            for(int i=0; i< model2.getRowCount(); i++) {
                for(int j=0; j < model2.getColumnCount(); j++) {
                    out.write(model2.getValueAt(i,j).toString()+"\t");
                }
                out.write("\n");
            }
        }
        
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSearch;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable purchaseTable;
    private javax.swing.JRadioButton rbnDate;
    private javax.swing.JRadioButton rbnMonthly;
    private javax.swing.JTable salesTable;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtTotalPurchase;
    private javax.swing.JTextField txtTotalSales;
    // End of variables declaration//GEN-END:variables
}
