package com.idb.controller.helper;
 
import com.idb.model.Students;
import com.idb.model.Teachers;
import com.idb.utility.FileUploadUtility;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadService {
    
    private final FileUploadUtility fileUploadUtility;
    
    public FileUploadService(FileUploadUtility fileUploadUtility) {
        this.fileUploadUtility = fileUploadUtility;
    }
    
    /**
     * Upload student image if provided
     */
    public void uploadStudentImageIfProvided(HttpServletRequest request, Students students) {
        if (students.getS_img() != null && 
            students.getS_img().getOriginalFilename() != null &&
            !students.getS_img().getOriginalFilename().isEmpty()) {
            
            FileUploadUtility.uploadFileStudent(
                request, 
                students.getS_img(), 
                students.getS_name(), 
                students.getRoll_no()
            );
        }
    }
    
    /**
     * Upload teacher image if provided
     */
    public void uploadTeacherImageIfProvided(HttpServletRequest request, Teachers teachers) {
        if (teachers.getT_img() != null && 
            teachers.getT_img().getOriginalFilename() != null &&
            !teachers.getT_img().getOriginalFilename().isEmpty()) {
            
            FileUploadUtility.uploadFileTeaher(
                request, 
                teachers.getT_img(), 
                teachers.getT_name(), 
                teachers.getP_number()
            );
        }
    }
}