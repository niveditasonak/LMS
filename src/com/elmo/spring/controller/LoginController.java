package com.elmo.spring.controller;
/**
 * Created by VB on 9/22/2015.
 */


import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.daos.SemesterDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.dos.User;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({"GLOBAL_USER", "GLOBAL_ENROLLMENTS", "GLOBAL_CURRENT_SEMESTER_NAME"})
public class LoginController {


    @RequestMapping(value = "/elmo/loginUser", method = RequestMethod.POST)
    public ModelAndView loginUser(@RequestParam Map<String, String> params) {
        ModelAndView modelAndView = new ModelAndView(ViewNames.LOGIN_FAILED);


        Boolean loginSuccessful = false;

        final String user_name = params.get("username");
        final String user_password = params.get("password");

        if (user_name == null || user_password == null) {

            modelAndView.getModel().put("error", "Username or password cannot be null");
            return modelAndView;
        }

        try {

            User newUser = new User(user_name, user_password);
            List<User> userList = null;
            try {
                userList = UserDao.searchMatching(newUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (!userList.isEmpty()) {
                modelAndView.setViewName(ViewNames.DASHBOARD);
                newUser = userList.get(0);
                loginSuccessful = true;
                List<Enrollment> enrollmentList = null;
                try {
                    enrollmentList = EnrollmentDao.loadAllForUserId(newUser.getUser_id());


                    for (Enrollment enr : enrollmentList) {

                        Toolbox.fillCourseObjectInEnrollment(enr);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                modelAndView.getModel().put("GLOBAL_USER", newUser);
                modelAndView.getModel().put("GLOBAL_ENROLLMENTS", enrollmentList);


            } else {
                modelAndView.getModel().put("error", "Incorrect username/password");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        SimpleDateFormat sm = new SimpleDateFormat("dd MMM yyyy");

        String strDate = sm.format(new Date());
        String semesterName = SemesterDao.getSemesterName(strDate);
        modelAndView.getModel().put("GLOBAL_CURRENT_SEMESTER_NAME", semesterName);

        System.out.println("Today's date" + strDate);

        return modelAndView;
    }


    @RequestMapping(value = "/elmo/logout", method = RequestMethod.GET)
    public ModelAndView logoutUser(@RequestParam Map<String, String> params) {
        ModelAndView modelAndView = new ModelAndView(ViewNames.LOGOUT_SUCCESS);
        Toolbox.invalidateSession();
        return modelAndView;
    }


}