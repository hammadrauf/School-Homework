/*
    Document   : TTModel.java
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hammad
 */
public class TTModel {
    
    private String sequence;
    private String answers;
    private List<CodeAndValue> listOptions;
    
    
    public TTModel() {
        super();
        listOptions = new ArrayList<CodeAndValue>();
        listOptions.add(new CodeAndValue("true", "Yes"));
        listOptions.add(new CodeAndValue("false", "No"));
    }
    
    public String getSequence() {
        return sequence;
    }
    
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
    
    public String getDefaultListValue(){
		return "true";
   }

     public List<CodeAndValue> getListOptions() {
        return listOptions;
    }

    public void setListOptions(List<CodeAndValue> listOptions) {
        this.listOptions = listOptions;
    } 
   
    
}
