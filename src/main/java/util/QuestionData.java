/*
    Document   : QuestionData.java
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

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Hammad
 */
public class QuestionData {

    public static enum Operation {

        addition, subtraction, multiplication, division
    };
    private int factor1 = 0;
    private int factor2 = 0;
    private Operation op = Operation.addition;
    private int answer = 0;
    private int remainder = 0;

    public QuestionData(int factor1, int factor2, Operation opcode) {
        super();
        this.factor1 = factor1;
        this.factor2 = factor2;
        this.op = opcode;
        this.calculate();
    }

    private int calculate() {
        switch (op) {
            case addition:
                answer = factor1 + factor2;
                break;
            case subtraction:
                answer = factor1 - factor2;
                break;
            case multiplication:
                answer = factor1 * factor2;
                break;
            case division:
                try {
                    answer = factor1 / factor2;
                    remainder = factor1 % factor2;
                } catch (Exception e) {
                    answer = 0;
                    remainder = 0;
                    System.out.println("factor2 is 0, division by zero");
                }
                break;
        }
        return (answer);
    }

    public int getRemainder() {
        return remainder;
    }

    public int getAnswer() {
        return answer;
    }

    public int getFactor1() {
        return factor1;
    }

    public int getFactor2() {
        return factor2;
    }

    public Operation getOp() {
        return op;
    }

    public String getOperation() {
        String s1 = "";
        switch(op) {
            case addition:
                s1 = "+";
            break;
            case multiplication:
 //               s1 = ""+0x00D7;
                s1 = "x";
                break;
            case division:
 //               s1 = ""+0x00F7;
                s1 = "/";
                break;
            case subtraction:
                s1 = "-";
                break;
        }
        return s1;
    }
    
    public String toString() {
        String s1 = null;
        switch (op) {
            case multiplication:
                s1 = "" + factor1 + "\tx\t" + factor2 + "\t=\t" + answer;
                break;
            default:
                s1 = "" + factor1 + "\tOP\t" + factor2 + "\t=\t" + answer + ",\tremainder\t=\t" + remainder;
        }
        return s1;
    }

    public String toStringQuestion() {
        String s1 = null;
        switch (op) {
            case multiplication:
                s1 = "" + factor1 + "\tx\t" + factor2 + "\t=\t" + "___";
                break;
            default:
                s1 = "" + factor1 + "\tOP\t" + factor2 + "\t=\t" + "___" + ",\tremainder\t=\t" + "___";
        }
        return s1;
    }

    public String toStringAnswer() {
        String s1 = null;
        switch (op) {
            case multiplication:
                s1 = "" + answer;
                break;
            default:
                s1 = answer + ",\tremainder\t=\t" + remainder;
        }
        return s1;
    }

    public String toStringQuestionAndAnswer() {
        String s1 = null;
        switch (op) {
            case multiplication:
                s1 = "" + factor1 + "\tx\t" + factor2 + "\t=\t" + "___" + "\t\t" + answer;
                break;
            default:
                s1 = "" + factor1 + "\tOP\t" + factor2 + "\t=\t" + "___" + ",\tremainder\t=\t" + "___" + "\t\t" + answer + ",\tremainder\t=\t" + remainder;
        }
        return s1;
    }

    public static ArrayList<QuestionData> generateRandomMultiplication(ArrayList<Integer> numbList,
            int howmany) {
        int qeach = 0;
        ArrayList<QuestionData> questionList = new ArrayList<QuestionData>();
        Integer f1 = null;
        QuestionData question = null;
        int ncount = numbList.size();
        try {
            qeach = howmany / ncount;
        } catch (Exception e) {
            qeach = 0;
        }
        int qcount = 0;
        while (!(numbList.isEmpty())) {
            f1 = numbList.remove(qcount);
            for (int i = 1; i <= qeach; i++) {
                question = new QuestionData(f1.intValue(), (int) (11 * Math.random()),
                        Operation.multiplication);
                questionList.add(question);
            }
        }
        Collections.shuffle(questionList);
        return questionList;
    }

    public static ArrayList<QuestionData> generateTimesTable(int number,
            int uptillValue) {
        QuestionData question = null;
        ArrayList<QuestionData> questionList = new ArrayList<QuestionData>();
        for (int a = 1; a <= uptillValue; a++) {
            question = new QuestionData(number, a, Operation.multiplication);
            questionList.add(question);
        }
        return questionList;
    }
    
    public static ArrayList<Character> getIntChars(int n) {
        ArrayList<Character> res = new ArrayList<Character>();
        String s = Integer.toString(n);
        int size = s.length();
        Character c = null;
        for(int i=0; i<(size); i++ ) {
            c = new Character(s.charAt(i));
            res.add(c);
        }
        return res;
    }
}
