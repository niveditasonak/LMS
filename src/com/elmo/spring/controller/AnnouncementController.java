package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.AnnouncementDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Announcement;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.dos.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


/**
 * Created by VB on 10/8/2015.
 */
@Controller
@SessionAttributes({""})
public class AnnouncementController {

    //private Logger logger = Logger.

    @RequestMapping(value = "/elmo/sendAnnouncement", method = RequestMethod.POST)
    public ModelAndView sendMessage(@RequestParam Map<String, String> params) {
        ModelAndView modelAndView = new ModelAndView(ViewNames.PEOPLE);
        String messageTitle = params.get("message_title");
        String messageBody = params.get("message_body");

        String message_type = params.get("message_type");

        Long userFromId = Long.valueOf(params.get("user_from_id"));
        String userToIds = params.get("user_to_ids");

        //announcemnts

        if (messageTitle.length() == 0 || messageBody.length() == 0) {
            modelAndView.getModel().put("errorList", "Message Body/Title can't be left empty!");
            return modelAndView;
        }

        if (message_type.equals("Announcement")) {
            Announcement anAnnouncement = new Announcement(userFromId, Long.valueOf(userToIds), messageTitle, messageBody, new Timestamp(new Date().getTime()), message_type);
            try {
                AnnouncementDao.create(anAnnouncement);
                modelAndView.getModel().put("errorList", "Announcement sent successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return modelAndView;
        }

        //messages

        if (userToIds.length() == 0) {
            modelAndView.getModel().put("errorList", "Please select at least one recipient!");
            return modelAndView;
        }
        for (StringTokenizer stringTokenizer = new StringTokenizer(userToIds, ","); stringTokenizer.hasMoreTokens(); ) {
            String s = stringTokenizer.nextToken();
            if (!s.equals("")) {
                Announcement anAnnouncement = new Announcement(userFromId, Long.valueOf(s), messageTitle, messageBody, new Timestamp(new Date().getTime()), message_type);
                try {
                    AnnouncementDao.create(anAnnouncement);
                    modelAndView.getModel().put("errorList", "Message(s) sent successfully!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return modelAndView;
    }


    @RequestMapping(value = "/elmo/inbox", method = RequestMethod.GET)
    public ModelAndView initInbox(@RequestParam Map<String, String> params) {
        ModelAndView modelAndView = new ModelAndView(ViewNames.INBOX);

        return modelAndView;
    }

    @RequestMapping(value = "/elmo/inboxList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listInboxMessages() {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.INBOX);

        List<Announcement> announcementList = null;
        List<Announcement> tmpList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        try {
            User current_user = Toolbox.getUserObjectInSession();
            announcementList = AnnouncementDao.loadAllMessageForUser(Toolbox.getUserObjectInSession().getUser_id());

            List<Enrollment> enrlList = Toolbox.getAllEnrollmentsForCurrentUser();
            for (Enrollment enr : enrlList) {

                tmpList = AnnouncementDao.loadAllAnnoucementForUser(enr.getCourse_sem_id());
                for (Announcement ann2 : tmpList) {
                    if (ann2.getFrom_user_id() != current_user.getUser_id()) {
                        announcementList.add(ann2);
                    }
                }
            }
            for (Announcement ann : announcementList) {
                Toolbox.fillUserObjectInAnnouncement(ann);
            }
            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", announcementList);

            // Convert Java Object to Json
            jsonArray = gson.toJson(JSONROOT);

            return jsonArray;
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }

    @RequestMapping(value = "/elmo/outboxList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listOutboxMessages() {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.INBOX);

        List<Announcement> announcementList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        try {

            announcementList = AnnouncementDao.loadAllMessageByUser(Toolbox.getUserObjectInSession().getUser_id());
            for (Announcement ann : announcementList) {
                if (!ann.getMessage_type().equals("Announcement")) {
                    ann.setSenderReceiver(UserDao.getObject(ann.getTo_user_id()).getName());
                } else {
                    ann.setSenderReceiver(Toolbox.getCourseFromCourseSemesterId(ann.getTo_user_id()).getCourse_number());
                }

            }

            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", announcementList);

            // Convert Java Object to Json
            jsonArray = gson.toJson(JSONROOT);

            return jsonArray;
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }


    @RequestMapping(value = "/elmo/outboxUpdate", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateOutbox(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            Announcement updatedAnnouncement = new Announcement();

            Long announcement_id = new Long(params.get("announcement_id"));

            updatedAnnouncement.setAnnouncement_id(announcement_id);

            AnnouncementDao.load(updatedAnnouncement);
            final String message_body = params.get("message_body");
            final String message_title = params.get("message_title");

            if (message_body == null || message_body.trim().length() == 0 || message_title == null || message_title.trim().length() == 0) {
                return Toolbox.Print_Jtable_Validation_Error("Fields can't be left empty!");
            }

            updatedAnnouncement.setAnnouncement_id(announcement_id);
            updatedAnnouncement.setMessage_body(message_body);
            updatedAnnouncement.setMessage_title(message_title);
            AnnouncementDao.save(updatedAnnouncement);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", updatedAnnouncement);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;


    }


    @RequestMapping(value = "/elmo/outboxDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String outboxDelete(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            Announcement ann = new Announcement(Long.valueOf(params.get("announcement_id")));
            AnnouncementDao.load(ann);


            if (ann.getMessage_type().equals("Announcement")) {
                AnnouncementDao.delete(Long.valueOf(params.get("announcement_id")));

            }

            if (ann.getMessage_type().equals("R_Deleted") || ann.getMessage_type().equals("S_Deleted")) {
                AnnouncementDao.delete(Long.valueOf(params.get("announcement_id")));
            }

            //Make the message

            if (ann.getMessage_type().equals("Message")) {
                AnnouncementDao.saveToSRDeleted(ann, "S_Deleted");
            }

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }

        JSONROOT.put("Result", "OK");
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }


    @RequestMapping(value = "/elmo/inboxDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String inboxDelete(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            Announcement ann = new Announcement(Long.valueOf(params.get("announcement_id")));
            AnnouncementDao.load(ann);

            if (ann.getMessage_type().equals("Announcement")) {
                return Toolbox.Print_Jtable_Validation_Error("Students can't delete Announcements");
            }

            if (ann.getMessage_type().equals("S_Deleted")) {
                AnnouncementDao.delete(Long.valueOf(params.get("announcement_id")));
            }

            //Make the message

            if (ann.getMessage_type().equals("Message")) {
                AnnouncementDao.saveToSRDeleted(ann, "R_Deleted");
            }

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }

        JSONROOT.put("Result", "OK");
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }

}
