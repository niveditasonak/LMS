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
    $('#AssignmentTableContainer').jtable({
      title : 'Assignment List',
      actions : {
        listAction : 'manageAssignmentsList',
        createAction:'manageAssignmentsCreate',
        updateAction: 'manageAssignmentsUpdate',
        deleteAction: 'manageAssignmentsDelete'
      },
      fields : {

          assignment_id : {
                key: true,
              type:'hidden',


      },

          assignment_title : {
              title : 'Title',
              width : '30%',
              edit : true
          }

        ,
          assign_description : {
          title : 'Description',
          width : '20%',
          edit : true
        }

        ,
          assign_date_created_str : {
          title : 'Created',
          width : '10%',
          edit : false,
            create :false,
        },
          assign_date_published_str : {
              title : 'Published',
              width : '10%',
              edit : true
          }
          ,          assign_date_due_str : {
              title : 'Due',
              width : '10%',
              edit : true
          },

          course_sem_id : {

              width : '10%',
              edit: true,
              type: 'hidden',

          }
          ,
          score_max : {
              title : 'Max Score',
              width : '10%',
              edit : true
          },
          Submissions: {
              title: 'Submissions',
              paging: true,
              edit:false,
              create:false,
              display: function (studentData) {
                  //Create an image that will be used to open child table
                  var $img = $('<img src="resources/plugins/jtable/list.png" title="Assignment Submissions" />');
                  //Open child table when user clicks the image
                  $img.click(function () {
                      $('#AssignmentTableContainer').jtable('openChildTable',
                              $img.closest('tr'),
                              {
                                  title: 'Submissions for: ' + studentData.record.assignment_title ,
                                  actions: {
                                      listAction: '/elmo/assSubmissionList?assignment_id=' + studentData.record.assignment_id,
                                      updateAction: '/elmo/assSubmissionUpdate',

                                  },
                                  fields: {
                                      submission_id: {

                                          key:true,
                                          type: 'hidden',
                                      },
                                      assign_id: {

                                          edit: true,
                                          type: 'hidden',
                                          input : function (data)
                                          {
                                              return ''+data.record.assign_id;
                                          }
                                      },
                                      uploaded_by_enr_id: {

                                          title: 'Uploaded by id',
                                          edit:false,
                                          type: 'hidden',
                                      },
                                      uploaded_by_name:
                                      {
                                          title: 'Uploaded by',
                                          width:'40%',
                                          edit: false,
                                      },
                                      submission_date:
                                      {
                                          title:'Submission Date',
                                          width:'25%',
                                          edit:false,
                                      },
                                      score_received:
                                      {
                                          title:'Score Received',
                                          width:'25%',
                                          edit:true,
                                          display: function (data) {
                                              if (data.record.score_received == -999)
                                              {return 'Not Graded';}
                                              else return ''+data.record.score_received;

                                          }
                                      },

                                      view_submission: {
                                          title: 'Submission',
                                          width: '25%',
                                          edit: false,
                                          display: function (data) {
                                                  return '<a href="/elmo/showSubmission?id=' + data.record.submission_id + '">Download</a>';

                                          },

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
    $('#AssignmentTableContainer').jtable('load');
  });



</script>

<div id = "content">
  <div style="text-align: center;margin-left: 14%">
    <h2>Manage Assignments</h2>
    <div id="AssignmentTableContainer"></div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>

