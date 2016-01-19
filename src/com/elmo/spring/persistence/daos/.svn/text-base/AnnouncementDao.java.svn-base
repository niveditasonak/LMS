package com.elmo.spring.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elmo.spring.persistence.dos.Announcement;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;

import framework.Toolbox;

public class AnnouncementDao {

    private static Connection conn;

    static {
        conn = ConnectionManager.getInstance().getConnection();

    }

    public static Announcement createValueObject() {
        return new Announcement();
    }


    public static Announcement getObject(long announcement_id) throws NotFoundException, SQLException {

        Announcement valueObject = createValueObject();
        valueObject.setAnnouncement_id(announcement_id);
        load(valueObject);
        return valueObject;
    }


    public static void load(Announcement valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM ANNOUNCEMENT_TABLE WHERE (ANNOUNCEMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getAnnouncement_id());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static List<Announcement> loadAll() throws SQLException {

        String sql = "SELECT * FROM ANNOUNCEMENT_TABLE ORDER BY ANNOUNCEMENT_ID ASC ";

        List<Announcement> searchResults = new ArrayList<Announcement>();

        try {
            searchResults = listQuery(conn.prepareStatement(sql));
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;

    }


    public static List<Announcement> loadAllMessageForUser(Long userId) throws SQLException {
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM ANNOUNCEMENT_TABLE WHERE TO_USER_ID = ? AND (MESSAGE_TYPE = 'Message' OR Message_Type = 'S_Deleted') ORDER BY ANNOUNCEMENT_ID DESC ";

        List<Announcement> searchResults = new ArrayList<Announcement>();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;

    }


    public static List<Announcement> loadAllMessageByUser(Long userId) throws SQLException {
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM ANNOUNCEMENT_TABLE WHERE (Message_Type = 'Message' or Message_Type = 'R_Deleted' or Message_Type = 'Announcement') and FROM_USER_ID = ?  ORDER BY ANNOUNCEMENT_ID DESC ";

        List<Announcement> searchResults = new ArrayList<Announcement>();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;

    }

    public static List<Announcement> loadAllAnnoucementForUser(Long courseSemId) throws SQLException {
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM ANNOUNCEMENT_TABLE WHERE TO_USER_ID = ? AND MESSAGE_TYPE = 'Announcement' ORDER BY ANNOUNCEMENT_ID DESC ";

        List<Announcement> searchResults = new ArrayList<Announcement>();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, courseSemId);
            searchResults = listQuery(stmt);
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;

    }


    public static synchronized void create(Announcement valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            Long primaryId = Toolbox.getSequenceNextValue("SEQ_ANNOUNCEMENT_TABLE");
            valueObject.setAnnouncement_id(primaryId);

            sql = "INSERT INTO ANNOUNCEMENT_TABLE ( ANNOUNCEMENT_ID, FROM_USER_ID, TO_USER_ID, "
                    + "MESSAGE_TITLE, MESSAGE_BODY, DATE_SENT,MESSAGE_TYPE) VALUES (?, ?, ?, ?, ?, ?,?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setLong(2, valueObject.getFrom_user_id());
            stmt.setLong(3, valueObject.getTo_user_id());
            stmt.setString(4, valueObject.getMessage_title());
            stmt.setString(5, valueObject.getMessage_body());
            stmt.setTimestamp(6, valueObject.getDate_sent());
            stmt.setString(7, valueObject.getMessage_type());

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


    public static void save(Announcement valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE ANNOUNCEMENT_TABLE SET MESSAGE_TITLE = ?, "
                + "MESSAGE_BODY = ? WHERE (ANNOUNCEMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, valueObject.getMessage_title());
            stmt.setString(2, valueObject.getMessage_body());


            stmt.setLong(3, valueObject.getAnnouncement_id());

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



    public static void saveToSRDeleted(Announcement valueObject, String deleteType)
            throws NotFoundException, SQLException {

        String sql = "UPDATE ANNOUNCEMENT_TABLE SET MESSAGE_TYPE = ? WHERE (ANNOUNCEMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, deleteType);
            stmt.setLong(2, valueObject.getAnnouncement_id());

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


    public static void delete(Long annId)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM Announcement_table WHERE (ANNOUNCEMENT_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, annId);

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

        String sql = "SELECT count(*) FROM ANNOUNCEMENT_TABLE";
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


    public static List<Announcement> searchMatching(Announcement valueObject) throws SQLException {

        List<Announcement> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM ANNOUNCEMENT_TABLE WHERE 1=1 ");

        if (valueObject.getAnnouncement_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND ANNOUNCEMENT_ID = ").append(valueObject.getAnnouncement_id()).append(" ");
        }

        if (valueObject.getFrom_user_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND FROM_USER_ID = ").append(valueObject.getFrom_user_id()).append(" ");
        }

        if (valueObject.getTo_user_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND TO_USER_ID = ").append(valueObject.getTo_user_id()).append(" ");
        }

        if (valueObject.getMessage_title() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND MESSAGE_TITLE LIKE '").append(valueObject.getMessage_title()).append("%' ");
        }

        if (valueObject.getMessage_body() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND MESSAGE_BODY LIKE '").append(valueObject.getMessage_body()).append("%' ");
        }

        if (valueObject.getDate_sent() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND DATE_SENT = '").append(valueObject.getDate_sent()).append("' ");
        }


        sql.append("ORDER BY ANNOUNCEMENT_ID ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first)
            searchResults = new ArrayList<Announcement>();
        else
            searchResults = listQuery(conn.prepareStatement(sql.toString()));

        return searchResults;
    }


    protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

        long result = stmt.executeUpdate();

        return result;
    }


    protected static void singleQuery(PreparedStatement stmt, Announcement valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setAnnouncement_id(result.getLong("ANNOUNCEMENT_ID"));
                valueObject.setFrom_user_id(result.getLong("FROM_USER_ID"));
                valueObject.setTo_user_id(result.getLong("TO_USER_ID"));
                valueObject.setMessage_title(result.getString("MESSAGE_TITLE"));
                valueObject.setMessage_body(result.getString("MESSAGE_BODY"));
                valueObject.setDate_sent(result.getTimestamp("DATE_SENT"));
                valueObject.setMessage_type(result.getString("MESSAGE_TYPE"));
            } else {
                //System.out.println("ANNOUNCEMENT_TABLE Object Not Found!");
                throw new NotFoundException("ANNOUNCEMENT_TABLE Object Not Found!");
            }
        } finally {
            if (result != null)
                result.close();
            if (stmt != null)
                stmt.close();
        }
    }


    protected static List<Announcement> listQuery(PreparedStatement stmt) throws SQLException {

        List<Announcement> searchResults = new ArrayList<Announcement>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                Announcement temp = createValueObject();

                temp.setAnnouncement_id(result.getLong("ANNOUNCEMENT_ID"));
                temp.setFrom_user_id(result.getLong("FROM_USER_ID"));
                temp.setTo_user_id(result.getLong("TO_USER_ID"));
                temp.setMessage_title(result.getString("MESSAGE_TITLE"));
                temp.setMessage_body(result.getString("MESSAGE_BODY"));
                temp.setDate_sent(result.getTimestamp("DATE_SENT"));
                temp.setMessage_type(result.getString("MESSAGE_TYPE"));
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
