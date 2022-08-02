package moomoo.study.java.builder;

import moomoo.study.java.module.Trade;

import java.util.function.Consumer;

public class TradeBuilder {
    private Trade trade;

    public void trade(Trade.Type type) {
        trade = new Trade(type);
    }

    public void quantity(int quantity) {
        trade.setQuantity(quantity);
    }

    public void price(double price) {
        trade.setPrice(price);
    }

    public void stock(Consumer<StockBuilder> consumer){
        StockBuilder builder = new StockBuilder();
        consumer.accept(builder);
        trade.setStock(builder.getStock());
    }

    public Trade getTrade() {
        return trade;
    }
}
