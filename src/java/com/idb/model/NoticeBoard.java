package com.idb.model;

import java.util.Date;

public class NoticeBoard {

    private int n_id;
    private String n_title;
    private String n_description;
    private Date publish_date;
    private Date expire_date;

    public int getN_id() {
        return n_id;
    }

    public void setN_id(int n_id) {
        this.n_id = n_id;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_description() {
        return n_description;
    }

    public void setN_description(String n_description) {
        this.n_description = n_description;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }
    
    

}
