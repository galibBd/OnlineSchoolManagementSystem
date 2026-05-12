package com.idb.controller;

import com.idb.model.Role;
import com.idb.model.Users;
import com.idb.service.AppService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @Autowired
    private AppService appService;

    private void addUserToSession(Users users, HttpSession session) {
        session.setAttribute("users", users);
        session.setAttribute("u_name", users.getU_name());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm() {
        ModelAndView model = new ModelAndView("login");
        model.addObject("login", new Users());
        model.addObject("roles", appService.getAllRole());
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(Users users, HttpSession session) {
        List<Users> userList = appService.checkUser(users.getU_name(), users.getPassword(), users.getR_id());

        if (!userList.isEmpty()) {
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
}