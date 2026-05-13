package com.idb.model;

/**
 * REFACTORED: Result model dengan enum untuk grade, validation, dan business logic
 * 
 * Improvements:
 * - Changed String grade to Grade enum
 * - Grouped related IDs into StudentResultContext class
 * - Added comprehensive validation
 * - Added business logic methods (getGradeDescription, isPassingGrade, etc.)
 * - Better encapsulation
 */

// ============ GRADE ENUM ============
public class Result {
    
    public enum Grade {
        A(4.0, "Excellent", 80, 100),
        B(3.0, "Very Good", 70, 79),
        C(2.0, "Good", 60, 69),
        D(1.0, "Satisfactory", 50, 59),
        F(0.0, "Fail", 0, 49);
        
        private final double gPoint;
        private final String description;
        private final int minMarks;
        private final int maxMarks;
        
        Grade(double gPoint, String description, int minMarks, int maxMarks) {
            this.gPoint = gPoint;
            this.description = description;
            this.minMarks = minMarks;
            this.maxMarks = maxMarks;
        }
        
        public double getGPoint() { return gPoint; }
        public String getDescription() { return description; }
        public int getMinMarks() { return minMarks; }
        public int getMaxMarks() { return maxMarks; }
        
        /**
         * Calculate grade from marks
         */
        public static Grade fromMarks(int marks) {
            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks must be between 0 and 100");
            }
            
            for (Grade grade : values()) {
                if (marks >= grade.minMarks && marks <= grade.maxMarks) {
                    return grade;
                }
            }
            return F; // fallback
        }
        
        @Override
        public String toString() {
            return String.format("%s (%.1f): %s", this.name(), gPoint, description);
        }
    }
    
    // ============ STUDENT RESULT CONTEXT ============
    /**
     * Extracted data clump: group related IDs
     */
    public static class StudentResultContext {
        private final int resultId;
        private final int semesterId;
        private final int studentId;
        private final int subjectId;
        
        public StudentResultContext(int resultId, int semesterId, int studentId, int subjectId) {
            if (resultId <= 0 || semesterId <= 0 || studentId <= 0 || subjectId <= 0) {
                throw new IllegalArgumentException("All IDs must be positive");
            }
            this.resultId = resultId;
            this.semesterId = semesterId;
            this.studentId = studentId;
            this.subjectId = subjectId;
        }
        
        public int getResultId() { return resultId; }
        public int getSemesterId() { return semesterId; }
        public int getStudentId() { return studentId; }
        public int getSubjectId() { return subjectId; }
        
        @Override
        public String toString() {
            return String.format("ResultContext{result=%d, sem=%d, student=%d, subject=%d}",
                               resultId, semesterId, studentId, subjectId);
        }
    }
    
    // ============ FIELDS ============
    private StudentResultContext context;
    private int marks;
    private Grade grade;
    private Double gPoint;
    
    // Constants
    private static final int MIN_MARKS = 0;
    private static final int MAX_MARKS = 100;
    
    // ============ CONSTRUCTORS ============
    
    public Result() {
    }
    
    public Result(StudentResultContext context, int marks) {
        this.context = context;
        setMarks(marks);
        calculateGradeAndPoint();
    }
    
    public Result(int resultId, int semesterId, int studentId, int subjectId, int marks) {
        this.context = new StudentResultContext(resultId, semesterId, studentId, subjectId);
        setMarks(marks);
        calculateGradeAndPoint();
    }
    
    // ============ GETTERS & SETTERS WITH VALIDATION ============
    
    public StudentResultContext getContext() {
        return context;
    }
    
    public void setContext(StudentResultContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
    }
    
    public int getMarks() {
        return marks;
    }
    
    public void setMarks(int marks) {
        if (marks < MIN_MARKS || marks > MAX_MARKS) {
            throw new IllegalArgumentException(
                String.format("Marks must be between %d and %d", MIN_MARKS, MAX_MARKS));
        }
        this.marks = marks;
        calculateGradeAndPoint(); // Auto-calculate when marks change
    }
    
    public Grade getGrade() {
        return grade;
    }
    
    public Double getGPoint() {
        return gPoint;
    }
    
    // ============ BUSINESS LOGIC METHODS ============
    
    /**
     * Calculate grade and point from marks
     */
    private void calculateGradeAndPoint() {
        this.grade = Grade.fromMarks(marks);
        this.gPoint = grade.getGPoint();
    }
    
    /**
     * Check if result is passing grade
     */
    public boolean isPassingGrade() {
        return grade != Grade.F;
    }
    
    /**
     * Get grade description
     */
    public String getGradeDescription() {
        return grade.getDescription();
    }
    
    /**
     * Check if student got excellent result
     */
    public boolean isExcellent() {
        return grade == Grade.A;
    }
    
    /**
     * Get marks as percentage
     */
    public double getPercentage() {
        return marks;
    }
    
    /**
     * Get result status summary
     */
    public String getResultSummary() {
        return String.format(
            "Result: %d/100 -> Grade %s (%.1f) - %s [%s]",
            marks, grade.name(), gPoint, grade.getDescription(),
            isPassingGrade() ? "PASS" : "FAIL"
        );
    }
    
    // ============ UTILITY METHODS ============
    
    @Override
    public String toString() {
        return String.format(
            "Result{context=%s, marks=%d, grade=%s, point=%.1f}",
            context, marks, grade.name(), gPoint
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Result other = (Result) obj;
        return context.equals(other.context) && marks == other.marks;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(context, marks);
    }
}