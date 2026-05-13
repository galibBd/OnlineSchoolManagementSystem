package com.idb.controller.helper;

public class AttendanceRequest {
    private final Integer classId;
    private final Integer sessionId;
    
    public AttendanceRequest(Integer classId, Integer sessionId) {
        if (classId == null || classId <= 0) {
            throw new IllegalArgumentException("Class ID must be positive");
        }
        if (sessionId == null || sessionId <= 0) {
            throw new IllegalArgumentException("Session ID must be positive");
        }
        this.classId = classId;
        this.sessionId = sessionId;
    }
    
    public Integer getClassId() { return classId; }
    public Integer getSessionId() { return sessionId; }
    
    @Override
    public String toString() {
        return String.format("AttendanceRequest{classId=%d, sessionId=%d}", classId, sessionId);
    }
}