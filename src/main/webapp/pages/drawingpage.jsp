<%-- 
    Document   : drawingpage.jsp
    Created on : Jul 6, 2012, 8:09:53 PM
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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Drawing Page</title>
    </head>
    <body>
        <h3>Drawing Practice</h3>
      <!--  <applet width="800" height="550" codebase="http://localhost:8080/SchoolWeb/" archive="HRUtils.jar" code="com.hrauf.util.applet.DrawingPanel.class" >
      -->
      <applet name="DrawingApplet" width="700" height="550" archive="HRUtils.jar" code="com.hrauf.util.applet.DrawingPanel.class" >
   <param name="userID" value="<%=request.getRemoteUser()%>"/>
          Your system does not support Applets, install the correct plugins.<br/>
        Android platform default JAVA may not be able to run Java Applets.
    </applet> 
    </body>
</html>
