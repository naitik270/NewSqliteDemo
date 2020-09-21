package com.example.myapplication.Classes;

public class ClsRegistrationGetSet {


    private int id;
    private String reg_photo;
    private String reg_name;
    private String reg_phone;
    private String reg_email;


    public ClsRegistrationGetSet(int id, String reg_photo, String reg_name, String reg_phone, String reg_email) {
        this.id = id;
        this.reg_photo = reg_photo;
        this.reg_name = reg_name;
        this.reg_phone = reg_phone;
        this.reg_email = reg_email;
    }

    ClsRegistrationGetSet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReg_photo() {
        return reg_photo;
    }

    public void setReg_photo(String reg_photo) {
        this.reg_photo = reg_photo;
    }

    public String getReg_name() {
        return reg_name;
    }

    public void setReg_name(String reg_name) {
        this.reg_name = reg_name;
    }

    public String getReg_phone() {
        return reg_phone;
    }

    public void setReg_phone(String reg_phone) {
        this.reg_phone = reg_phone;
    }

    public String getReg_email() {
        return reg_email;
    }

    public void setReg_email(String reg_email) {
        this.reg_email = reg_email;
    }
}
