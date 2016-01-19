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
    $('#InboxTableContainer').jtable({
      title : 'Inbox',
      actions : {
        listAction : 'inboxList',
          deleteAction: 'inboxDelete'
        },
      fields : {
          announcement_id:{
              key:true,
              type:'hidden'
          },
          date_sent : {
              title : 'Date',
              width : '20%',
              edit : false
          },

          senderReceiver : {
          title : 'Sender',
          width : '20%',
          edit : false
        },

          message_title : {
              title : 'Title',
              width : '20%',
              edit : false

          },
          message_body : {
              title : 'Message',
              width : '20%',
              edit : false

          }


       }
    });
    $('#InboxTableContainer').jtable('load');
  });


  $(document).ready(function() {
      $('#OutboxTableContainer').jtable({
          title : 'Outbox',
          actions : {
              listAction : 'outboxList',
              updateAction: 'outboxUpdate',
              deleteAction: 'outboxDelete'
          },
          fields : {
announcement_id:{
    key:true,
    type:'hidden'
},

              date_sent : {
                  title : 'Date',
                  width : '20%',
                  edit : false
              },

              senderReceiver : {
                  title : 'Receiver',
                  width : '20%',
                  edit : false

              },

              message_title : {
                  title : 'Title',
                  width : '20%',
                  edit : true

              },
              message_body : {
                  title : 'Message',
                  width : '20%',
                  edit : true

              }


          }
      });
      $('#OutboxTableContainer').jtable('load');
  });




</script>

<div id="content">
  <div style="text-align: center;margin-left: 14%">
    <h2>Inbox & Outbox</h2>
    <div id="InboxTableContainer"></div>
  </div>
    <br/> <br/><br/><br/> <hr/> <br/><br/><br/><br/>

    <div style="text-align: center;margin-left: 14%">

        <div id="OutboxTableContainer"></div>
    </div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>

