package moomoo.study.java.builder;

import moomoo.study.java.module.Order;
import moomoo.study.java.module.Stock;
import moomoo.study.java.module.Trade;

import java.util.stream.Stream;

public class NestedFunctionOrderBuilder {

    public static Order order(String customer, Trade... trades) {
        Order order = new Order(customer);
        Stream.of(trades).forEach(order::addTrades);
        return order;
    }

    public static Trade buy(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.BUY);
    }

    public static Trade sell(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.SELL);
    }

    private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type type) {
        Trade trade = new Trade(type);
        trade.setQuantity(quantity);
        trade.setStock(stock);
        trade.setPrice(price);
        return trade;
    }

    public static double at(double price){
        return price;
    }

    public static Stock stock(String symbol, String market) {
        return new Stock(symbol, market);
    }

    public static String in(String market) {
        return market;
    }
}
