/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

//import PanelForms.Test.RegistrationFrame;
import Dao.DataBase_Connection;
import Dao.hibernateConfiguration;
import beans.data_configuration_pojo;
import controller.login_controller;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import beans.log_in_pojo;
import controller.Encryption;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author ranjan
 */
public class regPanel extends javax.swing.JPanel {
    protected Connection conInstance;
     protected Statement smtInstance;
     ResultSet rs;
     DataBase_Connection dao;
     public String namefromDatabase;
     log_in_pojo pojo; 
     ImageIcon bi;
     login_controller controller;

    /**
     * Creates new form regPanel
     */
    public regPanel() {
        initComponents();
        dao = new DataBase_Connection();
        controller = new login_controller();
        pojo = new log_in_pojo();
        txtName.requestFocus();
        submittButton.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pwdConfirmUserPassword = new javax.swing.JPasswordField();
        submittButton = new javax.swing.JButton();
        pwdUserPassword = new javax.swing.JPasswordField();
        txtName = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        lblWarning = new javax.swing.JLabel();
        lblUserPwdNotification = new javax.swing.JLabel();

        setBackground(java.awt.SystemColor.control);

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel5.setText("Username :");

        jLabel6.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel6.setText("Name :");

        jLabel7.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel7.setText("Password :");

        jLabel8.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel8.setText("Re-Password :");

        pwdConfirmUserPassword.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        pwdConfirmUserPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pwdConfirmUserPasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pwdConfirmUserPasswordKeyReleased(evt);
            }
        });

        submittButton.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        submittButton.setText("Register");
        submittButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submittButtonActionPerformed(evt);
            }
        });

        pwdUserPassword.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        pwdUserPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pwdUserPasswordKeyPressed(evt);
            }
        });

        txtName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });

        txtUserName.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        txtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserNameFocusLost(evt);
            }
        });
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserNameKeyPressed(evt);
            }
        });

        lblWarning.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        lblWarning.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lblUserPwdNotification.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        lblUserPwdNotification.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 79, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(submittButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwdUserPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwdConfirmUserPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserPwdNotification, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWarning, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pwdConfirmUserPassword, pwdUserPassword, txtName, txtUserName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwdUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pwdConfirmUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(lblUserPwdNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submittButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, jLabel6, jLabel7, jLabel8, pwdConfirmUserPassword, pwdUserPassword, txtName, txtUserName});

    }// </editor-fold>//GEN-END:initComponents

    public void reset(){
        txtUserName.setText("");
        txtName.setText("");
        pwdUserPassword.setText("");
        pwdConfirmUserPassword.setText("");
        lblWarning.setText("");
        lblUserPwdNotification.setText("");
        submittButton.setEnabled(false);
    }
    
    private void pwdConfirmUserPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwdConfirmUserPasswordKeyReleased
        
    }//GEN-LAST:event_pwdConfirmUserPasswordKeyReleased

    
    
    
    
    private void submittButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submittButtonActionPerformed
        if(txtName.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter Name",
                            "Error", JOptionPane.ERROR_MESSAGE);
            txtName.requestFocus();
        }
        else if(txtUserName.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter Username",
                            "Error", JOptionPane.ERROR_MESSAGE);
            txtUserName.requestFocus();
        }
        else if(pwdUserPassword.getPassword().length<0){
            JOptionPane.showMessageDialog(this, "Enter Password",
                            "Error", JOptionPane.ERROR_MESSAGE);
            txtUserName.requestFocus();
        }
        else{
            pojo.setUserName(txtName.getText());
            pojo.setUserId(txtUserName.getText());
            pojo.setPassword(Encryption.SHA1(new String(pwdUserPassword.getPassword())));
            pojo.setCnfPassword(Encryption.SHA1(new String(pwdConfirmUserPassword.getPassword())));
            pojo.setMasterPassword(Encryption.SHA1("00001111"));
            
            ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator v = vf.getValidator();
		Set<ConstraintViolation<log_in_pojo>> seterror=v.validate(pojo);
		if(!seterror.isEmpty()) {
			for(ConstraintViolation<log_in_pojo> error:seterror) {
				//System.out.println(error.getPropertyPath()+":->"+error.getMessage());
			}
		}
		else {
		new hibernateConfiguration().save(pojo);
		}
            
            lblWarning.setIcon(null);
            lblUserPwdNotification.setIcon(null);
            reset();
            txtName.requestFocus();
        }

    }//GEN-LAST:event_submittButtonActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtUserNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNameFocusGained
        lblWarning.setIcon(null);
    }//GEN-LAST:event_txtUserNameFocusGained

    private void txtUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNameFocusLost
        if(!txtUserName.getText().isEmpty()){
            
            pojo.setUserId(txtUserName.getText());
            if(new login_controller().is_user_present(pojo)){
                bi = new ImageIcon(getClass().getResource("/BillingIcon/delete-filled.png"));
                //submittButton.setEnabled(false);
            }else{
                 bi = new ImageIcon(getClass().getResource("/BillingIcon/double-tick.png"));
                //submittButton.setEnabled(true);
             }
        }
        else{
            txtUserName.requestFocus();

        }
        lblWarning.setIcon((Icon) bi);
    }//GEN-LAST:event_txtUserNameFocusLost

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtName.getText().isEmpty())) {
            txtUserName.requestFocus();
        }
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(!txtUserName.getText().isEmpty())) {
            pwdUserPassword.requestFocus();
        }
    }//GEN-LAST:event_txtUserNameKeyPressed

    private void pwdUserPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwdUserPasswordKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER )&&(pwdUserPassword.getPassword().length>0)) {
            pwdConfirmUserPassword.requestFocus();
        }
    }//GEN-LAST:event_pwdUserPasswordKeyPressed

    private void pwdConfirmUserPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwdConfirmUserPasswordKeyPressed
        int key = evt.getKeyCode();
        if ((key == KeyEvent.VK_ENTER)&&(pwdConfirmUserPassword.getPassword().length>0)) {
            String pass = new String (pwdUserPassword.getPassword());
            String pass1 = new String (pwdConfirmUserPassword.getPassword());
        if((pass.equals(pass1))){
            bi = new ImageIcon(getClass().getResource("/BillingIcon/double-tick.png"));
            if(!new login_controller().is_user_present(pojo)){
                submittButton.setEnabled(true);
                submittButton.requestFocus();
            }
            else{
                JOptionPane.showMessageDialog(this, "UserId not available","Error",JOptionPane.ERROR_MESSAGE);
                txtUserName.requestFocus();
            }
            
        }else{
            bi = new ImageIcon(getClass().getResource("/BillingIcon/delete-filled.png"));
            submittButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Password didnot match","Error",JOptionPane.ERROR_MESSAGE);
            pwdConfirmUserPassword.requestFocus();
        }
         
        lblUserPwdNotification.setIcon((Icon) bi);
        
        }
    }//GEN-LAST:event_pwdConfirmUserPasswordKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblUserPwdNotification;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JPasswordField pwdConfirmUserPassword;
    private javax.swing.JPasswordField pwdUserPassword;
    private javax.swing.JButton submittButton;
    public javax.swing.JTextField txtName;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
