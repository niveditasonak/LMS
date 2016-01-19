package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.helpers.NotFoundException;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Created by VB on 10/8/2015.
 */
@Controller
@SessionAttributes({"GLOBAL_CURRENT_ENROLLMENT"})
public class CoursePageController {

    //private Logger logger = Logger.

    @RequestMapping(value = "/elmo/coursePage", method = RequestMethod.GET)
    public ModelAndView setEnrollmentInSession(@RequestParam Map<String, String> params) {
        ModelAndView modelAndView = new ModelAndView(ViewNames.DASHBOARD);
        Long enrollmentId = Long.valueOf(params.get("id"));

        List<Enrollment> enrCheckList = Toolbox.getAllEnrollmentsForCurrentUser();
        Boolean flag = false;
        for (Enrollment enrollment : enrCheckList) {

            if (enrollment.getEnrollment_id() == enrollmentId) {
                flag = true;
            }
        }

        if (flag == false) {
            Toolbox.invalidateSession();
            modelAndView.setViewName(ViewNames.LOGOUT_SUCCESS);
            return modelAndView;
        }
        Enrollment enr = null;
        try {
            enr = EnrollmentDao.getObject(enrollmentId);
            Toolbox.fillCourseObjectInEnrollment(enr);
            modelAndView.getModel().put("GLOBAL_CURRENT_ENROLLMENT", enr);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return modelAndView;
    }


}
