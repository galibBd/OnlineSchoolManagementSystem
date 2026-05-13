package com.idb.controller.helper;
 
import com.idb.model.Sessions;
import com.idb.model.Classes;
import com.idb.model.Semester;
import com.idb.model.Subjects;
import com.idb.model.Role;
import com.idb.service.AppService;
import org.springframework.web.servlet.ModelAndView;

public class ModelViewBuilder {
    
    private final ModelAndView model;
    private final AppService appService;
    
    public ModelViewBuilder(AppService appService) {
        this.model = new ModelAndView();
        this.appService = appService;
    }
    
    public ModelViewBuilder addNotices() {
        List<NoticeBoard> notices = appService.getAllNotice();
        model.addObject("nts", notices);
        return this;
    }
    
    public ModelViewBuilder addSessions() {
        List<Sessions> sessions = appService.getAllsessions();
        model.addObject("sessions", sessions);
        return this;
    }
    
    public ModelViewBuilder addClasses() {
        List<Classes> classes = appService.getAllClasses();
        model.addObject("classes", classes);
        return this;
    }
    
    public ModelViewBuilder addSemesters() {
        List<Semester> semesters = appService.getAllSemester();
        model.addObject("semester", semesters);
        return this;
    }
    
    public ModelViewBuilder addSubjects() {
        List<Subjects> subjects = appService.getAllSubjects();
        model.addObject("subject", subjects);
        return this;
    }
    
    public ModelViewBuilder addRoles() {
        List<Role> roles = appService.getAllRole();
        model.addObject("roles", roles);
        return this;
    }
    
    public ModelViewBuilder addObject(String key, Object value) {
        model.addObject(key, value);
        return this;
    }
    
    public ModelViewBuilder setViewName(String viewName) {
        model.setViewName(viewName);
        return this;
    }
    
    public ModelAndView build() {
        return model;
    }
}