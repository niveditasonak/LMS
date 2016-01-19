<%@ page import="com.elmo.spring.persistence.dos.User" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 10/4/2015
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.elmo.spring.persistence.dos.Course" %>
<%@ page import="framework.Toolbox" %>

<%@ page session="true" %>

<!DOCTYPE html>

<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


  <spring:url value="/resources/common/favicon.ico" var="ico1" />
  <spring:url value="/resources/common/newElmo.css" var="css1" />
  <spring:url value="/resources/common/elmoScript.js" var="js1" />
  <spring:url value="/resources/common/maryland_logo.png" var="logo1" />

  <spring:url value="/resources/jquery.min.js" var="js2" />
  <spring:url value="/resources/jquery-ui-1.10.4.custom.js" var="js3" />
  <spring:url value="/resources/jquery-ui-1.10.4.custom.css" var="css2" />

  <spring:url value="/resources/plugins/jtable/jtable.css" var="css3" />
    <spring:url value="/resources/plugins/datepicker/jquery.datetimepicker.css" var="css4" />
  <spring:url value="/resources/plugins/jtable/jquery.jtable.js" var="js4" />

    <spring:url value="/resources/plugins/datepicker/jquery.datetimepicker.js" var="js5" />

  <link href="${ico1}" rel="shortcut icon" type="favicon">
  <link href="${css1}" media="all" rel="stylesheet" type="text/css">
  <link href="${css2}" media="all" rel="stylesheet" type="text/css">

  <link href="${css3}" media="all" rel="stylesheet" type="text/css">

    <link href="${css4}" media="all" rel="stylesheet" type="text/css">


  <script type="text/javascript" async="" src="${js1}"></script>

  <script src="${js2}"></script>
  <script src="${js3}"></script>
  <script src="${js4}"></script>
    <script src="${js5}"></script>

  <title>Welcome to Elmo!</title>

</head>

<body class="">
<noscript>
  <span>Please enable JavaScript to access full site functionality.</span>
</noscript>

<div id="elmo-application">

  <div id="elmo-header" class="no-print">
    <img class="elmo-header_image" src="${logo1}" />
    <div id="elmo-header-inner">

      <div id="elmo-topbar" >
        <ul id="elmo-identity">
          <li class=""><a class="elmo-toplink" href="/elmo/profilePage"><% out.print(((User)request.getSession().getAttribute("GLOBAL_USER")).getName()); %></a></li>

            <%if(!Toolbox.isAdmin())
            {

            %>
          <li class="">

            <a href="/elmo/inbox" class="elmo-toplink">
              <span>Mailbox</span>
            </a>
          </li>
         <%

             }
         %>
          <li class=""><a class="elmo-toplink" href="/elmo/logout">Logout</a></li>

        </ul>

      </div>


<span id="elmo-menu">

<% if (((User)request.getSession().getAttribute("GLOBAL_USER")).getUser_type().equals("Non-Admin"))
{
%>
    <span id="courses-menu-item" class="elmo-menu-item ">

    </span>



    <span id="calendar-menu-item" class="elmo-menu-item ">
      <a href="/elmo/calendar">Calendar</a>
    </span>

 <%
   }
   else {
%>
     <span id="courses-menu-item" class="elmo-menu-item ">
     <a href="/elmo/manageCourses">Manage Courses</a>
     </span>

     <span id="offerings-menu-item" class="elmo-menu-item ">
     <a href="/elmo/manageOfferings">Manage Offerings</a>
     </span>

     <span id="users-menu-item" class="elmo-menu-item ">
     <a href="/elmo/manageUsers">Manage Users</a>
     </span>

    <span id="grades-menu-item" class="elmo-menu-item ">
      <a href="/elmo/gradesForAdmin">View Grades</a>
    </span>
  <%
   }

 %>


  </span>

    </div>
  </div>

