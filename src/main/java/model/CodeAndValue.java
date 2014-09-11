/*
    Document   : CodeAndValue.java
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
package model;

/**
 *
 * @author Hammad
 */
public class CodeAndValue {
    
    private String code;
    private String value;
    
    public CodeAndValue(String code, String value) {
        super();
        setCode(code);
        setValue(value);
    }
    
    public String getCode() {
        return this.code;
    }
    
    public final void setCode(String c) {
        this.code = c;
    }

        public String getValue() {
        return this.value;
    }
    
    public final void setValue(String v) {
        this.value = v;
    }

    
}
