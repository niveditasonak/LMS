<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 10/4/2015
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.elmo.spring.persistence.dos.User" %>
<%@ page import="com.elmo.spring.persistence.dos.Enrollment" %>
<%@ page import="com.elmo.spring.persistence.dos.Course_Semester" %>
<%@ page import="com.elmo.spring.persistence.daos.Course_SemesterDao" %>

<%@ page import="framework.Toolbox" %>
<%@ page import="java.util.*" %>
<%@ page import="com.elmo.spring.persistence.daos.Course_SemesterDao" %>

<% if (((User)request.getSession().getAttribute("GLOBAL_USER")).getUser_type().equals("Non-Admin"))
{
%>
<div id="elmo-course_item_container">

  <h4 id="elmo-semester"><%

      String semesterName = ((String)request.getSession().getAttribute("GLOBAL_CURRENT_SEMESTER_NAME"));

      if (semesterName != null) {
          out.print(semesterName);
  }
  %></h4>

    <%

        List<Enrollment> list = (List<Enrollment>)(request.getSession().getAttribute("GLOBAL_ENROLLMENTS"));
        if (list!=null)
        {
            for(Enrollment s: list) {
                final Course_Semester valueObject = new Course_Semester(s.getCourse_sem_id());
                Course_SemesterDao.load(valueObject);
                Toolbox.fillCSObjectWithCAndS(valueObject);
                String semShortName = valueObject.getSemester_type();
                semShortName = valueObject.getSemester_type().substring(0, 1);

                semShortName = semShortName + "'"+ valueObject.getSemester_type().substring(valueObject.getSemester_type().length()-2);
                out.print("<span class=\"elmo-course_item\"><a href=/elmo/coursePage?id=" + s.getEnrollment_id() + " \">" + s.getCourse_object().getCourse_number() + " (" + s.getEnrollment_type() + ")("+semShortName+")</a></span>");
            }
        }

        if (list == null || list.size() == 0)
        {
            out.print("<span>No Enrollments Found</span>");
        }

    %>

<span id="elmo-wrapper-container">

 <span id="elmo-wrapper">

 <% Enrollment curr_enrollment = ((Enrollment) request.getSession().getAttribute("GLOBAL_CURRENT_ENROLLMENT"));

     if(curr_enrollment !=null)
     {
         %>
              <h4 id="elmo-section-tabs-header">

<%
                          Toolbox.fillCourseObjectInEnrollment(curr_enrollment);
                          out.print("<span>"+curr_enrollment.getCourse_object().getCourse_number() + "</span>");

                  %>



              </h4>



	<span id="assignments-menu-item" class="elmo-section">
		<a class="assignments" href="/elmo/assignments">Assignments</a>
	</span>

     	<span id="assignments-menu-item" class="elmo-section">
		<a class="assignments" href="/elmo/quizes">Quizes</a>
	</span>

	<span id="people-menu-item" class="elmo-section">
		<a class="people" href="/elmo/people">People</a>
	</span>
	<span id="files-menu-item" class="elmo-section">
		<a class="files" href="/elmo/files">Files</a>
	</span>
     <span id="grades-menu-item" class="elmo-section">
		<a class="grades" href="/elmo/grades">Grades</a>
	</span>


    <%   }
                  %>

</span>


</div>

<%
    }

%>