/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author idiotbox
 */
@Entity
@Table(name = "bill_table", schema = "root")
public class billingPojo implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int bill_no;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column
    private String cust_name;
    
    @Column
    private String contact;
    
    @Column
    private double total;
    
    @Column
    private double disc;
    
    @Column
    private double paid;
    
    @Column
    private double due;
    
    @Column
    private double old_due;

    public int getBill_no() {
        return bill_no;
    }

    public void setBill_no(int bill_no) {
        this.bill_no = bill_no;
    }

    public double getOld_due() {
        return old_due;
    }

    public void setOld_due(double old_due) {
        this.old_due = old_due;
    }
    
       
    @Column
    private String status;

    public billingPojo() {
    }

    public int getBill() {
        return bill_no;
    }

    public void setBill(int bill) {
        this.bill_no = bill;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDisc() {
        return disc;
    }

    public void setDisc(double disc) {
        this.disc = disc;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
    
    public billingPojo(Date date, String cust_name, String contact, double total, double disc, double paid, double due, String status) {
        super();
        this.date = date;
        this.cust_name = cust_name;
        this.contact = contact;
        this.total = total;
        this.disc = disc;
        this.paid = paid;
        this.due = due;
        this.status = status;
    }
    
}

