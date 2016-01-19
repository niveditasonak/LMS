package com.elmo.spring.persistence.dos;

import java.io.Serializable;


public class Question implements Cloneable, Serializable {

   
    private long question_id;
    private long quiz_id;
    private long question_number;
    private String quest_description;
    private String correct_answers;
    private long max_score;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Question(long quiz_id, long question_number, String quest_description, String correct_answers, long max_score, String a,String b, String c, String d) {
        this.quiz_id = quiz_id;
        this.question_number = question_number;
        this.quest_description = quest_description;
        this.correct_answers = correct_answers;
        this.max_score = max_score;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;

    }

    public Question () {

    }

    public Question (long question_id) {

          this.question_id = question_id;

    }



    public long getQuestion_id() {
          return this.question_id;
    }
    public void setQuestion_id(long question_id) {
          this.question_id = question_id;
    }

    public long getQuiz_id() {
          return this.quiz_id;
    }
    public void setQuiz_id(long quiz_id) {
          this.quiz_id = quiz_id;
    }

    public long getQuestion_number() {
          return this.question_number;
    }
    public void setQuestion_number(long question_number) {
          this.question_number = question_number;
    }

    public String getQuest_description() {
          return this.quest_description;
    }
    public void setQuest_description(String quest_description) {
          this.quest_description = quest_description;
    }

    public String getCorrect_answers() {
          return this.correct_answers;
    }
    public void setCorrect_answers(String correct_answers) {
          this.correct_answers = correct_answers;
    }

    public long getMax_score() {
          return this.max_score;
    }
    public void setMax_score(long max_score) {
          this.max_score = max_score;
    }

    public void setAll(long question_id,

          String quest_description,
          String correct_answers,
          long max_score) {
          this.question_id = question_id;
          this.quest_description = quest_description;
          this.correct_answers = correct_answers;
          this.max_score = max_score;
    }


  
    public boolean hasEqualMapping(Question valueObject) {

          if (valueObject.getQuestion_id() != this.question_id) {
                    return(false);
          }
          if (valueObject.getQuiz_id() != this.quiz_id) {
                    return(false);
          }
          if (valueObject.getQuestion_number() != this.question_number) {
                    return(false);
          }
          if (this.quest_description == null) {
                    if (valueObject.getQuest_description() != null)
                           return(false);
          } else if (!this.quest_description.equals(valueObject.getQuest_description())) {
                    return(false);
          }
          if (this.correct_answers == null) {
                    if (valueObject.getCorrect_answers() != null)
                           return(false);
          } else if (!this.correct_answers.equals(valueObject.getCorrect_answers())) {
                    return(false);
          }
        return valueObject.getMax_score() == this.max_score;

    }



    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in textlog.
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass Question, mapping to table QUESTION_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("question_id = " + this.question_id + "\n"); 
        out.append("quiz_id = " + this.quiz_id + "\n"); 
        out.append("question_number = " + this.question_number + "\n"); 
        out.append("quest_description = " + this.quest_description + "\n"); 
        out.append("correct_answers = " + this.correct_answers + "\n"); 
        out.append("max_score = " + this.max_score + "\n"); 
        return out.toString();
    }

    public Object clone() {
        Question cloned = new Question();

        cloned.setQuestion_id(this.question_id); 
        cloned.setQuiz_id(this.quiz_id); 
        cloned.setQuestion_number(this.question_number); 
        if (this.quest_description != null)
             cloned.setQuest_description(new String(this.quest_description)); 
        if (this.correct_answers != null)
             cloned.setCorrect_answers(new String(this.correct_answers)); 
        cloned.setMax_score(this.max_score); 
        return cloned;
    }



}

