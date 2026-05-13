package com.idb.model;

import java.time.LocalDate;
import java.util.Date;

/**
 * REFACTORED: Attendance model dengan validation dan business logic
 * 
 * Improvements:
 * - Added AttendanceStatus enum (replace String "A")
 * - Added validation in setters
 * - Added business logic methods
 * - Better encapsulation
 * - Clear constant definitions
 */
public class Attendance {
    
    // Constants untuk status
    public enum AttendanceStatus {
        PRESENT("P", "Present"),
        ABSENT("A", "Absent"),
        LEAVE("L", "Leave"),
        LATE("LT", "Late");
        
        private final String code;
        private final String label;
        
        AttendanceStatus(String code, String label) {
            this.code = code;
            this.label = label;
        }
        
        public String getCode() { return code; }
        public String getLabel() { return label; }
        
        public static AttendanceStatus fromCode(String code) {
            for (AttendanceStatus status : values()) {
                if (status.code.equals(code)) return status;
            }
            return ABSENT; // default
        }
    }
    
    private int attendanceId;
    private int studentId;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    
    // ============ CONSTRUCTORS ============
    public Attendance() {
        this.status = AttendanceStatus.ABSENT; // default status
    }
    
    public Attendance(int studentId, LocalDate attendanceDate, AttendanceStatus status) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        if (attendanceDate == null) {
            throw new IllegalArgumentException("Attendance date cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        
        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }
    
    // ============ GETTERS & SETTERS WITH VALIDATION ============
    
    public int getAttendanceId() {
        return attendanceId;
    }
    
    public void setAttendanceId(int attendanceId) {
        if (attendanceId <= 0) {
            throw new IllegalArgumentException("Attendance ID must be positive");
        }
        this.attendanceId = attendanceId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        this.studentId = studentId;
    }
    
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
    
    public void setAttendanceDate(LocalDate attendanceDate) {
        if (attendanceDate == null) {
            throw new IllegalArgumentException("Attendance date cannot be null");
        }
        if (attendanceDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Attendance date cannot be in future");
        }
        this.attendanceDate = attendanceDate;
    }
    
    public AttendanceStatus getStatus() {
        return status;
    }
    
    public void setStatus(AttendanceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }
    
    // ============ BUSINESS LOGIC METHODS ============
    
    /**
     * Check if student is marked present
     */
    public boolean isPresent() {
        return status == AttendanceStatus.PRESENT;
    }
    
    /**
     * Check if student is absent
     */
    public boolean isAbsent() {
        return status == AttendanceStatus.ABSENT;
    }
    
    /**
     * Get human readable status label
     */
    public String getStatusLabel() {
        return status.getLabel();
    }
    
    /**
     * Get status code for database operations
     */
    public String getStatusCode() {
        return status.getCode();
    }
    
    // ============ UTILITY METHODS ============
    
    @Override
    public String toString() {
        return String.format(
            "Attendance{id=%d, studentId=%d, date=%s, status=%s}",
            attendanceId, studentId, attendanceDate, status.getLabel()
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Attendance other = (Attendance) obj;
        return attendanceId == other.attendanceId &&
               studentId == other.studentId &&
               attendanceDate.equals(other.attendanceDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(attendanceId, studentId, attendanceDate);
    }
}