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
@Table(name = "instock_entry_table",schema = "root")
public class instock_entry_pojo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int instock_id ;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column
    private String item_name;
    
    @Column
    private double total_exp;
    @Column
    private int unit;

    public instock_entry_pojo(Date date, String item_name, double total_exp, int unit) {
        super();
        this.date = date;
        this.item_name = item_name;
        this.total_exp = total_exp;
        this.unit = unit;
    }

    public instock_entry_pojo() {
    }

    public int getInstock_id() {
        return instock_id;
    }

    public void setInstock_id(int instock_id) {
        this.instock_id = instock_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getTotal_exp() {
        return total_exp;
    }

    public void setTotal_exp(double total_exp) {
        this.total_exp = total_exp;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
    
    
    
            
}
