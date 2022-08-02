package moomoo.study.java.builder.chaining;

import moomoo.study.java.module.Stock;
import moomoo.study.java.module.Trade;

public class StockBuilder {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock;

    public StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol, String market) {
        this.builder = builder;
        this.trade = trade;
        stock = new Stock(symbol, market);
        trade.setStock(stock);
    }

    public MethodChainingOrderBuilder at(double price) {
        trade.setPrice(price);
        return builder.addTrade(trade);
    }
}
