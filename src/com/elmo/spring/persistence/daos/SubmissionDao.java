package com.elmo.spring.persistence.daos;

import com.elmo.spring.persistence.dos.Submission;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;
import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDao {

    private static Connection conn;

    static {
        conn = ConnectionManager.getInstance().getConnection();

    }

    public static Submission createValueObject() {
        return new Submission();
    }


    public static Submission getObject(long submission_id) throws NotFoundException, SQLException {

        Submission valueObject = createValueObject();
        valueObject.setSubmission_id(submission_id);
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
    public static void load(Submission valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE (SUBMISSION_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getSubmission_id());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static List<Submission> loadAll() throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            searchResults = listQuery(conn.prepareStatement(sql));
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;
    }



    public static List<Submission> loadAllForAssignmentId(Long assId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE assign_id = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,assId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught in loadAllForAssignmentId");
        }

        return searchResults;
    }


    public static List<Submission> loadAllForAssignmentIdForEnrId(Long assId, Long enrId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE assign_id = ? and UPLOADED_BY_ENR_ID = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,assId);
            stmt.setLong(2,enrId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught in loadAllForAssignmentId");
        }

        return searchResults;
    }


    public static List<Submission> loadAllForQuizId(Long quizId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE quiz_id = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, quizId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught in loadAllForQuizId");
        }

        return searchResults;
    }

    public static List<Submission> loadAllForQuizIdForEnrId(Long quizId, Long enrId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE quiz_id = ? and uploaded_by_enr_id = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, quizId);
            stmt.setLong(2,enrId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught in loadAllForQuizId");
        }

        return searchResults;
    }

    public static List<Submission> loadAllForEnrollmentId(Long enrId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE UPLOADED_BY_ENR_ID = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,enrId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught loading enrollment for grades");
        }

        return searchResults;
    }

    public static List<Submission> loadAllForEnrollmentIdQuizId(Long enrId, Long quizId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE UPLOADED_BY_ENR_ID = ? and Quiz_id = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,enrId);
            stmt.setLong(2,quizId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught loading enrollment for grades");
        }

        return searchResults;
    }


    public static List<Submission> loadAllForEnrollmentIdAssId(Long enrId, Long assId) throws SQLException {

        String sql = "SELECT * FROM SUBMISSION_TABLE WHERE UPLOADED_BY_ENR_ID = ? and assign_id = ? ORDER BY SUBMISSION_ID ASC ";
        List<Submission> searchResults = new ArrayList<Submission>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,enrId);
            stmt.setLong(2, assId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught loading enrollment for grades");
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
    public static synchronized void create(Submission valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            Long primaryId = Toolbox.getSequenceNextValue("SEQ_SUBMISSION_TABLE");
            valueObject.setSubmission_id(primaryId);


            sql = "INSERT INTO SUBMISSION_TABLE ( SUBMISSION_ID, ASSIGN_ID, QUIZ_ID, "
                    + "SCORE_RECEIVED, SUBMISSION_DATE, ASSIGN_UPLOADED, "
                    + "UPLOADED_BY_ENR_ID) VALUES (?, ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setLong(2, valueObject.getAssign_id());
            stmt.setLong(3, valueObject.getQuiz_id());
            stmt.setLong(4, valueObject.getScore_received());
            stmt.setTimestamp(5, valueObject.getSubmission_date());
          //  stmt.setBlob(6, valueObject.getAssign_uploaded());
            stmt.setBinaryStream(6,valueObject.getAssign_uploaded());
            stmt.setLong(7, valueObject.getUploaded_by_enr_id());

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
    public static void save(Submission valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE SUBMISSION_TABLE SET ASSIGN_ID = ?, QUIZ_ID = ?, SCORE_RECEIVED = ?, "
                + "SUBMISSION_DATE = ?, ASSIGN_UPLOADED = ?, UPLOADED_BY_ENR_ID = ? WHERE (SUBMISSION_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getAssign_id());
            stmt.setLong(2, valueObject.getQuiz_id());
            stmt.setLong(3, valueObject.getScore_received());
            stmt.setTimestamp(4, valueObject.getSubmission_date());
            stmt.setBlob(5, valueObject.getAssign_uploaded());
            stmt.setLong(6, valueObject.getUploaded_by_enr_id());

            stmt.setLong(7, valueObject.getSubmission_id());

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


    public static void delete(Submission valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM SUBMISSION_TABLE WHERE (SUBMISSION_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getSubmission_id());

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


    public static long countAll() throws SQLException {

        String sql = "SELECT count(*) FROM SUBMISSION_TABLE";
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


    public static List<Submission> searchMatching(Submission valueObject) throws SQLException {

        List<Submission> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM SUBMISSION_TABLE WHERE 1=1 ");

        if (valueObject.getSubmission_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND SUBMISSION_ID = ").append(valueObject.getSubmission_id()).append(" ");
        }

        if (valueObject.getAssign_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_ID = ").append(valueObject.getAssign_id()).append(" ");
        }

        if (valueObject.getQuiz_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND QUIZ_ID = ").append(valueObject.getQuiz_id()).append(" ");
        }

        if (valueObject.getScore_received() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND SCORE_RECEIVED = ").append(valueObject.getScore_received()).append(" ");
        }

        if (valueObject.getSubmission_date() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND SUBMISSION_DATE = '").append(valueObject.getSubmission_date()).append("' ");
        }

        if (valueObject.getAssign_uploaded() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND ASSIGN_UPLOADED LIKE '").append(valueObject.getAssign_uploaded()).append("%' ");
        }

        if (valueObject.getUploaded_by_enr_id() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND UPLOADED_BY_ENR_ID LIKE '").append(valueObject.getUploaded_by_enr_id()).append("%' ");
        }


        sql.append("ORDER BY SUBMISSION_ID ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first)
            searchResults = new ArrayList<Submission>();
        else
            searchResults = listQuery(conn.prepareStatement(sql.toString()));

        return searchResults;
    }


    protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

        long result = stmt.executeUpdate();

        return result;
    }


    protected static void singleQuery(PreparedStatement stmt, Submission valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setSubmission_id(result.getLong("SUBMISSION_ID"));
                valueObject.setAssign_id(result.getLong("ASSIGN_ID"));
                valueObject.setQuiz_id(result.getLong("QUIZ_ID"));
                valueObject.setScore_received(result.getLong("SCORE_RECEIVED"));
                valueObject.setSubmission_date(result.getTimestamp("SUBMISSION_DATE"));
                valueObject.setAssign_uploaded(result.getBinaryStream("ASSIGN_UPLOADED"));
                valueObject.setUploaded_by_enr_id(result.getLong("UPLOADED_BY_ENR_ID"));

            } else {
                //System.out.println("Submission Object Not Found!");
                throw new NotFoundException("Submission Object Not Found!");
            }
        } finally {
            if (result != null)
                result.close();
            if (stmt != null)
                stmt.close();
        }
    }


    protected static List<Submission> listQuery(PreparedStatement stmt) throws SQLException {

        List<Submission> searchResults = new ArrayList<Submission>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                Submission temp = createValueObject();

                temp.setSubmission_id(result.getLong("SUBMISSION_ID"));
                temp.setAssign_id(result.getLong("ASSIGN_ID"));
                temp.setQuiz_id(result.getLong("QUIZ_ID"));
                temp.setScore_received(result.getLong("SCORE_RECEIVED"));
                temp.setSubmission_date(result.getTimestamp("SUBMISSION_DATE"));
               temp.setAssign_uploaded(result.getBinaryStream("ASSIGN_UPLOADED"));
                temp.setUploaded_by_enr_id(result.getLong("UPLOADED_BY_ENR_ID"));

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