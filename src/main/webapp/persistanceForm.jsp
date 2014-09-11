<%-- 
    Document   : persistanceForm.jsp
    Created on : Nov 29, 2012, 8:26:07 PM
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
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Persistence Form</title>
    </head>
    <body>
 <div id="global" style="width:450px">
	<h3>Register New User</h3>
        <s:form action="ActionSupportTestPersistance">
            <s:textfield name="username" label="User Name" required="true" maxlength="16" size="16" /> 
            <s:password name="password" label="Password" required="true" maxlength="50" size="25" />
            <s:checkbox name="mailinglist" label="Include in Email Notifications"/>
		<s:textfield name="email" label="E-mail Address" maxlength="50" size="25" />
                <s:submit type="submit" label="Submit" /> <s:reset type="reset" label="Clear" />
	</s:form>
</div>       
    </body>
</html>
