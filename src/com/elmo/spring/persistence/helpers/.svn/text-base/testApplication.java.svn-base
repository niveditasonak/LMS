package com.elmo.spring.persistence.helpers;

import com.elmo.spring.persistence.daos.AnnouncementDao;
import com.elmo.spring.persistence.dos.Announcement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class testApplication {
	
	public static void main(String[] args) throws SQLException, NotFoundException {
		
		DaoFactory fact =new DaoFactory();
		
		//you can directly fetch the row by user_id. check more function in DAO class and use them as per your convenience.
		//System.out.println(fact.getUserDao().getObject(1));
	  AnnouncementDao AnnDao = new AnnouncementDao();
      Connection conn=ConnectionManager.getInstance().getConnection();
      List<Announcement> obj = AnnouncementDao.loadAll();
    System.out.println(obj.get(0).getTo_user_id());
      
      
      
      
      
      
      
      
      
      
      
      //User u1 = userDao.createValueObject();

      /* Load all
      List<User> u = userDao.loadAll(conn);
      System.out.println(u.get(0).getUser_name());*/
      
      /*Get by ID
     
      User u2 = userDao.getObject(conn, 1);
      System.out.println(u2);
      
      */
      
     // u1.setAll(3, "Teacher", "Maoj", "MFR", "Hello123", "Biodata1");// don't run it entry already there
      /*Insert
      userDao.create(conn, u1);*/
      //User u3 = userDao.getObject(3);
      //System.out.println(u3);
  //    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
      //Date date = new Date("10 Feb 2015");
	  //System.out.println(dateFormat.format(date).toString());
      
      // Send date in this Format, You can use Formatter:SimpleDateFormat("dd MMM yyyy") to do it and the convert the date to string
     // System.out.println(s.getSemesterName("10 Feb 2015"));
     
	           
      
		
		
	}

}
