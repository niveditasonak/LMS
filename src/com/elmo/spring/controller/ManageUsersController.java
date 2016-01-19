package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.dos.User;
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
public class ManageUsersController {

    @RequestMapping(value = "/elmo/manageUsers", method = RequestMethod.GET)
    public ModelAndView sayHelloAgain() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_USERS);
        Toolbox.invalidateIfNotAdmin(modelAndView);
        return modelAndView;
    }


    @RequestMapping(value = "/elmo/manageUsersList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listUsers() {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_USERS);

        List<User> userList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        try {

            userList = UserDao.loadAll();

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


    @RequestMapping(value = "/elmo/manageUsersUpdate", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateUser(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            User updatedUser = new User();

            Long userId = new Long(params.get("user_id"));
            updatedUser.setUser_id(userId);
            UserDao.load(updatedUser);

            if (params.get("reset_password") != null && !params.get("reset_password").equals("")) {
                params.put("user_password", Long.toString(Toolbox.getPasswordHash(params.get("reset_password"))));
            }

            if (params.get("user_type").equals("Admin")) {
                updatedUser.setAll(userId, "Admin", params.get("name"), params.get("user_name"), params.get("user_password"), params.get("user_biodata"));
            } else {

                updatedUser.setAll(userId, "Non-Admin", params.get("name"), params.get("user_name"), params.get("user_password"), params.get("user_biodata"));
            }
            UserDao.save(updatedUser);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", updatedUser);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;


    }


    @RequestMapping(value = "/elmo/manageUsersDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteUser(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        final Long userId = Long.valueOf(params.get("user_id"));

        if (userId.equals(1L)) {
            return Toolbox.Print_Jtable_Validation_Error("Can't Delete Admin User!");
        }

        try {

            List<Enrollment> enrolList = EnrollmentDao.loadAllForUserId(userId);

            if (enrolList != null && enrolList.size() > 0) {
                JSONROOT.put("Result", "ERROR");
                JSONROOT.put("Message", "Can't Delete User Due to existing enrollment(s)!");
                jsonArray = gson.toJson(JSONROOT);
                return jsonArray;
            }

            UserDao.delete(new User(userId));
            JSONROOT.put("Result", "OK");
            jsonArray = gson.toJson(JSONROOT);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }


    @RequestMapping(value = "/elmo/manageUsersCreate", method = RequestMethod.POST)
    public
    @ResponseBody
    String createUser(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {
            final String user_type = params.get("user_type");
            final String name = params.get("name");
            final String user_name = params.get("user_name");
            final String user_password = params.get("user_password");
            final String user_biodata = params.get("user_biodata");

            if (user_type.trim().length() == 0 || name.trim().length()==0 || user_name.trim().length() ==0 || user_password.trim().length() ==0 ) {
                return Toolbox.Print_Jtable_Validation_Error("All fields are mandatory!");
            }
            User newUser = new User(user_type, name, user_name, user_password, user_biodata);
            UserDao.create(newUser);
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", newUser);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }


}
