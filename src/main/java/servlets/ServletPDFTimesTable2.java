/*
    Document   : ServletPDFTimesTable2.java
    Created on : Oct 1, 2014
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

package servlets;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import util.ParseSequence;
import util.QuestionData;

/**
 *
 * @author super
 */
public class ServletPDFTimesTable2 extends HttpServlet {
   private static final int tableColumns = 4;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, DocumentException, IOException {

        String seq = request.getParameter("Sequence");
        boolean answers = Boolean.parseBoolean(request.getParameter("ShowAnswers"));
        ParseSequence ps = new ParseSequence("(?<numbers>[\\d]{1,2})");
        ArrayList<Integer> oneDataList = ps.matchIntAllCapturingGroups(seq);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        generatePDF(false, answers, oneDataList, baos);

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
    }
    
    private void generatePDF(boolean borders, boolean ans, ArrayList<Integer> numbs, ByteArrayOutputStream baos)
            throws DocumentException, IOException {
       // int[] numbs = {n1, n2, n3, n4, n5};
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
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
    }

    private ArrayList<util.QuestionData> generateTable(int numb) {
        return util.QuestionData.generateTimesTable(numb, 12);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
