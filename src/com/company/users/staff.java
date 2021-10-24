package src.com.company.users;
import java.util.Date;

interface staff extends user{
    public void orderStock(Date date, String order);
    public void takeStock(Date date, String stock, int id_num);
    public void takePayment();
}