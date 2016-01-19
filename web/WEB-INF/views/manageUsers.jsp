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
    $('#UserTableContainer').jtable({
      title : 'User List',
      actions : {
        listAction : 'manageUsersList',
        createAction:'manageUsersCreate',
        updateAction: 'manageUsersUpdate',
        deleteAction: 'manageUsersDelete'
      },
      fields : {

      user_id : {
        title : 'User ID',
        key: true,
          type: 'hidden'

      },
          user_name : {
          title : 'Username',
          width : '15%',
          edit : true
        }
        ,
         name : {
          title : 'Name',
          width : '30%',
          edit : true
        }
          ,
          user_type : {
              title : 'Type',
              width : '15%',
              edit : true,
              list: true,
              input: function (data) {

                  if (data.formType === 'create') {
                      return '<select name="user_type"><option value="Admin">Admin</option><option value="Non-Admin" selected>Non-Admin</option></select>';
                  }

                   else   if (data.record.user_type === "Admin")
                    return '<select name="user_type"><option value="Admin" selected >Admin</option><option value="Non-Admin">Non-Admin</option></select>';
                    else
                       return '<select name="user_type"><option value="Admin">Admin</option><option value="Non-Admin" selected>Non-Admin</option></select>';

              }
          }
          ,
          user_biodata : {
              title : 'Biodata',
              width : '30%',
              edit : true
          }

          ,
          user_password : {
              title : 'Password',
              width : '15%',
              edit : true,
              visibility: 'hidden',
              display: function (data) {
                  return '<b>********</b>';
              },
              input: function (data) {

                  if (data.formType === 'edit')
                      return '<input type="hidden" name="user_password" value='+data.record.user_password+' /><input type="text" name="reset_password" value="" placeholder="Reset Password"></input>';
                  else
                  if (data.formType === 'create')
                    return '<input name="user_password" type="text" value="" placeholder="Set Password"></input>';
              }
          }

      }
    });
    $('#UserTableContainer').jtable('load');
  });
</script>

<div id = "content">
  <div style="text-align: center;margin-left: 14%">
    <h2>Manage Users</h2>
    <div id="UserTableContainer"></div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>

