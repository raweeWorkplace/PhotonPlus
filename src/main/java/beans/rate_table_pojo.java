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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author idiotbox
 */
@Entity
@Table(name = "client_rate_table", schema = "root",uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id","size_id"})})
public class rate_table_pojo implements Serializable {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private int client_rate_id;
    
    @ManyToOne(optional = false,targetEntity = clientPojo.class)
    @JoinColumn(name = "client_id")
    private clientPojo client_id;
    
    
    @ManyToOne(optional = false,targetEntity = size_entry_pojo.class)
    @JoinColumn(name = "size_id")
    private size_entry_pojo size_id;
    @Column()
    private double rate;

    public rate_table_pojo(clientPojo client_id, size_entry_pojo size_id, double rate) {
        this.client_id = client_id;
        this.size_id = size_id;
        this.rate = rate;
    }

    public int getClient_rate_id() {
        return client_rate_id;
    }

    public void setClient_rate_id(int client_rate_id) {
        this.client_rate_id = client_rate_id;
    }

    public clientPojo getClient_id() {
        return client_id;
    }

    public void setClient_id(clientPojo client_id) {
        this.client_id = client_id;
    }

    public size_entry_pojo getSize_id() {
        return size_id;
    }

    public void setSize_id(size_entry_pojo size_id) {
        this.size_id = size_id;
    }


    public rate_table_pojo() {
    }

    public int getId() {
        return client_rate_id;
    }

    public void setId(int id) {
        this.client_rate_id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    
}
