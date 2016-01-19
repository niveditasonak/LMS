<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 10/8/2015
  Time: 2:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/includes/header.jsp" %>
<%@ include file="/WEB-INF/includes/leftNavigation.jsp" %>


<script type="text/javascript">
  $(document).ready(function() {
    $('#CourseTableContainer').jtable({
      title : 'Course List',
      actions : {
        listAction : 'manageCoursesList',
        createAction:'manageCoursesCreate',
        updateAction: 'manageCoursesUpdate',
        deleteAction: 'manageCoursesDelete'
      },
      fields : {

      course_id : {
        title : 'Course ID',
        key: true,
        type:'hidden'
      },
          course_number : {
          title : 'Course Number',
          width : '20%',
          edit : true
        }
        ,
         course_name : {
          title : 'Name',
          width : '40%',
          edit : true
        }

        ,
        course_description : {
          title : 'Description',
          width : '40%',
          edit : true
        }
      }
    });
    $('#CourseTableContainer').jtable('load');
  });
</script>

<div id = "content">
  <div style="text-align: center;margin-left: 14%">
    <h2>Manage Courses</h2>
    <div id="CourseTableContainer"></div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>

