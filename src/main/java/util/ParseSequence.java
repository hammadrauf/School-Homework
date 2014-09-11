/*
    Document   : ParseSequence.java
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class ParseSequence {

    private static final String emailString = "[A-Z0-9._%+-]+@[[A-Z0-9.-]+\\.]+[A-Z]{2,4}";
    private String patternString = null;
    private Pattern pat;
    private Matcher mat;

    public ParseSequence(String pattern) throws PatternSyntaxException {
        pat = Pattern.compile(pattern);
        patternString = pattern;
    }

    public void makePatternEmail() throws PatternSyntaxException {
        pat = Pattern.compile(emailString);
        patternString = emailString;
    }

    public String getPatternString() {
        return patternString;
    }

    public boolean isCompleteMatch(String input) {
        boolean b = false;
        mat = pat.matcher(input.toUpperCase());
        if (mat.matches()) {
            b = true;
        }
        return b;
    }

    public ArrayList<Integer> matchIntAllCapturingGroups(String input, String namedCapturingGroup)
           throws NumberFormatException, IllegalStateException, IndexOutOfBoundsException
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        mat = pat.matcher(input);
        String s = null;
        Integer i = null;
        // Check all occurance
        while (mat.find()) {
            s = mat.group(namedCapturingGroup);
            i = Integer.parseInt(s);
            al.add(i);
        }
        return al;
    }

    public ArrayList<Integer> matchIntAllCapturingGroups(String input)
            throws NumberFormatException, IllegalStateException, IndexOutOfBoundsException
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        mat = pat.matcher(input);
        String s = null;
        Integer i = null;
        // Check all occurance
        while (mat.find()) {
            s = mat.group();
            i = Integer.parseInt(s);
            al.add(i);
        }
        return al;
    }

    public ArrayList<String> matchStringAllCapturingGroups(String input, String namedCapturingGroup)
    throws IllegalStateException, IndexOutOfBoundsException
    {
        ArrayList<String> al = new ArrayList<String>();
        mat = pat.matcher(input);
        String s = null;
        // Check all occurance
        while (mat.find()) {
            s = mat.group(namedCapturingGroup);
            al.add(s);
        }
        return al;
    }

    public ArrayList<String> matchStringAllCapturingGroups(String input)
    throws IllegalStateException, IndexOutOfBoundsException
    {
        ArrayList<String> al = new ArrayList<String>();
        mat = pat.matcher(input);
        String s = null;
        // Check all occurance
        while (mat.find()) {
            s = mat.group();
            al.add(s);
        }
        return al;
    }

    public static void main(String[] args) throws Exception {
        String EXAMPLE_TEST = " 2 , 4 , 5 , 1 , 2j1 ";
        String email;
        ParseSequence ps = new ParseSequence("(?<numbers>\\d+)");
        ArrayList<Integer> il = ps.matchIntAllCapturingGroups(EXAMPLE_TEST);
        int sum = 0;
        for (Integer i : il) {
            sum = sum + i.intValue();
        }
        System.out.println("Input String:" + EXAMPLE_TEST);
        System.out.println("Sum is:" + sum);

        if (args.length > 0) {
            email = args[0];
            ps.makePatternEmail();
            if (ps.isCompleteMatch(email)) {
                System.out.println("A valid email address.");
            } else {
                System.out.println("Invalid email address");
            }
        }
    }
}