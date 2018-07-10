/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author idiotbox
 */
@Entity
@Table(name="company_detail_tbl",schema = "root")
public class dataConfigurationPojo {

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getName() {
        return company_name;
    }

    public void setName(String name) {
        this.company_name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPan() {
        return pan_no;
    }

    public void setPan(String pan) {
        this.pan_no = pan;
    }

    public dataConfigurationPojo(String name, String email, String contact, String address, String pan) {
        
        super();
        this.company_name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.pan_no = pan;
    }
    @Transient
    private String productKey;
    
    @Column()
    private String company_name;
    
    @Column
    @Email
    private String email;
    
    @Column
    private String contact;
    
    @Column
    private String address;
    
    @Column
    private String pan_no;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    public dataConfigurationPojo() {
    }
    
}
