<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 10/4/2015
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.elmo.spring.persistence.dos.Enrollment" %>


</div> <!-- #application -->
<input type="hidden" value="${pageContext.request.servletPath}" id="currentTabName" />

<% Enrollment curr_enrollment2 = ((Enrollment) request.getSession().getAttribute("GLOBAL_CURRENT_ENROLLMENT"));


%>

<input type="hidden" value="<%

  if(curr_enrollment2 !=null)
    {
        Toolbox.fillCourseObjectInEnrollment(curr_enrollment2);
    out.print(curr_enrollment2.getCourse_object().getCourse_number());
     }
     %>" id="currentCourseShortName" />


<script type="text/javascript">


    var currentTabStringName = $('#currentTabName').attr('value');

    //To underline tab names
    if (currentTabStringName.contains("calendar")) {

        $('#calendar-menu-item').addClass("elmo-menu-active");

    }
    else if (currentTabStringName.contains("gradesForAdmin")) {
        $('#grades-menu-item').addClass("elmo-menu-active");
    }
    else if (currentTabStringName.contains("manageCourses")) {
        $('#courses-menu-item').addClass("elmo-menu-active");
    }
    else if (currentTabStringName.contains("manageUsers")) {
        $('#users-menu-item').addClass("elmo-menu-active");
    }
    else if (currentTabStringName.contains("manageOfferings")) {
        $('#offerings-menu-item').addClass("elmo-menu-active");
    }


    //To highlight menu items
    if (currentTabStringName.contains("assignments")) {
        $('#assignments-menu-item').addClass("elmo-active");

    }
    else if (currentTabStringName.contains("people")) {
        $('#people-menu-item').addClass("elmo-active");
    }
    else if (currentTabStringName.contains("files")) {
        $('#files-menu-item').addClass("elmo-active");
    }


    //To highlight course Name

    var currentCourseShortStringName = $('#currentCourseShortName').attr('value');


    $('.elmo-course_item').each(function() {
        if(currentCourseShortStringName != '') {
            if (this.innerHTML.contains(currentCourseShortStringName) === true) {
                $(this).addClass("elmo-active");
            }
        }

    });

    if (currentCourseShortStringName.contains("calendar")) {

        $('#calendar-menu-item').addClass("elmo-menu-active");

    }
    else if (currentCourseShortStringName.contains("grades")) {
        $('#grades-menu-item').addClass("elmo-menu-active");
    }
    else if (currentCourseShortStringName.contains("courses")) {
        $('#courses-menu-item').addClass("elmo-menu-active");
    }


</script>
</body></html>