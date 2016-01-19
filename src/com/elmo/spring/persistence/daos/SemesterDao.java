package com.elmo.spring.persistence.daos;


import com.elmo.spring.persistence.dos.Semester;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;

import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


 /**
  * Semester Data Access Object (DAO).
  * This class contains all database handling that is needed to 
  * permanently store and retrieve Semester object instances. 
  */

public class SemesterDao {

    // Cache contents:
    private static boolean cacheOk;
    private static List cacheData;
    private static Connection conn;


    /**
     * Constructor for Dao. This constructor will only reset cache.
     * If extended Dao classes are generated, it is important to
     * make sure resetCache() will be called in constructor.
     */
    static {
    	conn= ConnectionManager.getInstance().getConnection();
    	
    }

    /**
     * resetCache-method. This is important method when caching is used
     * to keep data in Dao instance. This method must be called whenever
     * the data in table has been changed. This method will mark current
     * cache to be outdated and next time when cacheable data will be
     * retrieved from database, the cache will be rebuilt. Please note
     * that using Dao-cache can have remarkable performace boost or it may
     * not help at all. It all depends on the amount of data and the rate
     * that data is being changed.
     */


    /**
     * createValueObject-method. This method is used when the Dao class needs
     * to create new value object instance. The reason why this method exists
     * is that sometimes the programmer may want to extend also the valueObject
     * and then this method can be overrided to return extended valueObject.
     * NOTE: If you extend the valueObject class, make sure to override the
     * clone() method in it!
     */
    public static Semester createValueObject() {
          return new Semester();
    }


    /**
     * getObject-method. This will create and load valueObject contents from database 
     * using given Primary-Key as identifier. This method is just a convenience method 
     * for the real load-method which accepts the valueObject as a parameter. Returned
     * valueObject will be created using the createValueObject() method.
     */
    public static Semester getObject(long semester_id) throws NotFoundException, SQLException {

          Semester valueObject = createValueObject();
          valueObject.setSemester_id(semester_id);
          load(valueObject);
          return valueObject;
    }


    /**
     * load-method. This will load valueObject contents from database using
     * Primary-Key as identifier. Upper layer should use this so that valueObject
     * instance is created and only primary-key should be specified. Then call
     * this method to complete other persistent information. This method will
     * overwrite all other fields except primary-key and possible runtime variables.
     * If load can not find matching row, NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be loaded.
     *                     Primary-key field must be set for this to work properly.
     */
    public static void load( Semester valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM SEMESTER_TABLE WHERE (SEMESTER_ID = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setLong(1, valueObject.getSemester_id()); 

               singleQuery(stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * LoadAll-method. This will read all contents from database table and
     * build a List containing valueObjects. Please note, that this method
     * will consume huge amounts of resources if table has lot's of rows. 
     * This should only be used when target tables have only small amounts
     * of data.
     *
     * @param conn         This method requires working database connection.
     */
    public static List<Semester> loadAll() throws SQLException {

          String sql = "SELECT * FROM SEMESTER_TABLE ORDER BY SEMESTER_ID ASC ";
          List<Semester> searchResults = new ArrayList<Semester>();
        		  
        		 try {
        	             searchResults = listQuery(conn.prepareStatement(sql));
        	         }
        	         catch(Exception e)
        	         {
        	             System.out.println("caugt");
        	         }

          return searchResults;
    }



    /**
     * create-method. This will create new row in database according to supplied
     * valueObject contents. Make sure that values for all NOT NULL columns are
     * correctly specified. Also, if this table does not use automatic surrogate-keys
     * the primary-key must be specified. After INSERT command this method will 
     * read the generated primary-key back to valueObject if automatic surrogate-keys
     * were used. 
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be created.
     *                     If automatic surrogate-keys are not used the Primary-key 
     *                     field must be set for this to work properly.
     */
    public static synchronized void create( Semester valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
        	  Long primaryId = Toolbox.getSequenceNextValue("SEQ_SEMESTER_TABLE");
              valueObject.setSemester_id(primaryId);
              
               sql = "INSERT INTO SEMESTER_TABLE ( SEMESTER_ID, SEMESTER_TYPE, SEM_START_DATE, "
               + "SEM_END_DATE) VALUES (?, ?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setString(1, primaryId.toString()); 
               stmt.setString(2, valueObject.getSemester_type()); 
               stmt.setTimestamp(3, valueObject.getSem_start_date()); 
               stmt.setTimestamp(4, valueObject.getSem_end_date()); 

               long rowcount = databaseUpdate(stmt);
               if (rowcount != 1) {
                    //System.out.println("PrimaryKey Error when updating DB!");
                    throw new SQLException("PrimaryKey Error when updating DB!");
               }

          } finally {
              if (stmt != null)
                  stmt.close();
          }


    }


    /**
     * save-method. This method will save the current state of valueObject to database.
     * Save can not be used to create new instances in database, so upper layer must
     * make sure that the primary-key is correctly specified. Primary-key will indicate
     * which instance is going to be updated in database. If save can not find matching 
     * row, NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be saved.
     *                     Primary-key field must be set for this to work properly.
     */
    public static void save( Semester valueObject)
          throws NotFoundException, SQLException {

          String sql = "UPDATE SEMESTER_TABLE SET SEMESTER_TYPE = ?, SEM_START_DATE = ?, SEM_END_DATE = ? WHERE (SEMESTER_ID = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, valueObject.getSemester_type()); 
              stmt.setTimestamp(2, valueObject.getSem_start_date()); 
              stmt.setTimestamp(3, valueObject.getSem_end_date()); 

              stmt.setLong(4, valueObject.getSemester_id()); 

              long rowcount = databaseUpdate(stmt);
              if (rowcount == 0) {
                   //System.out.println("Object could not be saved! (PrimaryKey not found)");
                   throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
              }
              if (rowcount > 1) {
                   //System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
              }
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * delete-method. This method will remove the information from database as identified by
     * by primary-key in supplied valueObject. Once valueObject has been deleted it can not 
     * be restored by calling save. Restoring can only be done using create method but if 
     * database is using automatic surrogate-keys, the resulting object will have different 
     * primary-key than what it was in the deleted object. If delete can not find matching row,
     * NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be deleted.
     *                     Primary-key field must be set for this to work properly.
     */
    public static void delete( Semester valueObject)
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM SEMESTER_TABLE WHERE (SEMESTER_ID = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setLong(1, valueObject.getSemester_id()); 

              long rowcount = databaseUpdate(stmt);
              if (rowcount == 0) {
                   //System.out.println("Object could not be deleted (PrimaryKey not found)");
                   throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
              }
              if (rowcount > 1) {
                   //System.out.println("PrimaryKey Error when updating DB! (Many objects were deleted!)");
                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
              }
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * coutAll-method. This method will return the number of all rows from table that matches
     * this Dao. The implementation will simply execute "select count(primarykey) from table".
     * If table is empty, the return value is 0. This method should be used before calling
     * loadAll, to make sure table has not too many rows.
     *
     * @param conn         This method requires working database connection.
     */
    public static long countAll() throws SQLException {

          // Check the cache status and use Cache if possible.
          if (cacheOk) {
              return cacheData.size();
          }


          String sql = "SELECT count(*) FROM SEMESTER_TABLE";
          PreparedStatement stmt = null;
          ResultSet result = null;
          long allRows = 0;

          try {
              stmt = conn.prepareStatement(sql);
              result = stmt.executeQuery();

              if (result.next())
                  allRows = result.getLong(1);
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
          return allRows;
    }
    

    /** 
     * searchMatching-Method. This method provides searching capability to 
     * get matching valueObjects from database. It works by searching all 
     * objects that match permanent instance variables of given object.
     * Upper layer should use this by setting some parameters in valueObject
     * and then  call searchMatching. The result will be 0-N objects in a List, 
     * all matching those criteria you specified. Those instance-variables that
     * have NULL values are excluded in search-criteria.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance where search will be based.
     *                     Primary-key field should not be set.
     */
    public static List<Semester> searchMatching( Semester valueObject) throws SQLException {

          List<Semester> searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM SEMESTER_TABLE WHERE 1=1 ");

          if (valueObject.getSemester_id() != 0) {
              if (first) { first = false; }
              sql.append("AND SEMESTER_ID = ").append(valueObject.getSemester_id()).append(" ");
          }

          if (valueObject.getSemester_type() != null) {
              if (first) { first = false; }
              sql.append("AND SEMESTER_TYPE LIKE '").append(valueObject.getSemester_type()).append("%' ");
          }

          if (valueObject.getSem_start_date() != null) {
              if (first) { first = false; }
              sql.append("AND SEM_START_DATE = '").append(valueObject.getSem_start_date()).append("' ");
          }

          if (valueObject.getSem_end_date() != null) {
              if (first) { first = false; }
              sql.append("AND SEM_END_DATE = '").append(valueObject.getSem_end_date()).append("' ");
          }


          sql.append("ORDER BY SEMESTER_ID ASC ");

          // Prevent accidential full table results.
          // Use loadAll if all rows must be returned.
          if (first)
               searchResults = new ArrayList();
          else
               searchResults = listQuery(conn.prepareStatement(sql.toString()));

          return searchResults;
    }

    /**
     * databaseUpdate-method. This method is a helper method for internal use. It will execute
     * all database handling that will change the information in tables. SELECT queries will
     * not be executed here however. The return value indicates how many rows were affected.
     * This method will also make sure that if cache is used, it will reset when data changes.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     */
    protected static long databaseUpdate( PreparedStatement stmt) throws SQLException {

          long result = stmt.executeUpdate();
          return result;
    }

     public static String getSemesterName(String d) {
         String sql = "select SEMESTER_TYPE FROM SEMESTER_TABLE where ? between SEM_START_DATE AND SEM_END_DATE";
         PreparedStatement stmt = null;
         ResultSet result = null;
         String sem=null;
         try {
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, d);

              result = stmt.executeQuery();
              if (result.next()){
              		sem=result.getString("SEMESTER_TYPE");
              }

         }catch (SQLException e)
         {
             e.printStackTrace();
         }
         finally {
             if (stmt != null)
                 try {
                     stmt.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
         }
         return sem;
         
    }


    /**
     * databaseQuery-method. This method is a helper method for internal use. It will execute
     * all database queries that will return only one row. The resultset will be converted
     * to valueObject. If no rows were found, NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     * @param valueObject  Class-instance where resulting data will be stored.
     */
    protected static void singleQuery( PreparedStatement stmt, Semester valueObject)
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setSemester_id(result.getLong("SEMESTER_ID")); 
                   valueObject.setSemester_type(result.getString("SEMESTER_TYPE")); 
                   valueObject.setSem_start_date(result.getTimestamp("SEM_START_DATE")); 
                   valueObject.setSem_end_date(result.getTimestamp("SEM_END_DATE")); 

              } else {
                    //System.out.println("Semester Object Not Found!");
                    throw new NotFoundException("Semester Object Not Found!");
              }
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * databaseQuery-method. This method is a helper method for internal use. It will execute
     * all database queries that will return multiple rows. The resultset will be converted
     * to the List of valueObjects. If no rows were found, an empty List will be returned.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     */
    protected static List<Semester> listQuery( PreparedStatement stmt) throws SQLException {

          List<Semester> searchResults = new ArrayList<Semester>();
          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              while (result.next()) {
                   Semester temp = createValueObject();

                   temp.setSemester_id(result.getLong("SEMESTER_ID")); 
                   temp.setSemester_type(result.getString("SEMESTER_TYPE")); 
                   temp.setSem_start_date(result.getTimestamp("SEM_START_DATE")); 
                   temp.setSem_end_date(result.getTimestamp("SEM_END_DATE")); 

                   searchResults.add(temp);
              }

          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }

          return searchResults;
    }


}