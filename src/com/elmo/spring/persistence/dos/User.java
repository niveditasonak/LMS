package com.elmo.spring.persistence.dos;
import java.io.Serializable;

 /**
  * User Value Object.
  * This class is value object representing database table USER_TABLE
  * This class is intended to be used together with associated Dao object.
  */


public class User implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private long user_id;
    private String user_type;
    private String name;
    private String user_name;
    private String user_password;
    private String user_biodata;



    /** 
     * Constructors. it generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public User () {

    }

    public User (long user_idIn) {

          this.user_id = user_idIn;

    }


     public User(
                        String user_typeIn,
                        String nameIn,
                        String user_nameIn,
                        String user_passwordIn,
                        String user_biodataIn) {

         this.user_type = user_typeIn;
         this.name = nameIn;
         this.user_name = user_nameIn;
         this.user_password = user_passwordIn;
         this.user_biodata = user_biodataIn;
     }



     public User(
             String user_nameIn,
             String user_passwordIn
             ) {

         this.user_name = user_nameIn;
         this.user_password = user_passwordIn;
         }


     /**
     * Get- and Set-methods for persistent variables. The default
     * behavior does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getUser_id() {
          return this.user_id;
    }
    public void setUser_id(long user_idIn) {
          this.user_id = user_idIn;
    }

    public String getUser_type() {
          return this.user_type;
    }
    public void setUser_type(String user_typeIn) {
          this.user_type = user_typeIn;
    }

    public String getName() {
          return this.name;
    }
    public void setName(String nameIn) {
          this.name = nameIn;
    }

    public String getUser_name() {
          return this.user_name;
    }
    public void setUser_name(String user_nameIn) {
          this.user_name = user_nameIn;
    }

    public String getUser_password() {
          return this.user_password;
    }
    public void setUser_password(String user_passwordIn) {
          this.user_password = user_passwordIn;
    }

    public String getUser_biodata() {
          return this.user_biodata;
    }
    public void setUser_biodata(String user_biodataIn) {
          this.user_biodata = user_biodataIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(long user_idIn,
          String user_typeIn,
          String nameIn,
          String user_nameIn,
          String user_passwordIn,
          String user_biodataIn) {
          this.user_id = user_idIn;
          this.user_type = user_typeIn;
          this.name = nameIn;
          this.user_name = user_nameIn;
          this.user_password = user_passwordIn;
          this.user_biodata = user_biodataIn;
    }


    /** 
     * hasEqualMapping-method will compare two User instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(User valueObject) {

          if (valueObject.getUser_id() != this.user_id) {
                    return(false);
          }
          if (this.user_type == null) {
                    if (valueObject.getUser_type() != null)
                           return(false);
          } else if (!this.user_type.equals(valueObject.getUser_type())) {
                    return(false);
          }
          if (this.name == null) {
                    if (valueObject.getName() != null)
                           return(false);
          } else if (!this.name.equals(valueObject.getName())) {
                    return(false);
          }
          if (this.user_name == null) {
                    if (valueObject.getUser_name() != null)
                           return(false);
          } else if (!this.user_name.equals(valueObject.getUser_name())) {
                    return(false);
          }
          if (this.user_password == null) {
                    if (valueObject.getUser_password() != null)
                           return(false);
          } else if (!this.user_password.equals(valueObject.getUser_password())) {
                    return(false);
          }
          if (this.user_biodata == null) {
                    if (valueObject.getUser_biodata() != null)
                           return(false);
          } else if (!this.user_biodata.equals(valueObject.getUser_biodata())) {
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
        out.append("\nclass User, mapping to table USER_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("user_id = " + this.user_id + "\n"); 
        out.append("user_type = " + this.user_type + "\n"); 
        out.append("name = " + this.name + "\n"); 
        out.append("user_name = " + this.user_name + "\n"); 
        out.append("user_password = " + this.user_password + "\n"); 
        out.append("user_biodata = " + this.user_biodata + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the retuned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        User cloned = new User();

        cloned.setUser_id(this.user_id); 
        if (this.user_type != null)
             cloned.setUser_type(new String(this.user_type)); 
        if (this.name != null)
             cloned.setName(new String(this.name)); 
        if (this.user_name != null)
             cloned.setUser_name(new String(this.user_name)); 
        if (this.user_password != null)
             cloned.setUser_password(new String(this.user_password)); 
        if (this.user_biodata != null)
             cloned.setUser_biodata(new String(this.user_biodata)); 
        return cloned;
    }


}

