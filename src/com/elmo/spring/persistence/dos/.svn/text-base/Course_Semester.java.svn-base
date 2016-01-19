package com.elmo.spring.persistence.dos;
import java.io.Serializable;

 /**
  * Course_Semester Value Object.
  * This class is value object representing database table COURSE_SEMESTER_TABLE
  * This class is intended to be used together with associated Dao object.
  */

public class Course_Semester implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private long course_sem_id;
    private long course_id;
    private long semester_id;
    private Course courseObject;
    private Semester semesterObject;

     private String course_name;
     private String course_number;
     private String semester_type;


     public void setSemester_id(Long semester_id) {
         this.semester_id = semester_id;
     }

     public String getCourse_name() {
         return course_name;
     }

     public void setCourse_name(String course_name) {
         this.course_name = course_name;
     }

     public String getCourse_number() {
         return course_number;
     }

     public void setCourse_number(String course_number) {
         this.course_number = course_number;
     }

     public String getSemester_type() {
         return semester_type;
     }

     public void setSemester_type(String semester_type) {
         this.semester_type = semester_type;
     }

     public Course getCourseObject() {
         return courseObject;
     }

     public void setCourseObject(Course courseObject) {
         this.courseObject = courseObject;
     }

     public Semester getSemesterObject() {
         return semesterObject;
     }

     public void setSemesterObject(Semester semesterObject) {
         this.semesterObject = semesterObject;
     }

     /**
     * Constructors. It generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */
     public Course_Semester(long course_id, long semester_id) {
         this.course_id = course_id;
         this.semester_id = semester_id;
     }

     public Course_Semester() {

     }

     public Course_Semester(long course_sem_id) {
         this.course_sem_id = course_sem_id;
     }

     /**
     * Get- and Set-methods for persistent variables. The default
     * behavior does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getCourse_sem_id() {
          return this.course_sem_id;
    }
    public void setCourse_sem_id(long course_sem_idIn) {
          this.course_sem_id = course_sem_idIn;
    }

    public long getCourse_id() {
          return this.course_id;
    }
    public void setCourse_id(long course_idIn) {
          this.course_id = course_idIn;
    }

    public long getSemester_id() {
          return this.semester_id;
    }
    public void setSemester_id(long semester_idIn) {
          this.semester_id = semester_idIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(long course_sem_idIn,
          long course_idIn,
          long semester_idIn) {
          this.course_sem_id = course_sem_idIn;
          this.course_id = course_idIn;
          this.semester_id = semester_idIn;
    }


    /** 
     * hasEqualMapping-method will compare two Course_Semester instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Course_Semester valueObject) {

          if (valueObject.getCourse_sem_id() != this.course_sem_id) {
                    return(false);
          }
          if (valueObject.getCourse_id() != this.course_id) {
                    return(false);
          }
        return valueObject.getSemester_id() == this.semester_id;

    }



    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in textlog.
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass Course_Semester, mapping to table COURSE_SEMESTER_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("course_sem_id = " + this.course_sem_id + "\n"); 
        out.append("course_id = " + this.course_id + "\n"); 
        out.append("semester_id = " + this.semester_id + "\n"); 
        return out.toString();
    }


}

