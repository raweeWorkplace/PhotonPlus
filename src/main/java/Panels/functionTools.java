/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author idiotbox
 */
public class functionTools {
    
     public void remove_table_data(DefaultTableModel tableModel, JTable table) {
          if(!isEmpty(table)){

        int rowCount = tableModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        }
    }
     
     public static boolean isEmpty(JTable table) {
        if (table != null && table.getModel() != null) {
            return table.getModel().getRowCount()<=0;
        }
        return false;
    }
    
}
