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
@Table(name = "sales_table",schema = "root")
public class sales_pojo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sales_id;
    
    @Column
    private int bill_no;
    
    @Column
    private int item_code;
    
    @Column(nullable = false)
    private int qty;
    
    @Column(nullable = false)
    private double cost;

    public sales_pojo(int bill_no, int item_code, int qty, double cost) {
        super();
        this.bill_no = bill_no;
        this.item_code = item_code;
        this.qty = qty;
        this.cost = cost;
    }

    public sales_pojo() {
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public int getBill_no() {
        return bill_no;
    }

    public void setBill_no(int bill_no) {
        this.bill_no = bill_no;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
    
           
}
