/*
    Document   : LogoutAction.java
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
package actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Hammad
 */
public class LogoutAction extends ActionSupport {
    
    public LogoutAction() {
        super();
        Logger.getLogger(actions.LogoutAction.class.getName()).log(Level.INFO, "LogoutAction - Startup");    
    }
    
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse(); 
        HttpSession session = request.getSession();
        
        try {
            request.logout();
        } catch(ServletException se) {
            request.setAttribute("authenticationMessage", "Logout failed.");
            Logger.getLogger(actions.LogoutAction.class.getName()).log(Level.INFO, "Logout attempt failed");
            return("fail");   
        }
            request.setAttribute("authenticationMessage", "Logged out sucessfully.");
            Logger.getLogger(actions.LogoutAction.class.getName()).log(Level.INFO, "Logout attempt successfull");
            session.removeAttribute("userID");
            return("success");
    }
}