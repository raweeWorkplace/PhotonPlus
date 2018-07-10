/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ranjan
 */
public class homeFrame extends javax.swing.JFrame {

    GridBagLayout layout = new GridBagLayout();

    expencesPanel expences;
    homePanel home;
    billingPanel sales;
    GridBagConstraints gridBag;
    public String globalVariableCashier;
    BufferedImage bi;
    aboutPanel about;
    masterPanel master;
    reportPanel report;

    public String getGlobalVariableCashier() {
        return globalVariableCashier;
    }

    public void setGlobalVariableCashier(String globalVariableCashier) {
        this.globalVariableCashier = globalVariableCashier;
        lblUser.setText(getGlobalVariableCashier());
    }

    /**
     * Creates new form homeFrame
     */
    public homeFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        try {

            bi = ImageIO.read(getClass().getClassLoader().getResource("BillingIcon/ICON.png"));
            this.setIconImage(bi);
            this.setTitle("PHOTON");
        } catch (IOException ex) {
            Logger.getLogger(homeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        getTodayDate();
        displayPanel.setLayout(layout);

        init();
        addPanel();
        state();
        home.setVisible(true);
    }

    private void init() {
        gridBag = new GridBagConstraints();
        expences = new expencesPanel();
        home = new homePanel();
        sales = new billingPanel();
        about = new aboutPanel();
        master = new masterPanel();
        report = new reportPanel();

    }

    private void addPanel() {
        displayPanel.add(expences, gridBag);
        displayPanel.add(home, gridBag);
        displayPanel.add(sales, gridBag);
        displayPanel.add(about, gridBag);
        displayPanel.add(master,gridBag);
        displayPanel.add(report,gridBag);

    }

    private void state() {

        expences.setVisible(false);
        home.setVisible(false);
        sales.setVisible(false);
        about.setVisible(false);
        master.setVisible(false);
        report.setVisible(false);
    }

    private void getTodayDate() {
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //System.out.println(dateformat.format(cal.getTime()));

        lblTodayDate.setText(dateformat.format(cal.getTime()));

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
        jLabel9 = new javax.swing.JLabel();
        lblTodayDate = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        basePanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        billingPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        expencePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        masterPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        reportPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        exitPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        aboutPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        scrollPanel = new javax.swing.JScrollPane();
        displayPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.gray);

        jLabel9.setFont(new java.awt.Font("Ubuntu", 3, 15)); // NOI18N
        jLabel9.setForeground(java.awt.Color.white);
        jLabel9.setText("Date:");

        lblTodayDate.setFont(new java.awt.Font("Ubuntu", 3, 15)); // NOI18N
        lblTodayDate.setForeground(java.awt.Color.white);

        jLabel11.setFont(new java.awt.Font("Ubuntu", 3, 15)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("Welcome:");

        lblUser.setFont(new java.awt.Font("Ubuntu", 3, 15)); // NOI18N
        lblUser.setForeground(java.awt.Color.white);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(58, 58, 58)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 813, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblTodayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(lblTodayDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel9, lblTodayDate, lblUser});

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 50));

        basePanel.setBackground(java.awt.Color.gray);

        sideBarPanel.setBackground(java.awt.Color.gray);

        homePanel.setBackground(java.awt.Color.lightGray);
        homePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homePanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });
        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/house.png"))); // NOI18N
        homePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblHome.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblHome.setForeground(java.awt.Color.white);
        lblHome.setText("HOME");
        homePanel.add(lblHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        billingPanel.setBackground(java.awt.Color.lightGray);
        billingPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        billingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billingPanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });
        billingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/billing-icon.png"))); // NOI18N
        billingPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel10.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("BILLING");
        billingPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        expencePanel.setBackground(java.awt.Color.lightGray);
        expencePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        expencePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expencePanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/expences-icon.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("EXPENCE");

        javax.swing.GroupLayout expencePanelLayout = new javax.swing.GroupLayout(expencePanel);
        expencePanel.setLayout(expencePanelLayout);
        expencePanelLayout.setHorizontalGroup(
            expencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expencePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(37, 37, 37))
        );
        expencePanelLayout.setVerticalGroup(
            expencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expencePanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(expencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        masterPanel.setBackground(java.awt.Color.lightGray);
        masterPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                masterPanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });
        masterPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());



        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/master-icon.png"))); // NOI18N
        masterPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel13.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setText("MASTER");
        masterPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));


        reportPanel.setBackground(java.awt.Color.lightGray);
        reportPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reportPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportPanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });
        reportPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/clipboard.png"))); // NOI18N
        reportPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel14.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel14.setForeground(java.awt.Color.white);
        jLabel14.setText("REPORT");
        reportPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        exitPanel.setBackground(java.awt.Color.lightGray);
        exitPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitPanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });
        exitPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/logout.png"))); // NOI18N
        exitPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel15.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel15.setForeground(java.awt.Color.white);
        jLabel15.setText("EXIT");
        exitPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        aboutPanel.setBackground(java.awt.Color.lightGray);
        aboutPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aboutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutPanelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExit(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onEnter(evt);
            }
        });
        aboutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/about-us-icon.png"))); // NOI18N
        aboutPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel16.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        jLabel16.setForeground(java.awt.Color.white);
        jLabel16.setText("ABOUT");
        aboutPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        displayPanel.setBackground(java.awt.Color.lightGray);
        displayPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        displayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        scrollPanel.setViewportView(displayPanel);

        javax.swing.GroupLayout sideBarPanelLayout = new javax.swing.GroupLayout(sideBarPanel);
        sideBarPanel.setLayout(sideBarPanelLayout);
        sideBarPanelLayout.setHorizontalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(homePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(exitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(aboutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(expencePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(masterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE))
        );
        sideBarPanelLayout.setVerticalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(homePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(billingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(expencePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(masterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(reportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(aboutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sideBarPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {aboutPanel, billingPanel, exitPanel, expencePanel, homePanel, masterPanel, reportPanel});

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(basePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1300, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePanelMouseClicked
        state();
        home.setVisible(true);
        home.txtBilNo.requestFocus();
    }//GEN-LAST:event_homePanelMouseClicked

    private void billingPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billingPanelMouseClicked
        state();
        sales.setVisible(true);
        sales.txtCustName.requestFocus(); 
    }//GEN-LAST:event_billingPanelMouseClicked

    private void reportPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportPanelMouseClicked
        state();
       report.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_reportPanelMouseClicked

    private void masterPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_masterPanelMouseClicked
       state();
       master.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_masterPanelMouseClicked

    private void aboutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutPanelMouseClicked
        state();
        about.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_aboutPanelMouseClicked

    private void expencePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expencePanelMouseClicked
        state();
        expences.setVisible(true);
        expences.txtItemName.requestFocus(); 
               // TODO add your handling code here:
    }//GEN-LAST:event_expencePanelMouseClicked

    private void exitPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitPanelMouseClicked
        int select = JOptionPane.showConfirmDialog(null, "Do you want to close the application ?", "Warning",JOptionPane.YES_NO_OPTION);
        if(select==JOptionPane.YES_OPTION){
            System.exit(0);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_exitPanelMouseClicked

    private void onExit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onExit
        JPanel parent = (JPanel)evt.getSource();
        parent.setBackground(Color.LIGHT_GRAY);
        parent.revalidate();
    }//GEN-LAST:event_onExit

    private void onEnter(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onEnter
        JPanel parent = (JPanel)evt.getSource();
        parent.setBackground(Color.decode("#f0be8b"));
        parent.revalidate();
    }//GEN-LAST:event_onEnter

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homeFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutPanel;
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel billingPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JPanel exitPanel;
    private javax.swing.JPanel expencePanel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblTodayDate;
    private javax.swing.JLabel lblUser;
    public javax.swing.JPanel masterPanel;
    public javax.swing.JPanel reportPanel;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JPanel sideBarPanel;
    // End of variables declaration//GEN-END:variables
}
