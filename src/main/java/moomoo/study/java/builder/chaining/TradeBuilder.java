package moomoo.study.java.builder.chaining;

import moomoo.study.java.module.Trade;

public class TradeBuilder {
    private final MethodChainingOrderBuilder builder;
    public final Trade trade;

    public TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
        this.builder = builder;
        trade = new Trade(type);
        trade.setQuantity(quantity);
    }

    public StockBuilder stock(String symbol, String market) {
        return new StockBuilder(builder, trade, symbol, market);
    }
}
