/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idb.service;

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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Student
 */
public class AppServiceImp implements AppService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(Users users) {
        String sql = "insert into Users (u_name,password,r_id,email,phone) value(?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{users.getU_name(), users.getPassword(), users.getR_id(), users.getEmail(), users.getPhone()});
    }

    @Override
    public void addSession(Sessions sessions) {
        String sql = "insert into session(sess_year) value(?)";
        jdbcTemplate.update(sql, new Object[]{sessions.getSess_year()});
    }

    @Override
    public void addClasses(Classes classes) {
        String sql = "insert into class (c_name) value(?)";
        jdbcTemplate.update(sql, new Object[]{classes.getC_name()});
    }

    @Override
    public void addNotice(NoticeBoard noticeBoard) {
        String sql = "insert into notice (n_title,n_description,publish_date) value(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{noticeBoard.getN_title(), noticeBoard.getN_description(), noticeBoard.getPublish_date()});
    }

    @Override
    public List<Users> checkUser(String name, String password, int r_id) {
        Users users = new Users();
        String sql = "select u_name, password,r_id from Users where u_name='" + name + "' and password = '" + password + "' and r_id=" + r_id;
        List user = jdbcTemplate.queryForList(sql);
        return user;
    }

//    @Override
//    public List<Users> grtAllUsers() {
//        String sql = "select name,password,email from users";
//        List user = jdbcTemplate.queryForList(sql);
//        return user;
//    }
    //    public static void main(String[] args) {
//        new AppServiceImp().add(new Users("a","aa","aaa"));
//    }
    @Override
    public List<Role> getAllRole() {
        String sql = "select * from role";
        List roles = jdbcTemplate.queryForList(sql);
        return roles;
    }

    @Override
    public List<NoticeBoard> getAllNotice() {
        String sql = "select * from notice ORDER BY n_id desc";
        List notice = jdbcTemplate.queryForList(sql);
        return notice;
    }

    @Override
    public List<Sessions> getAllsessions() {
        String sql = "select * from session ";
        List notice = jdbcTemplate.queryForList(sql);
        return notice;
    }

//    @Override
//    public List<Sessions> getAllsessionsWithClass() {
//        String sql = "select s.sess_id,s.sess_year,c.c_name from session s, class c where c.sess_id = s.sess_id";
//        List notice = jdbcTemplate.queryForList(sql);
//        return notice;
//    }
    @Override
    public List<Classes> getAllClasses() {
        String sql = "select DISTINCT * from class";
        List classes = jdbcTemplate.queryForList(sql);
        return classes;
    }

    @Override
    public List<Students> getAllStudents() {
        String sql = "select * from student_info";
        List students = jdbcTemplate.queryForList(sql);
        return students;
    }

    @Override
    public void addStudents(Students students) {
//        try {
        String sql = "insert into student_info ("
                + "s_name,"
                + "c_id,"
                + "sess_id,"
                + "roll_no,"
                + "gender,"
                + "dob,"
                + "age,"
                + "f_name,"
                + "m_name,"
                + "telephone_no,"
                + "mobile_no,"
                + "present_address,"
                + "permanent_address"
                + ") value(?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ")";
        jdbcTemplate.update(sql, new Object[]{
            students.getS_name(),
            students.getC_id(),
            students.getSess_id(),
            students.getRoll_no(),
            students.getGender(),
            students.getDob(),
            students.getAge(),
            students.getF_name(),
            students.getM_name(),
            students.getTelephone_no(),
            students.getMobile_no(),
            students.getPresent_address(),
            students.getParmanent_address()
        });
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }

    @Override
    public void addSubject(Subjects subjects) {
        String sql = "insert into subject (sub_name) value(?)";
        jdbcTemplate.update(sql, new Object[]{subjects.getSub_name()});
    }

    @Override
    public List<Subjects> getAllSubjects() {
        String sql = "select * from subject";
        List subjects = jdbcTemplate.queryForList(sql);
        return subjects;
    }

    @Override
    public List<Teachers> getAllTeachers() {
        String sql = "select t.t_id,t.t_name,t.t_email,t.gender,t.phone_number,t.address,sub.sub_name,t.join_date from teacher_info t, subject sub where t.sub_id = sub.sub_id ";
        List subjects = jdbcTemplate.queryForList(sql);
        return subjects;
    }

    @Override
    public void addTeacher(Teachers teachers) {
        String sql = "insert into teacher_info (t_name,t_email,gender,phone_number,address,sub_id,join_date) value(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{teachers.getT_name(), teachers.getT_email(), teachers.getGender(),
            teachers.getP_number(), teachers.getT_address(), teachers.getSub_id(), teachers.getJoin_date()});
    }

    @Override
    public List<Users> getAllUsers() {
        String sql = "select * from users";
        List users = jdbcTemplate.queryForList(sql);
        return users;
    }

    @Override
    public List<Students> getDailyAttendence(int c_id, int sess_id) {
        String sql = "SELECT s_id, s_name, roll_no FROM student_info s where c_id= " + c_id + " and sess_id= " + sess_id + " order by roll_no asc";
        List attendance = jdbcTemplate.queryForList(sql);
        return attendance;
    }

    @Override
    public void addAttendance(Attendance attendance) {
        String sql = "insert into student_attendance (sa_date,s_id,status) value(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{attendance.getSa_date(), attendance.getS_id(), attendance.getStatus()});
    }

    @Override
    public List<NoticeBoard> getNoticeById(int n_id) {
        String sql = "select * from notice where n_id=" + n_id;
        List notice = jdbcTemplate.queryForList(sql);
        return notice;
    }

    @Override
    public void updateNptice(int n_id, String n_title, String n_description) {
        String SQL = "update notice set n_title = '" + n_title + "' , n_description = '" + n_description + "'  where n_id = " + n_id;
        jdbcTemplate.update(SQL);
    }
    
    @Override
    public void updateTeacherInfo(Teachers teachers) {
        String SQL = "update teacher_info set t_name = '" + teachers.getT_name() + "' , t_email = '" + teachers.getT_email() 
                + "' , gender = '" + teachers.getGender() + "' , phone_number = " + teachers.getP_number()
                + " , address = '" + teachers.getT_address() + "' , join_date = '" + teachers.getJoin_date()
                + "'   where t_id = " + teachers.getT_id();
        jdbcTemplate.update(SQL);
    }

    @Override
    public List<Semester> getAllSemester() {
        String sql = "select * from semester";
        List semester = jdbcTemplate.queryForList(sql);
        return semester;
    }

    @Override
    public void addResult(Result result) {
        String sql = "insert into result (sem_id,s_id,sub_id,marks,g_point,grade) value(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{result.getSem_id(), result.getS_id(), result.getSub_id(), result.getMarks(), result.getgPoint(), result.getGrade()});
    }

    @Override
    public List<Result> getResultByID(int sem_id, int s_id) {

        String sql = "SELECT s.s_name , s.roll_no , sess.sess_year , c.c_name , sub.sub_name , sem.sem_name , r.marks , r.g_point , r.grade "
                + "FROM result r , student_info s , class c , semester sem , subject sub , session sess "
                + "where r.s_id = s.s_id "
                + "and sem.sem_id = r.sem_id "
                + "and c.c_id = s.c_id "
                + "and sub.sub_id = r.sub_id "
                + "and s.sess_id = sess.sess_id "
                + "and r.s_id = " + s_id + " "
                + " and r.sem_id = " + sem_id + " "
                + " order by sub.sub_id asc";
        List result = jdbcTemplate.queryForList(sql);
        return result;

    }

    @Override
    public List<Students> getStudentByID(int s_id) {
        String sql = "select * from student_info where s_id= " + s_id;
        List student = jdbcTemplate.queryForList(sql);
        return student;
    }

    @Override
    public List<Semester> getSemesterByID(int sem_id) {
        String sql = "select * from semester where sem_id= " + sem_id;
        List student = jdbcTemplate.queryForList(sql);
        return student;
    }

    @Override
    public List<Attendance> getAttendanceByS_ID(int s_id) {
    String sql = "select * from student_attendance where s_id= " + s_id+ " order by sa_date asc";
        List attendance = jdbcTemplate.queryForList(sql);
        return attendance;
    }

    @Override
    public List<Teachers> getTeachersById(int t_id) {
     String sql = "select t.t_id,t.t_name,t.t_email,t.gender,t.phone_number,t.address,sub.sub_name,t.join_date from teacher_info t, subject sub where t.sub_id = sub.sub_id  and t.t_id= "+t_id;
        List teacher = jdbcTemplate.queryForList(sql);
        return teacher;
    }
}
