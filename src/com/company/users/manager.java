package src.com.company.users;

public class Manager extends Staff {
    private int id_num;
    private String full_name;
    private String email;
    private String user_type;

    protected void addStaffMember(int id_num, String full_name, String email, String user_type) {
        this.id_num = id_num;
        this.full_name = full_name;
        this.email = email;
        this.user_type = user_type;
    }

    public void editStaffMember(int id_num, String full_name, String email){
        // code
    }

    public void makeMenu(){
        // code
    }

    public void editMenu(int menu_id, String edits){
        // code
    }

    public void addMenuItem(int menu_id, int item_id, String item){
        // code
    }

    public void editMenuItem(int item_id){
        // code
    }

    public void payStaff(){
        // code
    }
}
