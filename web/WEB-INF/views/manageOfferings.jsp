<%@ page import="java.util.List" %>
<%@ page import="com.elmo.spring.persistence.dos.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    $('#OfferingTableContainer').jtable({
        title: 'Offering List',
        openChildAsAccordion: true,
        actions: {
            listAction: 'manageOfferingsList',
            createAction: 'manageOfferingsCreate',
            updateAction: 'manageOfferingsUpdate',
            deleteAction: 'manageOfferingsDelete'
        },
        fields: {

            course_sem_id: {
                type: 'hidden',
                key: true,

            },
            semester_type: {
                title: 'Semester',
                width: '15%',
                edit: true,
                input: function (data) {
                    return <% out.print("'<select name=\"semester_type\">");

                List<Semester> list = (List<Semester>)(request.getSession().getAttribute("GLOBAL_SEMESTER_LIST_FOR_MANAGE_OFFERING"));
                    if (list!=null)
                    {
                    for(Semester s: list)
                    {
                 out.print("<option value=\""+ s.getSemester_id() + "\"> "+ s.getSemester_type()+"</option>" );
                    }
                    }
                out.print("</select>'");

                 %>;
                }
            }
            ,
            course_name: {
                title: 'Course',
                width: '30%',
                edit: true,
                input: function (data) {

                    return <% out.print("'<select name=\"course_name\">");

               List<Course> list2 = (List<Course>)(request.getSession().getAttribute("GLOBAL_COURSE_LIST_FOR_MANAGE_OFFERING"));
                     if(list2 != null)
                     {
                     for(Course c: list2)
                     {
                  out.print("<option value=\""+ c.getCourse_id() + "\"> "+ c.getCourse_number() +" - "+ c.getCourse_name() +"</option>" );
                     }
                     }
                 out.print("</select>'");

                  %>;
                }
            },
            Enrollments: {
                title: 'Enrollments',
                paging: true,
                edit:false,
                create:false,
                display: function (studentData) {
                    //Create an image that will be used to open child table
                    var $img = $('<img src="resources/plugins/jtable/list.png" title="Manage Enrollments" />');
                    //Open child table when user clicks the image
                    $img.click(function () {
                        $('#OfferingTableContainer').jtable('openChildTable',
                                $img.closest('tr'),
                                {
                                    title: 'Enrollments for: ' + studentData.record.course_name + ' ('+ studentData.record.semester_type+') ',
                                    actions: {
                                        listAction: '/elmo/enrollmentList?course_sem_id=' + studentData.record.course_sem_id,
                                        deleteAction: '/elmo/enrollmentDelete',
                                        updateAction: '/elmo/enrollmentUpdate',
                                        createAction: '/elmo/enrollmentCreate'
                                    },
                                    fields: {
                                        course_sem_id: {
                                            type: 'hidden',
                                            defaultValue: studentData.record.course_sem_id
                                        },
                                        enrollment_id: {
                                            key: true,
                                            create: false,
                                            edit: false,

                                            type:'hidden'
                                        },
                                        user_id: {
                                           edit:false,
                                            type: 'hidden',
                                            title: 'User id',
                                            input: function (data) {
                                                    return ''+data.value;
                                            }
                                        },
                                        user_name: {
                                            title: 'User Name',
                                            width: '60%',
                                            edit: true,
                                            input: function (data) {


                                                if (data.formType === 'create') {
                                                    return '<input type="text" name="user_name"></input>';
                                                }

                                                else
                                                return '<span>'+data.value+'</span>';
                                            }
                                        },
                                        enrollment_type: {
                                            title: 'Enrollment type',
                                            width: '40%',
                                            input : function(data){

                                                if (data.formType === 'create') {
                                                    return '<select name="enrollment_type"><option value="Student">Student</option><option value="Instructor" selected>Instructor</option></select>';
                                                }

                                                else   if (data.record.enrollment_type === "Student")
                                                    return '<select name="enrollment_type"><option value="Student" selected >Student</option><option value="Instructor">Instructor</option></select>';
                                                else
                                                    return '<select name="enrollment_type"><option value="Student">Student</option><option value="Instructor" selected>Instructor</option></select>';

                                            }

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

    $('#OfferingTableContainer').jtable('load');

  });
</script>

<div id = "content">
  <div style="text-align: center;margin-left: 14%">
    <h2>Manage Offering</h2>
    <div id="OfferingTableContainer"></div>
  </div>
</div>




<%@ include file="/WEB-INF/includes/footer.jsp" %>
