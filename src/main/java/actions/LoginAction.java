/*
    Document   : LoginAction.java
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
public class LoginAction extends ActionSupport {
    
    public LoginAction() {
        super();
        Logger.getLogger(actions.LoginAction.class.getName()).log(Level.INFO, "LoginAction - Startup");
    }
    
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        
        
        String uname = request.getParameter("username");
        String pword = request.getParameter("password");
        try {
        request.login(uname, pword);
        request.authenticate(response);
        } catch(ServletException se) {
            request.setAttribute("authenticationMessage", "Login failed.");
            Logger.getLogger(actions.LoginAction.class.getName()).log(Level.INFO, "Login Failed for User ID: "+uname);
            return("fail");
        }
        request.setAttribute("authenticationMessage", 
                "Successfull. AUTH TYPE:"+request.getAuthType()+
                ", PRINCIPAL:"+request.getUserPrincipal().toString() +
                ", IS_IN_ROLE:" +request.isUserInRole("webuser"));
        session.setAttribute("userID", uname);
       Logger.getLogger(actions.LoginAction.class.getName()).log(Level.INFO, "Login Succeded for User ID: "+uname);
       return("success");
    }
}