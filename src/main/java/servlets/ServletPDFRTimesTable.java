/*
    Document   : ServletRTimesTable.java
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
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
public class ServletPDFRTimesTable extends HttpServlet {

    private static final int tableColumns = 5;
    private static final float borderWidth = 3.0f;
    public static final String FONT = "resources/fonts/FreeSans.ttf";

    class MyFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
        String message = "";
        Image img = null;
        
        public MyFooter() {
            super();
            try {
                img = Image.getInstance("./images/Header1-exp1.png");
                img.scaleToFit(100,100);
            } catch (Exception ex) {
                Logger.getLogger(servlets.ServletPDFRTimesTable.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        
        public MyFooter(String message) {
            super();
            this.message = message;
        }
        
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("this is a header", ffont);
            String m = this.message;
            Phrase footer = new Phrase(m, ffont);
            /*
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            */
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
            try {
                if (img != null)
                    document.add(img);
            } catch (Exception ex) {
                Logger.getLogger(servlets.ServletPDFRTimesTable.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
    }
    
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
        
        String seq = request.getParameter("Sequence");
        int howmany = Integer.parseInt(request.getParameter("HowMany"));
        String domain = request.getServerName();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {        
            generatePDF(true, false, seq, howmany, baos, domain);
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


    private void generatePDF(boolean borders, boolean showAnswers, String seq, int howmany, ByteArrayOutputStream baos, String domain)
            throws DocumentException, NumberFormatException,
            IllegalStateException, IndexOutOfBoundsException, FileNotFoundException {
        //int[] numbs = {n1, n2, n3, n4, n5};
        ParseSequence ps = new ParseSequence("(?<numbers>[\\d]{1,2})");
        //Maximum two digit integers with any seperator in between.

        Font ff = FontFactory.getFont(FONT, null, true);
        //ff.setColor(BaseColor.RED);
        ff.setColor(BaseColor.BLACK);
        ff.setSize(18);
        
        Font fgrey = FontFactory.getFont(FONT, null, true);
        fgrey.setColor(BaseColor.DARK_GRAY);
        fgrey.setSize(18);
        
        Integer ii = null;
        ArrayList<Character> charList = null;
        ArrayList<Integer> oneDataList = ps.matchIntAllCapturingGroups(seq);
        ArrayList<QuestionData> qdList = generateQuestions(oneDataList, howmany);
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        MyFooter event = new MyFooter("SchoolHomeworkWeb - Multiplication Questions - "+domain+" - " + new Date().toString());
        writer.setPageEvent(event);
        // step 3
        document.open();
        // step 4
        //document.add(new Paragraph("SchoolHomeworkWeb - Random Questions - "+domain));
        //document.add(new Paragraph(new Date().toString()));
        // hmr - document.add(Chunk.NEWLINE);
        //document.add(new Paragraph("Seq:"+seq));
        //  for(QuestionData qd: qdList) {
        //      document.add(new Paragraph(qd.toStringQuestionAndAnswer()));
        //  }

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

            cellInner = new PdfPCell(new Phrase(" ",ff));
            cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            charList = QuestionData.getIntChars(qd.getFactor1());
            if (charList.size() == 1) {
                cellInner = new PdfPCell(new Phrase(" ", ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            } else if (charList.size() >= 2) {
                cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Phrase(charList.get(1).toString(),ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            }
            cellInner = new PdfPCell(new Phrase(qd.getOperation(),ff));
            cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            charList = QuestionData.getIntChars(qd.getFactor2());
            if (charList.size() == 1) {
                cellInner = new PdfPCell(new Phrase(" ",ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            } else if (charList.size() >= 2) {
                cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);

                cellInner = new PdfPCell(new Phrase(charList.get(1).toString(),ff));
                cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableInner.addCell(cellInner);
            }
            // Add top answer line
            cellInner = new PdfPCell(new Phrase("_",fgrey));
            //cellInner.setColspan(3);
            cellInner.setBorder(PdfPCell.TOP);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);

            cellInner = new PdfPCell(new Phrase("_",fgrey));
            //cellInner.setColspan(3);
            cellInner.setBorder(PdfPCell.TOP);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);


            cellInner = new PdfPCell(new Phrase("_",fgrey));
            //cellInner.setColspan(3);
            cellInner.setBorder(PdfPCell.TOP);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);
            
            if (showAnswers == true) {
                charList = QuestionData.getIntChars(qd.getAnswer());
                switch (charList.size()) {
                    case 3:
                        cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Phrase(charList.get(1).toString(),ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Phrase(charList.get(2).toString(),ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        break;
                    case 2:
                        cellInner = new PdfPCell(new Phrase(" ",ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Phrase(charList.get(1).toString(),ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        break;
                    case 1:
                        cellInner = new PdfPCell(new Phrase(" ",ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Phrase(" ",ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        cellInner = new PdfPCell(new Phrase(charList.get(0).toString(),ff));
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);

                        break;
                    default:
                        cellInner = new PdfPCell(new Phrase(" ", ff));
                        cellInner.setColspan(3);
                        cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
                        cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        tableInner.addCell(cellInner);
                }
            }
            //Add 1 blank lines for calculations
            cellInner = new PdfPCell(new Phrase(" ",ff));
            cellInner.setColspan(3);
            cellInner.setBorder(borders ? PdfPCell.NO_BORDER : PdfPCell.BOX);
            cellInner.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInner.addCell(cellInner);
            // Add lower Answer line
            cellInner = new PdfPCell(new Phrase(" ",ff));
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

        // step 4
        document.close();
    }


    private ArrayList<util.QuestionData> generateQuestions(ArrayList<Integer> li, int howmany) {
        return util.QuestionData.generateRandomMultiplication(li, howmany);
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
