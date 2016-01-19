package com.elmo.spring.persistence.dos;


import java.io.Serializable;
import java.sql.Timestamp;
 
public class Quiz implements Cloneable, Serializable {

   
    private long quiz_id;
    private long uploaded_by_id;
    private String quiz_description;
    private long course_sem_id;
    private String quiz_title;
    private Timestamp quiz_date_created;
    private Timestamp quiz_date_published;
    private Timestamp quiz_date_expired;
    private Timestamp quiz_due_date;
    private long time_taken;
    private long max_score;

    public long getMax_score() {
        return max_score;
    }

    public void setMax_score(long max_score) {
        this.max_score = max_score;
    }

    private String quiz_date_created_str;
    private String quiz_date_published_str;
    private String quiz_date_expired_str;
    private String quiz_due_date_str;

    public String getQuiz_date_created_str() {
        return quiz_date_created_str;
    }

    public void setQuiz_date_created_str(String quiz_date_created_str) {
        this.quiz_date_created_str = quiz_date_created_str;
    }

    public String getQuiz_date_published_str() {
        return quiz_date_published_str;
    }

    public void setQuiz_date_published_str(String quiz_date_published_str) {
        this.quiz_date_published_str = quiz_date_published_str;
    }

    public String getQuiz_date_expired_str() {
        return quiz_date_expired_str;
    }

    public void setQuiz_date_expired_str(String quiz_date_expired_str) {
        this.quiz_date_expired_str = quiz_date_expired_str;
    }

    public String getQuiz_due_date_str() {
        return quiz_due_date_str;
    }

    public void setQuiz_due_date_str(String quiz_due_date_str) {
        this.quiz_due_date_str = quiz_due_date_str;
    }

    public Quiz () {

    }

    public Quiz (long quiz_id) {

          this.quiz_id = quiz_id;

    }

    public Quiz(long uploaded_by_id, String quiz_description, long course_sem_id, String quiz_title, Timestamp quiz_date_created, Timestamp quiz_date_published, Timestamp quiz_date_expired, Timestamp quiz_due_date, long time_taken) {
        this.uploaded_by_id = uploaded_by_id;
        this.quiz_description = quiz_description;
        this.course_sem_id = course_sem_id;
        this.quiz_title = quiz_title;
        this.quiz_date_created = quiz_date_created;
        this.quiz_date_published = quiz_date_published;
        this.quiz_date_expired = quiz_date_expired;
        this.quiz_due_date = quiz_due_date;
        this.time_taken = time_taken;
    }

    /**
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getQuiz_id() {
          return this.quiz_id;
    }
    public void setQuiz_id(long quiz_id) {
          this.quiz_id = quiz_id;
    }

    public long getUploaded_by_id() {
          return this.uploaded_by_id;
    }
    public void setUploaded_by_id(long uploaded_by_id) {
          this.uploaded_by_id = uploaded_by_id;
    }

    public String getQuiz_description() {
          return this.quiz_description;
    }
    public void setQuiz_description(String quiz_description) {
          this.quiz_description = quiz_description;
    }

    public long getCourse_sem_id() {
          return this.course_sem_id;
    }
    public void setCourse_sem_id(long course_sem_id) {
          this.course_sem_id = course_sem_id;
    }

    public String getQuiz_title() {
          return this.quiz_title;
    }
    public void setQuiz_title(String quiz_title) {
          this.quiz_title = quiz_title;
    }

    public Timestamp getQuiz_date_created() {
          return this.quiz_date_created;
    }
    public void setQuiz_date_created(Timestamp quiz_date_created) {
          this.quiz_date_created = quiz_date_created;
    }

    public Timestamp getQuiz_date_published() {
          return this.quiz_date_published;
    }
    public void setQuiz_date_published(Timestamp quiz_date_published) {
          this.quiz_date_published = quiz_date_published;
    }

    public Timestamp getQuiz_date_expired() {
          return this.quiz_date_expired;
    }
    public void setQuiz_date_expired(Timestamp quiz_date_expired) {
          this.quiz_date_expired = quiz_date_expired;
    }

    public Timestamp getQuiz_due_date() {
          return this.quiz_due_date;
    }
    public void setQuiz_due_date(Timestamp quiz_due_date) {
          this.quiz_due_date = quiz_due_date;
    }

    public long getTime_taken() {
          return this.time_taken;
    }
    public void setTime_taken(long time_taken) {
          this.time_taken = time_taken;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variales, without going trough the 
     * individual set-methods.
     */

    public void setAll(
          long uploaded_by_id,
          String quiz_description,
          long course_id,
          String course_title,
          Timestamp quiz_date_created,
          Timestamp quiz_date_published,
          Timestamp quiz_date_expired,
          Timestamp quiz_due_date,
          long time_taken) {

          this.uploaded_by_id = uploaded_by_id;
          this.quiz_description = quiz_description;
          this.course_sem_id = course_id;
          this.quiz_title = course_title;
          this.quiz_date_created = quiz_date_created;
          this.quiz_date_published = quiz_date_published;
          this.quiz_date_expired = quiz_date_expired;
          this.quiz_due_date = quiz_due_date;
          this.time_taken = time_taken;
    }


    public void setAllForUpdate(
                       String quiz_description,

                       String course_title,

                       Timestamp quiz_date_published,

                       Timestamp quiz_due_date,
                       long time_taken) {

        this.quiz_description = quiz_description;
        this.quiz_title = course_title;
        this.quiz_date_published = quiz_date_published;

        this.quiz_due_date = quiz_due_date;
        this.time_taken = time_taken;
    }



    /** 
     * hasEqualMapping-method will compare two Quiz instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Quiz valueObject) {

          if (valueObject.getQuiz_id() != this.quiz_id) {
                    return(false);
          }
          if (valueObject.getUploaded_by_id() != this.uploaded_by_id) {
                    return(false);
          }
          if (this.quiz_description == null) {
                    if (valueObject.getQuiz_description() != null)
                           return(false);
          } else if (!this.quiz_description.equals(valueObject.getQuiz_description())) {
                    return(false);
          }
          if (valueObject.getCourse_sem_id() != this.course_sem_id) {
                    return(false);
          }
          if (this.quiz_title == null) {
                    if (valueObject.getQuiz_title() != null)
                           return(false);
          } else if (!this.quiz_title.equals(valueObject.getQuiz_title())) {
                    return(false);
          }
          if (this.quiz_date_created == null) {
                    if (valueObject.getQuiz_date_created() != null)
                           return(false);
          } else if (!this.quiz_date_created.equals(valueObject.getQuiz_date_created())) {
                    return(false);
          }
          if (this.quiz_date_published == null) {
                    if (valueObject.getQuiz_date_published() != null)
                           return(false);
          } else if (!this.quiz_date_published.equals(valueObject.getQuiz_date_published())) {
                    return(false);
          }
          if (this.quiz_date_expired == null) {
                    if (valueObject.getQuiz_date_expired() != null)
                           return(false);
          } else if (!this.quiz_date_expired.equals(valueObject.getQuiz_date_expired())) {
                    return(false);
          }
          if (this.quiz_due_date == null) {
                    if (valueObject.getQuiz_due_date() != null)
                           return(false);
          } else if (!this.quiz_due_date.equals(valueObject.getQuiz_due_date())) {
                    return(false);
          }
        return valueObject.getTime_taken() == this.time_taken;

    }



    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in textlog.
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass Quiz, mapping to table QUIZ_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("quiz_id = " + this.quiz_id + "\n"); 
        out.append("uploaded_by_id = " + this.uploaded_by_id + "\n"); 
        out.append("quiz_description = " + this.quiz_description + "\n"); 
        out.append("course_sem_id = " + this.course_sem_id + "\n");
        out.append("quiz_title = " + this.quiz_title + "\n");
        out.append("quiz_date_created = " + this.quiz_date_created + "\n"); 
        out.append("quiz_date_published = " + this.quiz_date_published + "\n"); 
        out.append("quiz_date_expired = " + this.quiz_date_expired + "\n"); 
        out.append("quiz_due_date = " + this.quiz_due_date + "\n"); 
        out.append("time_taken = " + this.time_taken + "\n"); 
        return out.toString();
    }


 
    public Object clone() {
        Quiz cloned = new Quiz();

        cloned.setQuiz_id(this.quiz_id); 
        cloned.setUploaded_by_id(this.uploaded_by_id); 
        if (this.quiz_description != null)
             cloned.setQuiz_description(new String(this.quiz_description)); 
        cloned.setCourse_sem_id(this.course_sem_id);
        if (this.quiz_title != null)
             cloned.setQuiz_title(new String(this.quiz_title));
        if (this.quiz_date_created != null)
             cloned.setQuiz_date_created((Timestamp)this.quiz_date_created.clone()); 
        if (this.quiz_date_published != null)
             cloned.setQuiz_date_published((Timestamp)this.quiz_date_published.clone()); 
        if (this.quiz_date_expired != null)
             cloned.setQuiz_date_expired((Timestamp)this.quiz_date_expired.clone()); 
        if (this.quiz_due_date != null)
             cloned.setQuiz_due_date((Timestamp)this.quiz_due_date.clone()); 
        cloned.setTime_taken(this.time_taken); 
        return cloned;
    }



}

