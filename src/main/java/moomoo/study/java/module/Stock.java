package moomoo.study.java.module;

public class Stock {
    private final String symbol;
    private final String market;

    public Stock(String symbol, String market) {
        this.symbol = symbol;
        this.market = market;
    }

    public String getSymbol() {
        return symbol;
    }


    public String getMarket() {
        return market;
    }

}
