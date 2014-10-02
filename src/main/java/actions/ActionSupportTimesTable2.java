/*
    Document   : ActionSupportTimesTable2.java
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
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TTModel;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Hammad
 */
public class ActionSupportTimesTable2 extends ActionSupport {
        
    private model.TTModel ttModel;
    
    public ActionSupportTimesTable2() {
        super();
        Logger.getLogger(actions.ActionSupportTimesTable2.class.getName()).log(Level.INFO, "ActionSupportTimesTable2 - Startup");
    }

    public TTModel getTtModel() {
        return ttModel;
    }

    public void setTtModel(TTModel ttModel) {
        this.ttModel = ttModel;
    }

    public String execute() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
       // int n1 = 2, n2 = 3, n3 = 4, n4 = 5, n5 = 10;
        boolean answers = false;
      //  String seq = request.getParameter("sequence");
        String seq = ttModel.getSequence();

     //  answers = Boolean.parseBoolean(request.getParameter("answers"));
        answers = Boolean.parseBoolean(ttModel.getAnswers());        

        String fpath = "/ServletPDFTimesTable2.strut";
        fpath = fpath + "?Sequence="+ URLEncoder.encode(seq, "UTF-8") + "&ShowAnswers=" + URLEncoder.encode(String.valueOf(answers), "UTF-8");
        request.setAttribute("filePathForJavaScript", fpath);
        return ("success");

    }
}
