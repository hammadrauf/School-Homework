<%-- 
    Document   : baseLayout2.jsp revised
    Created on : Oct 14, 2013, 12:15:57 PM
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><tiles:insertAttribute name="title" ignore="true" /></title>
            <style type="text/css" media="screen">@import url("css/layout.css");</style>
    </head>
    <body>
        <div class="cssWrapper" id="cssWrapper">
            <div class="cssHeader" id="cssHeader1">
                <div class="vertical-middle">
                    <tiles:insertAttribute name="header" />
                </div>
            </div>
            <div class="cssHeader" id="cssHeader2">
                <tiles:insertAttribute name="loginbar" />
            </div>
            <div class="cssGap" id="cssGapHeader">
            </div>
            <div class="cssCenter" id="cssCenter">
                <div class="cssCenter" id="cssLeft">
                    <tiles:insertAttribute name="menu" />
                </div>
                <div class="cssCenter" id="cssBody">
                    <tiles:insertAttribute name="body" />
                </div>
                <div class="cssCenter" id="cssRight">
                    <!-- This is the Right Sidebar -->
                </div>
            </div>
            <div class="cssGap" id="cssGapFooter">
            </div>
            <div class="cssFooter" id="cssFooter2">
                <div class="vertical-middle">
                    <!--Legal Contact Privacy Policy-->
                </div>
            </div>
            <div class="cssFooter" id="cssFooter1">
                <div class="vertical-middle">
                    <tiles:insertAttribute name="footer" />
                </div>
            </div>
        </div>
    </body>
</html>