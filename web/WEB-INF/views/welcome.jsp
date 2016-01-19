<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 9/22/2015
  Time: 1:54 PM

--%>

<%@ include file="/WEB-INF/includes/header.jsp" %>
<%@ include file="/WEB-INF/includes/leftNavigation.jsp" %>

<h2>

  Welcome, <% out.print(((User)request.getSession().getAttribute("GLOBAL_USER")).getName());%>

</h2>




<%@ include file="/WEB-INF/includes/footer.jsp" %>
