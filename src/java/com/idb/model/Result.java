
package com.idb.model;


public class Result {
    private int res_id,sem_id,s_id,sub_id,marks;
    private Double  gPoint;
    private String grade;

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public int getSem_id() {
        return sem_id;
    }

    public void setSem_id(int sem_id) {
        this.sem_id = sem_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Double getgPoint() {
        return gPoint;
    }

    public void setgPoint(Double gPoint) {
        this.gPoint = gPoint;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    
}
