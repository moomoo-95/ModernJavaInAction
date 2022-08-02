package moomoo.study.java.builder.chaining;

import moomoo.study.java.module.Order;
import moomoo.study.java.module.Trade;

public class MethodChainingOrderBuilder {

    public final Order order;

    private MethodChainingOrderBuilder(String customer) {
        order = new Order(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }

    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }

    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    public MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrades(trade);
        return this;
    }

    public Order end() {
        return order;
    }
}
