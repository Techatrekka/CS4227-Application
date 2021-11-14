package com.company.users;

import com.company.Database;
import com.company.menu.Menu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit;

public class Manager extends Staff {
    Scanner scanner = new Scanner(System.in);

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
        System.out.println("Enter their email");
        String email = scanner.nextLine();
        JSONObject existingUser = Database.readFromUserTable(email, null);
        while(existingUser.has("email") && Objects.equals(existingUser.getString("email"), email)) {
            System.out.println("Sorry, that email has already been used to register an account. Please use a different one or login if this is your account.");
            email = scanner.nextLine();
            existingUser = Database.readFromUserTable(email, null);
        }
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
        JSONArray allStaff = Database.readAllfromTable("user", -1, "user_type", "employee");

        System.out.println("List of Employees: ");
        for (Object obj : allStaff){
            JSONObject obj2 = (JSONObject)obj;
            List<String> salaryList = new ArrayList<String>();
            salaryList.add("salary");
            salaryList.add("employee_type");
            JSONObject salary = Database.readFromTable("employeesalary", obj2.getInt("user_id"), salaryList);
            System.out.println(obj2.get("user_id") + ": " + obj2.get("fullname") + " Salary: " + salary.get("salary") + " Position: " + salary.get("employee_type"));
        }
    }

    public void removeStaffMember() {
        viewStaffMember();

        System.out.println("Enter the ID number of the employee you want to delete");
        int idNum = scanner.nextInt();
        while (idNum == this.getIdNum()){
            System.out.println("You can not delete yourself, enter new ID number");
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

        if (Database.updateTable("user", editedStaff)){
            System.out.println("User salary edited successfully");
        }else{
            System.out.println("User salary was not edited");
        }
    }

    public Menu makeMenu(){
        JSONObject menuObj = new JSONObject();
        System.out.println("What will you call the menu?");
        String name = scanner.nextLine();
        System.out.println("Describe the menu:");
        String description = scanner.nextLine();
        System.out.println("Is this a set menu? y/n");
        String specialMenu = scanner.nextLine();
        if(specialMenu.toUpperCase().equals("Y")){
            System.out.println("How much does it cost?");
            specialMenu = scanner.nextLine();
            menuObj.put("set_menu_price", specialMenu);
            menuObj.put("discount", "0.0");
            menuObj.put("two_for_one", false);
        }else{
            System.out.println("Does this menu have a discount on it? y/n");
            specialMenu = scanner.nextLine();
            if(specialMenu.toUpperCase().equals("Y")){
                menuObj.put("set_menu_price", "0.0");
                menuObj.put("discount", specialMenu);
                menuObj.put("two_for_one", false);
            }else{
                menuObj.put("set_menu_price", "0.0");
                menuObj.put("discount", "0.0");
                menuObj.put("two_for_one", true);
            }
        }
        Menu menu = new Menu(name, description, LocalDate.now());

        menuObj.put("name", name);
        menuObj.put("description", description);
        menuObj.put("date_created", menu.getDate());
        System.out.println(menuObj);
        Database.writeToTable("menu", menuObj);
        return menu;
    }

    public void editMenu(){
        int menuID = super.viewMenu();

        String newName = askAboutAttr("name");
        String newDes = askAboutAttr("description");

        JSONObject editedMenu = new JSONObject();
        
        if(newName != ""){
            editedMenu.put("name", newName);
        }
        if(newDes != ""){
            editedMenu.put("description", newDes);
        }

        if(newName != "" || newDes != ""){
            editedMenu.put("menu_id", menuID);

            if (Database.updateTable("menu", editedMenu)){
                System.out.println("Menu edited successfully");
            }else{
                System.out.println("Menu was not edited");
            }
        }
    }

    public void deleteMenu(){
        super.viewMenu();

        System.out.println("Enter the ID number of the Menu you want to delete");
        int idNum = scanner.nextInt();
        if (Database.deleteFromTable("menu", "menu_id", idNum)){
            System.out.println("Menu deleted successfully");
        }else{
            System.out.println("Menu was not deleted");
        }
    }

    public void payStaff(){
        // code
    }

    public String askAboutAttr(String attribute){
        String newAttribute = "";
        System.out.println("Do you want to change the menu " + attribute + "? y/n");
        String input = scanner.nextLine();
        if(input.toUpperCase().equals("Y")){
            System.out.println("Enter the new menu " + attribute);
            newAttribute = scanner.nextLine();
        }
        return newAttribute;
    }
}
