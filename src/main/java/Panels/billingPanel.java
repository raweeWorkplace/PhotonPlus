/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import controller.functionTools;
import Dao.DataBase_Connection;
import beans.billingPojo;
import beans.journal_pojo;
import beans.rate_table_pojo;
import beans.size_entry_pojo;
import controller.billing_controller;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ranjan
 */
public class billingPanel extends javax.swing.JPanel {
    
    billing_controller controller;
    journal_pojo j_pojo;
    size_entry_pojo s_pojo;
    rate_table_pojo r_pojo;
    billingPojo b_pojo;
    protected String  display="", item_code="";
    DataBase_Connection dao;
    DefaultTableModel billTableModel, clientTableModel, size_table_model;
    functionTools fnTools;
    private int billTableIndex = 0;
    

    /**
     * Creates new form pharmacySalesPanel
     */
    public billingPanel() {
        initComponents();
        controller = new billing_controller();
        fnTools = new functionTools();
        j_pojo = new journal_pojo();
        r_pojo = new rate_table_pojo();
        s_pojo = new size_entry_pojo();
        lblPaid.setVisible(false);
        lblPaidlbl.setVisible(false);
        
                        
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
            item_code = "";
            resetSelection();
            JOptionPane.showMessageDialog(null, "Item Entered");
            calculateSubtotal();
        }else {
            JOptionPane.showMessageDialog(null, "Cost can't be Null. Re-Enter details");
        }
        
    }
    
    public boolean validateData(){
        if(txtCustName.getText().isEmpty()){
            txtCustName.requestFocus();
            return false;
        }else if(txtContact.getText().isEmpty()){
            txtContact.requestFocus();
            return false;
        }else if(fnTools.isEmpty(billTable)){
            txtsize.requestFocus();
            return false;
        }else if((txtDiscRate.getText().isEmpty())||(txtDiscAmt.getText().isEmpty())){
            txtDiscRate.requestFocus();
            return false;
        }else if(txtPayment.getText().isEmpty()){
            txtPayment.requestFocus();
            return false;
        }
        return true;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblPrevDue = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtDiscRate = new javax.swing.JTextField();
        txtDiscAmt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblPaidlbl = new javax.swing.JLabel();
        lblPaid = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblTotalDue = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtPayment = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        lblBalance = new javax.swing.JLabel();
        lblDue = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnPrint = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setBackground(java.awt.Color.gray);

        jPanel2.setBackground(java.awt.Color.darkGray);

        jPanel3.setBackground(java.awt.Color.darkGray);

        txtCost.setEditable(false);
        txtCost.setBackground(java.awt.Color.darkGray);
        txtCost.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtCost.setForeground(java.awt.Color.white);
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

        jLabel11.setBackground(java.awt.Color.darkGray);
        jLabel11.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("Rate  :");

        jLabel1.setBackground(java.awt.Color.darkGray);
        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Name :");

        jLabel6.setBackground(java.awt.Color.darkGray);
        jLabel6.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Quan :");

        jLabel12.setBackground(java.awt.Color.darkGray);
        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
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

        txtCustName.setBackground(java.awt.Color.darkGray);
        txtCustName.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtCustName.setForeground(java.awt.Color.white);
        txtCustName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustNameKeyReleased(evt);
            }
        });

        btnAdd.setBackground(java.awt.Color.lightGray);
        btnAdd.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        btnAdd.setText("Add ");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel13.setBackground(java.awt.Color.darkGray);
        jLabel13.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setText("Item :");

        txtsize.setBackground(java.awt.Color.darkGray);
        txtsize.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtsize.setForeground(java.awt.Color.white);
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

        jLabel5.setBackground(java.awt.Color.darkGray);
        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Contact :");

        txtContact.setBackground(java.awt.Color.darkGray);
        txtContact.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtContact.setForeground(java.awt.Color.white);
        txtContact.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContactFocusGained(evt);
            }
        });
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
        });

        txtQty.setBackground(java.awt.Color.darkGray);
        txtQty.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtQty.setForeground(java.awt.Color.white);
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        txtRate.setBackground(java.awt.Color.darkGray);
        txtRate.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtRate.setForeground(java.awt.Color.white);
        txtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRateKeyReleased(evt);
            }
        });

        jLabel4.setBackground(java.awt.Color.darkGray);
        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/icons8-search-25.png"))); // NOI18N
        jLabel4.setText("Bill Id ");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtBillId.setBackground(java.awt.Color.darkGray);
        txtBillId.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        txtBillId.setForeground(java.awt.Color.white);
        txtBillId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBillIdKeyPressed(evt);
            }
        });

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

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel24.setBackground(java.awt.Color.darkGray);
        jLabel24.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel24.setForeground(java.awt.Color.white);
        jLabel24.setText("Sub total :");

        lblSubTotal.setBackground(java.awt.Color.darkGray);
        lblSubTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblSubTotal.setForeground(java.awt.Color.white);
        lblSubTotal.setBorder(null);

        lblPrevDue.setBackground(java.awt.Color.darkGray);
        lblPrevDue.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblPrevDue.setForeground(java.awt.Color.white);
        lblPrevDue.setText("0");
        lblPrevDue.setBorder(null);
        lblPrevDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblPrevDueKeyTyped(evt);
            }
        });

        jLabel28.setBackground(java.awt.Color.darkGray);
        jLabel28.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel28.setForeground(java.awt.Color.white);
        jLabel28.setText("Old Due :");

        jLabel21.setBackground(java.awt.Color.darkGray);
        jLabel21.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel21.setForeground(java.awt.Color.white);
        jLabel21.setText("Disc. % :");

        txtDiscRate.setBackground(java.awt.Color.darkGray);
        txtDiscRate.setFont(new java.awt.Font("Century Schoolbook L", 0, 20)); // NOI18N
        txtDiscRate.setForeground(java.awt.Color.white);
        txtDiscRate.setText("0");
        txtDiscRate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        txtDiscAmt.setBackground(java.awt.Color.darkGray);
        txtDiscAmt.setFont(new java.awt.Font("Century Schoolbook L", 0, 20)); // NOI18N
        txtDiscAmt.setForeground(java.awt.Color.white);
        txtDiscAmt.setText("0");
        txtDiscAmt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        jLabel22.setBackground(java.awt.Color.darkGray);
        jLabel22.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel22.setForeground(java.awt.Color.white);
        jLabel22.setText("Total :");

        lblTotal.setBackground(java.awt.Color.darkGray);
        lblTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblTotal.setForeground(java.awt.Color.white);
        lblTotal.setText("0");
        lblTotal.setBorder(null);

        lblPaidlbl.setBackground(java.awt.Color.darkGray);
        lblPaidlbl.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblPaidlbl.setForeground(java.awt.Color.white);
        lblPaidlbl.setText("Paid :");

        lblPaid.setBackground(java.awt.Color.darkGray);
        lblPaid.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblPaid.setForeground(java.awt.Color.white);
        lblPaid.setText("0");
        lblPaid.setBorder(null);

        jLabel30.setBackground(java.awt.Color.darkGray);
        jLabel30.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel30.setForeground(java.awt.Color.white);
        jLabel30.setText("Total Due :");

        lblTotalDue.setBackground(java.awt.Color.darkGray);
        lblTotalDue.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblTotalDue.setForeground(java.awt.Color.white);
        lblTotalDue.setText("0");
        lblTotalDue.setBorder(null);
        lblTotalDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblTotalDueKeyTyped(evt);
            }
        });

        jLabel27.setBackground(java.awt.Color.darkGray);
        jLabel27.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel27.setForeground(java.awt.Color.white);
        jLabel27.setText("Payment :");

        txtPayment.setBackground(java.awt.Color.darkGray);
        txtPayment.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        txtPayment.setForeground(java.awt.Color.white);
        txtPayment.setText("0");
        txtPayment.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        jLabel29.setBackground(java.awt.Color.darkGray);
        jLabel29.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel29.setForeground(java.awt.Color.white);
        jLabel29.setText("Refund :");

        lblBalance.setBackground(java.awt.Color.darkGray);
        lblBalance.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblBalance.setForeground(java.awt.Color.white);
        lblBalance.setText("0");
        lblBalance.setBorder(null);
        lblBalance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblBalanceKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblBalanceKeyReleased(evt);
            }
        });

        lblDue.setBackground(java.awt.Color.darkGray);
        lblDue.setFont(new java.awt.Font("Century Schoolbook L", 0, 18)); // NOI18N
        lblDue.setForeground(java.awt.Color.white);
        lblDue.setText("0");
        lblDue.setBorder(null);
        lblDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblDueKeyTyped(evt);
            }
        });

        jLabel26.setBackground(java.awt.Color.darkGray);
        jLabel26.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel26.setForeground(java.awt.Color.white);
        jLabel26.setText("Due :");

        jSeparator2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnPrint.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        cancelButton.setFont(new java.awt.Font("Century Schoolbook L", 0, 24)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel13))
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtContact, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                            .addComponent(txtsize)))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPaidlbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtDiscRate, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiscAmt))
                            .addComponent(lblPrevDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                    .addComponent(lblPaid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPayment)
                                    .addComponent(lblBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTotalDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(813, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblSubTotal, lblTotal});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel24, jLabel28, jLabel29});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel21, jLabel26, lblPaidlbl});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel22, jLabel27});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAdd)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(lblSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrevDue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtDiscRate, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtDiscAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPaidlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPaid))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalDue, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDue, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, txtCost, txtCustName, txtQty, txtRate});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, jLabel5, txtContact, txtsize});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblDue, lblPaid, txtDiscRate});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel21, jLabel24, jLabel26, jLabel28, lblPaidlbl});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel22, jLabel27, jLabel29, jLabel30, lblBalance, lblTotalDue, txtDiscAmt, txtPayment});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel6});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, txtBillId});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblPrevDue, lblTotal});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
       resetSelection();
       resetBill();
       resetTables();
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
         if ((txtBillId.getText().isEmpty())||("".equals(txtBillId.getText()))) {
            if(validateData()){ 
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Make the Sales?","Warning",JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    b_pojo = new billingPojo();
                    b_pojo.setCust_name(txtCustName.getText().toUpperCase());
                    b_pojo.setContact(txtContact.getText());
                    b_pojo.setDate(new Date());
                    b_pojo.setTotal(Double.parseDouble(lblTotal.getText()));
                    b_pojo.setDisc(Double.parseDouble(txtDiscAmt.getText()));
                    double payment=0;
                    b_pojo.setOld_due(Double.parseDouble(lblPrevDue.getText()));
                    if(Double.parseDouble(lblTotalDue.getText())>Double.parseDouble(txtPayment.getText())){
                        payment = Double.parseDouble(txtPayment.getText());
                    }else {
                        payment = Double.parseDouble(lblTotalDue.getText());
                    }
                    
                    b_pojo.setDue(Double.parseDouble(lblDue.getText()));
                    b_pojo.setPaid(payment);
                    b_pojo.setStatus("Pending");
                    controller.billDetails(b_pojo);
                    
                    if(!fnTools.isEmpty(billTable)){
                        int rows=billTable.getRowCount();

                        for(int row = 0; row<rows; row++){
                            int item = Integer.parseInt(billTable.getValueAt(row, 0).toString());
                            int quan = Integer.parseInt(billTable.getValueAt(row, 2).toString());
                            double cost = Double.parseDouble(billTable.getValueAt(row, 4).toString());
                            controller.fill_order(controller.getBillNo(), item, quan, cost);
                        }
                    }
                    
                    if(!fnTools.isEmpty(clientTable)){
                        String sql = "from clientPojo where company_name = '"+b_pojo.getCust_name()+"'";
                              
                        j_pojo=controller.get_client(sql);
                        journal_pojo pojo = new journal_pojo();  
                        pojo.setClient_id(j_pojo.getClient_id());
                        pojo.setDate(b_pojo.getDate());
                        pojo.setCredit(b_pojo.getTotal());
                        pojo.setDebit(b_pojo.getPaid());
                        pojo.setFlag(j_pojo.getClient_id().getFlag());
                        pojo.setDescription("Bill Payment of bill no:  "+controller.getBillNo()+"");
                        controller.updateDue(pojo);
                    }
                    resetBill();
                    billTableIndex=0;
                    //controller.ireport();
                    resetTables();
                    resetBill();
                    txtCustName.requestFocus();
                }
        }
        else{
            JOptionPane.showMessageDialog(null,"Nothing to Print");
            txtCustName.requestFocus();
        }
        }else if((!txtBillId.getText().isEmpty())){
            //updateBill();
        }
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
                String sql = "from clientPojo where company_name = '"+txtCustName.getText()+"'";
                j_pojo=controller.get_client(sql);
                
                txtContact.setText(j_pojo.getClient_id().getContact());
                lblPrevDue.setText(Double.toString(controller.getBalance(j_pojo.getClient_id().getId())));
                txtsize.requestFocus();
                
            }else{
                txtContact.requestFocus();
            }
        
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
       if(!txtCustName.getText().isEmpty()){
           String sql = "from clientPojo where company_name like '"+txtCustName.getText()+"%'";
           controller.fill_company_name(clientTable, sql);
       }
        
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
            btnPrint.requestFocus();                       
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
                
                String sql ="from size_entry_pojo where size= '"+txtsize.getText()+"'";
                s_pojo = controller.get_size(sql);
                if(!fnTools.isEmpty(clientTable)){
                    String check_rate_sql ="from rate_table_pojo where client_id = "+j_pojo.getClient_id().getId()+" and size_id = "+s_pojo.getSize_id()+"";

                    if(controller.isData(check_rate_sql)){

                        r_pojo = controller.get_rate(check_rate_sql);
                        display = Double.toString(r_pojo.getRate());
                        item_code = Integer.toString(r_pojo.getId());

                    }else{
                    display = Double.toString(s_pojo.getDisplay());
                    item_code = Integer.toString(s_pojo.getSize_id());
                }
                }else{
                    display = Double.toString(s_pojo.getDisplay());
                    item_code = Integer.toString(s_pojo.getSize_id());
                }
            txtQty.requestFocus(); 
        }  
                  
        }else if ((key == KeyEvent.VK_DOWN)) {
            if(!fnTools.isEmpty(sizeTable)){
                
            size_table_model  =(DefaultTableModel)sizeTable.getModel();
            sizeTable.setRowSelectionInterval(0, 0);
            sizeTable.requestFocus();
            
            }
        }  
    }//GEN-LAST:event_txtsizeKeyPressed

    private void txtsizeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsizeKeyReleased
        String values = txtsize.getText();
        String sql = "FROM size_entry_pojo where size Like '"+values+"%'";
        controller.fill_size_name(sizeTable,sql);
        
        
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
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            //txtQty.setEditable(true);
        }else if ((key == KeyEvent.VK_ENTER)&&(!txtBillId.getText().isEmpty())) {
            txtDiscRate.requestFocus();
        }else if ((key == KeyEvent.VK_ENTER)&&(txtBillId.getText().isEmpty())){
            txtsize.requestFocus();
        }
    }//GEN-LAST:event_txtContactKeyPressed

    private void clientTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientTableKeyPressed
        int key = evt.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            int row_index = clientTable.getSelectedRow();
            String value = clientTable.getModel().getValueAt(row_index,0).toString();
            txtCustName.setText(value);
            txtCustName.requestFocus();
        }
    }//GEN-LAST:event_clientTableKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            //txtQty.setEditable(true);
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
//            try {
//                String search = "SELECT * FROM bill_table WHERE bill_no = '"+txtBillId.getText()+"'";
//                smtInstance = conInstance.createStatement();
//                ResultSet resultSearch = smtInstance.executeQuery(search);
//                if(resultSearch.next()){
//                    txtCustName.setText(resultSearch.getString("cust_name"));
//                    lblTotal.setText(resultSearch.getString("total"));
//                    lblPaid.setVisible(true);
//                    lblPaidlbl.setVisible(true);
//                    lblPaid.setText(resultSearch.getString("paid"));
//                    lblDue.setText(resultSearch.getString("due"));
//                    getData();
//                    lblSubTotal.setText(Double.toString(resultSearch.getDouble("total")+resultSearch.getDouble("disc")));
//                    txtDiscAmt.setText(resultSearch.getString("disc"));
//                    double due = 0;
//                    due = resultSearch.getDouble("due");
//                    if((!lblPrevDue.getText().equals("0"))&&(Double.parseDouble(lblPrevDue.getText())!=due)){
//                        String maxId = "Select max(bill_no) from bill_table where cust_name = '"+txtCustName.getText()+"' and contact = '"+txtContact.getText() +"'";
//                        smtInstance = conInstance.createStatement();
//                        ResultSet max = smtInstance.executeQuery(maxId);
//                        while(max.next()){
//                            BillId = max.getString(1);
//                        }
//                        JOptionPane.showMessageDialog(null,"Out-dated bill ! Previous Due might be different. Your Updated bill is "+BillId+"");
//                        resetBill();
//                        txtBillId.requestFocus();
//                    }else{
//                        lblPrevDue.setText(resultSearch.getString("old_due"));
//                        txtDiscRate.requestFocus();
//                    }
//                    
//                }
//
//                
//            } catch (SQLException ex) {
//                Logger.getLogger(expencesPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
            txtBillId.setText("");
        }
        String values = txtCustName.getText();
        //fillTable("SELECT * FROM sales_table WHERE bill_no = '"+txtBillId.getText()+"'");
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

    private void txtContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactActionPerformed


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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
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
