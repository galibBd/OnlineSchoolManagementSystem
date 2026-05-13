package com.idb.controller.helper;
 
import com.idb.model.Users;
import javax.servlet.http.HttpSession;

public class SessionDataHelper {
    
    /**
     * Add user information to session
     */
    public static void addUserToSession(Users users, HttpSession session) {
        if (users == null || session == null) {
            throw new IllegalArgumentException("Users and session cannot be null");
        }
        session.setAttribute("users", users);
        session.setAttribute("u_name", users.getU_name());
        session.setAttribute("r_id", users.getR_id());
    }
    
    /**
     * Get user from session
     */
    public static Users getUserFromSession(HttpSession session) {
        return (Users) session.getAttribute("users");
    }
    
    /**
     * Clear user session
     */
    public static void clearUserSession(HttpSession session) {
        session.removeAttribute("users");
        session.removeAttribute("u_name");
        session.removeAttribute("r_id");
    }
}