package com.elmo.spring.persistence.daos;

import java.sql.*;
import java.util.*;
import java.math.*;

import com.elmo.spring.persistence.dos.Assignment;
import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.elmo.spring.persistence.dos.File;
import com.elmo.spring.persistence.helpers.NotFoundException;
import com.elmo.spring.persistence.helpers.ConnectionManager;


public class FileDao {


    private static Connection conn;

    static {
        conn = ConnectionManager.getInstance().getConnection();

    }

    public static File createValueObject() {
        return new File();
    }


    public static File getObject(long file_id) throws NotFoundException, SQLException {

        File valueObject = createValueObject();
        valueObject.setFile_id(file_id);
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
     *         This method requires working database connection.
     *  valueObject This parameter contains the class instance to be loaded.
     *                    Primary-key field must be set for this to work properly.
     */
    public static void load(File valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM FILE_TABLE WHERE (FILE_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getFile_id());

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
     *  conn This method requires working database connection.
     */
    public static List<File> loadAll() throws SQLException {

        String sql = "SELECT * FROM FILE_TABLE ORDER BY FILE_ID ASC ";
        List<File> searchResults = new ArrayList<File>();

        try {
            searchResults = listQuery(conn.prepareStatement(sql));
        } catch (Exception e) {
            System.out.println("caught");
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
     *  conn        This method requires working database connection.
     *  valueObject This parameter contains the class instance to be created.
     *                    If automatic surrogate-keys are not used the Primary-key
     *                    field must be set for this to work properly.
     */


    public static List<File> loadAllForCSId(Long semId) throws SQLException {

        String sql = "SELECT * FROM FILE_TABLE WHERE COURSE_ID = ? AND FILE_DATE_EXPIRED > CURRENT_DATE ORDER BY FILE_ID ASC ";
        List<File> searchResults = new ArrayList<File>();

        try {
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,semId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;
    }


    public static synchronized void create(File valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {

            Long primaryId = Toolbox.getSequenceNextValue("SEQ_FILE_TABLE");
            valueObject.setFile_id(primaryId);

            sql = "INSERT INTO FILE_TABLE ( FILE_ID, COURSE_ID, FILE_DATE_CREATED, "
                    + "FILE_DATE_PUBLISHED, FILE_DATE_EXPIRED, FILE_CONTENT, "
                    + "UPLOADED_BY_ID) VALUES (?, ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setLong(2, valueObject.getCourse_id());
            stmt.setTimestamp(3, valueObject.getFile_date_created());
            stmt.setTimestamp(4, valueObject.getFile_date_published());
            stmt.setTimestamp(5, valueObject.getFile_date_expired());
            stmt.setBytes(6, valueObject.getFile_content());
            stmt.setLong(7, valueObject.getUploaded_by_id());

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
     *  conn        This method requires working database connection.
     *  valueObject This parameter contains the class instance to be saved.
     *                    Primary-key field must be set for this to work properly.
     */
    public static void save(File valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE FILE_TABLE SET COURSE_ID = ?, FILE_DATE_CREATED = ?, FILE_DATE_PUBLISHED = ?, "
                + "FILE_DATE_EXPIRED = ?, FILE_CONTENT = ?, UPLOADED_BY_ID = ? WHERE (FILE_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getCourse_id());
            stmt.setTimestamp(2, valueObject.getFile_date_created());
            stmt.setTimestamp(3, valueObject.getFile_date_published());
            stmt.setTimestamp(4, valueObject.getFile_date_expired());
            stmt.setBytes(5, valueObject.getFile_content());
            stmt.setLong(6, valueObject.getUploaded_by_id());

            stmt.setLong(7, valueObject.getFile_id());

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
     *  conn        This method requires working database connection.
     *  valueObject This parameter contains the class instance to be deleted.
     *                    Primary-key field must be set for this to work properly.
     */
    public static void delete(File valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM FILE_TABLE WHERE (FILE_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getFile_id());

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
     *  conn This method requires working database connection.
     */
    public static long countAll() throws SQLException {

        String sql = "SELECT count(*) FROM FILE_TABLE";
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


    public static List<File> searchMatching(File valueObject) throws SQLException {

        List<File> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM FILE_TABLE WHERE 1=1 ");

        if (valueObject.getFile_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND FILE_ID = ").append(valueObject.getFile_id()).append(" ");
        }

        if (valueObject.getCourse_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_ID = ").append(valueObject.getCourse_id()).append(" ");
        }

        if (valueObject.getFile_date_created() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND FILE_DATE_CREATED = '").append(valueObject.getFile_date_created()).append("' ");
        }

        if (valueObject.getFile_date_published() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND FILE_DATE_PUBLISHED = '").append(valueObject.getFile_date_published()).append("' ");
        }

        if (valueObject.getFile_date_expired() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND FILE_DATE_EXPIRED = '").append(valueObject.getFile_date_expired()).append("' ");
        }

        if (valueObject.getFile_content() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND FILE_CONTENT LIKE '").append(valueObject.getFile_content()).append("%' ");
        }

        if (valueObject.getUploaded_by_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND UPLOADED_BY_ID = ").append(valueObject.getUploaded_by_id()).append(" ");
        }


        sql.append("ORDER BY FILE_ID ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first)
            searchResults = new ArrayList<File>();
        else
            searchResults = listQuery(conn.prepareStatement(sql.toString()));

        return searchResults;
    }

    protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

        long result = stmt.executeUpdate();

        return result;
    }


    protected static void singleQuery(PreparedStatement stmt, File valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setFile_id(result.getLong("FILE_ID"));
                valueObject.setCourse_id(result.getLong("COURSE_ID"));
                valueObject.setFile_date_created(result.getTimestamp("FILE_DATE_CREATED"));
                valueObject.setFile_date_published(result.getTimestamp("FILE_DATE_PUBLISHED"));
                valueObject.setFile_date_expired(result.getTimestamp("FILE_DATE_EXPIRED"));
                valueObject.setFile_content(result.getBytes("FILE_CONTENT"));
                valueObject.setUploaded_by_id(result.getLong("UPLOADED_BY_ID"));

            } else {
                //System.out.println("File Object Not Found!");
                throw new NotFoundException("File Object Not Found!");
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
     *  conn This method requires working database connection.
     *  stmt This parameter contains the SQL statement to be excuted.
     */
    protected static List<File> listQuery(PreparedStatement stmt) throws SQLException {

        List<File> searchResults = new ArrayList<File>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                File temp = createValueObject();

                temp.setFile_id(result.getLong("FILE_ID"));
                temp.setCourse_id(result.getLong("COURSE_ID"));
                temp.setFile_date_created(result.getTimestamp("FILE_DATE_CREATED"));
                temp.setFile_date_published(result.getTimestamp("FILE_DATE_PUBLISHED"));
                temp.setFile_date_expired(result.getTimestamp("FILE_DATE_EXPIRED"));
                temp.setFile_content(result.getBytes("FILE_CONTENT"));
                temp.setUploaded_by_id(result.getLong("UPLOADED_BY_ID"));

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
