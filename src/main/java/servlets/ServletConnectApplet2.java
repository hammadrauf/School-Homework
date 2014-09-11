/*
 Document   : ServletConnectApplet2.java
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
package servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dbbeans.DBInitializer;
import dbbeans.Users;
import dbbeans.Drawings;
import java.util.Date;
import javax.persistence.NamedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hammad
 */
public class ServletConnectApplet2 extends HttpServlet {
private static final String caption = "Caption Drawing 1";
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequestPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean drawingExists = false;
        String userID = null;
        String vXML = null;
        try {
            userID = req.getParameter("userID");
            vXML = req.getParameter("vXML");

            Logger.getLogger(servlets.ServletConnectApplet2.class.getName()).log(Level.INFO, "REQUEST Remote User ID: " + userID);
            Logger.getLogger(servlets.ServletConnectApplet2.class.getName()).log(Level.INFO, "REQUEST Remote vXML: " + vXML);
            Drawings dr = null;
            DBInitializer.tranxBegin();
            dr = DBInitializer.getDrawingsByUsernameCaption(userID, caption);
            if (dr == null) {
                dr = new Drawings(userID, "Caption Drawing 1");
            } else {
                drawingExists = true;
            }
            // DBInitializer.getUserByID throws Exception
            //   String usid = (String)session.getAttribute("userID");
            Users u = DBInitializer.getUsersByID(userID);
            dr.setUsers(u);
            dr.setVXML(vXML);
            dr.setDatetime(new Date());
            if (!drawingExists) {
                u.getDrawingsList().add(dr);
                DBInitializer.tranxUpdate(u);
                DBInitializer.tranxPersist(dr);
            } else {
                DBInitializer.tranxUpdate(dr);
            }

            DBInitializer.tranxEnd();
            String responseMsg = "Image data saved";

            // set the response code and write the response data
            resp.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());

            writer.write(responseMsg);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(ServletConnectApplet2.class.getName()).log(Level.SEVERE, "Exception Thrown", e);
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
            }
        } catch (Exception ex) {
            Logger.getLogger(ServletConnectApplet2.class.getName()).log(Level.SEVERE, "Exception Thrown", ex);
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(ex.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
            }
        }

    }

    protected void processRequestGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String responseMsg = "";
        String userID = null;
        String dCaption = null;
        boolean drawingExists = false;
        try {
            userID = req.getParameter("userID");
            //dCaption = caption;
            dCaption = req.getParameter("dCaption");
            // responseMsg = "Image data load message recieved" + " UserID: "+userID+", Caption: "+dCaption;
            // set the response code and write the response data
            
            Logger.getLogger(servlets.ServletConnectApplet2.class.getName()).log(Level.INFO, "REQUEST Remote User ID: " + userID);
            Logger.getLogger(servlets.ServletConnectApplet2.class.getName()).log(Level.INFO, "REQUEST Remote dCaption: " + dCaption);
            Drawings dr = null;
            DBInitializer.tranxBegin();
            dr = DBInitializer.getDrawingsByUsernameCaption(userID, caption);
            if (dr == null) {
                responseMsg="No Data Found";
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                drawingExists = true;
                responseMsg=dr.getVXML();
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            DBInitializer.tranxEnd();
            
            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());

            writer.write(responseMsg);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(ServletConnectApplet2.class.getName()).log(Level.SEVERE, "Exception Thrown", e);
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
            }
        } catch (Exception ex) {
            Logger.getLogger(ServletConnectApplet2.class.getName()).log(Level.SEVERE, "Exception Thrown", ex);
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(ex.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequestGet(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequestPost(request, response);
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
