/*
    Document   : PatternMatcher.java
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
package util;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hammad
 */
public class PatternMatcher {

    private String sSimple = "[.]*";
    private String sDate = "MM/dd/yyyy",  sTime = "h:mm:ss a",  sTimeRange = "[0-9]{1,2}[:][0-9]{2,2}[\\s]*[pm[PM[am[AM]]]]";
    private Pattern pSimple = null;
    private Pattern pDate = null,  pTime = null,  pTimeRange = null;

    
    
    public String matchSimpleString(String inString) throws Exception {
    /*    String outString = "";
            if (sSimple == null) {
            throw new Exception("Matching pattern not defined.");
        } 
        pSimple = Pattern.compile(sSimple);
        Matcher m = pSimple.matcher(inString);
        if(m.find()) 
            outString = m.group();
     */
        String outString = inString;
        return outString;
    }

    

    public Date matchStringDate(String inString) {
            Date outDate=null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(sDate);
            outDate = df.parse(inString);
        } catch (ParseException ex) {
            Logger.getLogger(PatternMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
            finally {
                return outDate;
            }
    }
    
    
    public TimeRange matchStringTimeRange(String inString) throws Exception {
        Time outTime1, outTime2;
        TimeRange dr = new TimeRange();
        String stime1=null, stime2=null;
        pTimeRange = Pattern.compile(sTimeRange);
        if (sTimeRange == null) {
            throw new Exception("Matching pattern not defined.");
        }
        Matcher m = pTimeRange.matcher(inString);
        m.find(); 
            stime1 = m.group();
            int i = m.end();
            m.find(i);
            stime2=m.group();
     //
            SimpleDateFormat df = new SimpleDateFormat(sTimeRange);
            Date dt = null, dt2 = null;
        try {
            dt = df.parse(stime1);
            dt2 = df.parse(stime2);
        } catch (ParseException ex) {
            Logger.getLogger(PatternMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
            outTime1 = new Time(dt.getTime());
            outTime2 = new Time(dt2.getTime());
            dr.setTime1(outTime1);
            dr.setTime2(outTime2);
            return dr;
    }

    public Time matchStringTime(String inString) {
        Time outTime;
        SimpleDateFormat df = new SimpleDateFormat(sTime);
            Date dt = null;
        try {
            dt = df.parse(inString);
        } catch (ParseException ex) {
            Logger.getLogger(PatternMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
            outTime = new Time(dt.getTime());
            return outTime;
    }
}
