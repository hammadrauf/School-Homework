/*
    Document   : ServletConnectApplet.java
    Created on : 
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
 */
package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletConnectApplet extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
      
    // Use "request" to read incoming HTTP headers (e.g. cookies)
    // and HTML form data (e.g. data the user entered and submitted)
    
    // Use "response" to specify the HTTP response line and headers
    // (e.g. specifying the content type, setting cookies).
    
    PrintWriter out = response.getWriter();
    out.println("Survived");
    // out.flush();
    // Use "out" to send content to browser
  }

    public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
        this.doGet(request, response);
    }
}
