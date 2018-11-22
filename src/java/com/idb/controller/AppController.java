package com.idb.controller;

import com.idb.model.Attendance;
import com.idb.model.Classes;
import com.idb.model.NoticeBoard;
import com.idb.model.Result;
import com.idb.model.Role;
import com.idb.model.Semester;
import com.idb.model.Sessions;
import com.idb.model.Students;
import com.idb.model.Subjects;
import com.idb.model.Teachers;
import com.idb.model.Users;
import com.idb.service.AppService;
import com.idb.utility.FileUploadUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    AppService appService;

    private void addUserToSession(Users users, HttpSession session) {
        session.setAttribute("users", users);
        session.setAttribute("u_name", users.getU_name());
    }

    @RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public ModelAndView showadminHome(Users users, HttpSession session) {
        addUserToSession(users, session);
        ModelAndView model = new ModelAndView();
//        List<Users> users = appService.getAllUsers();
        List<NoticeBoard> notice = appService.getAllNotice();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
//        model.getModelMap().put("u_name", u_name);
        model.getModelMap().put("notice", notice);
        return model;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public ModelAndView showNoticeAdmin() {
        ModelAndView model = new ModelAndView();
        NoticeBoard noticeBoard = new NoticeBoard();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("notice", noticeBoard);
        model.getModelMap().put("nts", nts);
        return model;
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.GET)
    public ModelAndView showaNoticeForUpdate(@RequestParam("n_id") Integer n_id, NoticeBoard noticeBoard, HttpSession session) {
        ModelAndView model = new ModelAndView();
        List<NoticeBoard> notice = appService.getNoticeById(n_id);
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("notice", notice);
        return model;
    }

    @RequestMapping(value = "/noticeBoard", method = RequestMethod.GET)
    public ModelAndView showNoticeTeacher() {
        ModelAndView model = new ModelAndView();
        NoticeBoard noticeBoard = new NoticeBoard();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("notice", noticeBoard);
        model.getModelMap().put("nts", nts);
        return model;
    }

    @RequestMapping(value = "/notice_board", method = RequestMethod.GET)
    public ModelAndView showNoticeHome() {
        ModelAndView model = new ModelAndView();
        NoticeBoard noticeBoard = new NoticeBoard();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("notice", noticeBoard);
        model.getModelMap().put("nts", nts);
        return model;
    }
    
     @RequestMapping(value = "/notice-Board", method = RequestMethod.GET)
    public ModelAndView showNoticeStudentHome() {
        ModelAndView model = new ModelAndView();
        NoticeBoard noticeBoard = new NoticeBoard();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("notice", noticeBoard);
        model.getModelMap().put("nts", nts);
        return model;
    }

    @RequestMapping(value = "/teacherHome", method = RequestMethod.GET)
    public ModelAndView showTeacherHome() {
        ModelAndView model = new ModelAndView();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        Users users = new Users();
        model.getModelMap().put("teacherHome", users);
        return model;
    }

    @RequestMapping(value = "/attendance", method = RequestMethod.GET)
    public ModelAndView showAttendance() {
        ModelAndView model = new ModelAndView();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        return model;
    }

    @RequestMapping(value = "/add_attendance", method = RequestMethod.GET)
    public ModelAndView showAddAttendance(@RequestParam(value = "c_id", required = false) Integer c_id,
            @RequestParam(value = "sess_id", required = false) Integer sess_id,
            HttpSession session) {
        ModelAndView model = new ModelAndView();
        Integer cId = c_id;
        Integer sessId = sess_id;
        model.getModelMap().put("cId", cId);
        model.getModelMap().put("sessId", sessId);
        List<Students> stdList = new ArrayList<>();
        try {
            stdList = appService.getDailyAttendence(c_id, sess_id);
        } catch (Exception e) {
        }
        model.getModelMap().put("stdList", stdList);

        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);

        List<Sessions> sessions = appService.getAllsessions();
        model.getModelMap().put("sessions", sessions);

        List<Classes> classes = appService.getAllClasses();
        model.getModelMap().put("classes", classes);
        return model;
    }

    @RequestMapping(value = "/attendance_search", method = RequestMethod.GET)
    public ModelAndView getSearchAttendance(@RequestParam(value = "s_id", required = false) Integer s_id) {
        Integer sId = s_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);

        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);

        List<Students> std = new ArrayList<>();
        List<Attendance> attendance = new ArrayList<>();

        try {
            std = appService.getStudentByID(sId);
            attendance = appService.getAttendanceByS_ID(sId);

        } catch (Exception e) {
            System.out.println(e);
        }
        model.getModelMap().put("std", std);
        model.getModelMap().put("attendance", attendance);

        return model;
    }

    @RequestMapping(value = "/add_result", method = RequestMethod.GET)
    public ModelAndView showAddResultPage() {
        ModelAndView model = new ModelAndView();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);

        List<Semester> semester = appService.getAllSemester();
        model.getModelMap().put("semester", semester);

        List<Subjects> subject = appService.getAllSubjects();
        model.getModelMap().put("subject", subject);
        return model;
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView getResult() {
        ModelAndView model = new ModelAndView();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        return model;
    }

    @RequestMapping(value = "/result_search", method = RequestMethod.GET)
    public ModelAndView getSearchResult(@RequestParam(value = "sem_id", required = false) Integer sem_id,
            @RequestParam(value = "s_id", required = false) Integer s_id) {
        Integer sId = s_id;
        Integer semId = sem_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        model.getModelMap().put("semId", semId);

        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);

        List<Semester> semester = appService.getAllSemester();
        model.getModelMap().put("semester", semester);

        List<Semester> smtr = new ArrayList<>();
        List<Result> res = new ArrayList<>();
        List<Students> std = new ArrayList<>();

        try {
            res = appService.getResultByID(semId, sId);
            std = appService.getStudentByID(sId);
            smtr = appService.getSemesterByID(semId);
        } catch (Exception e) {
            System.out.println(e);
        }
        model.getModelMap().put("smtr", smtr);
        model.getModelMap().put("result", res);
        model.getModelMap().put("std", std);

        return model;
    }
    
    @RequestMapping(value = "/search_result", method = RequestMethod.GET)
    public ModelAndView getResultSearch(@RequestParam(value = "sem_id", required = false) Integer sem_id,
            @RequestParam(value = "s_id", required = false) Integer s_id) {
        Integer sId = s_id;
        Integer semId = sem_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        model.getModelMap().put("semId", semId);

        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);

        List<Semester> semester = appService.getAllSemester();
        model.getModelMap().put("semester", semester);

        List<Semester> smtr = new ArrayList<>();
        List<Result> res = new ArrayList<>();
        List<Students> std = new ArrayList<>();

        try {
            res = appService.getResultByID(semId, sId);
            std = appService.getStudentByID(sId);
            smtr = appService.getSemesterByID(semId);
        } catch (Exception e) {
            System.out.println(e);
        }
        model.getModelMap().put("smtr", smtr);
        model.getModelMap().put("result", res);
        model.getModelMap().put("std", std);

        return model;
    }
    

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ModelAndView getSession() {
        ModelAndView model = new ModelAndView();
        List<Sessions> sns = appService.getAllsessions();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        Sessions sessions = new Sessions();
        model.getModelMap().put("session", sessions);
        model.getModel().put("sns", sns);
        return model;
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ModelAndView getAllStudents() {
        ModelAndView model = new ModelAndView();
        List<Students> students = appService.getAllStudents();
        List<NoticeBoard> nts = appService.getAllNotice();
        List<Sessions> sessions = appService.getAllsessions();
        List<Classes> classes = appService.getAllClasses();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("sessions", sessions);
        model.getModelMap().put("classes", classes);
        model.getModel().put("students", students);
        return model;
    }

    @RequestMapping(value = "/studentsInfo", method = RequestMethod.GET)
    public ModelAndView getAllStudentInfo() {
        ModelAndView model = new ModelAndView();
        List<Students> students = appService.getAllStudents();
        List<NoticeBoard> nts = appService.getAllNotice();
        List<Sessions> sessions = appService.getAllsessions();
        List<Classes> classes = appService.getAllClasses();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("sessions", sessions);
        model.getModelMap().put("classes", classes);
        model.getModel().put("students", students);
        return model;
    }

    @RequestMapping(value = "/student_detail", method = RequestMethod.GET)
    public ModelAndView getStudentDetail(@RequestParam(value = "s_id", required = true) Integer s_id) {
        Integer sId = s_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        
        List<Students> student = new ArrayList();
        try {
            student = appService.getStudentByID(s_id);
        } catch (Exception e) {
        }
        model.getModelMap().put("student", student);
        return model;
    }
    
     @RequestMapping(value = "/update_student", method = RequestMethod.GET)
    public ModelAndView updateStudentDetail(@RequestParam(value = "s_id", required = true) Integer s_id) {
        Integer sId = s_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        
        List<Students> student = new ArrayList();
        try {
            student = appService.getStudentByID(s_id);
        } catch (Exception e) {
        }
        model.getModelMap().put("student", student);
        return model;
    }
    
     @RequestMapping(value = "/student_details", method = RequestMethod.GET)
    public ModelAndView getStudentDetails(@RequestParam(value = "s_id", required = true) Integer s_id) {
        Integer sId = s_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        
        List<Students> student = new ArrayList();
        try {
            student = appService.getStudentByID(s_id);
        } catch (Exception e) {
        }
        model.getModelMap().put("student", student);
        return model;
    }
    
    @RequestMapping(value = "/students_detail", method = RequestMethod.GET)
    public ModelAndView getStudentsDetail(@RequestParam(value = "s_id", required = true) Integer s_id) {
        Integer sId = s_id;
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        
        List<Students> student = new ArrayList();
        try {
            student = appService.getStudentByID(s_id);
        } catch (Exception e) {
        }
        model.getModelMap().put("student", student);
        return model;
    }

    @RequestMapping(value = "/student_information", method = RequestMethod.GET)
    public ModelAndView getAllStudentInformation() {
        ModelAndView model = new ModelAndView();
        List<Students> students = appService.getAllStudents();
        List<NoticeBoard> nts = appService.getAllNotice();
        List<Sessions> sessions = appService.getAllsessions();
        List<Classes> classes = appService.getAllClasses();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("sessions", sessions);
        model.getModelMap().put("classes", classes);
        model.getModel().put("students", students);
        return model;
    }
    
     @RequestMapping(value = "/students_information", method = RequestMethod.GET)
    public ModelAndView getStudentsInformation() {
        ModelAndView model = new ModelAndView();
        List<Students> students = appService.getAllStudents();
        List<NoticeBoard> nts = appService.getAllNotice();
        List<Sessions> sessions = appService.getAllsessions();
        List<Classes> classes = appService.getAllClasses();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("sessions", sessions);
        model.getModelMap().put("classes", classes);
        model.getModel().put("students", students);
        return model;
    }

    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public ModelAndView createclass() {
        ModelAndView model = new ModelAndView();
        List<Classes> cls = appService.getAllClasses();
//        List<Sessions> snsWcls = appService.getAllsessionsWithClass();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        Classes classes = new Classes();
        model.getModelMap().put("classes", classes);
        model.getModel().put("cls", cls);
//        model.getModel().put("snsWcls", snsWcls);
        return model;
    }

    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    public ModelAndView getSubjects() {
        ModelAndView model = new ModelAndView();
        List<Subjects> sub = appService.getAllSubjects();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        Subjects subjects = new Subjects();
        model.getModelMap().put("subjects", subjects);
        model.getModel().put("sns", sub);
        return model;
    }

    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public ModelAndView getTeachers() {
        ModelAndView model = new ModelAndView();
        List<Teachers> teachers = appService.getAllTeachers();
        List<NoticeBoard> nts = appService.getAllNotice();
        List<Sessions> sessions = appService.getAllsessions();
        List<Subjects> subjects = appService.getAllSubjects();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("sessions", sessions);
        model.getModelMap().put("subjects", subjects);
        model.getModel().put("teachers", teachers);
        return model;
    }
    
     @RequestMapping(value = "/all_teachers", method = RequestMethod.GET)
    public ModelAndView getAllTeachers() {
        ModelAndView model = new ModelAndView();
        List<Teachers> teachers = appService.getAllTeachers();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("teachers", teachers);
        return model;
    }
    
      @RequestMapping(value = "/teachers_info", method = RequestMethod.GET)
    public ModelAndView getTeachersInfo() {
        ModelAndView model = new ModelAndView();
        List<Teachers> teachers = appService.getAllTeachers();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("teachers", teachers);
        return model;
    }
    
      @RequestMapping(value = "/teacher_details", method = RequestMethod.GET)
    public ModelAndView getTeachersInfoById(@RequestParam(value ="t_id", required = false)Integer t_id) {
        
        ModelAndView model = new ModelAndView();
        List<Teachers> teachers = appService.getAllTeachers();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("teachers", teachers);
        List<Teachers> tcr = new ArrayList<>();
          try {
              tcr = appService.getTeachersById(t_id);
          } catch (Exception e) {
          }
          model.getModelMap().put("tcr", tcr);
        return model;
    }
    
       @RequestMapping(value = "/teachers_detail", method = RequestMethod.GET)
    public ModelAndView getTeacherInfoById(@RequestParam(value ="t_id", required = false)Integer t_id) {
        
        ModelAndView model = new ModelAndView();
        List<Teachers> teachers = appService.getAllTeachers();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("teachers", teachers);
        List<Teachers> tcr = new ArrayList<>();
          try {
              tcr = appService.getTeachersById(t_id);
          } catch (Exception e) {
          }
          model.getModelMap().put("tcr", tcr);
        return model;
    }
    
       @RequestMapping(value = "/teacher_detail", method = RequestMethod.GET)
    public ModelAndView getTeacherInformationById(@RequestParam(value ="t_id", required = false)Integer t_id) {      
        ModelAndView model = new ModelAndView();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        List<Teachers> tcr = new ArrayList<>();
          try {
              tcr = appService.getTeachersById(t_id);
          } catch (Exception e) {
          }
          model.getModelMap().put("tcr", tcr);
        return model;
    }

    @RequestMapping(value = "/teacher_detail", method = RequestMethod.POST)
    public ModelAndView updateTeacherInfo(Teachers teachers){
    ModelAndView model = new ModelAndView();
    appService.updateTeacherInfo(teachers);
    return model;
    }
    
    @RequestMapping(value = "/teachers", method = RequestMethod.POST)
    public String addTeacher(ModelAndView model, Teachers teachers, HttpServletRequest request) {

        if (!teachers.getT_img().getOriginalFilename().equals("")) {
            FileUploadUtility.uploadFileTeaher(request, teachers.getT_img(), teachers.getT_name(), teachers.getP_number());
        }
        appService.addTeacher(teachers);
        model.getModelMap().put("teachers", teachers);
        return "redirect:/teachers.htm";
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public String addStudents(ModelAndView model, Students students, HttpServletRequest request) {
        appService.addStudents(students);

        if (!students.getS_img().getOriginalFilename().equals("")) {
            FileUploadUtility.uploadFileStudent(request, students.getS_img(), students.getS_name(), students.getRoll_no());
        }
        model.getModelMap().put("students", students);
        return "redirect:/students.htm";
    }

    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    public String addsubject(ModelAndView model, Subjects subject) {
        appService.addSubject(subject);
        model.getModelMap().put("subject", subject);
        return "redirect:subject.htm";
    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public String addUser(ModelAndView model, Sessions sessions) {
        appService.addSession(sessions);
        model.getModelMap().put("session", sessions);
        return "redirect:session.htm";
    }

    @RequestMapping(value = "/classes", method = RequestMethod.POST)
    public String addClass(ModelAndView model, Classes classes) {
        appService.addClasses(classes);
        return "redirect:classes.htm";
    }

    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public String addNotice(ModelAndView model, NoticeBoard noticeBoard) {
        appService.addNotice(noticeBoard);
        model.getModelMap().put("classes", noticeBoard);
        return "redirect:notice.htm";
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.POST)
    public String updateNotice(ModelAndView model, NoticeBoard noticeBoard) {
        appService.updateNptice(noticeBoard.getN_id(), noticeBoard.getN_title(), noticeBoard.getN_description());
        model.getModelMap().put("classes", noticeBoard);
        return "redirect:notice.htm";
    }

//    @RequestMapping(value = "/result_search", method = RequestMethod.POST)
//    public String resultSearch(ModelAndView model, Result result) {
//        appService.getResultByID(result.getS_id());
////        model.getModelMap().put("result", res);
//        return "redirect:result_search.htm";
//    }
    @RequestMapping(value = "/add_attendance", method = RequestMethod.POST)
    public String dailyAttendance(ModelAndView model, Attendance attendance, @RequestParam(value = "c_id", required = false) Integer c_id,
            @RequestParam(value = "sess_id", required = false) Integer sess_id) {
        Integer cId = c_id;
        Integer sessId = sess_id;
        appService.addAttendance(attendance);
        model.getModelMap().put("attendance", attendance);
        return "redirect:add_attendance.htm?c_id=" + cId + "&sess_id=" + sessId;
    }

    @RequestMapping(value = "/user_registration", method = RequestMethod.POST)
    public String addUser(ModelAndView model, Users users) {
        appService.addUser(users);
        model.getModelMap().put("user_registration", users);
        return "redirect:login.htm";
    }

    @RequestMapping(value = "/add_result", method = RequestMethod.POST)
    public String addResult(ModelAndView model, Result result) {
        appService.addResult(result);
        model.getModelMap().put("result", result);
        return "redirect:add_result.htm";
    }

    @RequestMapping(value = "/studentHome", method = RequestMethod.GET)
    public ModelAndView showStudentHome() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("studentHome", users);
        return model;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView showHome() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("indes", users);
        return model;
    }
    
      @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public ModelAndView showAboutus() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        List<NoticeBoard> nts = appService.getAllNotice();
        model.getModelMap().put("nts", nts);
        model.getModelMap().put("indes", users);
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showloginForm() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        List<Role> roles = appService.getAllRole();
        model.getModelMap().put("login", users);
        model.getModelMap().put("roles", roles);
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processForm(ModelAndView model, Users users, HttpSession session) {
        List<Users> user = appService.checkUser(users.getU_name(), users.getPassword(), users.getR_id());

        if (user.size() > 0 && users.getR_id() == 1) {
            addUserToSession(users, session);
            return "redirect:adminHome.htm";
        }
        if (user.size() > 0 && users.getR_id() == 2) {
            addUserToSession(users, session);
            return "redirect:teacherHome.htm";
        }
        if (user.size() > 0 && users.getR_id() == 3) {
            addUserToSession(users, session);
            return "redirect:studentHome.htm";
        } else {
            return "redirect:user_registration.htm";
        }
    }

    @RequestMapping(value = "/user_registration", method = RequestMethod.GET)
    public ModelAndView showRegForm() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        List<Role> roles = appService.getAllRole();
//        List<Users> user = appService.grtAllUsers();
        model.getModelMap().put("user_registration", users);
        model.getModelMap().put("roles", roles);
        return model;
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView showSuccess() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        model.getModelMap().put("success", users);
        return model;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView showError() {
        ModelAndView model = new ModelAndView();
        Users users = new Users();
        model.getModelMap().put("error", users);
        return model;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index.htm";
    }
}
