package com.elmo.spring.persistence.dos;

import java.io.Serializable;
import java.sql.Timestamp;


public class Announcement implements Cloneable, Serializable {

    
    private long announcement_id;
    private long from_user_id;
    private long to_user_id;
    private String message_title;
    private String message_body;
    private String message_type;
    private Timestamp date_sent;
    private User fromUserObject;
    private String senderReceiver;

    public String getSenderReceiver() {
        return senderReceiver;
    }

    public void setSenderReceiver(String senderReceiver) {
        this.senderReceiver = senderReceiver;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public User getFromUserObject() {
        return fromUserObject;
    }

    public void setFromUserObject(User fromUserObject) {
        this.fromUserObject = fromUserObject;
    }

    public Announcement() {

    }

    public Announcement(long announcement_id) {

          this.announcement_id = announcement_id;

    }

    public Announcement(long from_user_id, long to_user_id, String message_title, String message_body, Timestamp date_sent, String message_type) {
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.message_title = message_title;
        this.message_body = message_body;
        this.date_sent = date_sent;
        this.message_type = message_type;
    }

    public long getAnnouncement_id() {
          return this.announcement_id;
    }
    public void setAnnouncement_id(long announcement_id) {
          this.announcement_id = announcement_id;
    }

    public long getFrom_user_id() {
          return this.from_user_id;
    }
    public void setFrom_user_id(long from_user_id) {
          this.from_user_id= from_user_id;
    }

    public long getTo_user_id() {
          return this.to_user_id;
    }
    public void setTo_user_id(long to_user_id) {
          this.to_user_id = to_user_id;
    }

    public String getMessage_title() {
          return this.message_title;
    }
    public void setMessage_title(String message_title) {
          this.message_title = message_title;
    }

    public String getMessage_body() {
          return this.message_body;
    }
    public void setMessage_body(String message_body) {
          this.message_body = message_body;
    }

    public Timestamp getDate_sent() {
          return this.date_sent;
    }
    public void setDate_sent(Timestamp date_sent) {
          this.date_sent = date_sent;
    }



    public void setAll(long announcement_id,
          long from_user_id,
          long to_user_id,
          String message_title,
          String message_body,
          Timestamp date_sent) {
          this.announcement_id = announcement_id;
          this.from_user_id = from_user_id;
          this.to_user_id = to_user_id;
          this.message_title = message_title;
          this.message_body = message_body;
          this.date_sent = date_sent;
    }


   
    public boolean hasEqualMapping(Announcement valueObject) {

          if (valueObject.getAnnouncement_id() != this.announcement_id) {
                    return(false);
          }
          if (valueObject.getFrom_user_id() != this.from_user_id) {
                    return(false);
          }
          if (valueObject.getTo_user_id() != this.to_user_id) {
                    return(false);
          }
          if (this.message_title == null) {
                    if (valueObject.getMessage_title() != null)
                           return(false);
          } else if (!this.message_title.equals(valueObject.getMessage_title())) {
                    return(false);
          }
          if (this.message_body == null) {
                    if (valueObject.getMessage_body() != null)
                           return(false);
          } else if (!this.message_body.equals(valueObject.getMessage_body())) {
                    return(false);
          }
          if (this.date_sent == null) {
                    if (valueObject.getDate_sent() != null)
                           return(false);
          } else if (!this.date_sent.equals(valueObject.getDate_sent())) {
                    return(false);
          }

          return true;
    }



    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass ANNOUNCEMENT_TABLE, mapping to table ANNOUNCEMENT_TABLE\n");
        out.append("Persistent attributes: \n"); 
        out.append("announcement_id = " + this.announcement_id + "\n"); 
        out.append("From_user_id = " + this.from_user_id + "\n"); 
        out.append("to_user_id = " + this.to_user_id + "\n"); 
        out.append("message_title = " + this.message_title + "\n"); 
        out.append("message_body = " + this.message_body + "\n"); 
        out.append("date_sent = " + this.date_sent + "\n"); 
        return out.toString();
    }



}

