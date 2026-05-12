package com.idb.controller;

import com.idb.model.*;
import com.idb.service.AppService;
import com.idb.utility.FileUploadUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    AppService appService;

    private void addUserToSession(Users users, HttpSession session) {
        session.setAttribute("users", users);
        session.setAttribute("u_name", users.getU_name());
    }
    
    // Bantuan untuk menambahkan notice ke model (menghindari duplikasi)
    private void addCommonNoticeToModel(ModelAndView model) {
        model.getModelMap().put("nts", appService.getAllNotice());
    }

    @RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public ModelAndView showAdminHome(Users users, HttpSession session) {
        addUserToSession(users, session);
        ModelAndView model = new ModelAndView();
        addCommonNoticeToModel(model);
        model.getModelMap().put("notice", appService.getAllNotice());
        return model;
    }

    // Menggabungkan 4 routing duplikat menjadi 1 method
    @RequestMapping(value = {"/notice", "/noticeBoard", "/notice_board", "/notice-Board"}, method = RequestMethod.GET)
    public ModelAndView showNotice() {
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("notice", new NoticeBoard());
        addCommonNoticeToModel(model);
        return model;
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.GET)
    public ModelAndView showNoticeForUpdate(@RequestParam("n_id") Integer n_id, HttpSession session) {
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("notice", appService.getNoticeById(n_id));
        addCommonNoticeToModel(model);
        return model;
    }

    @RequestMapping(value = {"/teacherHome", "/studentHome", "/index", "/aboutus"}, method = RequestMethod.GET)
    public ModelAndView showCommonHome() {
        ModelAndView model = new ModelAndView();
        addCommonNoticeToModel(model);
        model.getModelMap().put("user", new Users()); 
        return model;
    }

    // Menggabungkan duplikasi di URL pencarian result
    @RequestMapping(value = {"/result_search", "/search_result"}, method = RequestMethod.GET)
    public ModelAndView getSearchResult(@RequestParam(value = "sem_id", required = false) Integer semId,
                                        @RequestParam(value = "s_id", required = false) Integer sId) {
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        model.getModelMap().put("semId", semId);
        addCommonNoticeToModel(model);
        model.getModelMap().put("semester", appService.getAllSemester());

        try {
            model.getModelMap().put("result", appService.getResultByID(semId, sId));
            model.getModelMap().put("std", appService.getStudentByID(sId));
            model.getModelMap().put("smtr", appService.getSemesterByID(semId));
        } catch (Exception e) {
            System.out.println("Error fetching result: " + e.getMessage());
        }
        return model;
    }

    // Menggabungkan duplikasi URL detail student
    @RequestMapping(value = {"/student_detail", "/update_student", "/student_details", "/students_detail"}, method = RequestMethod.GET)
    public ModelAndView getStudentDetail(@RequestParam(value = "s_id", required = true) Integer sId) {
        ModelAndView model = new ModelAndView();
        model.getModelMap().put("sId", sId);
        addCommonNoticeToModel(model);

        try {
            model.getModelMap().put("student", appService.getStudentByID(sId));
        } catch (Exception e) {
            System.out.println("Error fetching student: " + e.getMessage());
        }
        return model;
    }

    // Menggabungkan duplikasi URL information student
    @RequestMapping(value = {"/students", "/studentsInfo", "/student_information", "/students_information"}, method = RequestMethod.GET)
    public ModelAndView getAllStudents() {
        ModelAndView model = new ModelAndView();
        addCommonNoticeToModel(model);
        model.getModelMap().put("sessions", appService.getAllsessions());
        model.getModelMap().put("classes", appService.getAllClasses());
        model.getModel().put("students", appService.getAllStudents());
        return model;
    }

    // Menggabungkan duplikasi URL detail teacher
    @RequestMapping(value = {"/teacher_details", "/teachers_detail", "/teacher_detail"}, method = RequestMethod.GET)
    public ModelAndView getTeacherInfoById(@RequestParam(value ="t_id", required = false) Integer tId) {
        ModelAndView model = new ModelAndView();
        addCommonNoticeToModel(model);
        model.getModelMap().put("teachers", appService.getAllTeachers());

        try {
            model.getModelMap().put("tcr", appService.getTeachersById(tId));
        } catch (Exception e) {
            System.out.println("Error fetching teacher: " + e.getMessage());
        }
        return model;
    }

    // Menggabungkan duplikasi URL getAllTeachers
    @RequestMapping(value = {"/teachers", "/all_teachers", "/teachers_info"}, method = RequestMethod.GET)
    public ModelAndView getTeachers() {
        ModelAndView model = new ModelAndView();
        addCommonNoticeToModel(model);
        model.getModelMap().put("sessions", appService.getAllsessions());
        model.getModelMap().put("subjects", appService.getAllSubjects());
        model.getModel().put("teachers", appService.getAllTeachers());
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processForm(Users users, HttpSession session) {
        List<Users> user = appService.checkUser(users.getU_name(), users.getPassword(), users.getR_id());
        if (user.size() > 0) {
            addUserToSession(users, session);
            if (users.getR_id() == 1) return "redirect:adminHome.htm";
            if (users.getR_id() == 2) return "redirect:teacherHome.htm";
            if (users.getR_id() == 3) return "redirect:studentHome.htm";
        }
        return "redirect:user_registration.htm";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index.htm";
    }
    
    // Sisa method POST dan GET dasar lainnya dibiarkan setelah pembersihan dead code...
} 