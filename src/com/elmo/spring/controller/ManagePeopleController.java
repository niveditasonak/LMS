package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.dos.User;
import com.elmo.spring.persistence.helpers.NotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VB on 10/8/2015.
 */
@Controller
@SessionAttributes({})
public class ManagePeopleController {

    public static final int MAX_ENROLLMENT_FOR_COURSE = 15;

    @RequestMapping(value = "/elmo/people", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.PEOPLE);


        return modelAndView;
    }


    @RequestMapping(value = "/elmo/peopleList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listEnrollments(@RequestParam Map<String, String> params) {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.PEOPLE);

        List<Enrollment> enrollmentList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        List<User> userList = new ArrayList<>();
        try {

            enrollmentList = EnrollmentDao.loadAllForOffering(Toolbox.getCSIDforActiveEnrollment());

            User current_user = Toolbox.getUserObjectInSession();
            for (Enrollment e : enrollmentList) {
                updateViewObject(e);
                if (current_user.getUser_id() != e.getUser_id()) {
                    userList.add(UserDao.getObject(e.getUser_id()));
                }
            }


            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", userList);

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


}
