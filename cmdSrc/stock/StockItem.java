package stock;

import org.json.JSONObject;

import java.time.LocalDate;

public class StockItem extends StockComponent {
    private String name;
    private int count;
    private LocalDate expiryDate;
    private boolean isFood;
    private int id;

    public StockItem(JSONObject details) {
        this.name = details.getString("name");
        this.count = details.getInt("count");
        this.expiryDate = LocalDate.parse(details.getString("expiry_date"));
        this.isFood = details.getBoolean("isFood");
        this.id = details.getInt("stock_item_id");
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return  count;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    int getId() {
        return this.id;
    }

    public String show() {
        return "\n" + getName() +
                "\n--Count: " + getCount() + "\n" +
                "--Expiry Date: " + getExpiryDate() +
                "\n--Id: " + getId() + "\n";
    }
}
