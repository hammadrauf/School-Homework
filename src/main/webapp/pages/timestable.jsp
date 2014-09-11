<%-- 
    Document   : timestable.jsp
    Created on : Jul 6, 2012, 8:01:12 PM
    Author     : Hammad Rauf (rauf.hammad@gmail.com)

    Copyright (C) 2013 Hammad Rauf

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see &lt;http://www.gnu.org/licenses/&gt;.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Times Table Form</title>
<s:head/>
</head>
<body>
<h3>Generate Straight Times Table</h3>
<%--
<form name="TimesTable" method="get" action="ActionSupportTimesTable2">
<table>
<tr><td>Numbers to generate tables for (2,3,4,5 and 10 - Separate by comma)</td><td><input name="sequence" type="text" size="12" value="2,3,4,5,10"/></td></tr>
<!--tr><td>No1</td><td><input name="n1" type="text" size="2" value="2"/></td></tr>
<tr><td>No2</td><td><input name="n2" type="text" size="2" value="3"/></td></tr>
<tr><td>No3</td><td><input name="n3" type="text" size="2" value="4"/></td></tr>
<tr><td>No4</td><td><input name="n4" type="text" size="2" value="5"/></td></tr>
<tr><td>No5</td><td><input name="n5" type="text" size="2" value="10"/></td></tr-->
<tr><td>Show Answers</td><td><input type="radio" name="answers" value="true" checked/>Yes
<input type="radio" name="answers" value="false"/>No</td></tr>
<tr><td><input name="submit" type="submit" value="Submit"><input name="reset" type="reset" value="Clear"></td></tr>
</table>
</form>
--%>
        <s:form name="TimesTable" action="ActionSupportTimesTable2" method="get">
            <s:textfield label="Numbers to generate tables for (2,3,4,5 and 10 - Separate by comma)" name="ttModel.sequence" size="12" value="2,3,4,5,10"/>
            <s:radio label="Show Answers" name="ttModel.answers" list="#{ 'true' : 'Yes', 'false' : 'No' }" value="true" />
            <%--   <s:radio label="Show Answers" name="ttModel.answers" list="ttModel.{listOptions}" 
                       listKey="ttModel.listOptions.code" listValue="ttModel.listOptions.value" value="ttModel.defaultListValue" />
          --%>  <s:token />
            <s:submit label="Submit" name="submit"/>
            <s:reset label="Clear" name="clear"/>
        </s:form>
</body>
</html>