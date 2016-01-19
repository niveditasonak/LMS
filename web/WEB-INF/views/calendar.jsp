<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: VB
  Date: 10/4/2015
  Time: 2:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/includes/header.jsp" %>
<%@ include file="/WEB-INF/includes/leftNavigation.jsp" %>


  <spring:url value="/resources/plugins/calendar/fullcalendar.css" var="css1" />
  <spring:url value="/resources/plugins/calendar/fullcalendar.print.css" var="css2" />
  <spring:url value="/resources/plugins/calendar/moment.min.js" var="js1" />
  <spring:url value="/resources/plugins/calendar/fullcalendar.min.js" var="js3" />


  <link href="${css1}" rel='stylesheet' />
  <link href="${css2}" rel='stylesheet' media='print' />
  <script src="${js1}"></script>
  <script src="${js3}"></script>
  <script>



    $(document).ready(function() {

      $('#calendar').fullCalendar({
        header: {
          left: 'prev,next today',
          center: 'title',
          right: 'month,basicWeek,basicDay'
        },
        defaultDate: '${defaultDate}',
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        events:${eventJsonList}

      });

    });

  </script>

<div id="content">
<div id='calendar'></div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>