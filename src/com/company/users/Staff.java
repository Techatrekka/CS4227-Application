package com.company.users;

public class Staff extends User{
    private double salary;
    private String employeeType;

    public Staff() {}

    public Staff(int userID, String email, String fullName, String type, double salary) {
        super.setIdNum(userID);
        super.setEmail(email);
        super.setFullName(fullName);
        super.setUserType("employee");
        this.setEmployeeType(type);
        this.setSalary(salary);
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeType() {
        return this.employeeType;
    }

    public void orderStock(){
        // code
    }

    public String toString() {
        return "Employee id: " + super.getIdNum() + "\nEmployee Name: " + super.getFullName() + "\nEmployee type: " + getEmployeeType();
    }

}