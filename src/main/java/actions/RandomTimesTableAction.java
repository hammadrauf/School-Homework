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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.BadLocationException;
import org.apache.struts2.ServletActionContext;
import util.QuestionData;
import util.ParseSequence;

/**
 *
 * @author Hammad
 */
public class RandomTimesTableAction extends ActionSupport {

    public static final String FNAME = "RandomTimesTable.pdf";
    private static final int tableColumns = 5;
    private static final float borderWidth = 3.0f;

    public RandomTimesTableAction() {
        super();
        Logger.getLogger(actions.RandomTimesTableAction.class.getName()).log(Level.INFO, "RandomTimesTableAction - Startup");
    }

    public String execute() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String seq = request.getParameter("sequence");
        int howmany = Integer.parseInt(request.getParameter("howmany"));
        /*
        if(request.getRemoteUser()==null) {
            request.setAttribute("authenticationMessage", "Authentication is required to view this resource.");
            return("not_authenticated");
        }
        */
        
        request.setAttribute("SillyMesg", "I am in here!");
        request.setAttribute("Sequence", seq);
        request.setAttribute("HowMany", howmany);

        String fpath = generatePDF(true, false, seq, howmany);
        request.setAttribute("filePathForJavaScript", fpath);
        return ("success");
    }

    private ArrayList<util.QuestionData> generateQuestions(ArrayList<Integer> li, int howmany) {
        return util.QuestionData.generateRandomMultiplication(li, howmany);
    }

    private String generatePDF(boolean borders, boolean showAnswers, String seq, int howmany)
            throws DocumentException, NumberFormatException,
            IllegalStateException, IndexOutOfBoundsException, FileNotFoundException {
        //int[] numbs = {n1, n2, n3, n4, n5};
        ParseSequence ps = new ParseSequence("(?<numbers>[\\d]{1,2})");
        //Maximum two digit integers with any seperator in between.

        Integer ii = null;
        ArrayList<Character> charList = null;
        ArrayList<Integer> oneDataList = ps.matchIntAllCapturingGroups(seq);
        ArrayList<QuestionData> qdList = generateQuestions(oneDataList, howmany);
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
        ServletContext sc = ServletActionContext.getServletContext();
        String FilePath = sc.getRealPath("/");
        String outPath = (FilePath==null)?"temp/" + FNAME:FilePath + "temp/" + FNAME;
        String pathForJavaScript = "./" + "temp/" + FNAME;
        PdfWriter.getInstance(document, new FileOutputStream(outPath));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("StrutsSchoolWeb - Random Multiplication Questions"));
        document.add(new Paragraph(new Date().toString()));
        document.add(Chunk.NEWLINE);
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

        // step 4
        document.close();
        return (pathForJavaScript);
    }
}