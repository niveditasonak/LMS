package com.elmo.spring.persistence.dos;



import java.io.Serializable;

 /**
  * Course Value Object.
  * This class is value object representing database table COURSE_TABLE
  * This class is intended to be used together with associated Dao object.
  */

public class Course implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private long course_id;
    private String course_number;
    private String course_name;
    private String course_description;




    /** 
     * Constructors. It generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public Course () {

    }

    public Course (long course_idIn) {

          this.course_id = course_idIn;

    }

     public Course(String course_number, String course_name, String course_description) {
         this.course_id = course_id;
         this.course_number = course_number;
         this.course_name = course_name;
         this.course_description = course_description;

     }

     /**
     * Get- and Set-methods for persistent variables. The default
     * Behavior does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getCourse_id() {
          return this.course_id;
    }
    public void setCourse_id(long course_idIn) {
          this.course_id = course_idIn;
    }

    public String getCourse_number() {
          return this.course_number;
    }
    public void setCourse_number(String course_numberIn) {
          this.course_number = course_numberIn;
    }

    public String getCourse_name() {
          return this.course_name;
    }
    public void setCourse_name(String course_nameIn) {
          this.course_name = course_nameIn;
    }

    public String getCourse_description() {
          return this.course_description;
    }
    public void setCourse_description(String course_descriptionIn) {
          this.course_description = course_descriptionIn;
    }


    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(long course_idIn,
          String course_numberIn,
          String course_nameIn,
          String course_descriptionIn) {
          this.course_id = course_idIn;
          this.course_number = course_numberIn;
          this.course_name = course_nameIn;
          this.course_description = course_descriptionIn;

    }


    /** 
     * hasEqualMapping-method will compare two Course instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Course valueObject) {

          if (valueObject.getCourse_id() != this.course_id) {
                    return(false);
          }
          if (this.course_number == null) {
                    if (valueObject.getCourse_number() != null)
                           return(false);
          } else if (!this.course_number.equals(valueObject.getCourse_number())) {
                    return(false);
          }
          if (this.course_name == null) {
                    if (valueObject.getCourse_name() != null)
                           return(false);
          } else if (!this.course_name.equals(valueObject.getCourse_name())) {
                    return(false);
          }
          if (this.course_description == null) {
                    if (valueObject.getCourse_description() != null)
                           return(false);
          } else if (!this.course_description.equals(valueObject.getCourse_description())) {
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
        out.append("\nclass Course, mapping to table COURSE_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("course_id = " + this.course_id + "\n"); 
        out.append("course_number = " + this.course_number + "\n"); 
        out.append("course_name = " + this.course_name + "\n"); 
        out.append("course_description = " + this.course_description + "\n"); 

        return out.toString();
    }

}
