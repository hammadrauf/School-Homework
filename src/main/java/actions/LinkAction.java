/*
    Document   : LinkAction.java
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

/**
 *
 * @author Hammad
 */
import com.opensymphony.xwork2.ActionSupport;

public class LinkAction extends ActionSupport {
//private static final long serialVersionUID = -2613425890762568273L;

    public String welcome() {
        return "welcome";
    }

    public String friends() {
        return "friends";
    }

    public String office() {
        return "office";
    }

    public String index() {
        return "index";
    }

    public String drawingpage() {
        return "drawingpage";
    }

    /*
    public String timesTableView() {
        return "timesTableView";
    }
*/
    public String errorView() {
        return "errorView";
    }
    
    public String timestable() {
        return "timestable";
    }
    
    public String countingtable() {
        return "countingtable";
    }

    public String randomtimestable() {
        return "randomtimestable";
    }

    public String persistanceForm() {
        return "persistanceForm";
    }

    public String persistanceResultView() {
        return "persistanceResultView";
    }
    
    public String login() {
        return "login";
    }
    
    public String adminemailform() {
        return "adminemailform";
    }
    
    public String mailConfirmation() {
        return "mailConfirmation";
    }
	
    public String privacyLegal() {
        return "privacyLegal";
    }	
    
    public String license() {
        return "license";
    }	
}