package com.elmo.spring.persistence.daos;


import com.elmo.spring.persistence.dos.Question;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;
import framework.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuestionDao {

	private static Connection conn;

	   static {
	    	conn= ConnectionManager.getInstance().getConnection();

	    }
	
	public static Question createValueObject() {
          return new Question();
    }


    public static Question getObject(long question_id) throws NotFoundException, SQLException {

          Question valueObject = createValueObject();
          valueObject.setQuestion_id(question_id);
          load(valueObject);
          return valueObject;
    }


    public static void load(Question valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM QUESTION_TABLE WHERE (QUESTION_ID = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setLong(1, valueObject.getQuestion_id()); 

               singleQuery(stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    public static List<Question> loadAllForQuizId(Long quizId) throws SQLException {

          String sql = "SELECT * FROM QUESTION_TABLE WHERE QUIZ_ID = ? ORDER BY QUESTION_ID ASC ";
        
    PreparedStatement stmt = null;
         List<Question> searchResults =  new ArrayList<Question>();
          
          try {
              stmt = conn.prepareStatement(sql);
              stmt.setLong(1,quizId);
               searchResults = listQuery(stmt);
           }
           catch(Exception e)
           {
               System.out.println("caught while loading question");
           }

            return searchResults;
          
          
    }



    public static synchronized void create(Question valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
        	  Long primaryId = Toolbox.getSequenceNextValue("SEQ_QUESTION_TABLE");
              valueObject.setQuestion_id(primaryId);
        	  
               sql = "INSERT INTO QUESTION_TABLE ( QUESTION_ID, QUIZ_ID, QUESTION_NUMBER, "
               + "QUEST_DESCRIPTION, CORRECT_ANSWERS, MAX_SCORE, optiona, optionb, optionc, optiond) VALUES (?, ?, ?, ?, ?, ?,?,?,?,?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setString(1, primaryId.toString()); 
               stmt.setLong(2, valueObject.getQuiz_id()); 
               stmt.setLong(3, valueObject.getQuestion_number()); 
               stmt.setString(4, valueObject.getQuest_description()); 
               stmt.setString(5, valueObject.getCorrect_answers()); 
               stmt.setLong(6, valueObject.getMax_score());


              stmt.setString(7, valueObject.getOptionA());
              stmt.setString(8, valueObject.getOptionB());
              stmt.setString(9, valueObject.getOptionC());
              stmt.setString(10, valueObject.getOptionD());


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


    public static void save(Question valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE QUESTION_TABLE SET QUIZ_ID = ?, QUESTION_NUMBER = ?, QUEST_DESCRIPTION = ?, "
               + "CORRECT_ANSWERS = ?, MAX_SCORE = ? , OPTIONA = ? , OPTIONB = ? , OPTIONC = ? , OPTIOND = ? WHERE (QUESTION_ID = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setLong(1, valueObject.getQuiz_id()); 
              stmt.setLong(2, valueObject.getQuestion_number());
              stmt.setString(3, valueObject.getQuest_description()); 
              stmt.setString(4, valueObject.getCorrect_answers());
              stmt.setLong(5, valueObject.getMax_score());

              stmt.setString(6, valueObject.getOptionA());
              stmt.setString(7, valueObject.getOptionB());
              stmt.setString(8, valueObject.getOptionC());
              stmt.setString(9, valueObject.getOptionD());

              stmt.setLong(10, valueObject.getQuestion_id());

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


    
    public static void delete(Question valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM QUESTION_TABLE WHERE (QUESTION_ID = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setLong(1, valueObject.getQuestion_id()); 

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




    public static long countAllForQuizId(Long quizId) throws SQLException {

          String sql = "SELECT count(*) FROM QUESTION_TABLE WHERE QUIZ_ID = ?";
          PreparedStatement stmt = null;
          ResultSet result = null;
          long allRows = 0;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setLong(1,quizId);
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


    public static List<Question> searchMatching(Question valueObject) throws SQLException {

          List<Question> searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM QUESTION_TABLE WHERE 1=1 ");

          if (valueObject.getQuestion_id() != 0) {
              if (first) { first = false; }
              sql.append("AND QUESTION_ID = ").append(valueObject.getQuestion_id()).append(" ");
          }

          if (valueObject.getQuiz_id() != 0) {
              if (first) { first = false; }
              sql.append("AND QUIZ_ID = ").append(valueObject.getQuiz_id()).append(" ");
          }

          if (valueObject.getQuestion_number() != 0) {
              if (first) { first = false; }
              sql.append("AND QUESTION_NUMBER = ").append(valueObject.getQuestion_number()).append(" ");
          }

          if (valueObject.getQuest_description() != null) {
              if (first) { first = false; }
              sql.append("AND QUEST_DESCRIPTION LIKE '").append(valueObject.getQuest_description()).append("%' ");
          }

          if (valueObject.getCorrect_answers() != null) {
              if (first) { first = false; }
              sql.append("AND CORRECT_ANSWERS LIKE '").append(valueObject.getCorrect_answers()).append("%' ");
          }

          if (valueObject.getMax_score() != 0) {
              if (first) { first = false; }
              sql.append("AND MAX_SCORE = ").append(valueObject.getMax_score()).append(" ");
          }


          sql.append("ORDER BY QUESTION_ID ASC ");

          // Prevent accidential full table results.
          // Use loadAll if all rows must be returned.
          if (first)
               searchResults = new ArrayList<Question>();
          else
               searchResults = listQuery(conn.prepareStatement(sql.toString()));

          return searchResults;
    }


     protected static long databaseUpdate(PreparedStatement stmt) throws SQLException {

          long result = stmt.executeUpdate();

          return result;
    }


    protected static void singleQuery(PreparedStatement stmt, Question valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setQuestion_id(result.getLong("QUESTION_ID")); 
                   valueObject.setQuiz_id(result.getLong("QUIZ_ID")); 
                   valueObject.setQuestion_number(result.getLong("QUESTION_NUMBER")); 
                   valueObject.setQuest_description(result.getString("QUEST_DESCRIPTION")); 
                   valueObject.setCorrect_answers(result.getString("CORRECT_ANSWERS")); 
                   valueObject.setMax_score(result.getLong("MAX_SCORE"));

                  valueObject.setOptionA(result.getString("OPTIONA"));
                  valueObject.setOptionB(result.getString("OPTIONB"));
                  valueObject.setOptionC(result.getString("OPTIONC"));
                  valueObject.setOptionD(result.getString("OPTIOND"));
              } else {
                    //System.out.println("Question Object Not Found!");
                    throw new NotFoundException("Question Object Not Found!");
              }
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
    }


    protected static List<Question> listQuery(PreparedStatement stmt) throws SQLException {

          List<Question> searchResults = new ArrayList<Question>();
          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              while (result.next()) {
                   Question temp = createValueObject();

                   temp.setQuestion_id(result.getLong("QUESTION_ID")); 
                   temp.setQuiz_id(result.getLong("QUIZ_ID")); 
                   temp.setQuestion_number(result.getLong("QUESTION_NUMBER")); 
                   temp.setQuest_description(result.getString("QUEST_DESCRIPTION")); 
                   temp.setCorrect_answers(result.getString("CORRECT_ANSWERS")); 
                   temp.setMax_score(result.getLong("MAX_SCORE"));

                  temp.setOptionA(result.getString("OPTIONA"));
                  temp.setOptionB(result.getString("OPTIONB"));
                  temp.setOptionC(result.getString("OPTIONC"));
                  temp.setOptionD(result.getString("OPTIOND"));

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
