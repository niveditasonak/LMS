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
    $('#QuizTableContainer').jtable({
      title : 'Quiz List',
      actions : {
        listAction : 'manageQuizesList',

      },
      fields : {

        quiz_id : {

          key: true,

          type:'hidden',

        },
        uploaded_by_enr_id : {
          title : 'uploaded by enr id',

          edit : true,
          create: false,
          type:'hidden'
        }
        ,quiz_title : {
          title : 'Title',
          width : '25%',
          edit : true,
          display: function (data) {


            return '<a href="openQuiz?id=' + data.record.quiz_id + '">' + data.record.quiz_title + '</a>';

          }
        },
        quiz_description : {
          title : 'Description',
          width : '15%',
          edit : true
        }

        ,
        quiz_date_created_str : {
          title : 'Created',
          width : '10%',
          edit : false,
          create: false,
        },
        quiz_date_published_str : {
          title : 'Published',
          width : '10%',
          edit : true
        }
        ,

        quiz_due_date_str : {
          title : 'Due',
          width : '10%',
          edit : true
        },


        time_taken : {
          title : 'Max Time',
          width : '10%',
          edit : true
        }
        ,         max_score : {
          title : 'Max Score',
          width : '15%',
          edit : false
        }
        ,
        Submissions: {
          title: 'Submissions',
          paging: true,
          width:'8%',
          edit:false,
          create:false,
          display: function (studentData) {
            //Create an image that will be used to open child table
            var $img = $('<img src="resources/plugins/jtable/list.png" title="Quiz Submissions" />');
            //Open child table when user clicks the image
            $img.click(function () {
              $('#QuizTableContainer').jtable('openChildTable',
                      $img.closest('tr'),
                      {
                        title: 'Submissions for: ' + studentData.record.quiz_title ,
                        actions: {
                          listAction: '/elmo/quizMySubmissionList?quiz_id=' + studentData.record.quiz_id,
                        },
                        fields: {
                          submission_id: {

                            key:true,
                            type: 'hidden',
                          },
                          quiz_id: {

                            create: false,
                            edit: false,
                            list: false,
                            type: 'hidden',

                          },
                          uploaded_by_enr_id: {

                            title: 'Uploaded by id',
                            edit:false,
                            type: 'hidden',
                          },
                          uploaded_by_name:
                          {
                            title: 'Uploaded by',
                            edit: false,
                            width:'40%'
                          },

                          submission_date:
                          {
                            title:'Submission Date',
                            width:'30%',
                            edit:false,
                          },
                          score_received:
                          {
                            title:'Score Received',
                            width:'30%',
                            edit:true,
                            display: function (data) {
                              if (data.record.score_received == -999)
                              {return 'Not Graded';}
                              else return ''+data.record.score_received;

                            }
                          },


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
    $('#QuizTableContainer').jtable('load');
  });
</script>

<div id = "content">
  <div style="text-align: center;margin-left: 14%">
    <h3 style="color:#007fff">${message}</h3>
    <h2>Take Quizes</h2>
    <div id="QuizTableContainer"></div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>

