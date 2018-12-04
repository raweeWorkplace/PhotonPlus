/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author idiotbox
 */

@Entity
@Table(name = "journal_table",schema = "root")
public class journal_pojo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int journal_id;
    
    @ManyToOne(optional = false,targetEntity = clientPojo.class)
    @JoinColumn(name = "client_id")
    private clientPojo client_id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column
    private double debit;
    @Column
    private double credit;
    
    @Column
    private int flag;
    
    @Column
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int isFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public journal_pojo() {
    }

    public clientPojo getClient_id() {
        return client_id;
    }

    public void setClient_id(clientPojo client_id) {
        this.client_id = client_id;
    }
    

    public journal_pojo(clientPojo client_id, Date date, double debit, double credit, int flag) {
        this.client_id = client_id;
        this.date = date;
        this.debit = debit;
        this.credit = credit;
        this.flag = flag;
    }





    public int getJournal_id() {
        return journal_id;
    }

    public void setJournal_id(int journal_id) {
        this.journal_id = journal_id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }


    
    
    
}