<%-- 
    Document   : countingtable.jsp
    Created on : Nov 4, 2014 7:25:12 PM
    Author     : Hammad Rauf (rauf.hammad@gmail.com)

    Copyright (C) 2014 Hammad Rauf

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
<title>Counting Table Form</title>
<s:head/>
</head>
<body>
<h3>Generate &quot;Counting-By&quot; Or Counting Tables</h3>
        <s:form name="CountingTables" action="ActionSupportCountingTables" method="get">
            <%-- s:textfield label="Numbers to generate tables for (2,3,4,5 and 10 - Separate by comma)" name="ttModel.sequence" size="12" value="2,3,4,5,10"/ --%>
            <%--s:radio label="Counting-by" name="ctModel.countBy" list="#{ 'one' : '1', 'two' : '2' : 'five' : '5' }" value="one" /--%>
            <s:radio id="radio1" name="ctModel.countBy" title="1" list="#{ 'one' : '1'}" />
            <s:radio id="radio1" name="ctModel.countBy" title="2" list="#{ 'two' : '2' }" />
            <s:radio id="radio1" name="ctModel.countBy" title="5" list="#{ 'five' : '5' }" />
            <%--s:radio label="Counting uptil" name="ctModel.countUptil" list="#{ '1hundred' : '100', '2hundred' : '200' }" value="1hundred" /--%>
            <s:radio id="radio2" name="ctModel.countUptil" title="100" list="#{ '1hundred' : '100'}" />
            <s:radio id="radio2" name="ctModel.countUptil" title="200" list="#{ '2hundred' : '200'}" />
            <%--   <s:radio label="Show Answers" name="ttModel.answers" list="ttModel.{listOptions}" 
                       listKey="ttModel.listOptions.code" listValue="ttModel.listOptions.value" value="ttModel.defaultListValue" />
          --%>  <s:token />
            <s:submit label="Submit" name="submit"/>
            <s:reset label="Clear" name="clear"/>
        </s:form>
</body>
</html>