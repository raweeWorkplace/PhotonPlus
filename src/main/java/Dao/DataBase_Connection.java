/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import Pojo.*;

/**
 *
 * @author ranjan
 */
public class DataBase_Connection {

    protected static Connection connectionInstance;

    protected String dbUserName ="root";
    protected String dbPassWord = "";
    protected String dbUrl ="jdbc:mysql://localhost:3306/photon?createDatabaseIfNotExist=true";
    protected String dbDriver ="com.mysql.jdbc.Driver";
    protected static String configFileName;
    protected Statement smtInstance;
    protected Connection conInstance;

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public static String getConfigFileName() {
        return configFileName;
    }

    public static void setConfigFileName(String configFileName) {
        DataBase_Connection.configFileName = configFileName;
    }

    public static Connection getConnectionInstance() {
        if (connectionInstance == null) {
            connectionInstance = new DataBase_Connection().getConnection();
        }
        return connectionInstance;
    }

    public DataBase_Connection() {

    }

    public Connection getConnection() {
        try {
            Class.forName(getDbDriver());
            connectionInstance = DriverManager.getConnection(getDbUrl(), getDbUserName(),getDbPassWord());
            return connectionInstance;
            } catch (ClassNotFoundException ex) {

            
        } catch (SQLException ex) {
            Logger.getLogger(DataBase_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connectionInstance;
    }

    public static void closeConnection() throws SQLException {
        if (connectionInstance != null) {
            connectionInstance.close();
            //System.out.println("DataBase Connection is closed");
        }

    }

 public static void main(String argc[]) {
        DataBase_Connection db = new DataBase_Connection();
        db.getConnection();

        System.out.println(db.toString());

    }
}
