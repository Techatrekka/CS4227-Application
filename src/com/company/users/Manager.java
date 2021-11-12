package com.company.users;

public class Manager extends Staff {

    public Manager(int idNum, String fullName, String email, String employeeType, double salary) {
        super.setIdNum(idNum);
        super.setFullName(fullName);
        super.setEmail(email);
        super.setUserType("employee");
        super.setEmployeeType(employeeType);
        super.setSalary(salary);
    }

    protected void addStaffMember(int idNum, String fullName, String email, String employeeType) {

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
