/*
    Document   : CTModel.java
    Created on : Nov 4, 2014 8:15:00 PM
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
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hammad
 */
public class CTModel {
    
    //private String countBy;
    //private String countUptil;
    private List<CodeAndValue> countBy;
    private List<CodeAndValue> countUptil;
    //    private List<CodeAndValue> listOptions;
    
    
    public CTModel() {
        super();
        countBy = new ArrayList<CodeAndValue>();
        countBy.add(new CodeAndValue("one", "1"));
        countBy.add(new CodeAndValue("two", "2"));
        countBy.add(new CodeAndValue("five", "5"));
    
        countUptil = new ArrayList<CodeAndValue>();
        countUptil.add(new CodeAndValue("1hundred", "100"));
        countUptil.add(new CodeAndValue("2hundred", "200"));
    }
    
/*    
    public String getCountBy() {
        return countBy;
    }
    
    public void setCountBy(String countBy) {
        this.countBy = countBy;
    }

    public String getCountUptil() {
        return countUptil;
    }

    public void seCountUptil(String countUptil) {
        this.countUptil = countUptil;
    }
    
*/
    
    public String getDefaultCountBy() {
		return "one";
   }

     public List<CodeAndValue> getCountBy() {
        return countBy;
    }

    public void setCountBy(List<CodeAndValue> countBy) {
        this.countBy = countBy;
    } 
      
    public String getDefaultCountUptil() {
		return "1hundred";
   }

     public List<CodeAndValue> getCountUptil() {
        return countUptil;
    }

    public void setCountUptil(List<CodeAndValue> countUptil) {
        this.countUptil = countUptil;
    } 

    
}
