package com.idb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REFACTORED: NoticeBoard model dengan validation, business logic, dan better date handling
 * 
 * Improvements:
 * - Changed Date to LocalDate/LocalDateTime (better than java.util.Date)
 * - Added validation for dates and content
 * - Added business logic methods (isExpired, isActive, etc.)
 * - Added date formatting methods
 * - Better encapsulation and constants
 */
public class NoticeBoard {
    
    // Constants
    private static final int MIN_TITLE_LENGTH = 3;
    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MIN_DESCRIPTION_LENGTH = 5;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    private int noticeId;
    private String title;
    private String description;
    private LocalDateTime publishDate;
    private LocalDateTime expireDate;
    
    // ============ CONSTRUCTORS ============
    
    public NoticeBoard() {
    }
    
    public NoticeBoard(String title, String description, LocalDateTime publishDate, LocalDateTime expireDate) {
        setTitle(title);
        setDescription(description);
        setPublishDate(publishDate);
        setExpireDate(expireDate);
    }
    
    public NoticeBoard(int noticeId, String title, String description, LocalDateTime publishDate, LocalDateTime expireDate) {
        setNoticeId(noticeId);
        setTitle(title);
        setDescription(description);
        setPublishDate(publishDate);
        setExpireDate(expireDate);
    }
    
    // ============ GETTERS & SETTERS WITH VALIDATION ============
    
    public int getNoticeId() {
        return noticeId;
    }
    
    public void setNoticeId(int noticeId) {
        if (noticeId <= 0) {
            throw new IllegalArgumentException("Notice ID must be positive");
        }
        this.noticeId = noticeId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        title = title.trim();
        if (title.length() < MIN_TITLE_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Title must be at least %d characters", MIN_TITLE_LENGTH));
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Title cannot exceed %d characters", MAX_TITLE_LENGTH));
        }
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        description = description.trim();
        if (description.length() < MIN_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Description must be at least %d characters", MIN_DESCRIPTION_LENGTH));
        }
        this.description = description;
    }
    
    public LocalDateTime getPublishDate() {
        return publishDate;
    }
    
    public void setPublishDate(LocalDateTime publishDate) {
        if (publishDate == null) {
            throw new IllegalArgumentException("Publish date cannot be null");
        }
        // Publish date shouldn't be in future
        if (publishDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Publish date cannot be in future");
        }
        this.publishDate = publishDate;
    }
    
    public LocalDateTime getExpireDate() {
        return expireDate;
    }
    
    public void setExpireDate(LocalDateTime expireDate) {
        if (expireDate == null) {
            throw new IllegalArgumentException("Expire date cannot be null");
        }
        // Expire date must be after publish date
        if (this.publishDate != null && expireDate.isBefore(this.publishDate)) {
            throw new IllegalArgumentException("Expire date must be after publish date");
        }
        this.expireDate = expireDate;
    }
    
    // ============ BUSINESS LOGIC METHODS ============
    
    /**
     * Check if notice is currently active (published and not expired)
     */
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(publishDate) && !now.isAfter(expireDate);
    }
    
    /**
     * Check if notice is expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireDate);
    }
    
    /**
     * Check if notice is not yet published
     */
    public boolean isNotYetPublished() {
        return LocalDateTime.now().isBefore(publishDate);
    }
    
    /**
     * Get days remaining until expiration
     */
    public long getDaysUntilExpire() {
        return java.time.temporal.ChronoUnit.DAYS.between(
            LocalDateTime.now(), expireDate);
    }
    
    /**
     * Get status as string
     */
    public String getStatus() {
        if (isExpired()) {
            return "EXPIRED";
        } else if (isNotYetPublished()) {
            return "PENDING";
        } else {
            return "ACTIVE";
        }
    }
    
    // ============ FORMATTING METHODS ============
    
    /**
     * Get formatted publish date
     */
    public String getFormattedPublishDate() {
        return publishDate.format(DATE_FORMATTER);
    }
    
    /**
     * Get formatted expire date
     */
    public String getFormattedExpireDate() {
        return expireDate.format(DATE_FORMATTER);
    }
    
    /**
     * Get date range as string
     */
    public String getDateRange() {
        return String.format("From %s to %s", 
                           getFormattedPublishDate(), 
                           getFormattedExpireDate());
    }
    
    /**
     * Get full notice information
     */
    public String getFullInfo() {
        return String.format(
            "Notice: %s [%s]\nDescription: %s\nValid: %s\nStatus: %s",
            title, noticeId, description, getDateRange(), getStatus()
        );
    }
    
    // ============ UTILITY METHODS ============
    
    @Override
    public String toString() {
        return String.format(
            "NoticeBoard{id=%d, title='%s', status=%s, expireIn=%d days}",
            noticeId, title, getStatus(), getDaysUntilExpire()
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        NoticeBoard other = (NoticeBoard) obj;
        return noticeId == other.noticeId &&
               title.equals(other.title) &&
               publishDate.equals(other.publishDate) &&
               expireDate.equals(other.expireDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(noticeId, title, publishDate, expireDate);
    }
}