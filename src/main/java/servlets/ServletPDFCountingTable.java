/*
    Document   : ServletPDFCountingTable.java
    Created on : Nov 11, 2014
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
public class ServletPDFCountingTable extends HttpServlet {

    private static final int tableColumns = 5;
    private static final float borderWidth = 3.0f;
    
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
            throws ServletException, IOException {
        
        int countBy = Integer.parseInt(request.getParameter("countBy"));
        int countUptil = Integer.parseInt(request.getParameter("countUptil"));
        boolean showBlanks = Boolean.parseBoolean(request.getParameter("showBlanks"));
        String domain = request.getServerName();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {        
            generatePDF(baos, domain, true, countBy, countUptil, showBlanks);
        } catch (DocumentException ex) {
            throw new IOException(ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new IOException(ex.getMessage());            
        } catch (IllegalStateException ex) {
            throw new IOException(ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            throw new IOException(ex.getMessage());
        } catch (FileNotFoundException ex) {
            throw new IOException(ex.getMessage());
        }
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


    private void generatePDF(ByteArrayOutputStream baos, String domain, boolean borders, int countBy, int countUptil, boolean showBlanks)
            throws DocumentException, NumberFormatException,
            IllegalStateException, IndexOutOfBoundsException, FileNotFoundException {
        /*
        //int[] numbs = {n1, n2, n3, n4, n5};
        ParseSequence ps = new ParseSequence("(?<numbers>[\\d]{1,2})");
        //Maximum two digit integers with any seperator in between.

        Integer ii = null;
        ArrayList<Character> charList = null;
        ArrayList<Integer> oneDataList = ps.matchIntAllCapturingGroups(seq);
        ArrayList<QuestionData> qdList = generateQuestions(oneDataList, howmany);
        */
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
        PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("SchoolHomeworkWeb - Counting Table - "+domain));
        document.add(new Paragraph(new Date().toString()));
        document.add(Chunk.NEWLINE);
        
        document.add(new Paragraph("Count By = "+countBy+", Count Uptil = "+countUptil+", Show Blanks = "+showBlanks));
        //document.add(new Paragraph("Seq:"+seq));
        //  for(QuestionData qd: qdList) {
        //      document.add(new Paragraph(qd.toStringQuestionAndAnswer()));
        //  }
/*
        PdfPTable table = null;
        PdfPCell cell = null;
        int columns = 0;

        PdfPTable tableInner = null;
        PdfPCell cellInner = null;

        table = new PdfPTable(tableColumns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(0);


        for (QuestionData qd : qdList) {
            columns++;

            tableInner = new PdfPTable(3); //Grade 3 questions should be 3 cols only (2 digits and 1 op-code)

            cellInner = new PdfPCell(new Paragraph(" "));
            cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            charList = QuestionData.getIntChars(qd.getFactor1());
            if (charList.size() == 1) {
                cellInner = new PdfPCell(new Paragraph(" "));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            } else if (charList.size() >= 2) {
                cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Paragraph(charList.get(1).toString()));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            }
            cellInner = new PdfPCell(new Paragraph(qd.getOperation()));
            cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            charList = QuestionData.getIntChars(qd.getFactor2());
            if (charList.size() == 1) {
                cellInner = new PdfPCell(new Paragraph(" "));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            } else if (charList.size() >= 2) {
                cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Paragraph(charList.get(1).toString()));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            }
            // Add top answer line
            cellInner = new PdfPCell(new Paragraph(" "));
            cellInner.setColspan(3);
            cellInner.setBorder(PdfPCell.TOP);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            if (showAnswers == true) {
                charList = QuestionData.getIntChars(qd.getAnswer());
                switch (charList.size()) {
                    case 3:
                        cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Paragraph(charList.get(1).toString()));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Paragraph(charList.get(2).toString()));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        break;
                    case 2:
                        cellInner = new PdfPCell(new Paragraph(" "));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Paragraph(charList.get(1).toString()));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        break;
                    case 1:
                        cellInner = new PdfPCell(new Paragraph(" "));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Paragraph(" "));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Paragraph(charList.get(0).toString()));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        break;
                    default:
                        cellInner = new PdfPCell(new Paragraph(" "));
                        cellInner.setColspan(3);
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);
                }
            }
            //Add 1 blank lines for calculations
            cellInner = new PdfPCell(new Paragraph(" "));
            cellInner.setColspan(3);
            cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);
            // Add lower Answer line
            cellInner = new PdfPCell(new Paragraph(" "));
            cellInner.setColspan(3);
            cellInner.setBorder(PdfPCell.TOP);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            cell = new PdfPCell(tableInner);
            // cell.addElement(tableInner);
            cell.setBorder(PdfPCell.BOX);
            cell.setBorderWidthRight(borderWidth);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);
            if (columns > tableColumns) {
                columns = 0;
            }
        }
        int remaining = tableColumns - columns;
        while (remaining > 0) {
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.BOX);
            cell.setBorderWidthRight(borderWidth);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);
            remaining--;
        }
        document.add(table);
        // but the result looks like one big table
*/
        // step 4
        document.close();
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
        return "Counting Table generator servlet. Generates PDF stream output.";
    }// </editor-fold>

}
