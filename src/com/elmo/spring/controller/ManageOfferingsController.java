package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.CourseDao;
import com.elmo.spring.persistence.daos.Course_SemesterDao;
import com.elmo.spring.persistence.daos.SemesterDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Course;
import com.elmo.spring.persistence.dos.Course_Semester;
import com.elmo.spring.persistence.dos.Semester;
import com.elmo.spring.persistence.dos.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VB on 10/8/2015.
 */
@Controller
@SessionAttributes({"GLOBAL_USER_LIST_FOR_MANAGE_OFFERING", "GLOBAL_COURSE_LIST_FOR_MANAGE_OFFERING", "GLOBAL_SEMESTER_LIST_FOR_MANAGE_OFFERING"})
public class ManageOfferingsController {

    @RequestMapping(value = "/elmo/manageOfferings", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_OFFERINGS);

        Toolbox.invalidateIfNotAdmin(modelAndView);

        List<Course> courseList = null;
        List<Semester> semesterList = null;
        List<User> userList = null;

        try {
            courseList = CourseDao.loadAll();
            semesterList = SemesterDao.loadAll();
            userList = UserDao.loadAll();

            modelAndView.getModel().put("GLOBAL_COURSE_LIST_FOR_MANAGE_OFFERING", courseList);
            modelAndView.getModel().put("GLOBAL_SEMESTER_LIST_FOR_MANAGE_OFFERING", semesterList);
            modelAndView.getModel().put("GLOBAL_USER_LIST_FOR_MANAGE_OFFERING", userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @RequestMapping(value = "/elmo/manageOfferingsList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listOfferings() {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_OFFERINGS);

        List<Course_Semester> offeringList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            offeringList = Course_SemesterDao.loadAll();


            for (Course_Semester cs : offeringList) {
                Toolbox.fillCSObjectWithCAndS(cs);

            }

            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", offeringList);

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


    @RequestMapping(value = "/elmo/manageOfferingsUpdate", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateOffering(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            Course_Semester updatedCourse_Semester = new Course_Semester();

            Long course_semester_id = new Long(params.get("course_sem_id"));
            updatedCourse_Semester.setCourse_sem_id(course_semester_id);
            Course_SemesterDao.load(updatedCourse_Semester);
            updatedCourse_Semester.setAll(course_semester_id, Long.valueOf(params.get("course_name")), Long.valueOf(params.get("semester_type")));
            Course_SemesterDao.save(updatedCourse_Semester);

            Toolbox.fillCSObjectWithCAndS(updatedCourse_Semester);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", updatedCourse_Semester);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;


    }


    @RequestMapping(value = "/elmo/manageOfferingsDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteOffering(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {
            Course_SemesterDao.delete(new Course_Semester(Long.valueOf(params.get("course_sem_id"))));
            JSONROOT.put("Result", "OK");
            jsonArray = gson.toJson(JSONROOT);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", "Can't delete offering due to existing enrollment(s)!");
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }


    @RequestMapping(value = "/elmo/manageOfferingsCreate", method = RequestMethod.POST)
    public
    @ResponseBody
    String createOffering(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {


            Course_Semester newCourse_Semester = new Course_Semester(Long.valueOf(params.get("course_name")), Long.valueOf(params.get("semester_type")));
            Course_SemesterDao.create(newCourse_Semester);

            Toolbox.fillCSObjectWithCAndS(newCourse_Semester);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", newCourse_Semester);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }


}
