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

<div id = "content">

  <h1>Profile Page</h1>

  ${errorStringList}
  <form action="/elmo/profilePageUpdate" method="POST">
  <h2>Name: <input type="text" name="updated_name" value="${user.name}"></input></h2>
  <textarea name="updated_biodata" style="height: 100px;width: 500px">${user.user_biodata}</textarea>

<input type="submit" value="Update Profile"></input>
  </form>
</div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
