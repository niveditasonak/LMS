package com.elmo.spring.persistence.dos;


import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;

 public class Submission implements Cloneable, Serializable {

    private long submission_id;
    private long assign_id;
    private long quiz_id;
    private long score_received;
    private Timestamp submission_date;
    private InputStream assign_uploaded;
    private Long uploaded_by_enr_id;
     private String uploaded_by_name;
    private String ass_quiz_title;
     private Long max_score_for_display;

     public Long getMax_score_for_display() {
         return max_score_for_display;
     }

     public void setMax_score_for_display(Long max_score_for_display) {
         this.max_score_for_display = max_score_for_display;
     }

     public String getAss_quiz_title() {
         return ass_quiz_title;
     }

     public void setAss_quiz_title(String ass_quiz_title) {
         this.ass_quiz_title = ass_quiz_title;
     }

     public String getUploaded_by_name() {
         return uploaded_by_name;
     }

     public void setUploaded_by_name(String uploaded_by_name) {
         this.uploaded_by_name = uploaded_by_name;
     }

     public Submission () {

    }

    public Submission (long submission_id) {

          this.submission_id = submission_id;

    }

     public static Submission createAssignmentSubmission(long assign_id, long score_received, Timestamp submission_date, InputStream assign_uploaded, Long uploaded_by_enr_id)
     {
         return  new Submission(assign_id,score_received,submission_date,assign_uploaded,uploaded_by_enr_id);
     }

     public static Submission createQuizSubmission(long quizId, long score_received, Timestamp submission_date, InputStream assign_uploaded, Long uploaded_by_enr_id)
     {
         return  new Submission(score_received,submission_date,assign_uploaded,uploaded_by_enr_id, quizId);
     }


     private Submission(long assign_id, long score_received, Timestamp submission_date, InputStream assign_uploaded, Long uploaded_by_enr_id) {
         this.assign_id = assign_id;
         this.score_received = score_received;
         this.submission_date = submission_date;
         this.assign_uploaded = assign_uploaded;
         this.uploaded_by_enr_id = uploaded_by_enr_id;
     }

     private Submission( long score_received, Timestamp submission_date, InputStream assign_uploaded, Long uploaded_by_enr_id, long assign_id) {
         this.quiz_id = assign_id;
         this.score_received = score_received;
         this.submission_date = submission_date;
         this.assign_uploaded = assign_uploaded;
         this.uploaded_by_enr_id = uploaded_by_enr_id;
     }


     public long getSubmission_id() {
          return this.submission_id;
    }
    public void setSubmission_id(long submission_id) {
          this.submission_id = submission_id;
    }

    public long getAssign_id() {
          return this.assign_id;
    }
    public void setAssign_id(long assign_id) {
          this.assign_id = assign_id;
    }

    public long getQuiz_id() {
          return this.quiz_id;
    }
    public void setQuiz_id(long quiz_id) {
          this.quiz_id = quiz_id;
    }

    public long getScore_received() {
          return this.score_received;
    }
    public void setScore_received(long score_received) {
          this.score_received = score_received;
    }

    public Timestamp getSubmission_date() {
          return this.submission_date;
    }
    public void setSubmission_date(Timestamp submission_date) {
          this.submission_date = submission_date;
    }

    public InputStream getAssign_uploaded() {
          return this.assign_uploaded;
    }
    public void setAssign_uploaded(InputStream assign_uploaded) {
          this.assign_uploaded = assign_uploaded;
    }

    public Long getUploaded_by_enr_id() {
          return this.uploaded_by_enr_id;
    }
    public void setUploaded_by_enr_id(Long uploaded_by_enr_id) {
          this.uploaded_by_enr_id = uploaded_by_enr_id;
    }


    public void setAll(long submission_id,
          long assign_id,
          long quiz_id,
          long score_received,
          Timestamp submission_date,
          InputStream assign_uploaded,
          Long uploaded_by_id) {
          this.submission_id = submission_id;
          this.assign_id = assign_id;
          this.quiz_id = quiz_id;
          this.score_received = score_received;
          this.submission_date = submission_date;
          this.assign_uploaded = assign_uploaded;
          this.uploaded_by_enr_id = uploaded_by_id;
    }


   
    public boolean hasEqualMapping(Submission valueObject) {

          if (valueObject.getSubmission_id() != this.submission_id) {
                    return(false);
          }
          if (valueObject.getAssign_id() != this.assign_id) {
                    return(false);
          }
          if (valueObject.getQuiz_id() != this.quiz_id) {
                    return(false);
          }
          if (valueObject.getScore_received() != this.score_received) {
                    return(false);
          }
          if (this.submission_date == null) {
                    if (valueObject.getSubmission_date() != null)
                           return(false);
          } else if (!this.submission_date.equals(valueObject.getSubmission_date())) {
                    return(false);
          }
          if (this.assign_uploaded == null) {
                    if (valueObject.getAssign_uploaded() != null)
                           return(false);
          } else if (!this.assign_uploaded.equals(valueObject.getAssign_uploaded())) {
                    return(false);
          }
          if (this.uploaded_by_enr_id == null) {
                    if (valueObject.getUploaded_by_enr_id() != null)
                           return(false);
          } else if (!this.uploaded_by_enr_id.equals(valueObject.getUploaded_by_enr_id())) {
                    return(false);
          }

          return true;
    }



    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in textlog.
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass Submission, mapping to table SUBMISSION_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("submission_id = " + this.submission_id + "\n"); 
        out.append("quizId = " + this.assign_id + "\n");
        out.append("quiz_id = " + this.quiz_id + "\n"); 
        out.append("score_received = " + this.score_received + "\n"); 
        out.append("submission_date = " + this.submission_date + "\n"); 
        out.append("assign_uploaded = " + this.assign_uploaded + "\n");
        out.append("uploaded_by_enr_id = " + this.uploaded_by_enr_id + "\n");
        return out.toString();
    }


}

