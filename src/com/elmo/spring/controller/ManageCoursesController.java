package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.CourseDao;
import com.elmo.spring.persistence.daos.Course_SemesterDao;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.dos.Course;
import com.elmo.spring.persistence.dos.Course_Semester;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by VB on 10/8/2015.
 */
@Controller
public class ManageCoursesController {

    @RequestMapping(value = "/elmo/manageCourses", method = RequestMethod.GET)
    public ModelAndView sayHelloAgain() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_COURSES);

        Toolbox.invalidateIfNotAdmin(modelAndView);

        return modelAndView;
    }


    @RequestMapping(value = "/elmo/manageCoursesList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listCourses() {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_COURSES);

        List<Course> courseList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        try {

            courseList = CourseDao.loadAll();

            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", courseList);

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


    @RequestMapping(value = "/elmo/manageCoursesUpdate", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateCourse(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            Long courseId = new Long(params.get("course_id"));

            Course updatedCourse = new Course();
            updatedCourse.setCourse_id(courseId);
            CourseDao.load(updatedCourse);


            final String course_number = params.get("course_number");
            final String course_name = params.get("course_name");
            final String course_description = params.get("course_description");

            if (!Toolbox.checkIfCourseNameIsUniqueForUpdate(course_number,courseId))
            {
                return Toolbox.Print_Jtable_Validation_Error("Course Number should be unique!");
            }


            if (course_number == null || course_number.trim().length() == 0 || course_name == null || course_name.trim().length() == 0) {
                return Toolbox.Print_Jtable_Validation_Error("Fields can't be left empty!");
            }

            updatedCourse.setAll(courseId, course_number, course_name,
                    course_description);
            CourseDao.save(updatedCourse);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", updatedCourse);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;


    }


    @RequestMapping(value = "/elmo/manageCoursesDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteCourse(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            List<Course_Semester> courseSemesterListForCourse = Course_SemesterDao.loadAllForCourseId(Long.valueOf(params.get("course_id")));

            for (Course_Semester cs : courseSemesterListForCourse) {
                List enrlList = EnrollmentDao.loadAllForOffering(cs.getCourse_id());
                if (enrlList != null && enrlList.size() > 0) {
                    JSONROOT.put("Result", "ERROR");
                    JSONROOT.put("Message", "Can't delete course due to existing enrollments");
                    jsonArray = gson.toJson(JSONROOT);
                    return jsonArray;
                }
            }
            CourseDao.delete(new Course(Long.valueOf(params.get("course_id"))));
            JSONROOT.put("Result", "OK");
            jsonArray = gson.toJson(JSONROOT);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }


    @RequestMapping(value = "/elmo/manageCoursesCreate", method = RequestMethod.POST)
    public
    @ResponseBody
    String createCourse(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {
            final String course_number = params.get("course_number");
            final String course_name = params.get("course_name");
            final String course_description = params.get("course_description");

            if (course_number.trim().length() == 0 || course_name.trim().length()==0 || course_description.trim().length() ==0 ) {
                return Toolbox.Print_Jtable_Validation_Error("All fields are mandatory!");
            }

            if (!Toolbox.checkIfCourseNameIsUnique(course_number))
            {
                return Toolbox.Print_Jtable_Validation_Error("Course Number should be unique!");
            }

            Course newCourse = new Course(course_number, course_name,
                    course_description);
            CourseDao.create(newCourse);
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", newCourse);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }


}
