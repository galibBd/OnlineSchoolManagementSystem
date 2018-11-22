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

/**
 *
 * @author Student
 */
public interface AppService {
    
      public void addUser(Users users);
      public void addSession(Sessions sessions);    
      public void addClasses(Classes classes);
      public void addNotice(NoticeBoard noticeBoard );
      public void addStudents(Students students);
      public void addSubject(Subjects subjects);
      public void addTeacher(Teachers teachers);
      public void addAttendance(Attendance attendance);
      public void addResult(Result result);

      
      
      
      public List<Users> checkUser(String name, String password, int r_id);
      public List<Users> getAllUsers();      
      public List<NoticeBoard> getAllNotice();
      public List<NoticeBoard> getNoticeById(int n_id);   
      public List<Role> getAllRole();
      public List<Sessions> getAllsessions();
      public List<Subjects> getAllSubjects();
      public List<Classes> getAllClasses();
      public List<Students> getAllStudents();
      public List<Teachers> getAllTeachers();
      public List<Students> getDailyAttendence(int c_id, int sess_id);
      public List<Semester> getAllSemester();
      public List<Result> getResultByID(int sem_id,int s_id);
      public List<Students> getStudentByID(int s_id);
      public List<Semester> getSemesterByID(int sem_id);
      public List<Attendance> getAttendanceByS_ID(int s_id);
      public List<Teachers> getTeachersById(int t_id);

      
      public void updateNptice(int n_id, String n_title, String n_description);
      public void updateTeacherInfo(Teachers teachers);

    
      
}
