package com.idb.controller.helper;

public class ResultRequest {
    private final Integer semesterId;
    private final Integer studentId;
    
    public ResultRequest(Integer semesterId, Integer studentId) {
        if (semesterId == null || semesterId <= 0) {
            throw new IllegalArgumentException("Semester ID must be positive");
        }
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        this.semesterId = semesterId;
        this.studentId = studentId;
    }
    
    public Integer getSemesterId() { return semesterId; }
    public Integer getStudentId() { return studentId; }
    
    @Override
    public String toString() {
        return String.format("ResultRequest{semesterId=%d, studentId=%d}", semesterId, studentId);
    }
}