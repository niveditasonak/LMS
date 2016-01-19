package com.elmo.spring.persistence.daos;


import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elmo.spring.persistence.dos.Quiz;
import com.elmo.spring.persistence.helpers.NotFoundException;
import com.elmo.spring.persistence.helpers.ConnectionManager;

public class QuizDao {

    private static Connection conn;

    static {
        conn = ConnectionManager.getInstance().getConnection();

    }

    public static Quiz createValueObject() {
        return new Quiz();
    }


    public static Quiz getObject(long quiz_id) throws NotFoundException, SQLException {

        Quiz valueObject = createValueObject();
        valueObject.setQuiz_id(quiz_id);
        load(valueObject);
        return valueObject;
    }


    public static void load(Quiz valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM QUIZ_TABLE WHERE (QUIZ_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getQuiz_id());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static List<Quiz> loadAllForCSId(Long semId) throws SQLException {

        String sql = "SELECT * FROM QUIZ_TABLE WHERE COURSE_SEM_ID = ? ORDER BY QUIZ_ID ASC ";
        List<Quiz> searchResults = new ArrayList<Quiz>();

        try {
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,semId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;
    }


    public static List<Quiz> loadAllForCSIdExceptUnpublished(Long semId) throws SQLException {

        String sql = "SELECT * FROM QUIZ_TABLE WHERE COURSE_SEM_ID = ? AND QUIZ_DATE_CREATED < QUIZ_DATE_PUBLISHED ORDER BY QUIZ_ID ASC ";
        List<Quiz> searchResults = new ArrayList<Quiz>();

        try {
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,semId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;
    }



    public static List<Quiz> loadAllForEnrId(Long enrId) throws SQLException {

        String sql = "SELECT * FROM QUIZ_TABLE where UPLOADED_BY_ENR_ID = ? ORDER BY QUIZ_ID ASC ";
        List<Quiz> searchResults = new ArrayList<Quiz>();

        try {
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,enrId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught in quizload");
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
    public static synchronized void create(Quiz valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            Long primaryId = Toolbox.getSequenceNextValue("SEQ_QUIZ_TABLE");
            valueObject.setQuiz_id(primaryId);


            sql = "INSERT INTO QUIZ_TABLE ( QUIZ_ID, UPLOADED_BY_ENR_ID, QUIZ_DESCRIPTION, "
                    + "course_sem_id, QUIZ_TITLE, QUIZ_DATE_CREATED, "
                    + "QUIZ_DATE_PUBLISHED, QUIZ_DATE_EXPIRED, QUIZ_DUE_DATE, "
                    + "TIME_TAKEN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setLong(2, valueObject.getUploaded_by_id());
            stmt.setString(3, valueObject.getQuiz_description());
            stmt.setLong(4, valueObject.getCourse_sem_id());
            stmt.setString(5, valueObject.getQuiz_title());
            stmt.setTimestamp(6, valueObject.getQuiz_date_created());
            stmt.setTimestamp(7, valueObject.getQuiz_date_published());
            stmt.setTimestamp(8, valueObject.getQuiz_date_expired());
            stmt.setTimestamp(9, valueObject.getQuiz_due_date());
            stmt.setLong(10, valueObject.getTime_taken());

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
    public static void save(Quiz valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE QUIZ_TABLE SET UPLOADED_BY_ENR_ID = ?, QUIZ_DESCRIPTION = ?, course_sem_id = ?, "
                + "QUIZ_TITLE = ?, QUIZ_DATE_CREATED = ?, QUIZ_DATE_PUBLISHED = ?, "
                + "QUIZ_DATE_EXPIRED = ?, QUIZ_DUE_DATE = ?, TIME_TAKEN = ? WHERE (QUIZ_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getUploaded_by_id());
            stmt.setString(2, valueObject.getQuiz_description());
            stmt.setLong(3, valueObject.getCourse_sem_id());
            stmt.setString(4, valueObject.getQuiz_title());
            stmt.setTimestamp(5, valueObject.getQuiz_date_created());
            stmt.setTimestamp(6, valueObject.getQuiz_date_published());
            stmt.setTimestamp(7, valueObject.getQuiz_date_expired());
            stmt.setTimestamp(8, valueObject.getQuiz_due_date());
            stmt.setLong(9, valueObject.getTime_taken());

            stmt.setLong(10, valueObject.getQuiz_id());

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
    public static void delete(Quiz valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM QUIZ_TABLE WHERE (QUIZ_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getQuiz_id());

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

        String sql = "SELECT count(*) FROM QUIZ_TABLE";
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
    public static List<Quiz> searchMatching(Quiz valueObject) throws SQLException {

        List<Quiz> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM QUIZ_TABLE WHERE 1=1 ");

        if (valueObject.getQuiz_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_ID = ").append(valueObject.getQuiz_id()).append(" ");
        }

        if (valueObject.getUploaded_by_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND UPLOADED_BY_ENR_ID = ").append(valueObject.getUploaded_by_id()).append(" ");
        }

        if (valueObject.getQuiz_description() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_DESCRIPTION LIKE '").append(valueObject.getQuiz_description()).append("%' ");
        }

        if (valueObject.getCourse_sem_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND course_sem_id = ").append(valueObject.getCourse_sem_id()).append(" ");
        }

        if (valueObject.getQuiz_title() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_TITLE LIKE '").append(valueObject.getQuiz_title()).append("%' ");
        }

        if (valueObject.getQuiz_date_created() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_DATE_CREATED = '").append(valueObject.getQuiz_date_created()).append("' ");
        }

        if (valueObject.getQuiz_date_published() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_DATE_PUBLISHED = '").append(valueObject.getQuiz_date_published()).append("' ");
        }

        if (valueObject.getQuiz_date_expired() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_DATE_EXPIRED = '").append(valueObject.getQuiz_date_expired()).append("' ");
        }

        if (valueObject.getQuiz_due_date() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_DUE_DATE = '").append(valueObject.getQuiz_due_date()).append("' ");
        }

        if (valueObject.getTime_taken() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND TIME_TAKEN = ").append(valueObject.getTime_taken()).append(" ");
        }


        sql.append("ORDER BY QUIZ_ID ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first)
            searchResults = new ArrayList<Quiz>();
        else
            searchResults = listQuery(conn.prepareStatement(sql.toString()));

        return searchResults;
    }

    protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

        long result = stmt.executeUpdate();

        return result;
    }


    protected static void singleQuery(PreparedStatement stmt, Quiz valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setQuiz_id(result.getLong("QUIZ_ID"));
                valueObject.setUploaded_by_id(result.getLong("UPLOADED_BY_ENR_ID"));
                valueObject.setQuiz_description(result.getString("QUIZ_DESCRIPTION"));
                valueObject.setCourse_sem_id(result.getLong("COURSE_SEM_ID"));
                valueObject.setQuiz_title(result.getString("QUIZ_TITLE"));
                valueObject.setQuiz_date_created(result.getTimestamp("QUIZ_DATE_CREATED"));
                valueObject.setQuiz_date_published(result.getTimestamp("QUIZ_DATE_PUBLISHED"));
                valueObject.setQuiz_date_expired(result.getTimestamp("QUIZ_DATE_EXPIRED"));
                valueObject.setQuiz_due_date(result.getTimestamp("QUIZ_DUE_DATE"));
                valueObject.setTime_taken(result.getLong("TIME_TAKEN"));

            } else {
                //System.out.println("Quiz Object Not Found!");
                throw new NotFoundException("Quiz Object Not Found!");
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
    protected static List<Quiz> listQuery(PreparedStatement stmt) throws SQLException {

        List<Quiz> searchResults = new ArrayList<Quiz>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                Quiz temp = createValueObject();

                temp.setQuiz_id(result.getLong("QUIZ_ID"));
                temp.setUploaded_by_id(result.getLong("UPLOADED_BY_ENR_ID"));
                temp.setQuiz_description(result.getString("QUIZ_DESCRIPTION"));
                temp.setCourse_sem_id(result.getLong("COURSE_SEM_ID"));
                temp.setQuiz_title(result.getString("QUIZ_TITLE"));
                temp.setQuiz_date_created(result.getTimestamp("QUIZ_DATE_CREATED"));
                temp.setQuiz_date_published(result.getTimestamp("QUIZ_DATE_PUBLISHED"));
                temp.setQuiz_date_expired(result.getTimestamp("QUIZ_DATE_EXPIRED"));
                temp.setQuiz_due_date(result.getTimestamp("QUIZ_DUE_DATE"));
                temp.setTime_taken(result.getLong("TIME_TAKEN"));

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
