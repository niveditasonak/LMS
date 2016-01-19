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

<div id = "content">


<h3>Assignment Title: ${assignment_title}</h3>
  <h3>Assignment Description: ${assign_description}</h3>
    <h3>Creation Date: ${assign_date_created}</h3>
      <h3>Published Date: ${assign_date_published}</h3>
        <h3>Due Date: ${assign_date_due}</h3>
          <h3>Max Score: ${score_max}</h3>
<br />


  <hr />

    <%
      final Long global_current_ass_id = (Long) Toolbox.getSession().getAttribute("GLOBAL_CURRENT_ASSIGNMENT_ID");
     if (global_current_ass_id != -1L) {

 %>
  <br />
  <br />
  <form action="/elmo/submitAssignmentSubmit" method="post" enctype="multipart/form-data">
    <input type="file" name="uploadedFile" accept=".pdf">
    <input type="submit" value="Upload Assignment">
  </form>
<%
  }
  else {
    out.println("<br /><span style='color:red;font-size:25px'> Sorry! You cannot take this assignment at the moment. A submission already exists <b>OR</b> the current date is not between <i>Published</i> and <i>Due</i> date! Please contact instructor.</span>");

  }

%>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
