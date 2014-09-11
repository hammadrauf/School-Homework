<%-- 
    Document   : randomtimestable.jsp
    Created on : Jul 6, 2012, 8:31:43 PM
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Random Times Table</title>
</head>
<body>
<h3>Generate Random Times Table</h3>
<form name="RandomTimesTable" method="get" action="RandomTimesTable">
<table>
<tr><td>Numbers to base question on (2,3,4,5 or 10 - Separate by comma)</td><td><input name="sequence" type="text" size="12" value="2,3,4,5,10"/></td></tr>
<tr><td>How many questions to generate</td><td><input name="howmany" type="text" size="2" value="15"/></td></tr>
<tr><td><input name="submit" type="submit" value="Submit"><input name="reset" type="reset" value="Clear"></td></tr>
</table>
</form>
</body>
</html>
