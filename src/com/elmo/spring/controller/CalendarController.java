package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.AssignmentDao;
import com.elmo.spring.persistence.daos.EnrollmentDao;
import com.elmo.spring.persistence.daos.QuizDao;
import com.elmo.spring.persistence.dos.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by VB on 10/4/2015.
 */


/**
 * Handles requests for the calendar plugin.
 */
@Controller
public class CalendarController {

    @RequestMapping(value = "/elmo/calendar", method = RequestMethod.GET)
    public ModelAndView fetchDataAndPopulateJsonArray() {

        //TODO:Write Method to fetch all events for given student-semester combination
        //Don't create event table. Extract data from quiz and quiz instead

        User user = Toolbox.getUserObjectInSession();
        List<Event> eventList = null;
        try {
            List<Enrollment> enrollmentList = EnrollmentDao.loadAllForUserId(user.getUser_id());
            eventList = new ArrayList<>();
            for (Enrollment enrollment : enrollmentList) {
                Long csId = enrollment.getCourse_sem_id();
                List<Assignment> assList = null;

                if (enrollment.getEnrollment_type().equals("Student"))
                    {
                        assList = AssignmentDao.loadAllForCSIdExceptUnpublished(csId);
                    }
                    else
                    {
                        assList = AssignmentDao.loadAllForCSId(csId);
                    }


                for (Assignment assignment : assList) {
                    Event tempEvent = new Event();
                    tempEvent.setTitle(assignment.getAssignment_title());
                    if (assignment.getAssign_date_due() == null) {
                        continue;
                    }
                    tempEvent.setStart(Toolbox.formateThisDateStringForCalendarPlugin(new Date(assignment.getAssign_date_due().getTime())));

                    if (Toolbox.getActiveEnrollment() == null || enrollment.getEnrollment_id() != Toolbox.getActiveEnrollment().getEnrollment_id()) {
                        tempEvent.setUrl("");
                    } else {
                        if (enrollment.getEnrollment_type().equals("Student")) {
                            tempEvent.setUrl("/elmo/openAssignment?id=" + assignment.getAssignment_id());
                        } else {
                            tempEvent.setUrl("/elmo/assignments");
                        }

                    }
                    eventList.add(tempEvent);
                }


                List<Quiz> quizList = null;
                try {
                    if (enrollment.getEnrollment_type().equals("Student"))
                    {
                        quizList = QuizDao.loadAllForCSIdExceptUnpublished(csId);

                    }
                    else
                    {
                        quizList = QuizDao.loadAllForCSId(csId);

                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

                for (Quiz quiz : quizList) {
                    Event tempEvent = new Event();
                    tempEvent.setTitle(quiz.getQuiz_title());
                    if (quiz.getQuiz_due_date() == null) {
                        continue;
                    }
                    tempEvent.setStart(Toolbox.formateThisDateStringForCalendarPlugin(new Date(quiz.getQuiz_due_date().getTime())));

                    if (Toolbox.getActiveEnrollment() == null || enrollment.getEnrollment_id() != Toolbox.getActiveEnrollment().getEnrollment_id()) {
                        tempEvent.setUrl("");
                    } else {

                        if (enrollment.getEnrollment_type().equals("Student")) {
                            tempEvent.setUrl("/elmo/openQuiz?id=" + quiz.getQuiz_id());
                        } else {
                            tempEvent.setUrl("/elmo/quizes");
                        }
                    }
                    eventList.add(tempEvent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //   Event evt1 = new Event("Assignment#1 Due Today", "2015-03-24", "/elmo/quiz?1");
        // Event evt2 = new Event("Assignment#2 Due at 6pm", "2015-03-24T18:00:00", "/elmo/quiz?1");

        // display to console
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();


        ModelAndView modelAndView = new ModelAndView(ViewNames.CALENDAR);
        modelAndView.getModel().put("defaultDate", Toolbox.getTodaysDateStringForCalendarPlugin());
        modelAndView.getModel().put("eventJsonList", gson.toJson(eventList));
        return modelAndView;
    }

}
