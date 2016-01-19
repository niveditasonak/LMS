<%--
  Created by IntelliJ IDEA.
  User: Nivedita
  Date: 12/1/2015
  Time: 7:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/includes/header.jsp" %>
<%@ include file="/WEB-INF/includes/leftNavigation.jsp" %>

<!--script type="text/javascript">
$(document).ready(function() {
$('#FileTableContainer').jtable({
title : 'File List',
actions : {
//listAction : 'manageFileList',
createAction: function (data) {
var deferred = $.Deferred();

// Capture form submit result from the hidden iframe
$("#postiframe").load(function () {
iframeContents = $("#postiframe").contents().find("body").text();
var result = $.parseJSON(iframeContents);
deferred.resolve(result);
});

// Submit form with file upload settings
var form = $('#jtable-create-form');
form.unbind("submit");
form.attr("action", "/manageFileCreate");
form.attr("method", "post");
form.attr("enctype", "multipart/form-data");
form.attr("encoding", "multipart/form-data");
form.attr("target", "postiframe");
form.submit();

return deferred;
},
updateAction:
function (data) {
var deferred = $.Deferred();

// Capture form submit result from the hidden iframe
$("#postiframe").load(function () {
iframeContents = $("#postiframe").contents().find("body").text();
var result = $.parseJSON(iframeContents);
deferred.resolve(result);
});

// Submit form with file upload settings
var form = $('#jtable-edit-form');
form.unbind("submit");
form.attr("action", '/elmo/updateFile');
form.attr("method", "post");
form.attr("enctype", "multipart/form-data");
form.attr("encoding", "multipart/form-data");
form.attr("target", "postiframe");
form.submit();

return deferred;
}
//deleteAction: 'manageFileDelete'
},
fields : {

file_id: {
key: true,
type: 'hidden',
},
file_date_expired_str: {
title: 'Expiry',
width: '10%',
edit: true,
},
FileName: {
title: "Document"
},
docBytes: {
title: "Document Upload",
list: false,
create: true,
input: function (data) {
return '<input id="docBytes" type="file" name="docBytes" /><iframe name="postiframe" id="postiframe" style="display: none" />';
}
}
}
});
$('#FileTableContainer').jtable('load');
});
</script>



<div id = "content">
<div style="text-align: center;margin-left: 14%">
<h2>Manage File</h2>
<div id="FileTableContainer"></div>
</div>
</div>

<!--div id = "content">
<h3>File Upload:</h3>
Select a file to upload: <br />
<form action="/elms/uploadFile" method="post"
enctype="multipart/form-data">
<input type="file" name="file" size="50" />
<br />
<input type="submit" value="Upload File" />
</form>
</div-->

<table width="80%" border="1" cellspacing="0" cellpadding="5">
    <tr>
        <th width="4%">No</th>
        <th width="30%">Filename</th>
        <th width="30%">Published Date</th>
        <th width="16%">Expiry Date</th>
        <th width="20%">&nbsp;</th>
    </tr>
    <c:choose>
        <c:when test="${files != null}">
            <c:forEach var="file" items="${files}" varStatus="counter">
                <tr>
                    <td>${counter.index + 1}</td>
                    <td>File_${file.file_id}</td>
                    <td>${file.file_date_published}</td>
                    <td>${file.file_date_expired}</td>
                    <td><div align="center"><a href="/elmo/download?id=${file.file_id}">Download</a>
                        <% if (Toolbox.isInstructor()){
                    %>
                        /
                        <a href="/elmo/delete?id=${file.file_id}">Delete</a></div>
                        <%}%>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
    </c:choose>
</table>

<% if (Toolbox.isInstructor()){
%>
<h2>Add New File</h2>
<form action="/elmo/upload" method="post" enctype="multipart/form-data">
    <table width="60%" border="1" cellspacing="0">
        <tr>
            <td width="35%"><strong>File to upload</strong></td>
            <td width="65%"><input type="file" name="file" accept=".pdf"/></td>
        </tr>
        <tr>
            <td><strong>Expiry Date</strong></td>
            <td><input type="text" name="exp" width="60" /></td>
        </tr>
        <tr>
            <td><strong>Published Date</strong></td>
            <td><input type="text" name="pub" width="60" /></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="submit" value="Add"/></td>
        </tr>
    </table>
</form>
<% }%>

<%@ include file="/WEB-INF/includes/footer.jsp" %>