package com.elmo.spring.persistence.daos;


import com.elmo.spring.persistence.dos.Assignment;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;
import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDao {


    private static Connection conn;

    static {
        conn = ConnectionManager.getInstance().getConnection();

    }

    public static Assignment createValueObject() {
        return new Assignment();
    }


    public static Assignment getObject(long assignment_id) throws NotFoundException, SQLException {

        Assignment valueObject = createValueObject();
        valueObject.setAssignment_id(assignment_id);
        load(valueObject);
        return valueObject;
    }


    public static void load(Assignment valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM ASSIGNMENT_TABLE WHERE (ASSIGNMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getAssignment_id());

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
    public static List<Assignment> loadAllForCSId(Long semId) throws SQLException {

        String sql = "SELECT * FROM ASSIGNMENT_TABLE WHERE COURSE_SEM_ID = ? ORDER BY ASSIGNMENT_ID ASC ";
        List<Assignment> searchResults = new ArrayList<Assignment>();

        try {
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,semId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }


        return searchResults;
    }

    public static List<Assignment> loadAllForCSIdExceptUnpublished(Long semId) throws SQLException {

        String sql = "SELECT * FROM ASSIGNMENT_TABLE WHERE COURSE_SEM_ID = ? AND ASSIGN_DATE_CREATED < ASSIGN_DATE_PUBLISHED ORDER BY ASSIGNMENT_ID ASC ";
        List<Assignment> searchResults = new ArrayList<Assignment>();

        try {
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,semId);
            searchResults = listQuery(stmt);
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
     * @param conn        This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be created.
     *                    If automatic surrogate-keys are not used the Primary-key
     *                    field must be set for this to work properly.
     */
    public static synchronized void create(Assignment valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            Long primaryId = Toolbox.getSequenceNextValue("SEQ_ASSIGNMENT_TABLE");
            valueObject.setAssignment_id(primaryId);

            sql = "INSERT INTO ASSIGNMENT_TABLE ( ASSIGNMENT_ID, UPLOADED_BY_ENR_ID, ASSIGN_DESCRIPTION, "
                    + "ASSIGN_DATE_CREATED, ASSIGN_DATE_PUBLISHED, ASSIGN_DATE_EXPIRED, "
                    + "ASSIGN_DATE_DUE, COURSE_SEM_ID, ASSIGNMENT_TITLE, "
                    + "SCORE_MAX) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setLong(2, valueObject.getUploaded_by_id());
            stmt.setString(3, valueObject.getAssign_description());
            stmt.setTimestamp(4, valueObject.getAssign_date_created());
            stmt.setTimestamp(5, valueObject.getAssign_date_published());
            stmt.setTimestamp(6, valueObject.getAssign_date_expired());
            stmt.setTimestamp(7, valueObject.getAssign_date_due());
            stmt.setLong(8, valueObject.getCourse_sem_id());
            stmt.setString(9, valueObject.getAssignment_title());
            stmt.setLong(10, valueObject.getScore_max());

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
    public static void save(Assignment valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE ASSIGNMENT_TABLE SET UPLOADED_BY_ENR_ID = ?, ASSIGN_DESCRIPTION = ?, ASSIGN_DATE_CREATED = ?, "
                + "ASSIGN_DATE_PUBLISHED = ?, ASSIGN_DATE_EXPIRED = ?, ASSIGN_DATE_DUE = ?, "
                + "COURSE_SEM_ID = ?, ASSIGNMENT_TITLE = ?, SCORE_MAX = ? WHERE (ASSIGNMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getUploaded_by_id());
            stmt.setString(2, valueObject.getAssign_description());
            stmt.setTimestamp(3, valueObject.getAssign_date_created());
            stmt.setTimestamp(4, valueObject.getAssign_date_published());
            stmt.setTimestamp(5, valueObject.getAssign_date_expired());
            stmt.setTimestamp(6, valueObject.getAssign_date_due());
            stmt.setLong(7, valueObject.getCourse_sem_id());
            stmt.setString(8, valueObject.getAssignment_title());
            stmt.setLong(9, valueObject.getScore_max());

            stmt.setLong(10, valueObject.getAssignment_id());

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
    public static void delete(Assignment valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM ASSIGNMENT_TABLE WHERE (ASSIGNMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getAssignment_id());

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

        String sql = "SELECT count(*) FROM ASSIGNMENT_TABLE";
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
    public static List<Assignment> searchMatching(Assignment valueObject) throws SQLException {

        List<Assignment> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM ASSIGNMENT_TABLE WHERE 1=1 ");

        if (valueObject.getAssignment_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGNMENT_ID = ").append(valueObject.getAssignment_id()).append(" ");
        }

        if (valueObject.getUploaded_by_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND UPLOADED_BY_ENR_ID = ").append(valueObject.getUploaded_by_id()).append(" ");
        }

        if (valueObject.getAssign_description() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_DESCRIPTION LIKE '").append(valueObject.getAssign_description()).append("%' ");
        }

        if (valueObject.getAssign_date_created() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_DATE_CREATED = '").append(valueObject.getAssign_date_created()).append("' ");
        }

        if (valueObject.getAssign_date_published() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_DATE_PUBLISHED = '").append(valueObject.getAssign_date_published()).append("' ");
        }

        if (valueObject.getAssign_date_expired() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_DATE_EXPIRED = '").append(valueObject.getAssign_date_expired()).append("' ");
        }

        if (valueObject.getAssign_date_due() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_DATE_DUE = '").append(valueObject.getAssign_date_due()).append("' ");
        }

        if (valueObject.getCourse_sem_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_SEM_ID = ").append(valueObject.getCourse_sem_id()).append(" ");
        }

        if (valueObject.getAssignment_title() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGNMENT_TITLE LIKE '").append(valueObject.getAssignment_title()).append("%' ");
        }

        if (valueObject.getScore_max() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND SCORE_MAX = ").append(valueObject.getScore_max()).append(" ");
        }


        sql.append("ORDER BY ASSIGNMENT_ID ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first)
            searchResults = new ArrayList<Assignment>();
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
    protected static void singleQuery(PreparedStatement stmt, Assignment valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setAssignment_id(result.getLong("ASSIGNMENT_ID"));
                valueObject.setUploaded_by_id(result.getLong("UPLOADED_BY_ENR_ID"));
                valueObject.setAssign_description(result.getString("ASSIGN_DESCRIPTION"));
                valueObject.setAssign_date_created(result.getTimestamp("ASSIGN_DATE_CREATED"));
                valueObject.setAssign_date_published(result.getTimestamp("ASSIGN_DATE_PUBLISHED"));
                //valueObject.setAssign_date_expired(result.getTimestamp("ASSIGN_DATE_EXPIRED"));
                valueObject.setAssign_date_due(result.getTimestamp("ASSIGN_DATE_DUE"));
                valueObject.setCourse_sem_id(result.getLong("COURSE_SEM_ID"));
                valueObject.setAssignment_title(result.getString("ASSIGNMENT_TITLE"));
                valueObject.setScore_max(result.getLong("SCORE_MAX"));

            } else {
                //System.out.println("Assignment Object Not Found!");
                throw new NotFoundException("Assignment Object Not Found!");
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
    protected static List<Assignment> listQuery(PreparedStatement stmt) throws SQLException {

        List<Assignment> searchResults = new ArrayList<Assignment>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                Assignment temp = createValueObject();

                temp.setAssignment_id(result.getLong("ASSIGNMENT_ID"));
                temp.setUploaded_by_id(result.getLong("UPLOADED_BY_ENR_ID"));
                temp.setAssign_description(result.getString("ASSIGN_DESCRIPTION"));
                temp.setAssign_date_created(result.getTimestamp("ASSIGN_DATE_CREATED"));
                temp.setAssign_date_published(result.getTimestamp("ASSIGN_DATE_PUBLISHED"));
              //  temp.setAssign_date_expired(result.getTimestamp("ASSIGN_DATE_EXPIRED"));
                temp.setAssign_date_due(result.getTimestamp("ASSIGN_DATE_DUE"));
                temp.setCourse_sem_id(result.getLong("COURSE_SEM_ID"));
                temp.setAssignment_title(result.getString("ASSIGNMENT_TITLE"));
                temp.setScore_max(result.getLong("SCORE_MAX"));

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
