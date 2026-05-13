package com.idb.model;

/**
 * REFACTORED: Classes model dengan better encapsulation dan validation
 * 
 * Improvements:
 * - Changed naming convention to camelCase (c_id → classId)
 * - Added validation in constructor and setters
 * - Added business logic methods
 * - Added toString(), equals(), hashCode()
 * - Better documentation
 */
public class Classes {
    
    private int classId;
    private String className;
    private int sessionId;
    
    // ============ CONSTRUCTORS ============
    
    public Classes() {
    }
    
    public Classes(String className, int sessionId) {
        setClassName(className);
        setSessionId(sessionId);
    }
    
    public Classes(int classId, String className, int sessionId) {
        setClassId(classId);
        setClassName(className);
        setSessionId(sessionId);
    }
    
    // ============ GETTERS & SETTERS WITH VALIDATION ============
    
    public int getClassId() {
        return classId;
    }
    
    public void setClassId(int classId) {
        if (classId <= 0) {
            throw new IllegalArgumentException("Class ID must be positive");
        }
        this.classId = classId;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        if (className == null || className.trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be null or empty");
        }
        if (className.length() > 100) {
            throw new IllegalArgumentException("Class name cannot exceed 100 characters");
        }
        this.className = className.trim();
    }
    
    public int getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(int sessionId) {
        if (sessionId <= 0) {
            throw new IllegalArgumentException("Session ID must be positive");
        }
        this.sessionId = sessionId;
    }
    
    // ============ BUSINESS LOGIC METHODS ============
    
    /**
     * Get full class information
     */
    public String getFullInfo() {
        return String.format("Class: %s (ID: %d, Session: %d)", 
                           className, classId, sessionId);
    }
    
    /**
     * Check if class data is valid
     */
    public boolean isValid() {
        return classId > 0 && 
               className != null && !className.trim().isEmpty() && 
               sessionId > 0;
    }
    
    // ============ UTILITY METHODS ============
    
    @Override
    public String toString() {
        return String.format("Classes{classId=%d, className='%s', sessionId=%d}", 
                           classId, className, sessionId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Classes other = (Classes) obj;
        return classId == other.classId &&
               className.equals(other.className) &&
               sessionId == other.sessionId;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(classId, className, sessionId);
    }
}