package com.elmo.spring.persistence.dos;


import java.io.Serializable;
import java.sql.Timestamp;


public class Assignment implements Cloneable, Serializable {

    
    private long assignment_id;
    private long uploaded_by_id;
    private String assign_description;
    private Timestamp assign_date_created;
    private Timestamp assign_date_published;
    private Timestamp assign_date_expired;
    private Timestamp assign_date_due;
    private long course_sem_id;
    private String assignment_title;
    private long score_max;

    private String assign_date_created_str;
    private String assign_date_published_str;
    private String assign_date_expired_str;
    private String assign_date_due_str;

    public String getAssign_date_created_str() {
        return assign_date_created_str;
    }

    public void setAssign_date_created_str(String assign_date_created_str) {
        this.assign_date_created_str = assign_date_created_str;
    }

    public String getAssign_date_published_str() {
        return assign_date_published_str;
    }

    public void setAssign_date_published_str(String assign_date_published_str) {
        this.assign_date_published_str = assign_date_published_str;
    }

    public String getAssign_date_expired_str() {
        return assign_date_expired_str;
    }

    public void setAssign_date_expired_str(String assign_date_expired_str) {
        this.assign_date_expired_str = assign_date_expired_str;
    }

    public String getAssign_date_due_str() {
        return assign_date_due_str;
    }

    public void setAssign_date_due_str(String assign_date_due_str) {
        this.assign_date_due_str = assign_date_due_str;
    }

    public Assignment () {

    }

    public Assignment (long assignment_id) {

          this.assignment_id = assignment_id;

    }

    public Assignment(long uploaded_by_id, String assign_description, Timestamp assign_date_created, Timestamp assign_date_published, Timestamp assign_date_expired, Timestamp assign_date_due, long course_sem_id, String assignment_title, long score_max) {
        this.uploaded_by_id = uploaded_by_id;
        this.assign_description = assign_description;
        this.assign_date_created = assign_date_created;
        this.assign_date_published = assign_date_published;
        this.assign_date_expired = assign_date_expired;
        this.assign_date_due = assign_date_due;
        this.course_sem_id = course_sem_id;
        this.assignment_title = assignment_title;
        this.score_max = score_max;
    }

    /**
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getAssignment_id() {
          return this.assignment_id;
    }
    public void setAssignment_id(long assignment_id) {
          this.assignment_id = assignment_id;
    }

    public long getUploaded_by_id() {
          return this.uploaded_by_id;
    }
    public void setUploaded_by_id(long uploaded_by_id) {
          this.uploaded_by_id = uploaded_by_id;
    }

    public String getAssign_description() {
          return this.assign_description;
    }
    public void setAssign_description(String assign_description) {
          this.assign_description = assign_description;
    }

    public Timestamp getAssign_date_created() {
          return this.assign_date_created;
    }
    public void setAssign_date_created(Timestamp assign_date_created) {
          this.assign_date_created = assign_date_created;
    }

    public Timestamp getAssign_date_published() {
          return this.assign_date_published;
    }
    public void setAssign_date_published(Timestamp assign_date_published) {
          this.assign_date_published = assign_date_published;
    }

    public Timestamp getAssign_date_expired() {
          return this.assign_date_expired;
    }
    public void setAssign_date_expired(Timestamp assign_date_expired) {
          this.assign_date_expired = assign_date_expired;
    }

    public Timestamp getAssign_date_due() {
          return this.assign_date_due;
    }
    public void setAssign_date_due(Timestamp assign_date_due) {
          this.assign_date_due = assign_date_due;
    }

    public long getCourse_sem_id() {
          return this.course_sem_id;
    }
    public void setCourse_sem_id(long course_sem_id) {
          this.course_sem_id = course_sem_id;
    }

    public String getAssignment_title() {
          return this.assignment_title;
    }
    public void setAssignment_title(String assignment_title) {
          this.assignment_title = assignment_title;
    }

    public long getScore_max() {
          return this.score_max;
    }
    public void setScore_max(long score_max) {
          this.score_max = score_max;
    }



    public void setAll(
          long uploaded_by_id,
          String assign_description,
          Timestamp assign_date_created,
          Timestamp assign_date_published,
          Timestamp assign_date_expired,
          Timestamp assign_date_due,
          long course_id,
          String assignment_title,
          long score_max) {

          this.uploaded_by_id = uploaded_by_id;
          this.assign_description = assign_description;
          this.assign_date_created = assign_date_created;
          this.assign_date_published = assign_date_published;
          this.assign_date_expired = assign_date_expired;
          this.assign_date_due = assign_date_due;
          this.course_sem_id = course_id;
          this.assignment_title = assignment_title;
          this.score_max = score_max;
    }


    public void setAllForUpdate(long assignment_id,

                       String assign_description,

                       Timestamp assign_date_published,
                       Timestamp assign_date_expired,
                       Timestamp assign_date_due,

                       String assignment_title,
                       long score_max) {
        this.assignment_id = assignment_id;

        this.assign_description = assign_description;

        this.assign_date_published = assign_date_published;
        this.assign_date_expired = assign_date_expired;
        this.assign_date_due = assign_date_due;

        this.assignment_title = assignment_title;
        this.score_max = score_max;
    }

    public boolean hasEqualMapping(Assignment valueObject) {

          if (valueObject.getAssignment_id() != this.assignment_id) {
                    return(false);
          }
          if (valueObject.getUploaded_by_id() != this.uploaded_by_id) {
                    return(false);
          }
          if (this.assign_description == null) {
                    if (valueObject.getAssign_description() != null)
                           return(false);
          } else if (!this.assign_description.equals(valueObject.getAssign_description())) {
                    return(false);
          }
          if (this.assign_date_created == null) {
                    if (valueObject.getAssign_date_created() != null)
                           return(false);
          } else if (!this.assign_date_created.equals(valueObject.getAssign_date_created())) {
                    return(false);
          }
          if (this.assign_date_published == null) {
                    if (valueObject.getAssign_date_published() != null)
                           return(false);
          } else if (!this.assign_date_published.equals(valueObject.getAssign_date_published())) {
                    return(false);
          }
          if (this.assign_date_expired == null) {
                    if (valueObject.getAssign_date_expired() != null)
                           return(false);
          } else if (!this.assign_date_expired.equals(valueObject.getAssign_date_expired())) {
                    return(false);
          }
          if (this.assign_date_due == null) {
                    if (valueObject.getAssign_date_due() != null)
                           return(false);
          } else if (!this.assign_date_due.equals(valueObject.getAssign_date_due())) {
                    return(false);
          }
          if (valueObject.getCourse_sem_id() != this.course_sem_id) {
                    return(false);
          }
          if (this.assignment_title == null) {
                    if (valueObject.getAssignment_title() != null)
                           return(false);
          } else if (!this.assignment_title.equals(valueObject.getAssignment_title())) {
                    return(false);
          }
        return valueObject.getScore_max() == this.score_max;

    }


    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass Assignment, mapping to table ASSIGNMENT_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("assignment_id = " + this.assignment_id + "\n"); 
        out.append("uploaded_by_id = " + this.uploaded_by_id + "\n"); 
        out.append("assign_description = " + this.assign_description + "\n"); 
        out.append("assign_date_created = " + this.assign_date_created + "\n"); 
        out.append("assign_date_published = " + this.assign_date_published + "\n"); 
        out.append("assign_date_expired = " + this.assign_date_expired + "\n"); 
        out.append("assign_date_due = " + this.assign_date_due + "\n"); 
        out.append("course_sem_id = " + this.course_sem_id + "\n");
        out.append("assignment_title = " + this.assignment_title + "\n"); 
        out.append("score_max = " + this.score_max + "\n"); 
        return out.toString();
    }

}
