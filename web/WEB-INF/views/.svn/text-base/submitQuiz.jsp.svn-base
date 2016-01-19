<%@ page import="com.elmo.spring.persistence.dos.*" %>
<%@ page import="com.elmo.spring.persistence.daos.QuestionDao" %>
<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 9/22/2015
  Time: 1:54 PM

--%>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/includes/header.jsp" %>
<%@ include file="/WEB-INF/includes/leftNavigation.jsp" %>
<script type="text/javascript">
  function checkBoxValidation()
  {
  }
</script>
<div id = "content">


<h3>Quiz Title: ${quiz_title}</h3>
  <h3>Quiz Description: ${quiz_description}</h3>
    <h3>Creation Date: ${quiz_date_created}</h3>
      <h3>Published Date: ${quiz_date_published}</h3>
        <h3>Due Date: ${quiz_due_date}</h3>
          <h3>Max Time: ${time_taken}</h3>


  <form id="quizform" action="/elmo/submitQuizSubmit" method="post" onsubmit="checkBoxValidation();">


    <%
      final Long global_current_quiz_id = (Long) Toolbox.getSession().getAttribute("GLOBAL_CURRENT_QUIZ_ID");
     if (global_current_quiz_id != -1) {

   out.print("  <input type=\"hidden\" id=\"maxtimeminutes\" value=\"${time_taken}\" />\n<span style=\"display: inline\" id=\"mytimerminutes\">0</span><span style=\"display: inline\"> minutes</span>\n" +
           "  <span id=\"mytimerseconds\">0</span><span> seconds</span>\n" +
           "  <br />\n" +
           "  <hr />");
       List<Question> questionList = QuestionDao.loadAllForQuizId((Long) Toolbox.getSession().getAttribute("GLOBAL_CURRENT_QUIZ_ID"));


       for (Question question : questionList) {

         String optA = question.getOptionA();
         String optB = question.getOptionB();
         String optC = question.getOptionC();
         String optD = question.getOptionD();

         String desc = question.getQuest_description();
         Long number = question.getQuestion_number();
         out.println(number + "). " + desc + "<br/>");

         if (question.getCorrect_answers().contains("true") || question.getCorrect_answers().contains("false")) {

           out.println("<input type='radio' name='q" + number + "' value='true'>True</input><br/>");
           out.println("<input type='radio' name='q" + number + "' value='false'>False</input>");


         } else {
           out.println("<input type='checkbox' name='q" + number + "' value='a'>" + optA + "</input><br/>");

           out.println("<input type='checkbox' name='q" + number + "' value='b'>" + optB + "</input><br/>");
           out.println("<input type='checkbox' name='q" + number + "' value='c'>" + optC + "</input><br/>");
           out.println("<input type='checkbox' name='q" + number + "' value='d'>" + optD + "</input>");

         }

         out.println("<br/><hr/>");

       }
       out.println("<input type=\"submit\" value=\"Submit Quiz\">");
     }
      else {
       out.println("<br /><span style='color:red;font-size:25px'> Sorry! You cannot take this quiz at the moment. A submission already exists <b>OR</b> the current date is not between <i>Published</i> and <i>Due</i> date! Please contact instructor.</span>");

     }
    %>



  </form>

  <script>

    var start = new Date;

    setInterval(function() {
      var seconds = parseInt($('#mytimerseconds').text());
      var minutes = parseInt($('#mytimerminutes').text());
      seconds++;
      var maxtimeminutesval = parseInt($('#maxtimeminutes').val());
      if (minutes + 1 == maxtimeminutesval && seconds == 60 )
      {
        $('#quizform').submit();
      }
      if (seconds == 60)
      {
        $('#mytimerminutes').text(minutes+1);
        $('#mytimerseconds').text(0);

      }
      else
      {
        $('#mytimerseconds').text(seconds);
      }

    }, 1000);
    </script>

</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
