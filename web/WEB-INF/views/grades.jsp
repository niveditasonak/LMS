<%@ page import="com.elmo.spring.persistence.dos.Semester" %>
<%@ page import="com.elmo.spring.persistence.daos.*" %>
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
  $(document).ready(function() {
    $('#GradeEnrollmentTableContainer').jtable({
      title: 'Enrollment(s) for <% out.print(Toolbox.getCourseNameForActiveEnrollment());%>',
      openChildAsAccordion: true,
      actions: {
        listAction: 'enrollmentListForGrades?course_sem_id='+<%out.println(Toolbox.getActiveEnrollment().getCourse_sem_id()); %>+''
      },
      fields: {

        enrollment_id: {
          title: 'enrollment_id ID',
          key: true,
          type: 'hidden'

        },
        user_name: {
          title: 'Name',
          width: '15%',
          edit: false,

        },


        Grades: {
          title: 'Grades',
          paging: true,
          edit:false,
          display: function (studentData) {
            //Create an image that will be used to open child table
            var $img = $('<img src="resources/plugins/jtable/list.png" title="View Grades" />');
            //Open child table when user clicks the image
            $img.click(function () {
              $('#GradeEnrollmentTableContainer').jtable('openChildTable',
                      $img.closest('tr'),
                      {
                        title: ' ',
                        actions: {
                          listAction: '/elmo/viewGradesList?enrollment_id=' + studentData.record.enrollment_id,
                          },
                        fields: {
                          ass_quiz_title: {
                          edit:false,
                            title:'Exam Title'
                          },

                          max_score_for_display: {
                            edit:false,
                            title:'Max Score',
                          },

                          score_received: {
                            edit:false,
                            title:'Score Received',
                          }

                        }
                      }, function (data) { //opened handler
                        data.childTable.jtable('load');
                      });
            });
            //Return image to show on the person row
            return $img;
          }
        }
      }


    });

    $('#GradeEnrollmentTableContainer').jtable('load');

  });
</script>


<div id = "content">



  <div style="text-align: center;margin-left: 14%">
    <h2>Gradebook</h2>
    <div id="GradeEnrollmentTableContainer"></div>
  </div>

</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
