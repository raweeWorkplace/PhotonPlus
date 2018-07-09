/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Dao.databaseInitializer;
import Panels.LoginFrame;
import Panels.databaseConfiguration;
import javax.swing.JPanel;

/**
 *
 * @author idiotbox
 */
public class startup_controller {

    public startup_controller(JPanel panel) {
       
        if(new databaseInitializer().checkDBExists("photon")){
            new LoginFrame().setVisible(true);
        }else{
            new databaseConfiguration().setVisible(true);
        }
        new functionTools().close(panel);
    }
    
    
}
