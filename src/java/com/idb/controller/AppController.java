package com.idb.controller;

import com.idb.controller.helper.*;

import com.idb.controller.helper.NoticeViewHelper;
import com.idb.controller.helper.ModelViewBuilder;
import com.idb.controller.helper.SessionDataHelper;
import com.idb.controller.helper.LoginService;
import com.idb.controller.helper.FileUploadService;
import com.idb.controller.helper.AttendanceRequest;
import com.idb.controller.helper.ResultRequest;

import com.idb.model.*;
import com.idb.service.AppService;
import com.idb.utility.FileUploadUtility;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * REFACTORED: AppController
 * 
 * Improvements:
 * - Extracted helper classes untuk reduce duplication
 * - Extracted data clumps (AttendanceRequest, ResultRequest)
 * - Extracted login logic
 * - Better method organization
 * - Reduced method length
 * - Better separation of concerns
 */
@Controller
public class AppController {

    @Autowired
    private AppService appService;
    
    private final NoticeViewHelper noticeHelper;
    private final LoginService loginService;
    private final FileUploadService fileUploadService;
    private final ModelViewBuilder modelBuilder;

    public AppController() {
        // Note: In real app, use dependency injection for these
        this.noticeHelper = null;
        this.loginService = null;
        this.fileUploadService = null;
        this.modelBuilder = null;
    }

    @PostConstruct
    public void init() {
        // Initialize helpers with autowired appService
        if (noticeHelper == null) {
            noticeHelper = new NoticeViewHelper(appService);
        }
        if (loginService == null) {
            loginService = new LoginService(appService);
        }
        if (fileUploadService == null) {
            fileUploadService = new FileUploadService(FileUploadUtility);
        }
        if (modelBuilder == null) {
            modelBuilder = new ModelViewBuilder(appService);
        }
    }

    // ============ ADMIN HOME ============
    
    @RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public ModelAndView showAdminHome(Users users, HttpSession session) {
        SessionDataHelper.addUserToSession(users, session);
        
        return modelBuilder
            .addNotices()
            .addObject("notice", appService.getAllNotice())
            .build();
    }

    // ============ NOTICE MANAGEMENT ============
    
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public ModelAndView showNoticeAdmin() {
        ModelAndView model = new ModelAndView();
        noticeHelper.setupNoticeFormView(model);
        return model;
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.GET)
    public ModelAndView showNoticeForUpdate(@RequestParam("n_id") Integer noticeId) {
        ModelAndView model = new ModelAndView();
        noticeHelper.setupNoticeUpdateView(model, noticeId);
        return model;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public String addNotice(NoticeBoard noticeBoard) {
        appService.addNotice(noticeBoard);
        return "redirect:notice.htm";
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.POST)
    public String updateNotice(NoticeBoard noticeBoard) {
        appService.updateNptice(noticeBoard.getN_id(), 
                               noticeBoard.getN_title(), 
                               noticeBoard.getN_description());
        return "redirect:notice.htm";
    }

    // ============ NOTICE BOARD (FOR DIFFERENT ROLES) ============
    // Consolidated 3 methods into 1 pattern
    
    @RequestMapping(value = {"/noticeBoard", "/notice_board", "/notice-Board"}, 
                   method = RequestMethod.GET)
    public ModelAndView showNoticeBoard() {
        return buildNoticeListView();
    }

    private ModelAndView buildNoticeListView() {
        ModelAndView model = new ModelAndView();
        noticeHelper.setupNoticeFormView(model);
        return model;
    }

    // ============ HOME PAGES ============
    
    @RequestMapping(value = "/teacherHome", method = RequestMethod.GET)
    public ModelAndView showTeacherHome() {
        return buildHomePageView("teacherHome");
    }

    @RequestMapping(value = "/studentHome", method = RequestMethod.GET)
    public ModelAndView showStudentHome() {
        return buildHomePageView("studentHome");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView showHome() {
        return buildHomePageView("index");
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public ModelAndView showAboutUs() {
        return buildHomePageView("aboutus");
    }

    private ModelAndView buildHomePageView(String viewName) {
        return modelBuilder
            .addNotices()
            .addObject(viewName, new Users())
            .setViewName(viewName)
            .build();
    }

    // ============ ATTENDANCE MANAGEMENT ============
    
    @RequestMapping(value = "/attendance", method = RequestMethod.GET)
    public ModelAndView showAttendance() {
        return modelBuilder
            .addNotices()
            .build();
    }

    @RequestMapping(value = "/add_attendance", method = RequestMethod.GET)
    public ModelAndView showAddAttendance(
            @RequestParam(value = "c_id", required = false) Integer classId,
            @RequestParam(value = "sess_id", required = false) Integer sessionId) {
        
        AttendanceRequest request = new AttendanceRequest(classId, sessionId);
        List<Students> studentList = appService.getDailyAttendence(
            request.getClassId(), 
            request.getSessionId()
        );

        return modelBuilder
            .addNotices()
            .addSessions()
            .addClasses()
            .addObject("cId", classId)
            .addObject("sessId", sessionId)
            .addObject("stdList", studentList)
            .build();
    }

    @RequestMapping(value = "/add_attendance", method = RequestMethod.POST)
    public String addAttendance(Attendance attendance,
            @RequestParam(value = "c_id", required = false) Integer classId,
            @RequestParam(value = "sess_id", required = false) Integer sessionId) {
        
        appService.addAttendance(attendance);
        return String.format("redirect:add_attendance.htm?c_id=%d&sess_id=%d", classId, sessionId);
    }

    @RequestMapping(value = "/attendance_search", method = RequestMethod.GET)
    public ModelAndView searchAttendance(@RequestParam(value = "s_id", required = false) Integer studentId) {
        List<Students> students = new ArrayList<>();
        List<Attendance> attendance = new ArrayList<>();

        try {
            students = appService.getStudentByID(studentId);
            attendance = appService.getAttendanceByS_ID(studentId);
        } catch (Exception e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        }

        return modelBuilder
            .addNotices()
            .addObject("sId", studentId)
            .addObject("std", students)
            .addObject("attendance", attendance)
            .build();
    }

    // ============ RESULT MANAGEMENT ============
    
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView showResultPage() {
        return modelBuilder
            .addNotices()
            .build();
    }

    @RequestMapping(value = "/add_result", method = RequestMethod.GET)
    public ModelAndView showAddResultPage() {
        return modelBuilder
            .addNotices()
            .addSemesters()
            .addSubjects()
            .build();
    }

    @RequestMapping(value = "/add_result", method = RequestMethod.POST)
    public String addResult(Result result) {
        appService.addResult(result);
        return "redirect:add_result.htm";
    }

    @RequestMapping(value = "/result_search", method = RequestMethod.GET)
    public ModelAndView searchResult(
            @RequestParam(value = "sem_id", required = false) Integer semesterId,
            @RequestParam(value = "s_id", required = false) Integer studentId) {
        
        ResultRequest request = new ResultRequest(semesterId, studentId);
        List<Result> results = new ArrayList<>();

        try {
            results = appService.getResultByID(
                request.getSemesterId(), 
                request.getStudentId()
            );
        } catch (Exception e) {
            System.out.println("Error fetching results: " + e.getMessage());
        }

        return modelBuilder
            .addNotices()
            .addObject("sId", studentId)
            .addObject("semId", semesterId)
            .addObject("result", results)
            .build();
    }

    // ============ STUDENT MANAGEMENT ============
    
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ModelAndView showStudents() {
        List<Students> studentList = appService.getAllStudents();

        return modelBuilder
            .addNotices()
            .addClasses()
            .addSessions()
            .addObject("students", studentList)
            .addObject("student", new Students())
            .build();
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public String addStudent(Students students, HttpServletRequest request) {
        appService.addStudents(students);
        fileUploadService.uploadStudentImageIfProvided(request, students);
        return "redirect:students.htm";
    }

    // ============ TEACHER MANAGEMENT ============
    
    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public ModelAndView showTeachers() {
        List<Teachers> teacherList = appService.getAllTeachers();

        return modelBuilder
            .addNotices()
            .addSubjects()
            .addObject("teachers", teacherList)
            .addObject("teacher", new Teachers())
            .build();
    }

    @RequestMapping(value = "/teachers", method = RequestMethod.POST)
    public String addTeacher(Teachers teachers, HttpServletRequest request) {
        appService.addTeacher(teachers);
        fileUploadService.uploadTeacherImageIfProvided(request, teachers);
        return "redirect:teachers.htm";
    }

    @RequestMapping(value = "/teacher_detail", method = RequestMethod.GET)
    public ModelAndView showTeacherDetail(@RequestParam(value = "t_id", required = false) Integer teacherId) {
        List<Teachers> teachers = new ArrayList<>();

        try {
            teachers = appService.getTeachersById(teacherId);
        } catch (Exception e) {
            System.out.println("Error fetching teacher: " + e.getMessage());
        }

        return modelBuilder
            .addNotices()
            .addObject("tcr", teachers)
            .build();
    }

    @RequestMapping(value = "/teacher_detail", method = RequestMethod.POST)
    public String updateTeacherInfo(Teachers teachers) {
        appService.updateTeacherInfo(teachers);
        return "redirect:teachers.htm";
    }

    // ============ CLASS MANAGEMENT ============
    
    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public ModelAndView showClasses() {
        List<Classes> classList = appService.getAllClasses();

        return modelBuilder
            .addNotices()
            .addSessions()
            .addObject("classes", classList)
            .addObject("class", new Classes())
            .build();
    }

    @RequestMapping(value = "/classes", method = RequestMethod.POST)
    public String addClass(Classes classes) {
        appService.addClasses(classes);
        return "redirect:classes.htm";
    }

    // ============ SUBJECT MANAGEMENT ============
    
    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    public ModelAndView showSubjects() {
        List<Subjects> subjectList = appService.getAllSubjects();

        return modelBuilder
            .addNotices()
            .addObject("subjects", subjectList)
            .addObject("subject", new Subjects())
            .build();
    }

    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    public String addSubject(Subjects subjects) {
        appService.addSubject(subjects);
        return "redirect:subject.htm";
    }

    // ============ SESSION MANAGEMENT ============
    
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ModelAndView showSessions() {
        List<Sessions> sessionList = appService.getAllsessions();

        return modelBuilder
            .addNotices()
            .addObject("sessions", sessionList)
            .addObject("session", new Sessions())
            .build();
    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public String addSession(Sessions sessions) {
        appService.addSession(sessions);
        return "redirect:session.htm";
    }

    // ============ USER & AUTHENTICATION ============
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm() {
        return modelBuilder
            .addRoles()
            .addObject("login", new Users())
            .build();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(Users users, HttpSession session) {
        try {
            String redirectPath = loginService.authenticateAndGetRedirectPath(users);
            if (!redirectPath.contains("registration")) {
                SessionDataHelper.addUserToSession(users, session);
            }
            return redirectPath;
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return "redirect:user_registration.htm";
        }
    }

    @RequestMapping(value = "/user_registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm() {
        return modelBuilder
            .addRoles()
            .addObject("user_registration", new Users())
            .build();
    }

    @RequestMapping(value = "/user_registration", method = RequestMethod.POST)
    public String registerUser(Users users) {
        appService.addUser(users);
        return "redirect:login.htm";
    }

    // ============ SYSTEM PAGES ============
    
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView showSuccess() {
        ModelAndView model = new ModelAndView("success");
        model.addObject("success", new Users());
        return model;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView showError() {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", new Users());
        return model;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        SessionDataHelper.clearUserSession(session);
        session.invalidate();
        return "redirect:index.htm";
    }
}