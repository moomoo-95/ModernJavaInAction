package moomoo.study.java.builder;

import moomoo.study.java.module.Order;
import moomoo.study.java.module.Trade;

import java.util.function.Consumer;

public class LambdaOrderBuilder {
    private Order order;

    public static Order order(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder);
        return builder.order;
    }

    public void forCustomer(String customer) {
        order = new Order(customer);
    }

    public void buy(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.BUY);
    }

    public void sell(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.SELL);
    }

    private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade(type);
        consumer.accept(builder);
        order.addTrades(builder.getTrade());
    }
}
