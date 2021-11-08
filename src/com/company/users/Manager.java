package com.company.users;

public class Manager extends Staff {
    private int idNum;
    private String fullName;
    private String email;
    private String userType;

    protected void addStaffMember(int idNum, String fullName, String email, String userType) {
        this.idNum = idNum;
        this.fullName = fullName;
        this.email = email;
        this.userType = userType;
    }

    public void editStaffMember(int idNum, String fullName, String email){
        // code
    }

    public void makeMenu(){
        // code
    }

    public void editMenu(int menuId, String edits){
        // code
    }

    public void addMenuItem(int menuId, int itemId, String item){
        // code
    }

    public void editMenuItem(int itemId){
        // code
    }

    public void payStaff(){
        // code
    }
}
