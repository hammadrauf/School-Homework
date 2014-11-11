/*
    Document   : ActionSupportCountingTables.java
    Created on : Nov 4, 2014 7:30:12 PM
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
*/
package actions;

import com.opensymphony.xwork2.ActionSupport;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CTModel;
import model.CodeAndValue;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Hammad
 */
public class ActionSupportCountingTables extends ActionSupport {
    
    public ActionSupportCountingTables() {
        super();
        Logger.getLogger(actions.ActionSupportCountingTables.class.getName()).log(Level.INFO, "ActionSupportCountingTables - Startup");
    }


    public String execute() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        int countBy = Integer.parseInt(request.getParameter("countBy"));
        int countUptil = Integer.parseInt(request.getParameter("countUptil"));
        boolean showBlanks = Boolean.parseBoolean(request.getParameter("showBlanks"));
   
        String fpath = "/ServletPDFCountingTable.strut";
        fpath = fpath + "?countBy="+ URLEncoder.encode(String.valueOf(countBy), "UTF-8") + "&countUptil=" + URLEncoder.encode(String.valueOf(countUptil), "UTF-8") + "&showBlanks=" + URLEncoder.encode(String.valueOf(showBlanks), "UTF-8");
        request.setAttribute("filePathForJavaScript", fpath);
        return ("success");
        
    }
}
