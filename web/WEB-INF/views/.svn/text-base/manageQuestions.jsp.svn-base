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
    $('#QuestionTableContainer').jtable({
      title : 'Question List For: ${QUIZ_NAME}',
      actions : {
        listAction : 'manageQuestionsList',
        createAction:'manageQuestionsCreate',
        updateAction: 'manageQuestionsUpdate',
        deleteAction: 'manageQuestionsDelete'
      },
      fields : {

      question_id : {
        key: true,
          type:'hidden',
      },

          question_number : {
              title : 'Question#',
              edit:false,
              create:false,
              width : '15%',


          },
          quest_description : {
          title : 'Description',
          width : '15%',
          edit : true
        }
        ,
         correct_answers : {
          title : 'Correct Answers',
          width : '15%',
          edit : true
        }

        ,
        max_score : {
          title : 'Max Score',
          width : '15%',
          edit : true
        },
          optionA: {
              title : 'A',
              width : '15%',
              edit : true
          },
          optionB: {
              title : 'B',
              width : '15%',
              edit : true
          },
          optionC: {
              title : 'C',
              width : '15%',
              edit : true
          }, optionD: {
              title: 'D',
              width: '15%',
              edit: true
          }


      }
    });
    $('#QuestionTableContainer').jtable('load');
  });
</script>

<div id = "content">
  <div style="text-align: center;margin-left: 14%">
    <h2>Manage Questions</h2>
    <div id="QuestionTableContainer"></div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>

