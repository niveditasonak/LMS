<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 9/22/2015
  Time: 1:54 PM

--%>
<%@ page session="false" %>

<html>
<head>
  <title>Logged Out!</title>
  <spring:url value="/resources/common/maryland_logo.png" var="logo1" ></spring:url>
  <spring:url value="/resources/common/newElmo.css" var="css1" />
  <link href="${css1}" media="all" rel="stylesheet" type="text/css">

</head>
<body>


<div id="loginbanner">

  <img id="elmo-login-image" class="elmo-header_image" src="${logo1}" />

 <span id="loginFailedMessage">You have been logged out! <a href="/elmo/index.jsp" style="color: yellow"> Login Again. </a></span>

</div>

</body>
</html>