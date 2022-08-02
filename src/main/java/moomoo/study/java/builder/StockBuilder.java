package moomoo.study.java.builder;

import moomoo.study.java.module.Stock;

public class StockBuilder {
    private Stock stock;

    public void stock(String symbol, String market){
        stock = new Stock(symbol, market);
    }

    public Stock getStock() {
        return stock;
    }
}
