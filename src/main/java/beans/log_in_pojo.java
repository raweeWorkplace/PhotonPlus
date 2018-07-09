/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author idiotbox
 */
@Entity
@Table( name = "Login_tbl",schema = "root")
public class log_in_pojo {
    
    @Column
    @NotNull
    private String UserName;

    public log_in_pojo(String UserName, String UserId, String Password, String masterPassword) {
        this.UserName = UserName;
        this.UserId = UserId;
        this.Password = Password;
        this.masterPassword = masterPassword;
    }
    
    @Id
    private String UserId;
    
    @Column
    @NotNull
    private String Password;
    
    @Column
    @NotNull
    private String masterPassword;
    
    @Transient
    private String cnfPassword;

    public String getCnfPassword() {
        return cnfPassword;
    }

    public log_in_pojo() {
    }

    public void setCnfPassword(String cnfPassword) {
        this.cnfPassword = cnfPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }
}
