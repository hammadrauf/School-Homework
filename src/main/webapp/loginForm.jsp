<%-- 
    Document   : loginForm.jsp
    Created on : 4-Oct-2013, 6:22:45 PM
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
        <title>Login Page</title>
    </head>
    <body>
        <h2>Login</h2>
        <form name="loginform" method="post" action="loginSubmit">
        <!--form name="loginform" method="post" action="j_security_check"-->     
            <table border="0">
                <tr>
                    <td>User Name</td><td><input name="username" type="text"/></td>
                </tr>
                <tr>
                    <td>Password</td><td><input name="password" type="password"/></td>
                </tr>    
                <tr><td><div align="right"><input name="submit" type="submit"/>&nbsp;
                            <input name="reset" type="reset"/></div>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
