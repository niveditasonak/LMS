package com.elmo.spring.persistence.dos;

import java.io.Serializable;

 /**
  * Enrollment Value Object.
  * This class is value object representing database table ENROLLMENT_TABLE
  * This class is intended to be used together with associated Dao object.
  */

public class Enrollment implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private long enrollment_id;
    private long user_id;
    private long course_sem_id;
    private String enrollment_type;
    private String user_name;
    private Course course_object;

     public Course getCourse_object() {
         return course_object;
     }

     public void setCourse_object(Course course_object) {
         this.course_object = course_object;
     }

     public String getUser_name() {
         return user_name;
     }

     public void setUser_name(String user_name) {
         this.user_name = user_name;
     }

     /**
     * Constructors. It generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public Enrollment () {

    }

    public Enrollment (long enrollment_idIn) {

          this.enrollment_id = enrollment_idIn;

    }

     public Enrollment(long user_id, long course_sem_id, String enrollment_type) {
         this.user_id = user_id;
         this.course_sem_id = course_sem_id;
         this.enrollment_type = enrollment_type;
     }

     /**
     * Get- and Set-methods for persistent variables. The default
     * behavior does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getEnrollment_id() {
          return this.enrollment_id;
    }
    public void setEnrollment_id(long enrollment_idIn) {
          this.enrollment_id = enrollment_idIn;
    }

    public long getUser_id() {
          return this.user_id;
    }
    public void setUser_id(long user_idIn) {
          this.user_id = user_idIn;
    }

    public long getCourse_sem_id() {
          return this.course_sem_id;
    }
    public void setCourse_sem_id(long course_idIn) {
          this.course_sem_id = course_idIn;
    }

    public String getEnrollment_type() {
          return this.enrollment_type;
    }
    public void setEnrollment_type(String enrollment_typeIn) {
          this.enrollment_type = enrollment_typeIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(long enrollment_idIn,

          long course_idIn,
          String enrollment_typeIn) {
          this.enrollment_id = enrollment_idIn;

          this.course_sem_id = course_idIn;
          this.enrollment_type = enrollment_typeIn;
    }


    /** 
     * hasEqualMapping-method will compare two Enrollment instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Enrollment valueObject) {

          if (valueObject.getEnrollment_id() != this.enrollment_id) {
                    return(false);
          }
          if (valueObject.getUser_id() != this.user_id) {
                    return(false);
          }
          if (valueObject.getCourse_sem_id() != this.course_sem_id) {
                    return(false);
          }
          if (this.enrollment_type == null) {
                    if (valueObject.getEnrollment_type() != null)
                           return(false);
          } else if (!this.enrollment_type.equals(valueObject.getEnrollment_type())) {
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
        out.append("\nclass Enrollment, mapping to table ENROLLMENT_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("enrollment_id = " + this.enrollment_id + "\n"); 
        out.append("user_id = " + this.user_id + "\n"); 
        out.append("course_sem_id = " + this.course_sem_id + "\n");
        out.append("enrollment_type = " + this.enrollment_type + "\n"); 
        return out.toString();
    }




}

