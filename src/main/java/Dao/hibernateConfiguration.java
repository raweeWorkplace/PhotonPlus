/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author idiotbox
 */
public class hibernateConfiguration {
    Configuration cnf ;
    SessionFactory sf;
    Session s;
    Transaction t;
    
    public Configuration  doConnection() {
	cnf = new Configuration();
	cnf.configure();
	return cnf;
        }
    
    public void save(Object obj) {
	cnf = doConnection();
        sf = cnf.buildSessionFactory();
        s = sf.openSession();
        t =s.beginTransaction();

	s.saveOrUpdate(obj);
	t.commit();
	s.close();
	sf.close();
        }
}
