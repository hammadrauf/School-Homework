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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author super
 */
public class ServletPDFCountingTable extends HttpServlet {

    private static final int tableColumns = 10;
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
            generatePDF(baos, domain, false, countBy, countUptil, showBlanks);
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
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
        PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("SchoolHomeworkWeb - Counting Table - " + domain));
        document.add(new Paragraph(new Date().toString()));
        document.add(Chunk.NEWLINE);

        PdfPTable table = null;
        PdfPCell cell = null;
        int columns = 0;
        Font fontNormal = new Font(FontFamily.COURIER, 20, Font.NORMAL);
        Font fontBold = new Font(FontFamily.COURIER, 20, Font.BOLD);
        
        table = new PdfPTable(tableColumns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(0);

        PdfPTableEvent tableEvent = new HelperForTable();
        table.setTableEvent(tableEvent);
        boolean markedCell = false;
        
        for (int i = 1; i <= countUptil; i++) { 
            cell = new PdfPCell(new Paragraph(((i%countBy)==0 & showBlanks)?" ":Integer.toString(i),((i%countBy)==0)?fontBold:fontNormal));
            cell.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            if((i%countBy)==0) {
                cell.setBackgroundColor(BaseColor.GRAY);
            }
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(40f);
            table.addCell(cell);
        }
        document.add(table);
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

    //Inner class
    class HelperForTable implements PdfPTableEvent {

        @Override
        public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
            int columns;
            Rectangle rect;
            int footer = widths.length - table.getFooterRows();
            int header = table.getHeaderRows() - table.getFooterRows() + 1;
            for (int row = header; row < footer; row += 2) {
                columns = widths[row].length - 1;
                rect = new Rectangle(widths[row][0], heights[row],
                        widths[row][columns], heights[row + 1]);
                //rect.setBackgroundColor(BaseColor.YELLOW);
                rect.setBorder(Rectangle.NO_BORDER);
                canvases[PdfPTable.BASECANVAS].rectangle(rect);
            }
        }

    }

}
