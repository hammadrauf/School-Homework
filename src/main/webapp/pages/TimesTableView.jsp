<%-- 
    Document   : timesTableView.jsp
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
--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%--@page contentType="text/html" pageEncoding="UTF-8"--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Times Table Result</title>
    </head>
    <body>
        <h3>Times Table Result</h3>
        <h3><tiles:insertAttribute name="viewHeading" /></h3>
<%-- Text Messages
        <p>The answers setting is : <%=request.getAttribute("ShowAnswers")%></p>
        <p> Table1 <br/>
            <%=request.getAttribute("Table1")%> </p>
        <p>Table2 <br/>
            <%=request.getAttribute("Table2")%> </p>
        <p>Table3 <br/>
            <%=request.getAttribute("Table3")%> </p>
        <p>Table4 <br/>
            <%=request.getAttribute("Table4")%> </p>
        <p>Table5 <br/>
            <%=request.getAttribute("Table5")%> </p>
        <p>filePathForJavaScript <br/>
            <%=request.getAttribute("filePathForJavaScript")%> </p>
--%>
 <%-- Following is the PDF JavaScript --%>           
        <div>
            <button id="prev" onclick="goPrevious()">Previous</button>
            <button id="next" onclick="goNext()">Next</button>
            &nbsp; &nbsp;
            <span>Page: <span id="page_num"></span> / <span id="page_count"></span></span>
            <a href="<%=request.getAttribute("filePathForJavaScript")%>">Download From Here</a>
        </div>

        <div>
            <canvas id="the-canvas" style="border:1px solid black"></canvas>
        </div>

        <script type="text/javascript" src="./scripts/pdf.js"></script>

        <script type="text/javascript">
                //
                // NOTE: 
                // Modifying the URL below to another server will likely *NOT* work. Because of browser
                // security restrictions, we have to use a file server with special headers
                // (CORS) - most servers don't support cross-origin browser requests.
                //
                // var url = './example/06151880.pdf';
                var url = '<%=request.getAttribute("filePathForJavaScript")%>';

                //
                // Disable workers to avoid yet another cross-origin issue (workers need the URL of
                // the script to be loaded, and currently do not allow cross-origin scripts)
                //
                PDFJS.disableWorker = true;

                var pdfDoc = null,
                        pageNum = 1,
                        scale = 1.1,
                        canvas = document.getElementById('the-canvas'),
                        ctx = canvas.getContext('2d');

                //
                // Get page info from document, resize canvas accordingly, and render page
                //
                function renderPage(num) {
                    // Using promise to fetch the page
                    pdfDoc.getPage(num).then(function(page) {
                        var viewport = page.getViewport(scale);
                        canvas.height = viewport.height;
                        canvas.width = viewport.width;

                        // Render PDF page into canvas context
                        var renderContext = {
                            canvasContext: ctx,
                            viewport: viewport
                        };
                        page.render(renderContext);
                    });

                    // Update page counters
                    document.getElementById('page_num').textContent = pageNum;
                    document.getElementById('page_count').textContent = pdfDoc.numPages;
                }

                //
                // Go to previous page
                //
                function goPrevious() {
                    if (pageNum <= 1)
                        return;
                    pageNum--;
                    renderPage(pageNum);
                }

                //
                // Go to next page
                //
                function goNext() {
                    if (pageNum >= pdfDoc.numPages)
                        return;
                    pageNum++;
                    renderPage(pageNum);
                }

                //
                // Asynchronously download PDF as an ArrayBuffer
                //
                PDFJS.getDocument(url).then(function getPdfHelloWorld(_pdfDoc) {
                    pdfDoc = _pdfDoc;
                    renderPage(pageNum);
                });
        </script>        
    </body>
</html>

