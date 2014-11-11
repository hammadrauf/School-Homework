<%-- 
    Document   : errorView.jsp
    Created on : Nov 6, 2014 6:05:00 AM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h3>Error</h3>
        <p>An error has occurred during execution. Report to development team.</p>
        <%--
        Runtime Exception Name: <s:property value="exception"/><br><br>
 
        Runtime Exception Stack Trace: <br>
        <s:property value="exceptionStack"/>
        --%>
        <s:actionerror/>
        <p>
            <s:property value="%{exception.message}"/>
        </p>
        <hr/>
        <h4>Technical Details</h4>
        <p>
            <s:property value="%{exceptionStack}"/>
        </p>
    </body>
</html>

