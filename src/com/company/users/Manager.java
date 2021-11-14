package com.company.users;

import com.company.Database;
import com.company.menu.Menu;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Scanner;

public class Manager extends Staff {
    Scanner scanner = new Scanner(System.in);

    public Manager(int idNum, String fullName, String email, String employeeType, double salary) {
        super.setIdNum(idNum);
        super.setFullName(fullName);
        super.setEmail(email);
        super.setUserType("employee");
        super.setEmployeeType(employeeType);
        super.setSalary(salary);
    }

    public void addStaffMember() {

    }

    public void viewStaffMember() {

    }

    public void removeStaffMember() {

    }

    public void editStaffMember(){
        // code
    }

    public Menu makeMenu(){
        System.out.println("What will you call the menu?");
        String name = scanner.nextLine();
        System.out.println("Describe the menu:");
        String description = scanner.nextLine();
        Menu menu = new Menu(name, description, LocalDate.now());

        JSONObject menuObj = new JSONObject();
        menuObj.put("name", name);
        menuObj.put("description", description);
        menuObj.put("date_created", menu.getDate());
        menuObj.put("set_menu_price", JSONObject.NULL);
        menuObj.put("discount", JSONObject.NULL);
        menuObj.put("two_for_one", JSONObject.NULL);

        Database.writeToDatabase("menu", menuObj);
        return menu;
    }



    public void editMenu(){
        // code
    }

    public void deleteMenu(){
        // code
    }

    public void payStaff(){
        // code
    }
}
