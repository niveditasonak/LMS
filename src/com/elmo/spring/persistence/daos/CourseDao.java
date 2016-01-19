package com.elmo.spring.persistence.daos;

import com.elmo.spring.persistence.dos.Course;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;
import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

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
     * createValueObject-method. This method is used when the Dao class needs
     * to create new value object instance. The reason why this method exists
     * is that sometimes the programmer may want to extend also the valueObject
     * and then this method can be overrided to return extended valueObject.
     * NOTE: If you extend the valueObject class, make sure to override the
     * clone() method in it!
     */
    public static Course createValueObject() {
        return new Course();
    }


    /**
     * getObject-method. This will create and load valueObject contents from database
     * using given Primary-Key as identifier. This method is just a convenience method
     * for the real load-method which accepts the valueObject as a parameter. Returned
     * valueObject will be created using the createValueObject() method.
     */
    public static Course getObject(long course_id) throws NotFoundException, SQLException {

        Course valueObject = createValueObject();
        valueObject.setCourse_id(course_id);
        load(valueObject);
        return valueObject;
    }


    public static void load(Course valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM COURSE_TABLE WHERE (COURSE_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getCourse_id());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static List<Course> loadAll() throws SQLException {

        String sql = "SELECT * FROM COURSE_TABLE ORDER BY COURSE_ID ASC ";
        List<Course> searchResults = new ArrayList<Course>();
        try {
            searchResults = listQuery(conn.prepareStatement(sql));
        } catch (Exception e) {
            System.out.println("caught");
        }

        return searchResults;
    }


    public static synchronized void create(Course valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {

            Long primaryId = Toolbox.getSequenceNextValue("seq_COURSE_TABLE");
            valueObject.setCourse_id(primaryId);

            sql = "INSERT INTO COURSE_TABLE ( COURSE_ID, COURSE_NUMBER, COURSE_NAME, "
                    + "COURSE_DESCRIPTION) VALUES (?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, primaryId.toString());
            stmt.setString(2, valueObject.getCourse_number());
            stmt.setString(3, valueObject.getCourse_name());
            stmt.setString(4, valueObject.getCourse_description());


            long rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                //System.out.prlongln("PrimaryKey Error when updating DB!");
                throw new SQLException("PrimaryKey Error when updating DB!");
            }

        } finally {
            if (stmt != null)
                stmt.close();
        }


    }


    public static void save(Course valueObject)
            throws NotFoundException, SQLException {

        String sql = "UPDATE COURSE_TABLE SET COURSE_NUMBER = ?, COURSE_NAME = ?, COURSE_DESCRIPTION = ? " + " WHERE (COURSE_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, valueObject.getCourse_number());
            stmt.setString(2, valueObject.getCourse_name());
            stmt.setString(3, valueObject.getCourse_description());
            stmt.setLong(4, valueObject.getCourse_id());

            long rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
                //System.out.prlongln("Object could not be saved! (PrimaryKey not found)");
                throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
            }
            if (rowcount > 1) {
                //System.out.prlongln("PrimaryKey Error when updating DB! (Many objects were affected!)");
                throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
            }
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static void delete(Course valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM COURSE_TABLE WHERE (COURSE_ID = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, valueObject.getCourse_id());

            long rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
                //System.out.prlongln("Object could not be deleted (PrimaryKey not found)");
                throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
            }
            if (rowcount > 1) {
                //System.out.prlongln("PrimaryKey Error when updating DB! (Many objects were deleted!)");
                throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
            }
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }



    public static long countAll() throws SQLException {

        String sql = "SELECT count(*) FROM COURSE_TABLE";
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

    public static List<Course> searchMatching(Course valueObject) throws SQLException {

        List<Course> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM COURSE_TABLE WHERE 1=1 ");

        if (valueObject.getCourse_id() != 0) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_ID = ").append(valueObject.getCourse_id()).append(" ");
        }

        if (valueObject.getCourse_number() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_NUMBER LIKE '").append(valueObject.getCourse_number()).append("%' ");
        }

        if (valueObject.getCourse_name() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_NAME LIKE '").append(valueObject.getCourse_name()).append("%' ");
        }

        if (valueObject.getCourse_description() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND COURSE_DESCRIPTION LIKE '").append(valueObject.getCourse_description()).append("%' ");
        }


        sql.append("ORDER BY COURSE_ID ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first)
            searchResults = new ArrayList();
        else
            searchResults = listQuery(conn.prepareStatement(sql.toString()));

        return searchResults;
    }


    protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

        long result = stmt.executeUpdate();

        return result;
    }


    protected static void singleQuery(PreparedStatement stmt, Course valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setCourse_id(result.getLong("COURSE_ID"));
                valueObject.setCourse_number(result.getString("COURSE_NUMBER"));
                valueObject.setCourse_name(result.getString("COURSE_NAME"));
                valueObject.setCourse_description(result.getString("COURSE_DESCRIPTION"));


            } else {
                //System.out.prlongln("Course Object Not Found!");
                throw new NotFoundException("Course Object Not Found!");
            }
        } finally {
            if (result != null)
                result.close();
            if (stmt != null)
                stmt.close();
        }
    }


    protected static List<Course> listQuery(PreparedStatement stmt) throws SQLException {

        List<Course> searchResults = new ArrayList<Course>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                Course temp = createValueObject();

                temp.setCourse_id(result.getLong("COURSE_ID"));
                temp.setCourse_number(result.getString("COURSE_NUMBER"));
                temp.setCourse_name(result.getString("COURSE_NAME"));
                temp.setCourse_description(result.getString("COURSE_DESCRIPTION"));


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
