package com.company.users;

import com.company.restaurant.Database;
import com.company.menu.Menu;
import com.company.menu.MenuFactory;

import com.company.ui.UiUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Manager extends Staff {
    private Scanner scanner = new Scanner(System.in);

    public Manager(int idNum, String email, String fullName, String employeeType, double salary) {
        super.setIdNum(idNum);
        super.setFullName(fullName);
        super.setEmail(email);
        super.setUserType("employee");
        super.setEmployeeType(employeeType);
        super.setSalary(salary);
    }

    public void addStaffMember() {
        System.out.println("What is the Staff member's full name?");
        String fullName = scanner.nextLine();
        String email = UiUtils.getEmail();
        System.out.println("Enter temporary password");
        String password = scanner.nextLine();
        System.out.println("What is their salary? EG: 25000.0");
        Double salary = scanner.nextDouble();

        JSONObject staffDetails = new JSONObject();
        staffDetails.put("fullname", fullName);
        staffDetails.put("email", email);
        staffDetails.put("password", password);
        staffDetails.put("user_type", "employee");
        Database.writeToTable("user", staffDetails);

        JSONObject staffID = Database.readFromUserTable(email, null);

        JSONObject newStaffSalary = new JSONObject();
        newStaffSalary.put("user_id", staffID.get("user_id"));
        newStaffSalary.put("employee_type", "clerk");
        newStaffSalary.put("salary", salary);

        Database.writeToTable("employeesalary", newStaffSalary);
    }

    public void viewStaffMember() {
        JSONArray allStaff = Database.readAllFromTable("user", -1, "user_type", "employee");

        System.out.println("List of Employees: ");
        for (Object obj : allStaff){
            JSONObject obj2 = (JSONObject)obj;
            List<String> salaryList = new ArrayList<>();
            salaryList.add("salary");
            salaryList.add("employee_type");
            JSONObject salary = Database.readFromTable("employeesalary", obj2.getInt("user_id"), salaryList, "user_id");
            System.out.printf("\tID: %s\t\tName: %s\n\t\t\t\tSalary: €%s\t Position: %s\n", obj2.get("user_id"), obj2.get("fullname"), salary.get("salary"), salary.get("employee_type"));
        }
    }

    public void removeStaffMember() {
        viewStaffMember();

        System.out.println("Enter the ID number of the employee you want to delete");
        int idNum = scanner.nextInt();
        while (idNum == this.getIdNum()){
            System.out.println("You cannot delete yourself, enter a different ID number");
            idNum = scanner.nextInt();
        }
        if (Database.deleteFromTable("user", "user_id", idNum)){
            System.out.println("User deleted successfully");
        }else{
            System.out.println("User was not deleted");
        }
    }

    public void editStaffSalary(){
        viewStaffMember();

        System.out.println("Enter the ID number of the employee whose salary you want to change");
        int idNum = scanner.nextInt();
        System.out.println("Enter new salary amount EG: 25000.0");
        double newSalary = scanner.nextDouble();

        JSONObject editedStaff = new JSONObject();
        editedStaff.put("salary", newSalary);
        editedStaff.put("user_id", idNum);

        if (Database.updateTable("employeesalary", editedStaff)){
            System.out.println("User salary edited successfully");
        }else{
            System.out.println("User salary was not edited");
        }
    }

    public Menu makeMenu(){
        JSONObject menuObj = new JSONObject();
        System.out.println("What will you call the menu? Press B to go back");
        String name = scanner.nextLine();
        if(UiUtils.inputB(name)) return null;
        System.out.println("Describe the menu:");
        String description = scanner.nextLine();
        System.out.println("Is this a set menu? y/n");
        String menuType = scanner.nextLine();
        if(menuType.equalsIgnoreCase("Y")){
            System.out.println("How much does it cost?");
            menuType = scanner.nextLine();
            menuObj.put("set_menu_price", menuType);
            menuObj.put("discount", "0.0");
        } else {
            System.out.println("Does this menu have a discount on it? y/n");
            menuType = scanner.nextLine();
            if(menuType.equalsIgnoreCase("Y")){
                System.out.println("How much of a discount does this menu have? Enter a number for the percentage discount");
                menuType = scanner.nextLine();
                menuObj.put("discount", menuType);
            } else {
                menuObj.put("discount", "0.0");
            }
            menuObj.put("set_menu_price", "0.0");
        }
        menuObj.put("name", name);
        menuObj.put("description", description);
        menuObj.put("date_created", LocalDate.now());
        menuObj.put("menu_items", JSONObject.NULL);
        int id = Database.writeToTable("menu", menuObj);
        return new Menu(id, name, description, LocalDate.now(), "");
    }

    public void deleteMenu(int menuID){
        if (Database.deleteFromTable("menu", "menu_id", menuID)){
            System.out.println("Menu deleted successfully");
        }else{
            System.out.println("Menu was not deleted");
        }
    }

    public void payStaff(){
        // Use case not implemented
    }
}
