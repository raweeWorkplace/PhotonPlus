/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author idiotbox
 */@Entity
 @Table(name="client_detail_table", schema = "root")
public class client_table_pojo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int client_id;
    
    @Column(unique = true)
    private String company_name;
    
    @Column
    private String client_name;
    
    @Column(unique = true)
    private String contact;
    
    @Column
    private String address;
     
    @Column
    private int flag;

    public client_table_pojo(String company_name, String client_name, String contact, String address, int flag) {
        this.company_name = company_name;
        this.client_name = client_name;
        this.contact = contact;
        this.address = address;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    
   
    public int getId() {
        return client_id;
    }

    public void setId(int id) {
        this.client_id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
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

    public client_table_pojo() {
    }
    
    
    
    
    
}
