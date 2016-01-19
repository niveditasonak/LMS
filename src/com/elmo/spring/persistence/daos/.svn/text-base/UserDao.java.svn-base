package com.elmo.spring.persistence.daos;

import com.elmo.spring.persistence.dos.User;
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
 * User Data Access Object (DAO).
 * This class contains all database handling that is needed to
 * permanently store and retrieve User object instances.
 */

public class UserDao {

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
     * and then this method can be overrided to return extended valueObject.
     * NOTE: If you extend the valueObject class, make sure to override the
     * clone() method in it!
     */
    public static User createValueObject() {
        return new User();
    }


    /**
     * getObject-method. This will create and load valueObject contents from database
     * using given Primary-Key as identifier. This method is just a convenience method
     * for the real load-method which accepts the valueObject as a parameter. Returned
     * valueObject will be created using the createValueObject() method.
     */
    public static User getObject(long user_id) throws NotFoundException, SQLException {

        User valueObject = createValueObject();
        valueObject.setUser_id(user_id);
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
    public static void load(User valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM USER_TABLE WHERE (USER_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getUser_id());

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
    public static List<User> loadAll() throws SQLException {

        String sql = "SELECT * FROM USER_TABLE ORDER BY USER_ID ASC ";
        List<User> searchResults = new ArrayList<User>();
        try {
            searchResults = listQuery(conn.prepareStatement(sql));
        } catch (Exception e) {
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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be created.
     *                    If automatic surrogate-keys are not used the Primary-key
     *                    field must be set for this to work properly.
     */
    public static synchronized void create(User valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {

            Long primaryId = Toolbox.getSequenceNextValue("seq_USER_TABLE");
            valueObject.setUser_id(primaryId);


            sql = "INSERT INTO USER_TABLE ( USER_ID, USER_TYPE, NAME, "
                    + "USER_NAME, USER_PASSWORD, USER_BIODATA) VALUES (?, ?, ?, ?, ora_hash(?), ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setString(2, valueObject.getUser_type());
            stmt.setString(3, valueObject.getName());
            stmt.setString(4, valueObject.getUser_name());
            stmt.setString(5, valueObject.getUser_password());
            stmt.setString(6, valueObject.getUser_biodata());

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
    public static void save(User valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE USER_TABLE SET USER_TYPE = ?, NAME = ?, USER_NAME = ?, "
                + "USER_PASSWORD = ?, USER_BIODATA = ? WHERE (USER_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, valueObject.getUser_type());
            stmt.setString(2, valueObject.getName());
            stmt.setString(3, valueObject.getUser_name());
            stmt.setString(4, valueObject.getUser_password());
            stmt.setString(5, valueObject.getUser_biodata());

            stmt.setLong(6, valueObject.getUser_id());

            long rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {

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

    public static void saveProfile(User valueObject) {

        String sql = "UPDATE USER_TABLE SET NAME = ?, "
                + "USER_BIODATA = ? WHERE (USER_ID = ? )";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, valueObject.getName());
            stmt.setString(2, valueObject.getUser_biodata());

            stmt.setLong(3, valueObject.getUser_id());

            long rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {

                throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
            }
            if (rowcount > 1) {
                //System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
                throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    public static void delete(User valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM USER_TABLE WHERE (USER_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getUser_id());

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
    public static long countAll() throws SQLException {

        // Check the cache status and use Cache if possible.
        if (cacheOk) {
            return cacheData.size();
        }


        String sql = "SELECT count(*) FROM USER_TABLE";
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
    public static List<User> searchMatching(User valueObject) throws SQLException {

        List<User> searchResults;

        if (valueObject.getUser_password()!=null) {
            valueObject.setUser_password(Long.toString(Toolbox.getPasswordHash(valueObject.getUser_password())));
        }
        StringBuffer sql = new StringBuffer("SELECT * FROM USER_TABLE WHERE 1=1 ");

        if (valueObject.getUser_id() != 0) {
            sql.append("AND USER_ID = ? ");
        }

        if (valueObject.getUser_type() != null) {
            sql.append("AND USER_TYPE = ? ");
        }

        if (valueObject.getName() != null) {
            sql.append("AND NAME = ? ");
        }

        if (valueObject.getUser_name() != null) {

            sql.append("AND USER_NAME = ? ");
        }

        if (valueObject.getUser_password() != null) {
            sql.append("AND USER_PASSWORD = ? ");
        }

        if (valueObject.getUser_biodata() != null) {
            sql.append("AND USER_BIODATA = ? ");
        }


        sql.append("ORDER BY USER_ID ASC ");


        final PreparedStatement stmt = conn.prepareStatement(sql.toString());
int i = 1;
        if (valueObject.getUser_id() != 0) {

            stmt.setLong(i++, valueObject.getUser_id());
        }

        if (valueObject.getUser_type() != null) {

            stmt.setString(i++,valueObject.getUser_type());
        }

        if (valueObject.getName() != null) {
            stmt.setString(i++,valueObject.getName());
        }

        if (valueObject.getUser_name() != null) {
            stmt.setString(i++,valueObject.getUser_name());
        }

        if (valueObject.getUser_password() != null) {
            stmt.setString(i++,valueObject.getUser_password());
        }

        if (valueObject.getUser_biodata() != null) {
            stmt.setString(i++,valueObject.getUser_biodata());
        }



        searchResults = listQuery(stmt);

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
    protected static void singleQuery(PreparedStatement stmt, User valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setUser_id(result.getLong("USER_ID"));
                valueObject.setUser_type(result.getString("USER_TYPE"));
                valueObject.setName(result.getString("NAME"));
                valueObject.setUser_name(result.getString("USER_NAME"));
                valueObject.setUser_password(result.getString("USER_PASSWORD"));
                valueObject.setUser_biodata(result.getString("USER_BIODATA"));

            } else {
                //System.out.println("User Object Not Found!");
                throw new NotFoundException("User Object Not Found!");
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
    protected static List<User> listQuery(PreparedStatement stmt) throws SQLException {

        List<User> searchResults = new ArrayList<User>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                User temp = createValueObject();

                temp.setUser_id(result.getLong("USER_ID"));
                temp.setUser_type(result.getString("USER_TYPE"));
                temp.setName(result.getString("NAME"));
                temp.setUser_name(result.getString("USER_NAME"));
                temp.setUser_password(result.getString("USER_PASSWORD"));
                temp.setUser_biodata(result.getString("USER_BIODATA"));

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