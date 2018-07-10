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
 */
@Entity
@Table(name = "photo_size_detail_table",schema = "root")
public class size_entry_pojo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int size_id;
    
    @Column(nullable = false,unique = true)
    private String size;
    
    @Column
    private double rate;
    
    @Column
    private double display;

    public size_entry_pojo(String size, double rate, double display) {
        super();
        this.size = size;
        this.rate = rate;
        this.display = display;
    }

    public size_entry_pojo() {
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
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

    public double getDisplay() {
        return display;
    }

    public void setDisplay(double display) {
        this.display = display;
    }
    
    
    
    
            
}
