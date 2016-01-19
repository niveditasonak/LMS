package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.QuizDao;
import com.elmo.spring.persistence.daos.SubmissionDao;
import com.elmo.spring.persistence.dos.Quiz;
import com.elmo.spring.persistence.dos.Submission;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by VB on 10/8/2015.
 */
@Controller
public class ManageQuizesController {


    @RequestMapping(value = "/elmo/quizes", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();

        if (Toolbox.isInstructor()) {
            modelAndView.setViewName(ViewNames.MANAGE_QUIZES);

        } else {
            modelAndView.setViewName(ViewNames.TAKE_QUIZ);
        }
        return modelAndView;
    }


    @RequestMapping(value = "/elmo/manageQuizes", method = RequestMethod.GET)
    public ModelAndView initManage() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_QUIZES);
        return modelAndView;
    }


    @RequestMapping(value = "/elmo/manageQuizesList", method = RequestMethod.POST)
    public
    @ResponseBody
    String listQuizs() {
        //TODO: Sort order in SQL.
        ModelAndView modelAndView = new ModelAndView(ViewNames.MANAGE_QUIZES);

        List<Quiz> quizList = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;
        try {

            if(Toolbox.isInstructor()) {
                quizList = QuizDao.loadAllForCSId(Toolbox.getCSIDforActiveEnrollment());
            }
            else
            {
                quizList = QuizDao.loadAllForCSIdExceptUnpublished(Toolbox.getCSIDforActiveEnrollment());
            }
            for (Quiz q : quizList) {
                q.setQuiz_date_created_str(Toolbox.formatThisTimestampForTimePicker(q.getQuiz_date_created()));
                //q.setQuiz_date_expired_str(Toolbox.formatThisTimestampForTimePicker(q.getQuiz_date_expired()));
                q.setQuiz_date_published_str(Toolbox.formatThisTimestampForTimePicker(q.getQuiz_date_published()));
                q.setQuiz_due_date_str(Toolbox.formatThisTimestampForTimePicker(q.getQuiz_due_date()));

                Long runningSum = Toolbox.findTotalMaxScoreForQuiz(q);

                q.setMax_score(runningSum);
            }

            //Return in the format required by jTable plugin
            JSONROOT.put("Result", "OK");
            JSONROOT.put("Records", quizList);

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


    @RequestMapping(value = "/elmo/manageQuizesUpdate", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateQuiz(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            Quiz updatedQuiz = new Quiz();

            final Long quizId = new Long(params.get("quiz_id"));

            final String quiz_description = params.get("quiz_description");

            final String quiz_date_published = params.get("quiz_date_published_str");
            //final String quiz_date_expired = params.get("quiz_date_expired_str");

            final String quiz_date_due = params.get("quiz_due_date_str");


            final String quiz_title = params.get("quiz_title");
            final Long time_taken ;

            if (quiz_title.trim().length()==0 || quiz_description.trim().length()==0 || quiz_date_due.trim().length() == 0 || quiz_date_published.trim().length() ==0 || params.get("time_taken").trim().length()==0)
            {
                return Toolbox.Print_Jtable_Validation_Error("All fields are mandatory!");
            }

            try {
                time_taken = Long.valueOf(params.get("time_taken"));
            }
            catch (NumberFormatException nfe)
            {
                return Toolbox.Print_Jtable_Validation_Error("Invalid Time Taken!");
            }

            if (quiz_title == null || quiz_title.trim().length() == 0) {
                return Toolbox.Print_Jtable_Validation_Error("Fields can't be left empty!");
            }

            if (time_taken == null || time_taken < 0L) {
                return Toolbox.Print_Jtable_Validation_Error("Max Time can't be less than zero!");
            }

//            if (Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(Toolbox.formatThisTimestampForTimePicker(updatedQuiz.getQuiz_date_created()))).after(Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(quiz_date_published))))
//            {
//                return Toolbox.Print_Jtable_Validation_Error("Published Date has to be after Created Date!");
//            }

            if (Toolbox.isIncomingPublishedBeforeDue(Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(quiz_date_published)), Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(quiz_date_due)))) {

                return Toolbox.Print_Jtable_Validation_Error("Published Date has to be before Due Date!");
            }

            updatedQuiz.setQuiz_id(quizId);
            QuizDao.load(updatedQuiz);
            updatedQuiz.setAllForUpdate(quiz_description, quiz_title, Toolbox.formatThisStringToTimestamp(quiz_date_published), Toolbox.formatThisStringToTimestamp(quiz_date_due), time_taken);
            QuizDao.save(updatedQuiz);


            updatedQuiz.setQuiz_due_date_str(quiz_date_due);
            // updatedQuiz.setQuiz_date_expired_str(quiz_date_expired);
            updatedQuiz.setQuiz_date_created_str(Toolbox.formatThisTimestampForTimePicker(updatedQuiz.getQuiz_date_created()));
            updatedQuiz.setQuiz_date_published_str(quiz_date_published);


            Long runningSum = Toolbox.findTotalMaxScoreForQuiz(updatedQuiz);
            updatedQuiz.setMax_score(runningSum);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", updatedQuiz);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;


    }


    @RequestMapping(value = "/elmo/manageQuizesDelete", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteQuiz(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            List<Submission> subList = SubmissionDao.loadAllForQuizId(Long.valueOf(params.get("quiz_id")));


            if (subList != null && subList.size() > 0) {
                JSONROOT.put("Result", "ERROR");
                JSONROOT.put("Message", "Can't delete quiz due to existing submissions!");
                jsonArray = gson.toJson(JSONROOT);
                return jsonArray;

            }

            QuizDao.delete(new Quiz(Long.valueOf(params.get("quiz_id"))));
            JSONROOT.put("Result", "OK");
            jsonArray = gson.toJson(JSONROOT);
        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());
            jsonArray = gson.toJson(JSONROOT);

        }
        return jsonArray;
    }


    @RequestMapping(value = "/elmo/manageQuizesCreate", method = RequestMethod.POST)
    public
    @ResponseBody
    String createQuiz(@RequestParam Map<String, String> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        String jsonArray = null;

        try {

            final Timestamp ts_temp = new Timestamp(new Date().getTime());

            final Long uploaded_by_enr_id = Toolbox.getActiveEnrollment().getEnrollment_id();

            final String quiz_description = params.get("quiz_description");

            final String quiz_date_published = params.get("quiz_date_published_str");
           // final String quiz_date_expired = params.get("quiz_date_expired_str");

            final String quiz_date_due = params.get("quiz_due_date_str");
            final Long course_sem_id = Toolbox.getCSIDforActiveEnrollment();


            final String quiz_title = params.get("quiz_title");
            final Long time_taken ;


            try {
                time_taken = Long.valueOf(params.get("time_taken"));
            }
            catch (NumberFormatException nfe)
            {
                return Toolbox.Print_Jtable_Validation_Error("Invalid Time Taken!");
            }

            if (quiz_title.trim().length()==0 || quiz_description.trim().length()==0 || quiz_date_due.trim().length() == 0 || quiz_date_published.trim().length() ==0 || params.get("time_taken").trim().length()==0)
            {
                return Toolbox.Print_Jtable_Validation_Error("All fields are mandatory!");
            }

            if (time_taken == null || time_taken < 0L) {
                return Toolbox.Print_Jtable_Validation_Error("Max Time can't be less than zero!");
            }

//            Date today = new Date();
//            if (today.after(Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(quiz_date_published))))
//            {
//                return Toolbox.Print_Jtable_Validation_Error("Published Date has to be after current date!");
//            }

            if (Toolbox.isIncomingPublishedBeforeDue(Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(quiz_date_published)), Toolbox.convertTStoDate(Toolbox.formatThisStringToTimestamp(quiz_date_due)))) {

                return Toolbox.Print_Jtable_Validation_Error("Published Date has to be before Due Date!");
            }
            Quiz newQuiz = new Quiz();
            newQuiz.setAll(uploaded_by_enr_id, quiz_description, course_sem_id, quiz_title, ts_temp, Toolbox.formatThisStringToTimestamp(quiz_date_published), null, Toolbox.formatThisStringToTimestamp(quiz_date_due), time_taken);
            QuizDao.create(newQuiz);


            newQuiz.setQuiz_due_date_str(quiz_date_due);
          //  newQuiz.setQuiz_date_expired_str(quiz_date_expired);
            newQuiz.setQuiz_date_created_str(Toolbox.formatThisTimestampForTimePicker(ts_temp));
            newQuiz.setQuiz_date_published_str(quiz_date_published);

            JSONROOT.put("Result", "OK");
            JSONROOT.put("Record", newQuiz);

        } catch (Exception ex) {
            JSONROOT.put("Result", "ERROR");
            JSONROOT.put("Message", ex.getMessage());

        }
        jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }


}
