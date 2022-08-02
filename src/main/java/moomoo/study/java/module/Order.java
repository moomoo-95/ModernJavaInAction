package moomoo.study.java.module;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String customer;
    private List<Trade> trades = new ArrayList<>();

    public Order(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public void addTrades(Trade trade) {
        trades.add(trade);
    }

    public double getValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}
