package com.idb.model;

import com.mysql.jdbc.Blob;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class Students {

    private int s_id, c_id,sess_id, roll_no, age, telephone_no, mobile_no;
    private String s_name,gender, f_name, m_name, present_address, Parmanent_address,img_url;
    private Date dob;
    private MultipartFile s_img;

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getSess_id() {
        return sess_id;
    }

    public void setSess_id(int sess_id) {
        this.sess_id = sess_id;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTelephone_no() {
        return telephone_no;
    }

    public void setTelephone_no(int telephone_no) {
        this.telephone_no = telephone_no;
    }

    public int getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(int mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public String getParmanent_address() {
        return Parmanent_address;
    }

    public void setParmanent_address(String Parmanent_address) {
        this.Parmanent_address = Parmanent_address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public MultipartFile getS_img() {
        return s_img;
    }

    public void setS_img(MultipartFile s_img) {
        this.s_img = s_img;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    
}
