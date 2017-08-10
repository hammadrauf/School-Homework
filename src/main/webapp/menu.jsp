<%--
    Document   : menu.jsp
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
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Page</title>
<style type="text/css" media="screen,print">
/*	@import '/css/lab.css';  */
	body {padding:1em}
	#wrap {
		width:500px;
		margin:1em auto;
	}
/* Disable properties specified in the imported CSS file */
	.menu a {
		border:none;
		font-weight:normal;
	}

/* Actual menu CSS starts here */
	.menu,
	.menu ul {
		margin:0;
		padding:0;
		list-style:none;
	}
        .menu {
            width:auto;
        }
	.menu li {
                font-size:70%;
		display:block;
		margin:0;
		padding:0;
		margin-bottom:1px;
	}
	.menu a {
		display:block;
		padding:2px 5px;
		color:#000;
		background:#bf4ec2;
		text-decoration:none;
	}
	.menu a:hover,
	.menu a:focus,
	.menu a:active {background:#d68dd8;}
	.menu ul li {padding-left:15px;}
	.menu ul a {background:#ce7ee2;}
	.hidden {display:none;}
</style>
<!--[if ie]>
<style type="text/css" media="screen">
	.menu a {zoom:1;}
</style>
<![endif]-->
<!--script type="text/javascript" src="/scripts/togglemenu.js"></script -->
<script type="text/javascript" >
  var toggleMenu = {
	init : function(sContainerClass, sHiddenClass) {
		if (!document.getElementById || !document.createTextNode) {return;} // Check for DOM support
		var arrMenus = this.getElementsByClassName(document, 'ul', sContainerClass);
		var arrSubMenus, oSubMenu, oLink;
		for (var i = 0; i < arrMenus.length; i++) {
			arrSubMenus = arrMenus[i].getElementsByTagName('ul');
			for (var j = 0; j < arrSubMenus.length; j++) {
				oSubMenu = arrSubMenus[j];
				oLink = oSubMenu.parentNode.getElementsByTagName('a')[0];
				oLink.onclick = function(){toggleMenu.toggle(this.parentNode.getElementsByTagName('ul')[0], sHiddenClass); return false;}
				this.toggle(oSubMenu, sHiddenClass);
			}
		}
	},
	toggle : function(el, sHiddenClass) {
		var oRegExp = new RegExp("(^|\\s)" + sHiddenClass + "(\\s|$)");
		el.className = (oRegExp.test(el.className)) ? el.className.replace(oRegExp, '') : el.className + ' ' + sHiddenClass; // Add or remove the class name that hides the element
	},

        addEvent : function(obj, type, fn) {
		if (obj.addEventListener)
			obj.addEventListener(type, fn, false);
		else if (obj.attachEvent) {
			obj["e"+type+fn] = fn;
			obj[type+fn] = function() {obj["e"+type+fn](window.event);}
			obj.attachEvent("on"+type, obj[type+fn]);
		}
	},

    getElementsByClassName : function(oElm, strTagName, strClassName){
	    var arrElements = (strTagName == "*" && document.all)? document.all : oElm.getElementsByTagName(strTagName);
	    var arrReturnElements = new Array();
	    strClassName = strClassName.replace(/\-/g, "\\-");
	    var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)");
	    var oElement;
	    for(var i=0; i<arrElements.length; i++){
	        oElement = arrElements[i];      
	        if(oRegExp.test(oElement.className)){
	            arrReturnElements.push(oElement);
	        }   
	    }
	    return (arrReturnElements)
	}
};
toggleMenu.addEvent(window, 'load', function(){toggleMenu.init('menu','hidden');});  
</script>
    </head>
    <body>
		<ul class="menu">
			<li><a href="indexLink">Home</a>
				<!--ul>
					<li><a href="/">Sub category 1a</a></li>
					<li><a href="/">Sub category 1b</a></li>
					<li><a href="/">Sub category 1c</a></li>
					<li><a href="/">Sub category 1d</a></li>
				</ul -->
			</li>
			<li><a href="drawingpageLink">Draw</a>
				<!-- ul>
					<li><a href="/">Sub category 2a</a></li>
					<li><a href="/">Sub category 2b</a></li>
					<li><a href="/">Sub category 2c</a></li>
					<li><a href="/">Sub category 2d</a></li>
				</ul -->
			</li>
			<li><a href=".">Math</a>
				<ul>
                                    <li><a href=".">Grade 1</a>
                                        <ul>
					<li><a href="countingtableLink">Counting Tables</a></li>
                                        </ul>
                                    </li>                                    
                                    <li><a href=".">Grade 3</a>
                                        <ul>
					<li><a href="timestableLink">Times Tables</a></li>
					<li><a href="randomtimestableLink">Mixed Multiply</a></li>
                                        </ul>
                                    </li>
				</ul>
			</li>
			<li><a href="persistanceFormLink">Register</a>
				<!-- ul>
					<li><a href="/">Sub category 4a</a></li>
					<li><a href="/">Sub category 4b</a></li>
					<li><a href="/">Sub category 4c</a></li>
					<li><a href="/">Sub category 4d</a></li>
				</ul -->
			</li>
			<!-- li><a href=".">Category 5</a>
				<ul>
					<li><a href="/">Sub category 5a</a></li>
					<li><a href="/">Sub category 5b</a></li>
					<li><a href="/">Sub category 5c</a></li>
					<li><a href="/">Sub category 5d</a></li>
				</ul>
			</li -->
		</ul>
            <% if(request.isUserInRole("admin")) { %>
		<ul class="menu" id="adminmenu">
			<li><a href="adminemailformLink">Email Form</a>
				<!--ul>
					<li><a href="/">Sub category 1a</a></li>
					<li><a href="/">Sub category 1b</a></li>
					<li><a href="/">Sub category 1c</a></li>
					<li><a href="/">Sub category 1d</a></li>
				</ul -->
			</li>
			<!--li><a href=".">Math</a>
				<ul>
                                    <li><a href=".">Grade 1</a></li>
                                    <li><a href=".">Grade 3</a>
                                        <ul>
					<li><a href="timestableLink">Times Tables</a></li>
					<li><a href="randomtimestableLink">Mixed Multiply</a></li>
                                        </ul>
                                    </li>
				</ul>
			</li-->
		</ul>                 
             <% } else { %>
             <% } %>
    </body>
</html>

