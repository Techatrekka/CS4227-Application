package src.com.company.users;

public class manager implements staff {
    private int id_num;
    private String full_name;
    private String email;
    private String user_type;

    @Override
    public void addStaff(int id_num, String full_name, String email, String user_type) {
        this.id_num = id_num;
        this.full_name = full_name;
        this.email = email;
        this.user_type = user_type;
    }
}
