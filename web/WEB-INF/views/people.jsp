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
    $('#PeopleTableContainer').jtable({
      title : 'People List',
      actions : {
        listAction : 'peopleList'
      },
      fields : {

      user_id : {
        title : 'User ID',
        key: true,
          type: 'hidden'
      },
          name : {
          title : 'Name',
          width : '20%',
          edit : false
        },
        select_user : {
          title: 'Mark For Message&nbsp;&nbsp;&nbsp;<input type="checkbox" id="user_all" onclick="toggleCheckboxes()"/>',
    width:'15%',
    display: function (studentData) {
      return '<input type="checkbox" name="user_checkbox" value="'+studentData.record.user_id.valueOf()+'" />';
    }

  }

       }
    });
    $('#PeopleTableContainer').jtable('load');
  });


  function toggleCheckboxes()
  {
    $("[name='user_checkbox']").click();
  }

  function collateUserIds()
  {


      if ($("[name='message_type']")[1]!=null)
      {
          if ($("[name='message_type']")[0].checked == false && $("[name='message_type']")[1].checked == false)
          {
              alert ("Please select message/announcement.");
              return false;
          }
      }

      ///announcemnt
      if ($("[name='message_type']")[1]!=null)
      {
          if ($("[name='message_type']")[1].checked == true)
          {
              $("[name='user_to_ids']").val(<%out.print(Toolbox.getActiveEnrollment().getCourse_sem_id());%>);
              return true;
          }
      }

      //messages



      var userIds='';
      $("[name='user_checkbox']").each(function () {

          if(this.checked === true )
              userIds = userIds + this.value+",";

      });

      $("[name='user_to_ids']").val(userIds);


  }


</script>

<div id="content">
  <form action="/elmo/sendAnnouncement" method="POST" onsubmit="collateUserIds();">
  <div style="text-align: center;margin-left: 14%">
    <h2>People in <%out.print(Toolbox.getCourseNameForActiveEnrollment());%></h2>
    <div id="PeopleTableContainer"></div>
  </div>


  <input name="user_from_id" type="hidden" value ="<%out.print(((User)request.getSession().getAttribute("GLOBAL_USER")).getUser_id()); %>" >
  </input>


    <input name="user_to_ids" type="hidden" value ="" >
    </input>
    <br/>
    <br/>
    <hr/>
    <br/>
    <br/>

    <div style="margin-left: 14%">
        <div align="center" style="font-size: 15px;color: #007fff">${errorList}</div>
            <br/>
 <label for="mtitleid">Message Title:&nbsp;&nbsp;</label>
  <input id="mtitleid" type="text" name="message_title" size="80"></input>
<br/>
   <br/>

   <label for="mbodyid">Message Body:</label>
  <textarea id="mbodyid" name="message_body" style="width: 500px;height: 100px"></textarea>
      <br/>

      <br/>



     <% if (Toolbox.getActiveEnrollment().getEnrollment_type().equals("Instructor")) { %>
            <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <input type="radio" name="message_type" value="Message">Message<br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <input type="radio" name="message_type" value="Announcement">Announcement</input>
    <br />
            <%
        }else
        {
            %>
            <input type="hidden" name="message_type" value="Message" />
       <%
           }
       %>



      <br/>
      <br/>


      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value="Send" />
  </div>
  </form>
  <br/>
  <br/>
  <br/>
<%@ include file="/WEB-INF/includes/footer.jsp" %>

