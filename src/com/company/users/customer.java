package src.com.company.users;

public class customer implements user{
    private int id_num;
    private String full_name;
    private String email;
    private String user_type;

    @Override
    public void addCustomer(int id_num, String full_name, String email) {
        this.id_num = id_num;
        this.full_name = full_name;
        this.email = email;
        this.user_type = "Customer";
    }
}

