<%-- 
    Document   : AdminEmailForm.jsp
    Created on : Oct 26, 2013, 5:36:29 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Email Registered Users Form</title>
        <s:head/>
    </head>
    <body>
        <h3>Email Registered Users</h3>

        <s:form enctype="multipart/form-data" action="sendEmailFormSubmit" method="post">
            <s:textfield label="To" name="mailBean.to"/>
            <s:textfield label="Subject" name="mailBean.subject"/>
            <s:textarea label="Message" name="mailBean.message"/>
            
            <s:label for="attachment" value="Attachment (HTML file)"/>
            <s:file name="mailBean.attachment" accept="text/html" />
            
            <s:token />
            <s:submit label="Submit" name="submit"/>
        </s:form>  
    </body>
</html>
