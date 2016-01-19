package framework;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.*;
import com.elmo.spring.persistence.dos.*;
import com.elmo.spring.persistence.helpers.ConnectionManager;
import com.elmo.spring.persistence.helpers.NotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VB on 10/4/2015.
 */
public class Toolbox {

    private static Connection conn;

    static {
        conn = ConnectionManager.getInstance().getConnection();
    }


    public static String getTodaysDateStringForCalendarPlugin() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);

    }


    public static String formateThisDateStringForCalendarPlugin(Date date) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);

    }

    public static Long getSequenceNextValue(String sequenceName) {
        String sqlIdentifier = "select " + sequenceName + ".NEXTVAL from dual";
        PreparedStatement pst = null;
        long returnValue = 0L;
        try {
            pst = conn.prepareStatement(sqlIdentifier);

            ResultSet rs = pst.executeQuery();
            if (rs.next())
                returnValue = rs.getLong(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;
    }


    public static Long getPasswordHash(String plainText) {
        String sqlIdentifier = "select ora_hash('" + plainText + "') from dual";
        PreparedStatement pst = null;
        long returnValue = 0L;
        try {
            pst = conn.prepareStatement(sqlIdentifier);

            ResultSet rs = pst.executeQuery();
            if (rs.next())
                returnValue = rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;
    }


    public static String Print_Jtable_Validation_Error(String message) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> JSONROOT = new HashMap<String, Object>();
        JSONROOT.put("Result", "ERROR");
        JSONROOT.put("Message", message);
        String jsonArray = gson.toJson(JSONROOT);

        return jsonArray;
    }

    public static void fillCourseObjectInEnrollment(Enrollment enr) {
        Course_Semester tempCS = new Course_Semester();
        tempCS.setCourse_sem_id(enr.getCourse_sem_id());
        try {
            Course_SemesterDao.load(tempCS);
            enr.setCourse_object(CourseDao.getObject(tempCS.getCourse_id()));
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getCourseNameForActiveEnrollment() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Enrollment curr_enrollment = ((Enrollment) request.getSession().getAttribute("GLOBAL_CURRENT_ENROLLMENT"));


        if (curr_enrollment != null) {
            Toolbox.fillCourseObjectInEnrollment(curr_enrollment);
            return curr_enrollment.getCourse_object().getCourse_number();
        }
        return "";
    }


    public static Long getCSIDforActiveEnrollment() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Enrollment curr_enrollment = ((Enrollment) request.getSession().getAttribute("GLOBAL_CURRENT_ENROLLMENT"));

        if (curr_enrollment != null) {
            Toolbox.fillCourseObjectInEnrollment(curr_enrollment);
            return curr_enrollment.getCourse_sem_id();
        }
        return 0L;
    }


    public static Enrollment getActiveEnrollment() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return ((Enrollment) request.getSession().getAttribute("GLOBAL_CURRENT_ENROLLMENT"));
    }


    public static List<Enrollment> getAllEnrollmentsForCurrentUser() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return ((List<Enrollment>) request.getSession().getAttribute("GLOBAL_ENROLLMENTS"));
    }

    public static HttpSession getSession() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return request.getSession();
    }

    public static Long getCurrentAssignmentId() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return ((Long) request.getSession().getAttribute("GLOBAL_CURRENT_ASSIGNMENT_ID"));
    }

    public static Long getCurrentQuizId() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return ((Long) request.getSession().getAttribute("GLOBAL_CURRENT_QUIZ_ID"));
    }

    public static User getUserObjectInSession() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getSession().getAttribute("GLOBAL_USER");


        if (user == null) {
            Toolbox.invalidateSession();
        }


        return user;
    }

    public static Boolean isAdmin() {
        User user = getUserObjectInSession();
        return user.getUser_type().equals("Admin");
    }

    public static void fillUserObjectInAnnouncement(Announcement ann) {
        try {
            ann.setFromUserObject(UserDao.getObject(ann.getFrom_user_id()));
            ann.setSenderReceiver(ann.getFromUserObject().getName());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Course getCourseFromCourseSemesterId(Long cs_id)

    {
        Course_Semester cs = new Course_Semester(cs_id);

        try {
            Course_SemesterDao.load(cs);

            return CourseDao.getObject(cs.getCourse_id());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isInstructor() {
        return getActiveEnrollment().getEnrollment_type().equals("Instructor");
    }

    public static void invalidateSession() {
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    public static void invalidateIfNotAdmin(ModelAndView modelAndView) {
        if (!isAdmin()) {
            invalidateSession();
            modelAndView.setViewName(ViewNames.LOGOUT_SUCCESS);
        }
    }

    public static void checkIfUserIsNullAndRedirect(ModelAndView modelAndView, User user) {
        if (user == null) {
            modelAndView.setViewName(ViewNames.LOGOUT_SUCCESS);
        }
    }

    public static void fillCSObjectWithCAndS(Course_Semester cs) throws NotFoundException, SQLException {
        cs.setCourseObject(CourseDao.getObject(cs.getCourse_id()));
        cs.setCourse_name(cs.getCourseObject().getCourse_number() + " - " + cs.getCourseObject().getCourse_name());
        cs.setCourse_number(cs.getCourseObject().getCourse_number());
        cs.setSemesterObject(SemesterDao.getObject(cs.getSemester_id()));
        cs.setSemester_type(cs.getSemesterObject().getSemester_type());
    }

    public static String formatThisDateForTimePicker(Date date) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String strDate = sm.format(date);
        return strDate;
    }


    public static String formatThisTimestampForTimePicker(Timestamp ts) {
        if (ts != null) {
            Date date = new Date(ts.getTime());
            SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String strDate = sm.format(date);
            return strDate;
        }
        return null;
    }


    public static Timestamp formatThisStringToTimestamp(String strDate) throws ParseException {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date parsedDate = null;

        if (strDate != null) {
            parsedDate = sm.parse(strDate);
            return new java.sql.Timestamp(parsedDate.getTime());
        }

        return null;
    }


    public static User getUserForEnrId(Long enrId) throws NotFoundException, SQLException {
        return UserDao.getObject(EnrollmentDao.getObject(enrId).getUser_id());
    }

    public static Long findTotalMaxScoreForQuiz(Quiz q) throws SQLException {
        List<Question> questionList = QuestionDao.loadAllForQuizId(q.getQuiz_id());
        Long runningSum = 0L;
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            runningSum += question.getMax_score();
        }
        return runningSum;
    }

    public static boolean doesQuizSubmissionExistForEnrIdForQuizId(Long enrId, Long quizId) {
        try {
            return !SubmissionDao.loadAllForEnrollmentIdQuizId(enrId, quizId).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean doesAssignmentSubmissionExistForEnrIdForAssId(Long enrId, Long assId) {
        try {
            return !SubmissionDao.loadAllForEnrollmentIdAssId(enrId, assId).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isQuizBetweenPublishedAndDue(Long quizId) {
        Quiz q = new Quiz(quizId);

        try {
            QuizDao.load(q);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Date published = convertTStoDate(q.getQuiz_date_published());
        Date due = convertTStoDate(q.getQuiz_due_date());
        Date today = new Date();

        if (today.after(published) && today.before(due)) {
            return true;
        } else return false;

    }


    public static boolean isAssBetweenPublishedAndDue(Long assId) {
        Assignment ass = new Assignment(assId);

        try {
            AssignmentDao.load(ass);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Date published = convertTStoDate(ass.getAssign_date_published());
        Date due = convertTStoDate(ass.getAssign_date_due());
        Date today = new Date();

        if (today.after(published) && today.before(due)) {
            return true;
        } else return false;

    }


    public static boolean isIncomingPublishedBeforeDue(Date pub, Date due) {

        if (pub.before(due)) {
            return false;
        } else return true;

    }


    public static Date convertTStoDate(Timestamp ts) {
        return new Date(ts.getTime());
    }

    public static boolean checkIfAssignmentCSMatchesUserCS(Assignment newAss, Enrollment activeEnrollment) {

        if (newAss.getCourse_sem_id() == activeEnrollment.getCourse_sem_id()) {
            return true;
        }
        return false;
    }

    public static boolean checkIfQuizCSMatchesUserCS(Quiz newQuiz, Enrollment activeEnrollment) {

        if (newQuiz.getCourse_sem_id() == activeEnrollment.getCourse_sem_id()) {
            return true;
        }
        return false;
    }

    public static boolean checkIfCourseNameIsUnique(String newCourseName) {
        List<Course> courseList = null;
        try {
            courseList = CourseDao.loadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Course course : courseList) {
            if (course.getCourse_number().equalsIgnoreCase(newCourseName)) {
                return false;
            }
        }

return  true;


}

    public static boolean checkIfCourseNameIsUniqueForUpdate(String newCourseName,Long courseId)
    {
        List<Course> courseList = null;
        try {
            courseList = CourseDao.loadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Course course : courseList) {
            if (course.getCourse_number().equalsIgnoreCase(newCourseName) && courseId !=course.getCourse_id())
            {
                return false;
            }
        }
return  true;

    }
}