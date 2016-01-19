package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.daos.SubmissionDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.dos.Submission;
import com.elmo.spring.persistence.dos.User;
import com.elmo.spring.persistence.helpers.NotFoundException;
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
@SessionAttributes({})
public class ManageEnrollmentsController {

    public static final int MAX_ENROLLMENT_FOR_COURSE = 15;

    @RequestMapping(value = "/elmo/enrollment", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_OFFERINGS);

        Toolbox.invalidateIfNotAdmin(modelAndView);

        return modelAndView;
    }


    @RequestMapping(value = "/elmo/enrollmentList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listEnrollments(@RequestParam Map<String, String> params) {
        //TODO: Sort order in SQL.

        List<Enrollment> enrollmentList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            enrollmentList = EnrollmentDao.loadAllForOffering(Long.valueOf(params.get("course_sem_id")));


            for (Enrollment e : enrollmentList) {
                updateViewObject(e);

            }

            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", enrollmentList);

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


    @RequestMapping(value = "/elmo/enrollmentListForGrades", method = RequestMethod.POST)
    public
    @ResponseBody
    String listEnrollmentsForGrades(@RequestParam Map<String, String> params) {
        //TODO: Sort order in SQL.

        List<Enrollment> enrollmentList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {
            final Long course_sem_id = Long.valueOf(params.get("course_sem_id"));


            if (Toolbox.isAdmin() || Toolbox.isInstructor()) {
                enrollmentList = EnrollmentDao.loadAllForOfferingForType(course_sem_id, "Student");


                for (Enrollment e : enrollmentList) {
                    updateViewObject(e);

                }
            } else {
                enrollmentList = EnrollmentDao.loadAllForUserIdForCSId(Toolbox.getUserObjectInSession().getUser_id(), course_sem_id);


                for (Enrollment e : enrollmentList) {
                    updateViewObject(e);

                }
            }
            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", enrollmentList);

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


    private static void updateViewObject(Enrollment cs) throws NotFoundException, SQLException {

        cs.setUser_name((UserDao.getObject(cs.getUser_id())).getUser_name());

    }


    @RequestMapping(value = "/elmo/enrollmentUpdate", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateEnrollment(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {


            final Long course_sem_id = Long.valueOf(params.get("course_sem_id"));

            final String enrollment_type = params.get("enrollment_type");
            List<Enrollment> enrolList2 = EnrollmentDao.loadAllForOfferingForType(course_sem_id, "Instructor");


            Enrollment oldRecord = null;

            if (enrollment_type.equals("Instructor") && enrolList2.size() >= 1) {
                oldRecord = enrolList2.get(0);
                updateViewObject(oldRecord);
                if (!oldRecord.getUser_name().equalsIgnoreCase(params.get("user_name")))
                    return Toolbox.Print_Jtable_Validation_Error("Can't update enrollment: Instructor already exists for this course!");


            }

            String userName = params.get("user_name");
            User tempUser = new User();
            tempUser.setUser_name(userName);
            tempUser.setUser_type("Non-Admin");
            List<User> userList = UserDao.searchMatching(tempUser);
            if (userList != null && userList.size() > 0) {
                tempUser = userList.get(0);
            }
            //    List<Enrollment> enrolList3 = EnrollmentDao.loadAllForUserIdForCSId(tempUser.getUser_id(), course_sem_id);

            //  if (enrolList3 != null && enrolList3.size()>1)
            // {
            //   return Toolbox.Print_Jtable_Validation_Error("Can't update enrollment: Enrollment already exists for this user for this course!");

            //}
            Long enrollment_id = Long.valueOf(params.get("enrollment_id"));

            Enrollment updatedEnrollment = new Enrollment(enrollment_id);
            EnrollmentDao.load(updatedEnrollment);

            updatedEnrollment.setEnrollment_type(enrollment_type);

            EnrollmentDao.save(updatedEnrollment);

            updateViewObject(updatedEnrollment);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", updatedEnrollment);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;


    }


    @RequestMapping(value = "/elmo/enrollmentDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteEnrollment(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {


            final Long enrollment_id = Long.valueOf(params.get("enrollment_id"));
            List<Submission> assList = SubmissionDao.loadAllForEnrollmentId(enrollment_id);

            if (assList != null && assList.size() > 0) {
                return Toolbox.Print_Jtable_Validation_Error("Can't delete enrollment: Submission(s) already exist for this enrollment!");
            }


            EnrollmentDao.delete(new Enrollment(enrollment_id));
            JSONROOT.put("Result", "OK");
            jsonArray = gson.toJson(JSONROOT);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }


    @RequestMapping(value = "/elmo/enrollmentCreate", method = RequestMethod.POST)
    public
    @ResponseBody
    String createEnrollment(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        try {

            final Long course_sem_id = Long.valueOf(params.get("course_sem_id"));
            List<Enrollment> enrolList = EnrollmentDao.loadAllForOffering(course_sem_id);

            if (enrolList.size() > MAX_ENROLLMENT_FOR_COURSE) {
                return Toolbox.Print_Jtable_Validation_Error("Can't create enrollment: Maximum limit exceeded");
            }


            final String enrollment_type = params.get("enrollment_type");
            List<Enrollment> enrolList2 = EnrollmentDao.loadAllForOfferingForType(course_sem_id, enrollment_type);

            Enrollment oldRecord = null;

            if (enrollment_type.equals("Instructor") && enrolList2.size() >= 1) {
                oldRecord = enrolList2.get(0);
                updateViewObject(oldRecord);
                if (!oldRecord.getUser_name().equals(params.get("user_name")))
                    return Toolbox.Print_Jtable_Validation_Error("Can't create enrollment: Instructor already exists for this course!");

            }

            String userName = params.get("user_name");
            User tempUser = new User();
            tempUser.setUser_name(userName);
            tempUser.setUser_type("Non-Admin");
            List<User> userList = UserDao.searchMatching(tempUser);
            if (userList != null && userList.size() > 0) {
                tempUser = userList.get(0);
            }
            List<Enrollment> enrolList3 = EnrollmentDao.loadAllForUserIdForCSId(tempUser.getUser_id(), course_sem_id);

            if (enrolList3 != null && enrolList3.size() > 0) {
                return Toolbox.Print_Jtable_Validation_Error("Can't create enrollment: Enrollment already exists for this user for this course!");

            }

            Long user_id = 0L;
            final Long course_name = Long.valueOf(params.get("course_sem_id"));

            final String user_name = params.get("user_name");

            User dummyUser = new User();
            dummyUser.setUser_name(user_name);
            dummyUser.setUser_type("Non-Admin");

            List<User> userList2 = UserDao.searchMatching(dummyUser);

            if (userList2.size() == 0) {
                return Toolbox.Print_Jtable_Validation_Error("Can't create enrollment: Username doesn't exist");
            } else {
                user_id = userList2.get(0).getUser_id();
            }


            Enrollment newEnrollment = new Enrollment(user_id, course_name, enrollment_type);
            EnrollmentDao.create(newEnrollment);

            updateViewObject(newEnrollment);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", newEnrollment);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }


}
