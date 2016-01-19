package com.elmo.spring.persistence.daos;

import com.elmo.spring.persistence.dos.Course_Semester;
import com.elmo.spring.persistence.dos.Enrollment;
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
 * Enrollment Data Access Object (DAO).
 * This class contains all database handling that is needed to
 * permanently store and retrieve Enrollment object instances.
 */
public class EnrollmentDao {

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
        conn = ConnectionManager.getInstance().getConnection();

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
     * and then this method can be override to return extended valueObject.
     * NOTE: If you extend the valueObject class, make sure to override the
     * clone() method in it!
     */
    public static Enrollment createValueObject() {
        return new Enrollment();
    }


    /**
     * getObject-method. This will create and load valueObject contents from database
     * using given Primary-Key as identifier. This method is just a convenience method
     * for the real load-method which accepts the valueObject as a parameter. Returned
     * valueObject will be created using the createValueObject() method.
     */
    public static Enrollment getObject(long enrollment_id) throws NotFoundException, SQLException {

        Enrollment valueObject = createValueObject();
        valueObject.setEnrollment_id(enrollment_id);
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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be loaded.
     *                    Primary-key field must be set for this to work properly.
     */
    public static void load(Enrollment valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM ENROLLMENT_TABLE WHERE (ENROLLMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getEnrollment_id());

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
     * @param conn This method requires working database connection.
     */


    public static List<Enrollment> loadAllForUserId(Long UserId) throws SQLException {

        PreparedStatement stmt = null;

        String sql = "SELECT * FROM ENROLLMENT_TABLE  WHERE USER_ID = ? ORDER BY ENROLLMENT_ID ASC ";
        List<Enrollment> searchResults = new ArrayList<Enrollment>();

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, UserId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("Error fetching Enrollment");
        }

        return searchResults;
    }

    public static List<Enrollment> loadAllForUserIdForCSId(Long UserId, Long csId) throws SQLException {

        PreparedStatement stmt = null;

        String sql = "SELECT * FROM ENROLLMENT_TABLE  WHERE USER_ID = ? and COURSE_SEMESTER_ID = ? ORDER BY ENROLLMENT_ID ASC ";
        List<Enrollment> searchResults = new ArrayList<Enrollment>();

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, UserId);
            stmt.setLong(2,csId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("Error fetching Enrollment");
        }

        return searchResults;
    }


    public static List<Enrollment> loadAllForCOURSE_SEM_ID(Long COURSE_SEM_ID) throws SQLException {

        PreparedStatement stmt = null;

        String sql = "SELECT * FROM ENROLLMENT_TABLE  WHERE COURSE_SEMESTER_ID = ? ORDER BY ENROLLMENT_ID ASC ";
        List<Enrollment> searchResults = new ArrayList<Enrollment>();

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, COURSE_SEM_ID);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("Error fetching Enrollment");
        }

        return searchResults;
    }

    public static List<Enrollment> loadAll() throws SQLException {

        String sql = "SELECT * FROM ENROLLMENT_TABLE ORDER BY ENROLLMENT_ID ASC ";
        List<Enrollment> searchResults = null;

        try {
            searchResults = listQuery(conn.prepareStatement(sql));
        } catch (Exception e) {
            System.out.println("Error while loading Enrollments");
        }


        return searchResults;
    }


    public static List<Enrollment> loadAllForOffering(Long course_sem_id) throws SQLException {

        String sql = "SELECT * FROM ENROLLMENT_TABLE WHERE course_semester_id = ? ORDER BY ENROLLMENT_ID ASC ";
        List<Enrollment> searchResults = new ArrayList<Enrollment>();
        PreparedStatement stmt = conn.prepareStatement(sql);
        try {

            stmt.setLong(1, course_sem_id);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("Error while loading Enrollments");
        }


        return searchResults;
    }

    public static List<Enrollment> loadAllForOfferingForType(Long course_sem_id, String USER_TYPE) throws SQLException {

        String sql = "SELECT * FROM ENROLLMENT_TABLE WHERE COURSE_SEMESTER_ID = ? AND ENROLLMENT_TYPE = ? ORDER BY ENROLLMENT_ID ASC ";
        List<Enrollment> searchResults = new ArrayList<Enrollment>();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setLong(1,course_sem_id);
        stmt.setString(2,USER_TYPE);
        try {
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("Error while loading Enrollments");
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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be created.
     *                    If automatic surrogate-keys are not used the Primary-key
     *                    field must be set for this to work properly.
     */
    public static synchronized void create(Enrollment valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {

            Long primaryId = Toolbox.getSequenceNextValue("seq_ENROLLMENT_TABLE");
            valueObject.setEnrollment_id(primaryId);


            sql = "INSERT INTO ENROLLMENT_TABLE ( ENROLLMENT_ID, USER_ID, COURSE_SEMESTER_ID, "
                    + "ENROLLMENT_TYPE) VALUES (?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setLong(2, valueObject.getUser_id());
            stmt.setLong(3, valueObject.getCourse_sem_id());
            stmt.setString(4, valueObject.getEnrollment_type());

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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be saved.
     *                    Primary-key field must be set for this to work properly.
     */
    public static void save(Enrollment valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE ENROLLMENT_TABLE SET USER_ID = ?, COURSE_SEMESTER_ID = ?, ENROLLMENT_TYPE = ? WHERE (ENROLLMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getUser_id());
            stmt.setLong(2, valueObject.getCourse_sem_id());
            stmt.setString(3, valueObject.getEnrollment_type());

            stmt.setLong(4, valueObject.getEnrollment_id());

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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be deleted.
     *                    Primary-key field must be set for this to work properly.
     */
    public static void delete(Enrollment valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM ENROLLMENT_TABLE WHERE (ENROLLMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getEnrollment_id());

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
     * @param conn This method requires working database connection.
     */
    public static long countAll(Connection conn) throws SQLException {

        // Check the cache status and use Cache if possible.
        if (cacheOk) {
            return cacheData.size();
        }


        String sql = "SELECT count(*) FROM ENROLLMENT_TABLE";
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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance where search will be based.
     *                    Primary-key field should not be set.
     */
    public static List<Enrollment> searchMatching(Enrollment valueObject) throws SQLException {

        List<Enrollment> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM ENROLLMENT_TABLE WHERE 1=1 ");

        if (valueObject.getEnrollment_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND ENROLLMENT_ID = ").append(valueObject.getEnrollment_id()).append(" ");
        }

        if (valueObject.getUser_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND USER_ID = ").append(valueObject.getUser_id()).append(" ");
        }

        if (valueObject.getCourse_sem_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_ID = ").append(valueObject.getCourse_sem_id()).append(" ");
        }

        if (valueObject.getEnrollment_type() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ENROLLMENT_TYPE LIKE '").append(valueObject.getEnrollment_type()).append("%' ");
        }


        sql.append("ORDER BY ENROLLMENT_ID ASC ");

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
     * @param conn This method requires working database connection.
     * @param stmt This parameter contains the SQL statement to be excuted.
     */
    protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

        long result = stmt.executeUpdate();

        return result;
    }


    /**
     * databaseQuery-method. This method is a helper method for internal use. It will execute
     * all database queries that will return only one row. The resultset will be converted
     * to valueObject. If no rows were found, NotFoundException will be thrown.
     *
     * @param conn        This method requires working database connection.
     * @param stmt        This parameter contains the SQL statement to be excuted.
     * @param valueObject Class-instance where resulting data will be stored.
     */
    protected static void singleQuery(PreparedStatement stmt, Enrollment valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setEnrollment_id(result.getLong("ENROLLMENT_ID"));
                valueObject.setUser_id(result.getLong("USER_ID"));
                valueObject.setCourse_sem_id(result.getLong("COURSE_SEMESTER_ID"));
                valueObject.setEnrollment_type(result.getString("ENROLLMENT_TYPE"));

            } else {
                //System.out.println("Enrollment Object Not Found!");
                throw new NotFoundException("Enrollment Object Not Found!");
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
     * @param conn This method requires working database connection.
     * @param stmt This parameter contains the SQL statement to be excuted.
     */
    protected static List<Enrollment> listQuery(PreparedStatement stmt) throws SQLException {

        List<Enrollment> searchResults = new ArrayList<Enrollment>();
        ResultSet result = null;

        try {

            result = stmt.executeQuery();

            while (result.next()) {
                Enrollment temp = createValueObject();

                temp.setEnrollment_id(result.getLong("ENROLLMENT_ID"));
                temp.setUser_id(result.getLong("USER_ID"));
                temp.setCourse_sem_id(result.getLong("COURSE_SEMESTER_ID"));
                temp.setEnrollment_type(result.getString("ENROLLMENT_TYPE"));

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

