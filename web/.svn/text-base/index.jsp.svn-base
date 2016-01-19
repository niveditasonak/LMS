<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 9/22/2015
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to Elmo!</title>
    <spring:url value="/resources/common/maryland_logo.png" var="logo1" ></spring:url>
    <spring:url value="/resources/common/newElmo.css" var="css1" />
    <link href="${css1}" media="all" rel="stylesheet" type="text/css">

</head>
<body>

<div id="loginbanner">

    <img id="elmo-login-image" class="elmo-header_image" src="${logo1}" />

    <form method="POST" action="/elmo/loginUser">

    <input id="username" type="text" name="username" value="" placeholder="Username"/>

    <input id="password"  type="password" name="password" value="" placeholder="Password" />

        <input id="loginbutton" type="submit" value="Login"/>

</form>
</div>



</body>
</html>
