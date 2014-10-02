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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CodeAndValue;
import model.TTModel;
import org.apache.struts2.ServletActionContext;
import util.ParseSequence;
import util.QuestionData;

/**
 *
 * @author Hammad
 */
public class ActionSupportTimesTable2 extends ActionSupport {
    public static final String FNAME = "TimesTable.pdf";
    private static final int tableColumns = 4;
    
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
        int n1 = 2, n2 = 3, n3 = 4, n4 = 5, n5 = 10;
        boolean answers = false;
/*
        n1 = Integer.parseInt(request.getParameter("n1"));
        n2 = Integer.parseInt(request.getParameter("n2"));
        n3 = Integer.parseInt(request.getParameter("n3"));
        n4 = Integer.parseInt(request.getParameter("n4"));
        n5 = Integer.parseInt(request.getParameter("n5"));
*/
      //  String seq = request.getParameter("sequence");
        String seq = ttModel.getSequence();

     //  answers = Boolean.parseBoolean(request.getParameter("answers"));
        answers = Boolean.parseBoolean(ttModel.getAnswers());

        
// -- Move all of following to ServletPDF        
        request.setAttribute("SillyMesg", "I am in here!");
        request.setAttribute("ShowAnswers", answers);
/*        
        request.setAttribute("Table1", generateTable(n1));
        request.setAttribute("Table2", generateTable(n2));
        request.setAttribute("Table3", generateTable(n3));
        request.setAttribute("Table4", generateTable(n4));
        request.setAttribute("Table5", generateTable(n5));
*/
        ParseSequence ps = new ParseSequence("(?<numbers>[\\d]{1,2})");
        ArrayList<Integer> oneDataList = ps.matchIntAllCapturingGroups(seq);

             //String fpath = generatePDF(false, answers, n1, n2, n3, n4, n5);
/*
           String fpath = generatePDF(false, answers, oneDataList);
             request.setAttribute("filePathForJavaScript", fpath);
             return ("success");
*/
       //ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        String fpath = generatePDF(false, answers, oneDataList, baos);
        String fpath = "/ServletPDFTimesTable2.strut";
        //fpath = fpath+"?Sequence="+seq+"&ShowAnswers="+answers;
        fpath = fpath + URLEncoder.encode("Sequence="+seq, "Percent-encoding");
        request.setAttribute("filePathForJavaScript", fpath);
        return ("success");
/*        
        // step 1
            Document document = new Document();
            // step 2
            
            PdfWriter.getInstance(document, baos);
            // step 3
            document.open();
            // step 4
            document.add(new Paragraph(String.format(
                "You have submitted the following text using the %s method:",
                request.getMethod())));
            document.add(new Paragraph(text));
            // step 5
            document.close();
 
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
*/
         }
    /*  
     public String execute() throws Exception {
     Logger.getLogger("Action").info("I am in execute method.");
     return("success");
     }
     */
    private ArrayList<util.QuestionData> generateTable(int numb) {
        return util.QuestionData.generateTimesTable(numb, 12);
    }

    private String generatePDF(boolean borders, boolean ans, ArrayList<Integer> numbs, ByteArrayOutputStream baos)
            throws DocumentException, IOException {
       // int[] numbs = {n1, n2, n3, n4, n5};
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
        ServletContext sc = ServletActionContext.getServletContext();
        String FilePath = sc.getRealPath("/");
        FilePath = "/var/lib/openshift/54105d5fe0b8cd569a000153/app-deployments/current/repo/src/main/webapp/";
        String outPath = (FilePath==null)?"temp/" + FNAME:FilePath + "temp/" + FNAME;
        Logger.getLogger(actions.ActionSupportTimesTable2.class.getName()).log(Level.INFO, "ActionSupportTimesTable2 > generatePDF > ServletContextPath (outPath) = " + ((outPath==null)?"empty":outPath));
        //String pathForJavaScript = "/" + "temp/" + FNAME;
        String pathForJavaScript = "/" + "temp/" + FNAME;
//      PdfWriter.getInstance(document, new FileOutputStream(outPath));
        PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("StrutsSchoolWeb - Times Table"));
        document.add(new Paragraph(new Date().toString()));
        document.add(Chunk.NEWLINE);

        PdfPTable table = null, tableOuter = null;
        PdfPCell cell = null, cellOuter = null;
        int columns = 0;
        tableOuter = new PdfPTable(tableColumns);
        tableOuter.setWidthPercentage(100);
        tableOuter.setSpacingBefore(0);
        for (Iterator<Integer> it = numbs.iterator(); it.hasNext();) {
            int i = it.next();
            columns++;
            table = new PdfPTable(5);
            table.setWidthPercentage(24);
            table.setSpacingBefore(0);
            cell = new PdfPCell(new Phrase(i + " Times Table"));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            table.addCell(cell);
            ArrayList<QuestionData> list = generateTable(i);
            for (QuestionData qd : list) {
                cell = new PdfPCell(new Phrase(Integer.toString(qd.getFactor1())));
                cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(qd.getOperation()));
                cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Integer.toString(qd.getFactor2())));
                cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("="));
                cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
                if (ans) {
                    cell = new PdfPCell(new Phrase(Integer.toString(qd.getAnswer())));
                    cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    table.addCell(cell);
                } else {
                    cell = new PdfPCell();
                    cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
            cellOuter = new PdfPCell(table);
            cellOuter.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellOuter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableOuter.addCell(cellOuter);
            if(columns>tableColumns)
                columns=0;
        }
// if the last row column is less than total columns then add empty cells to complete the last row.
        int remaining = tableColumns-columns;
        while(remaining>0) {
            cellOuter = new PdfPCell();
            // cell.addElement(tableInner);
            cellOuter.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellOuter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableOuter.addCell(cellOuter);
            remaining--;
        }
        document.add(tableOuter);
        // step 4
        document.close();
        return (pathForJavaScript);
    }
}
