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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author idiotbox
 */
@Entity
@Table(name = "client_rate_table", schema = "root",uniqueConstraints = {@UniqueConstraint(columnNames = {"company_name","size"})})
public class rate_table_pojo implements Serializable {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private int client_rate_id;
    
    @Column()
    private String company_name;
    @Column()
    private String size;
    @Column()
    private double rate;

    public rate_table_pojo(String company_name, String size, double rate) {
        this.company_name = company_name;
        this.size = size;
        this.rate = rate;
    }

    public rate_table_pojo() {
    }

    public int getId() {
        return client_rate_id;
    }

    public void setId(int id) {
        this.client_rate_id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    
}
