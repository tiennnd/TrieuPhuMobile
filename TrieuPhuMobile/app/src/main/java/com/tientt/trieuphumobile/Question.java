package com.tientt.trieuphumobile;

/**
 * Created by tiennd on 16/07/2016.
 */
public class Question {
    private String question;
    private int level, trueCase;
    private String caseA, caseB, caseC, caseD;

    public Question(String question, int level, int trueCase, String caseA, String caseB, String caseC, String caseD) {
        this.question = question;
        this.level = level;
        this.trueCase = trueCase;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
    }

    public String getQuestion() {
        return question;
    }

    public int getLevel() {
        return level;
    }

    public int getTrueCase() {
        return trueCase;
    }

    public String getCaseA() {
        return caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    @Override
    public String toString() {
        return question + "\n" +
                "A:" + caseA + "\n" +
                "B:" + caseB + "\n" +
                "C:" + caseC + "\n" +
                "D:" + caseD + "\n" +
                "True case:" + trueCase + "\n";
    }
}
