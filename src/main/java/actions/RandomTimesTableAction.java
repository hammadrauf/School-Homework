/*
    Document   : RandomTimesTableAction.java
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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import util.QuestionData;
import util.ParseSequence;

/**
 *
 * @author Hammad
 */
public class RandomTimesTableAction extends ActionSupport {

    public RandomTimesTableAction() {
        super();
        Logger.getLogger(actions.RandomTimesTableAction.class.getName()).log(Level.INFO, "RandomTimesTableAction - Startup");
    }

    public String execute() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String seq = request.getParameter("sequence");
        int howmany = Integer.parseInt(request.getParameter("howmany"));
   
        String fpath = "/ServletPDFRTimesTable.strut";
        fpath = fpath + "?Sequence="+ URLEncoder.encode(seq, "UTF-8") + "&HowMany=" + URLEncoder.encode(String.valueOf(howmany), "UTF-8");
        request.setAttribute("filePathForJavaScript", fpath);
        return ("success");
        
    }
}