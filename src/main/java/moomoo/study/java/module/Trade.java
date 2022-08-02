package moomoo.study.java.module;

public class Trade {
    public enum Type { BUY, SELL}
    private final Type type;

    private Stock stock;
    private int quantity;
    private double price;

    public Trade(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getValue() {
        return quantity * price;
    }
}
