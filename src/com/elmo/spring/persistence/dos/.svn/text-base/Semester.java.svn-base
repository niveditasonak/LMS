package com.elmo.spring.persistence.dos;

import java.io.Serializable;
import java.sql.Timestamp;

 /**
  * Semester Value Object.
  * This class is value object representing database table SEMESTER_TABLE
  * This class is intended to be used together with associated Dao object.
  */

public class Semester implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private long semester_id;
    private String semester_type;
    private Timestamp sem_start_date;
    private Timestamp sem_end_date;



    /** 
     * Constructors. It generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public Semester () {

    }

    public Semester (long semester_idIn) {

          this.semester_id = semester_idIn;

    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behavior does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getSemester_id() {
          return this.semester_id;
    }
    public void setSemester_id(long semester_idIn) {
          this.semester_id = semester_idIn;
    }

    public String getSemester_type() {
          return this.semester_type;
    }
    public void setSemester_type(String semester_typeIn) {
          this.semester_type = semester_typeIn;
    }

    public Timestamp getSem_start_date() {
          return this.sem_start_date;
    }
    public void setSem_start_date(Timestamp sem_start_dateIn) {
          this.sem_start_date = sem_start_dateIn;
    }

    public Timestamp getSem_end_date() {
          return this.sem_end_date;
    }
    public void setSem_end_date(Timestamp sem_end_dateIn) {
          this.sem_end_date = sem_end_dateIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(long semester_idIn,
          String semester_typeIn,
          Timestamp sem_start_dateIn,
          Timestamp sem_end_dateIn) {
          this.semester_id = semester_idIn;
          this.semester_type = semester_typeIn;
          this.sem_start_date = sem_start_dateIn;
          this.sem_end_date = sem_end_dateIn;
    }


    /** 
     * hasEqualMapping-method will compare two Semester instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Semester valueObject) {

          if (valueObject.getSemester_id() != this.semester_id) {
                    return(false);
          }
          if (this.semester_type == null) {
                    if (valueObject.getSemester_type() != null)
                           return(false);
          } else if (!this.semester_type.equals(valueObject.getSemester_type())) {
                    return(false);
          }
          if (this.sem_start_date == null) {
                    if (valueObject.getSem_start_date() != null)
                           return(false);
          } else if (!this.sem_start_date.equals(valueObject.getSem_start_date())) {
                    return(false);
          }
          if (this.sem_end_date == null) {
                    if (valueObject.getSem_end_date() != null)
                           return(false);
          } else if (!this.sem_end_date.equals(valueObject.getSem_end_date())) {
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
        out.append("\nclass Semester, mapping to table SEMESTER_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("semester_id = " + this.semester_id + "\n"); 
        out.append("semester_type = " + this.semester_type + "\n"); 
        out.append("sem_start_date = " + this.sem_start_date + "\n"); 
        out.append("sem_end_date = " + this.sem_end_date + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        Semester cloned = new Semester();

        cloned.setSemester_id(this.semester_id); 
        if (this.semester_type != null)
             cloned.setSemester_type(new String(this.semester_type)); 
        if (this.sem_start_date != null)
             cloned.setSem_start_date((Timestamp)this.sem_start_date.clone()); 
        if (this.sem_end_date != null)
             cloned.setSem_end_date((Timestamp)this.sem_end_date.clone()); 
        return cloned;
    }

}
