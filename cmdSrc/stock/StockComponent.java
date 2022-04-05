package stock;

import java.time.LocalDate;

public abstract class StockComponent {
    public void add(StockComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(StockComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public StockComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public int getCount() {
        throw new UnsupportedOperationException();
    }

    public String show() {
        throw new UnsupportedOperationException();
    }
}
