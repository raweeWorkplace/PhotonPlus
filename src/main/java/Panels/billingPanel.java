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
import java.sql.PreparedStatement;
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
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ranjan
 */
public class billingPanel extends javax.swing.JPanel {
    
    protected Connection conInstance;
    protected Statement smtInstance;
    ResultSet rs,rs1, rs2;
    protected String  customer="",rate="",item_code="0",BillId="", sql="",display="",values="", client_id="";
    DataBase_Connection dao;
    DefaultTableModel billTableModel, clientTableModel, size_table_model;
    functionTools fnTools;
    InputStream url7;
    private int billTableIndex = 0;
    

    /**
     * Creates new form pharmacySalesPanel
     */
    public billingPanel() {
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        fnTools = new functionTools();
        lblPaid.setVisible(false);
        lblPaidlbl.setVisible(false);
        
                        
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
           
    private void resetBill(){
        lblSubTotal.setText("");
        txtContact.setText("");
        txtDiscRate.setText("0");
        lblDue.setText("");
        lblBalance.setText("");
        lblTotal.setText("");
        txtPayment.setText("0");
        txtDiscAmt.setText("0");
        txtCustName.setText("");
        lblPrevDue.setText("0");
        lblTotalDue.setText("");
        lblPaid.setVisible(false);
        lblPaid.setText("0");
        lblPaidlbl.setVisible(false);
    }
    
    private void resetTables(){
        fnTools.remove_table_data(billTableModel, billTable);
        fnTools.remove_table_data(clientTableModel, clientTable);
        fnTools.remove_table_data((DefaultTableModel)sizeTable.getModel(), sizeTable);
    }
    
   private void resetSelection() {

        txtsize.setText("");
        txtQty.setText("");
        txtRate.setText("");
        txtCost.setText("");
        
    }
    
    private void ireport() throws SQLException{
        try {
            String maxId = "Select max(bill_no) from bill_table";
            smtInstance = conInstance.createStatement();
            ResultSet max = smtInstance.executeQuery(maxId);
            while(max.next()){
                BillId = max.getString(1);
            }
                        String reportSql ="select * from bill_table as b, sales_table as s where b.bill_no= s.bill_no and s.bill_no = '"+BillId+"'";   
                         //System.out.println(reportSql);   
                            url7 = getClass().getResourceAsStream("/report/report.jrxml");
                        
                JasperDesign jd = JRXmlLoader.load(url7);
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(reportSql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, conInstance);
                JasperViewer.viewReport(jp,false);
                int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to print Bill?","Warning",JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JasperPrintManager.printReport(jp, true);
                }
                
        } catch (JRException ex) {
            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void calculateSubtotal(){
        int i=0;
        double  subTotal = 0;
        if(billTable.getRowCount()!=0){
        while(i<billTable.getRowCount())
        {
        subTotal = subTotal+Double.parseDouble((String) billTable.getValueAt(i, 4));
        lblSubTotal.setText(new DecimalFormat("##.##").format(subTotal));
        i++;
        }
        }
        else{
             lblSubTotal.setText("");
        }
    }
    
    private void calculateTotal(){
        double total = 0, totaldue =0;
        if((!lblSubTotal.getText().isEmpty())&&(!txtDiscAmt.getText().isEmpty())){
            total = Double.parseDouble(lblSubTotal.getText())-Double.parseDouble(txtDiscAmt.getText());
            lblTotal.setText((new DecimalFormat("##.##").format(total)));
            totaldue = Double.parseDouble(lblTotal.getText())+Double.parseDouble(lblPrevDue.getText())- Double.parseDouble(lblPaid.getText());
            lblTotalDue.setText((new DecimalFormat("##.##").format(totaldue)));
            }
    }
    
    private void add_to_bill_table() {
        String item_name = txtsize.getText();
        String quan = txtQty.getText();
        String rate = txtRate.getText();
        String cost = txtCost.getText();
        
        if(!txtCost.getText().isEmpty()){
            billTableModel = (DefaultTableModel) billTable.getModel();
            billTableIndex=0;
            billTableModel.insertRow(billTableIndex, new Object[]{item_code,item_name, quan, rate, cost});
            billTableIndex++;
            resetSelection();
            JOptionPane.showMessageDialog(null, "Item Entered");
            calculateSubtotal();
        }else {
            JOptionPane.showMessageDialog(null, "Cost can't be Null. Re-Enter details");
        }
        
    }
   
    private void billDetails(){
        try {
            
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String todayDate =dateformat.format(cal.getTime());
            customer = txtCustName.getText();
            double payment=0;
            if(Double.parseDouble(lblTotalDue.getText())>Double.parseDouble(txtPayment.getText())){
                payment = Double.parseDouble(txtPayment.getText());
            }else {
                payment = Double.parseDouble(lblTotalDue.getText());
            }
            String enterBills = "insert into bill_table(date,cust_name, contact,total,paid,due,disc,old_due) values ('"+todayDate+"','"+customer+"','"+txtContact.getText()+"','"+lblTotal.getText()+"','"+payment+"','"+lblDue.getText()+"','"+txtDiscAmt.getText()+"','"+lblPrevDue.getText()+"')";
            smtInstance = conInstance.createStatement();
            int result = smtInstance.executeUpdate(enterBills);
            if(result!=0){
                updateDue();
                resetBill();
                fill_order();
                JOptionPane.showMessageDialog(null, "Detailed Entered");
                txtCustName.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void fill_order(){
        try{
            String maxId = "Select max(bill_no) from bill_table";
            smtInstance = conInstance.createStatement();
            ResultSet max = smtInstance.executeQuery(maxId);
            while(max.next()){
                BillId = max.getString(1);
            }
            int rows=billTable.getRowCount();
            conInstance.setAutoCommit(false);
            
            String queryco = "insert into sales_table (bill_no,item_code, item_name, qty,cost) values (?,?,?,?,?)";
            PreparedStatement pst = conInstance.prepareStatement(queryco);
                for(int row = 0; row<rows; row++){
            String proCode = (String)billTable.getValueAt(row, 0);
            String medName = (String)billTable.getValueAt(row, 1);
            String quan = (String)billTable.getValueAt(row,2);
            String cost = (String)billTable.getValueAt(row,4);
            
                pst.setString(1, BillId);
                pst.setString(2, proCode);
                pst.setString(3, medName);
                pst.setString(4, quan);
                pst.setString(5, cost);
                pst.addBatch();
                }
            pst.executeBatch();
            conInstance.commit();
            resetBill();
            billTableIndex=0;
            ireport();
            resetTables();
            
        }   catch (SQLException ex) {
                Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void getData(){
        String sql = "(select id, old_due, contact from client_detail_table where company_name ='"+txtCustName.getText()+"') union (select id, old_due, contact from special_customer_table where cust_name = '"+txtCustName.getText()+"')";
        try {
            smtInstance = conInstance.createStatement();
            ResultSet rs3 = smtInstance.executeQuery(sql);
            int i = 0;
            if(rs3.next()){
                i++;
                
            }
            if(i==1){
                client_id = rs3.getString("id");
                txtContact.setText(rs3.getString("contact"));
                lblPrevDue.setText(rs3.getString("old_due"));
            }else if(i==0){
                lblPrevDue.setText("0");
            }
            
                    
        } catch (SQLException ex) {
            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void fillCustTable(){
       clientTableModel  =(DefaultTableModel)clientTable.getModel();
       String values = txtCustName.getText();
        try {
               //     
            String  sql1= "(select distinct company_name from client_detail_table where company_name like '"+values+"%') union (select distinct cust_name from special_customer_table where cust_name like '"+values+"%')";
            smtInstance= conInstance.createStatement();
            ResultSet rs1 = smtInstance.executeQuery(sql1);
                fnTools.remove_table_data(clientTableModel,clientTable);
                int i = 0;
                while ( rs1.next() ) //step through the result set
                {
                    i++;//count raws
                }
                if (i>0){
                        int j= 0;
                        rs1.beforeFirst();
                    while (rs1.next()) {
                    String company_name = rs1.getString("company_name");
                    clientTableModel.insertRow(j,new Object[]{company_name});
                    j++;
                    
                }
                }
        } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
              
}
    
    private void fillTable(String queryUsingSelection){
         billTableModel= (DefaultTableModel)billTable.getModel();
        try
        {
                Statement smtUsingDate = conInstance.createStatement();
                rs1 = (ResultSet) smtUsingDate.executeQuery(queryUsingSelection);
                
                if (rs1 != null)
                {
                    
                    int i = 0;
                    while ( rs1.next() ) //step through the result set
                    {
                        i++;//count raws
                    }
                    int j = 0;
                    rs1.beforeFirst();
                    
                    fnTools.remove_table_data(billTableModel, billTable);
                    while (rs1.next()) 
                    {
                        item_code = rs1.getString("item_code");
                        String item_name = rs1.getString("item_name");
                        String qty = rs1.getString("qty");
                        String rate = "NA";
                        String cost = rs1.getString("cost");
                        

                        billTableModel.insertRow(j,new Object[]{item_code,item_name,qty,rate,cost});
                        j++;
                    }
                }
              } catch (SQLException ex) {
             Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    private void updateDue(){
        try {
            String sql = "update client_detail_table set old_due = '"+lblDue.getText()+"' where company_name ='"+txtCustName.getText()+"' and contact = '"+txtContact.getText()+"'";
            smtInstance = conInstance.createStatement();
            int r = smtInstance.executeUpdate(sql);
            int i = 0;
            if(r==1){
                i++;
            }
            else if(i==0){
            String sql1 = "update special_customer_table set old_due = '"+lblDue.getText()+"' where cust_name ='"+txtCustName.getText()+"' and contact = '"+txtContact.getText()+"'";
            smtInstance = conInstance.createStatement();
            smtInstance.executeUpdate(sql1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    private void updateBill(){
        try {
            String query3 = "Update bill_table set paid = '"+(Double.parseDouble(lblPaid.getText())+Double.parseDouble(txtPayment.getText()))+"', due = '"+(Double.parseDouble(lblTotalDue.getText())-Double.parseDouble(txtPayment.getText()))+"', disc='"+txtDiscAmt.getText()+"' where bill_no = '"+txtBillId.getText()+"'";
            smtInstance = conInstance.createStatement();
            int r = smtInstance.executeUpdate(query3);
            if(r==1){
                updateDue();
                JOptionPane.showMessageDialog(null, "Bill Updated");
                resetBill();
                resetTables();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtCost = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        txtCustName = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtsize = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        sizeTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        txtRate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBillId = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        lblSubTotal = new javax.swing.JLabel();
        lblBalance = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblDue = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtDiscRate = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtDiscAmt = new javax.swing.JTextField();
        txtPayment = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblPrevDue = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblTotalDue = new javax.swing.JLabel();
        lblPaidlbl = new javax.swing.JLabel();
        lblPaid = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();

        setBackground(java.awt.Color.gray);

        jPanel2.setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(java.awt.Color.lightGray);

        txtCost.setEditable(false);
        txtCost.setBackground(java.awt.Color.lightGray);
        txtCost.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCostFocusGained(evt);
            }
        });
        txtCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel11.setText("Rate  :");

        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel1.setText("Name :");

        jLabel6.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel6.setText("Quan :");

        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setText("Cost  :");

        clientTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        clientTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        clientTable.setDropMode(javax.swing.DropMode.ON);
        clientTable.setRowHeight(24);
        clientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientTableMouseClicked(evt);
            }
        });
        clientTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clientTableKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(clientTable);
        if (clientTable.getColumnModel().getColumnCount() > 0) {
            clientTable.getColumnModel().getColumn(0).setResizable(false);
        }

        txtCustName.setBackground(java.awt.Color.lightGray);
        txtCustName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtCustName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustNameKeyReleased(evt);
            }
        });

        btnAdd.setBackground(java.awt.Color.lightGray);
        btnAdd.setFont(new java.awt.Font("Century Schoolbook L", 1, 26)); // NOI18N
        btnAdd.setText("Add ");
        btnAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel13.setBackground(java.awt.Color.lightGray);
        jLabel13.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel13.setText("Item :");

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
        jScrollPane3.setViewportView(sizeTable);

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel5.setText("Cont. No. :");

        txtContact.setBackground(java.awt.Color.lightGray);
        txtContact.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtContact.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContactFocusGained(evt);
            }
        });
        txtContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContactKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContactKeyPressed(evt);
            }
        });

        txtQty.setBackground(java.awt.Color.lightGray);
        txtQty.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        txtRate.setBackground(java.awt.Color.lightGray);
        txtRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRateKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel4.setForeground(java.awt.Color.black);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/icons8-search-25.png"))); // NOI18N
        jLabel4.setText("Bill Id ");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtBillId.setBackground(java.awt.Color.lightGray);
        txtBillId.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        txtBillId.setForeground(java.awt.Color.black);
        txtBillId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBillIdKeyPressed(evt);
            }
        });

        jPanel4.setBackground(java.awt.Color.lightGray);

        cancelButton.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        lblSubTotal.setBackground(java.awt.Color.white);
        lblSubTotal.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblSubTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lblBalance.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblBalance.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblBalance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblBalanceKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblBalanceKeyReleased(evt);
            }
        });

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel21.setText("Disc. % :");

        jLabel26.setBackground(java.awt.Color.white);
        jLabel26.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel26.setText("Due :");

        lblDue.setBackground(java.awt.Color.white);
        lblDue.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblDue.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblDueKeyTyped(evt);
            }
        });

        btnPrint.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        btnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPrintKeyPressed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel29.setText("Refund :");

        txtDiscRate.setBackground(java.awt.Color.lightGray);
        txtDiscRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
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

        jLabel27.setBackground(java.awt.Color.white);
        jLabel27.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel27.setText("Payment :");

        txtDiscAmt.setBackground(java.awt.Color.lightGray);
        txtDiscAmt.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
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

        txtPayment.setBackground(java.awt.Color.lightGray);
        txtPayment.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        txtPayment.setText("0");
        txtPayment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaymentFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaymentFocusLost(evt);
            }
        });
        txtPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaymentKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaymentKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaymentKeyReleased(evt);
            }
        });

        jLabel23.setBackground(java.awt.Color.white);
        jLabel23.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel23.setText("Disc.Rs. :");

        jLabel24.setBackground(java.awt.Color.white);
        jLabel24.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel24.setText("Sub total :");

        jLabel28.setBackground(java.awt.Color.white);
        jLabel28.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel28.setText("Old Due :");

        lblPrevDue.setBackground(java.awt.Color.white);
        lblPrevDue.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblPrevDue.setText("0");
        lblPrevDue.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblPrevDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblPrevDueKeyTyped(evt);
            }
        });

        jLabel30.setBackground(java.awt.Color.white);
        jLabel30.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel30.setText("Total Due :");

        lblTotalDue.setBackground(java.awt.Color.white);
        lblTotalDue.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblTotalDue.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblTotalDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblTotalDueKeyTyped(evt);
            }
        });

        lblPaidlbl.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblPaidlbl.setText("Paid :");

        lblPaid.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblPaid.setText("0");

        jLabel22.setBackground(java.awt.Color.white);
        jLabel22.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel22.setText("Total :");

        lblTotal.setBackground(java.awt.Color.white);
        lblTotal.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jSeparator2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(lblPrevDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(lblPaidlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiscRate, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(lblPaid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotalDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDiscAmt, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(txtPayment))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblSubTotal, lblTotal, txtDiscRate});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel24, jLabel28, jLabel29});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel21, jLabel26, lblPaidlbl});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel22, jLabel27});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPrevDue, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiscRate, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiscAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTotalDue, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel27)
                                                .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblDue, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(lblPaid))
                                            .addComponent(lblPaidlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblDue, lblPaid, lblSubTotal, txtDiscRate});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel21, jLabel24, jLabel26, jLabel28, lblPaidlbl});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel22, jLabel23, jLabel27, jLabel29, jLabel30, lblBalance, lblTotalDue, txtDiscAmt, txtPayment});

        billTable.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Item", "Qty", "Rate", "Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        billTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(billTable);
        if (billTable.getColumnModel().getColumnCount() > 0) {
            billTable.getColumnModel().getColumn(0).setResizable(false);
            billTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            billTable.getColumnModel().getColumn(1).setResizable(false);
            billTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            billTable.getColumnModel().getColumn(2).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(3).setResizable(false);
            billTable.getColumnModel().getColumn(3).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(4).setPreferredWidth(8);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtContact))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtQty))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtCost, txtQty, txtRate});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAdd))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, txtCost, txtCustName, txtQty, txtRate});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, jLabel5, txtContact, txtsize});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel12, jLabel6});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, txtBillId});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostFocusGained

    }//GEN-LAST:event_txtCostFocusGained

    private void txtCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyTyped
        float cost =0;
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
              cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
                txtCost.setText(new DecimalFormat("##.##").format(cost));
                
            }
    }//GEN-LAST:event_txtCostKeyTyped

    private void billTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billTableMouseClicked
                int index = billTable.getSelectedRow();
                TableModel model2 = billTable.getModel();
                DefaultTableModel del = (DefaultTableModel) billTable.getModel();
                del.removeRow(index);
                billTableIndex--;
                calculateSubtotal();
          
    }//GEN-LAST:event_billTableMouseClicked

    private void lblDueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblDueKeyTyped
        float due=0;
        if ((!"".equals(lblSubTotal.getText())) && (!"".equals(txtDiscRate.getText()))) {
            if (Float.parseFloat(lblSubTotal.getText()) > Float.parseFloat(txtDiscRate.getText())) {
                due = Float.parseFloat(lblSubTotal.getText()) - Float.parseFloat(txtDiscRate.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText(new DecimalFormat("##.##").format(due));
                lblBalance.setText("0.0");
            } else {
                due = Float.parseFloat(txtDiscRate.getText()) - Float.parseFloat(lblSubTotal.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText("0.0");
                lblBalance.setText(new DecimalFormat("##.##").format(due));
            }
        }
    }//GEN-LAST:event_lblDueKeyTyped

    private void lblBalanceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblBalanceKeyTyped
      float due=0;
        if ((!"".equals(lblSubTotal.getText())) && (!"".equals(txtDiscRate.getText()))) {
            if (Float.parseFloat(lblSubTotal.getText()) > Float.parseFloat(txtDiscRate.getText())) {
                due = Float.parseFloat(lblSubTotal.getText()) - Float.parseFloat(txtDiscRate.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText(new DecimalFormat("##.##").format(due));
                lblBalance.setText("0.0");
            } else {
                due = Float.parseFloat(txtDiscRate.getText()) - Float.parseFloat(lblSubTotal.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText("0.0");
                lblBalance.setText(new DecimalFormat("##.##").format(due));
            }
        }
    }//GEN-LAST:event_lblBalanceKeyTyped

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
       resetBill();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add_to_bill_table();
        resetSelection();
        txtsize.requestFocus();
    }//GEN-LAST:event_btnAddActionPerformed

    private void lblBalanceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblBalanceKeyReleased
       float due=0;
       if ((!"".equals(lblTotal.getText())) && (!"".equals(txtPayment.getText()))) {
            if (Float.parseFloat(lblTotal.getText()) > Float.parseFloat(txtPayment.getText())) {
                due = Float.parseFloat(lblTotal.getText()) - Float.parseFloat(txtPayment.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText(new DecimalFormat("##.##").format(due));
                lblBalance.setText("0.0");
            } else {
                due = Float.parseFloat(txtPayment.getText()) - Float.parseFloat(lblTotal.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText("0.0");
                lblBalance.setText(new DecimalFormat("##.##").format(due));
            }
        }
    }//GEN-LAST:event_lblBalanceKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
         txtCustName.requestFocus();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtDiscRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscRateKeyReleased
        
    }//GEN-LAST:event_txtDiscRateKeyReleased

    private void txtDiscRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscRateKeyTyped

    }//GEN-LAST:event_txtDiscRateKeyTyped

    private void txtDiscRateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscRateFocusLost
        //jButton2FocusGained(evt);
    }//GEN-LAST:event_txtDiscRateFocusLost

    private void txtDiscRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscRateKeyPressed
       int key = evt.getKeyCode();
       double total = 0;
       char car = evt.getKeyChar();
        if((car < '0'|| car > '9') && (car!='.') && (car!=(char)KeyEvent.VK_BACK_SPACE)&& (car!=(char)KeyEvent.VK_ENTER)){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Invalid Insert");
            txtDiscRate.setText("");
        }
        else if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtDiscRate.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!"0".equals(txtDiscRate.getText()))&&(!txtDiscRate.getText().isEmpty())) {
            total = Double.parseDouble(lblSubTotal.getText())*Double.parseDouble(txtDiscRate.getText())*0.01;
            txtDiscAmt.setText(new DecimalFormat("##.##").format(total));
            txtDiscAmt.setEditable(false);
            txtPayment.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&("0".equals(txtDiscRate.getText()))) {
            txtDiscAmt.setEditable(true);
            txtDiscAmt.requestFocus();
        } else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtDiscRate.setText("0");
        }
    }//GEN-LAST:event_txtDiscRateKeyPressed

    private void clientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTableMouseClicked
        
    }//GEN-LAST:event_clientTableMouseClicked

    private void txtCustNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtCustName.getText().isEmpty())) {
            if(!fnTools.isEmpty(clientTable)){
        clientTableModel  =(DefaultTableModel)clientTable.getModel();
        String Name = clientTableModel.getValueAt(0, 0).toString();
        txtCustName.setText(Name);
        getData();
            }
        txtsize.requestFocus();
        }else if ((key == KeyEvent.VK_ESCAPE)){
        txtBillId.requestFocus();
        }
        else if ((key == KeyEvent.VK_DOWN)) {
            if(!fnTools.isEmpty(clientTable)){
            clientTableModel  =(DefaultTableModel)clientTable.getModel();
            clientTable.setRowSelectionInterval(0, 0);
            clientTable.requestFocus();
            
            }
            
        }
        
    }//GEN-LAST:event_txtCustNameKeyPressed

    private void txtCustNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustNameKeyReleased
        fillCustTable();        
    }//GEN-LAST:event_txtCustNameKeyReleased

    private void txtDiscAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscAmtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtFocusLost

    private void txtDiscAmtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtKeyTyped

    private void txtDiscAmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyPressed
        int key = evt.getKeyCode();
        char car = evt.getKeyChar();
        if((car < '0'|| car > '9') && (car!='.') && (car!=(char)KeyEvent.VK_BACK_SPACE)&& (car!=(char)KeyEvent.VK_ENTER)){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Invalid Insert");
            txtDiscRate.setText("0");
        }
        else if((key == KeyEvent.VK_ENTER)&&(!txtCustName.getText().isEmpty())&&(!txtDiscAmt.getText().isEmpty())){
           txtPayment.requestFocus(); 
        }
        else if((key == KeyEvent.VK_ENTER)&&(!txtCustName.getText().isEmpty())&&(txtDiscAmt.getText().isEmpty())){
           txtDiscAmt.setText("0");
           txtPayment.requestFocus(); 
        }
        
    }//GEN-LAST:event_txtDiscAmtKeyPressed

    private void txtDiscAmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscAmtKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscAmtKeyReleased

    private void txtPaymentFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentFocusLost

    private void txtPaymentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaymentKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentKeyTyped

    private void txtPaymentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaymentKeyPressed
       int key = evt.getKeyCode();
        char car = evt.getKeyChar();
        if((car < '0'|| car > '9') && (car!='.') && (car!=(char)KeyEvent.VK_BACK_SPACE)&& (car!=(char)KeyEvent.VK_ENTER)){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Invalid Insert");
            txtDiscRate.setText("");
        }
        else if((key == KeyEvent.VK_ENTER)&&(!txtPayment.getText().isEmpty())){
            if(txtPayment.getText().equals("0")){
                lblDue.setText(lblTotalDue.getText());
            }
            int dialogResult = JOptionPane.showConfirmDialog (null, "Regular Customer ?","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.NO_OPTION){
                 int dialogResult2 = JOptionPane.showConfirmDialog (null, "Make it Special Customer ?","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult2 == JOptionPane.YES_OPTION){
                     try {
                         String sql = "insert into special_customer_table(cust_name,contact,old_due) values('"+txtCustName.getText()+"','"+txtContact.getText()+"','"+lblDue.getText()+"')";
                         smtInstance = conInstance.createStatement();
                         smtInstance.executeUpdate(sql);
                         btnPrint.requestFocus();
                     } catch (SQLException ex) {
                         Logger.getLogger(billingPanel.class.getName()).log(Level.SEVERE, null, ex);
                     }
            }  
             
             }else if(dialogResult == JOptionPane.YES_OPTION){
                 btnPrint.requestFocus();
             }
            
            
            
        }
    }//GEN-LAST:event_txtPaymentKeyPressed

    private void txtPaymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaymentKeyReleased
        float due=0;
        if ((!"".equals(lblTotal.getText())) && (!"".equals(txtPayment.getText()))) {
            if (Float.parseFloat(lblTotalDue.getText()) > Float.parseFloat(txtPayment.getText())) {
                due = Float.parseFloat(lblTotalDue.getText()) - Float.parseFloat(txtPayment.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText(new DecimalFormat("##.##").format(due));
                lblBalance.setText("0.0");
            } else {
                due = Float.parseFloat(txtPayment.getText()) - Float.parseFloat(lblTotalDue.getText());//tax*Double.parseDouble(lblSubTotal.getText())/100;
                lblDue.setText("0.0");
                lblBalance.setText(new DecimalFormat("##.##").format(due));
            }
        }
    }//GEN-LAST:event_txtPaymentKeyReleased

    private void txtPaymentFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentFocusGained
        calculateTotal();
    }//GEN-LAST:event_txtPaymentFocusGained

    private void btnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPrintKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&& ((txtBillId.getText().isEmpty())||("".equals(txtBillId.getText())))) {
        if((!fnTools.isEmpty(billTable)&&(!"".equals(txtPayment.getText()))&&(!"".equals(lblTotalDue.getText())))){
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Make the Sales?","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                billDetails();
                txtCustName.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Nothing to Print");
            txtCustName.requestFocus();
        }
        }else if((key == KeyEvent.VK_ENTER)&& (!txtBillId.getText().isEmpty())){
            updateBill();
        }else if(key == KeyEvent.VK_ESCAPE){
            txtCustName.requestFocus();
        }
        
    }//GEN-LAST:event_btnPrintKeyPressed

    private void txtsizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyPressed
        int key = evt.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE){
            txtDiscRate.requestFocus();
            
        } else if ((key == KeyEvent.VK_ENTER)&&(!txtsize.getText().isEmpty())) {
            
            if(!fnTools.isEmpty(sizeTable)){
                size_table_model  =(DefaultTableModel)sizeTable.getModel();
                String Name = size_table_model.getValueAt(0, 0).toString();
                txtsize.setText(Name);
            
                            
            try {
                String itemcode = "Select id,rate from client_rate_table where size = '"+txtsize.getText()+"' and company_name = '"+txtCustName.getText()+"'";
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
            if(!txtContact.getText().isEmpty()){
                txtQty.requestFocus();
            }else{
                txtContact.requestFocus();
            }
        }else if ((key == KeyEvent.VK_DOWN)) {
            if(!fnTools.isEmpty(sizeTable)){
            size_table_model  =(DefaultTableModel)sizeTable.getModel();
            sizeTable.setRowSelectionInterval(0, 0);
            sizeTable.requestFocus();
            
            }
            //txtRate.requestFocus();
        }  
    }//GEN-LAST:event_txtsizeKeyPressed

    private void txtsizeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyReleased
        String values = txtsize.getText();
        sql = "SELECT Distinct size FROM photo_size_detail_table where size Like '"+values+"%'";
        fill_size_entry_table(sql,"size",sizeTable);
        
        
    }//GEN-LAST:event_txtsizeKeyReleased

    private void sizeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sizeTableMouseClicked
        
    }//GEN-LAST:event_sizeTableMouseClicked

    private void txtContactFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactFocusGained
        txtContact.selectAll();
    }//GEN-LAST:event_txtContactFocusGained

    private void txtContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyTyped
        if(txtContact.getText().length()>9){
            evt.consume();
        }
    }//GEN-LAST:event_txtContactKeyTyped

    private void txtContactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyPressed
        ((AbstractDocument) txtContact.getDocument()).setDocumentFilter(new MyIntFilter());
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtBillId.getText().isEmpty())) {
            txtDiscRate.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtBillId.getText().isEmpty())){
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtContactKeyPressed

    private void clientTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = clientTable.getSelectedRow();
            String value = clientTable.getModel().getValueAt(row_index,0).toString();
            txtCustName.setText(value);
            txtsize.requestFocus();
            getData();
        }
    }//GEN-LAST:event_clientTableKeyPressed

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
        float cost=0;
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
            cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
            txtCost.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyPressed
        int key = evt.getKeyCode();
        char car = evt.getKeyChar();
        if((car < '0'|| car > '9') && (car!='.') && (car!=(char)KeyEvent.VK_BACK_SPACE)&& (car!=(char)KeyEvent.VK_ENTER)){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Invalid Insert");
            txtRate.setText("");
        }
         else if ((key == KeyEvent.VK_ENTER)&&(!txtRate.getText().isEmpty())) {
            btnAdd.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtRate.getText().isEmpty())) {
            txtRate.setText(display);
            btnAdd.requestFocus();
        }
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
            float cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
            txtCost.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtRateKeyPressed

    private void txtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyReleased
        float cost=0;
        if ((!"".equals(txtQty.getText())) && (!"".equals(txtRate.getText()))) {
            cost = Float.parseFloat(txtQty.getText()) * Float.parseFloat(txtRate.getText());
            txtCost.setText(new DecimalFormat("##.##").format(cost));

        }
    }//GEN-LAST:event_txtRateKeyReleased

    private void lblPrevDueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblPrevDueKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPrevDueKeyTyped

    private void lblTotalDueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTotalDueKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTotalDueKeyTyped

    private void txtBillIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBillIdKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtBillId.setEditable(true);

        } else if ((key == KeyEvent.VK_ENTER)&&(!txtBillId.getText().isEmpty())) {
            try {
                String search = "SELECT * FROM bill_table WHERE bill_no = '"+txtBillId.getText()+"'";
                smtInstance = conInstance.createStatement();
                ResultSet resultSearch = smtInstance.executeQuery(search);
                if(resultSearch.next()){
                    txtCustName.setText(resultSearch.getString("cust_name"));
                    lblTotal.setText(resultSearch.getString("total"));
                    lblPaid.setVisible(true);
                    lblPaidlbl.setVisible(true);
                    lblPaid.setText(resultSearch.getString("paid"));
                    lblDue.setText(resultSearch.getString("due"));
                    getData();
                    lblSubTotal.setText(Double.toString(resultSearch.getDouble("total")+resultSearch.getDouble("disc")));
                    txtDiscAmt.setText(resultSearch.getString("disc"));
                    double due = 0;
                    due = resultSearch.getDouble("due");
                    if((!lblPrevDue.getText().equals("0"))&&(Double.parseDouble(lblPrevDue.getText())!=due)){
                        String maxId = "Select max(bill_no) from bill_table where cust_name = '"+txtCustName.getText()+"' and contact = '"+txtContact.getText() +"'";
                        smtInstance = conInstance.createStatement();
                        ResultSet max = smtInstance.executeQuery(maxId);
                        while(max.next()){
                            BillId = max.getString(1);
                        }
                        JOptionPane.showMessageDialog(null,"Out-dated bill ! Previous Due might be different. Your Updated bill is "+BillId+"");
                        resetBill();
                        txtBillId.requestFocus();
                    }else{
                        lblPrevDue.setText(resultSearch.getString("old_due"));
                        txtDiscRate.requestFocus();
                    }
                    
                }

                
            } catch (SQLException ex) {
                Logger.getLogger(expencesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtBillId.setText("");
        }
        values = txtCustName.getText();
        fillTable("SELECT * FROM sales_table WHERE bill_no = '"+txtBillId.getText()+"'");
        //calculate();
    }//GEN-LAST:event_txtBillIdKeyPressed

    private void sizeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizeTableKeyPressed
        int name = 43;
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable clientTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblDue;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblPaidlbl;
    private javax.swing.JLabel lblPrevDue;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalDue;
    private javax.swing.JTable sizeTable;
    public javax.swing.JTextField txtBillId;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtCost;
    public javax.swing.JTextField txtCustName;
    private javax.swing.JTextField txtDiscAmt;
    private javax.swing.JTextField txtDiscRate;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtRate;
    public javax.swing.JTextField txtsize;
    // End of variables declaration//GEN-END:variables
}
