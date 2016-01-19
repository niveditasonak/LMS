package com.elmo.spring.persistence.dos;


import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

import java.io.InputStream;

 
public class File implements Cloneable, Serializable {

    
    private long file_id;
    private long course_id;
    private Timestamp file_date_created;
    private Timestamp file_date_published;
    private Timestamp file_date_expired;
    private byte[] file_content;
    private long uploaded_by_id;



    public File () {

    }

    public File (long file_id) {

          this.file_id = file_id;

    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public long getFile_id() {
          return this.file_id;
    }
    public void setFile_id(long file_id) {
          this.file_id = file_id;
    }

    public long getCourse_id() {
          return this.course_id;
    }
    public void setCourse_id(long course_id) {
          this.course_id = course_id;
    }

    public Timestamp getFile_date_created() {
          return this.file_date_created;
    }
    public void setFile_date_created(Timestamp file_date_created) {
          this.file_date_created = file_date_created;
    }

    public Timestamp getFile_date_published() {
          return this.file_date_published;
    }
    public void setFile_date_published(Timestamp file_date_published) {
          this.file_date_published = file_date_published;
    }

    public Timestamp getFile_date_expired() {
          return this.file_date_expired;
    }
    public void setFile_date_expired(Timestamp file_date_expired) {
          this.file_date_expired = file_date_expired;
    }

    public byte[] getFile_content() {
          return this.file_content;
    }
    public void setFile_content(byte[] file_content) {
          this.file_content = file_content;
    }

    public long getUploaded_by_id() {
          return this.uploaded_by_id;
    }
    public void setUploaded_by_id(long uploaded_by_id) {
          this.uploaded_by_id = uploaded_by_id;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variales, without going trough the 
     * individual set-methods.
     */

    public void setAll(long file_id,
          long course_id,
          Timestamp file_date_created,
          Timestamp file_date_published,
          Timestamp file_date_expired,
          byte[] file_content,
          long uploaded_by_id) {
          this.file_id = file_id;
          this.course_id = course_id;
          this.file_date_created = file_date_created;
          this.file_date_published = file_date_published;
          this.file_date_expired = file_date_expired;
          this.file_content = file_content;
          this.uploaded_by_id = uploaded_by_id;
    }


    public boolean hasEqualMapping(File valueObject) {

          if (valueObject.getFile_id() != this.file_id) {
                    return(false);
          }
          if (valueObject.getCourse_id() != this.course_id) {
                    return(false);
          }
          if (this.file_date_created == null) {
                    if (valueObject.getFile_date_created() != null)
                           return(false);
          } else if (!this.file_date_created.equals(valueObject.getFile_date_created())) {
                    return(false);
          }
          if (this.file_date_published == null) {
                    if (valueObject.getFile_date_published() != null)
                           return(false);
          } else if (!this.file_date_published.equals(valueObject.getFile_date_published())) {
                    return(false);
          }
          if (this.file_date_expired == null) {
                    if (valueObject.getFile_date_expired() != null)
                           return(false);
          } else if (!this.file_date_expired.equals(valueObject.getFile_date_expired())) {
                    return(false);
          }
          if (this.file_content == null) {
                    if (valueObject.getFile_content() != null)
                           return(false);
          } else if (!this.file_content.equals(valueObject.getFile_content())) {
                    return(false);
          }
        return valueObject.getUploaded_by_id() == this.uploaded_by_id;

    }



   
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass File, mapping to table FILE_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("file_id = " + this.file_id + "\n"); 
        out.append("course_id = " + this.course_id + "\n"); 
        out.append("file_date_created = " + this.file_date_created + "\n"); 
        out.append("file_date_published = " + this.file_date_published + "\n"); 
        out.append("file_date_expired = " + this.file_date_expired + "\n"); 
        out.append("file_content = " + this.file_content + "\n"); 
        out.append("uploaded_by_id = " + this.uploaded_by_id + "\n"); 
        return out.toString();
    }


    
        public Object clone() {
        File cloned = new File();

        cloned.setFile_id(this.file_id); 
        cloned.setCourse_id(this.course_id); 
        if (this.file_date_created != null)
             cloned.setFile_date_created((Timestamp)this.file_date_created.clone()); 
        if (this.file_date_published != null)
             cloned.setFile_date_published((Timestamp)this.file_date_published.clone()); 
        if (this.file_date_expired != null)
             cloned.setFile_date_expired((Timestamp)this.file_date_expired.clone()); 
        
         cloned.setFile_content(this.file_content); 
        cloned.setUploaded_by_id(this.uploaded_by_id); 
        return cloned;
    }



 
}

