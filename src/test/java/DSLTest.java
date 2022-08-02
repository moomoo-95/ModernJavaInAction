import moomoo.study.java.builder.LambdaOrderBuilder;
import moomoo.study.java.builder.chaining.MethodChainingOrderBuilder;
import moomoo.study.java.module.Order;
import moomoo.study.java.module.Stock;
import moomoo.study.java.module.TaxCalculator;
import moomoo.study.java.module.Trade;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static moomoo.study.java.builder.NestedFunctionOrderBuilder.*;

public class DSLTest {
    private static final Logger log = LoggerFactory.getLogger(DSLTest.class);

    private static final String CUSTOMER = "BigBank";
    private static final String IBM = "IBM";
    private static final String GGE = "GOOGLE";
    private static final String NYSE = "NYSE";
    private static final String NSDQ = "NASDAQ";

    @BeforeClass
    public static void setUp() {
    }

    @Test
    public void testMain10() {
        log.debug("DSL chapter10 Test main Start");
        Order order1 = new Order(CUSTOMER);
        Trade trade1 = new Trade(Trade.Type.BUY);
        Stock stock1 = new Stock(IBM, NYSE);

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order1.addTrades(trade1);

        Trade trade2 = new Trade(Trade.Type.SELL);
        Stock stock2 = new Stock(GGE, NSDQ);

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order1.addTrades(trade2);
        log.debug("direct : [{}]", order1.getValue());

        Order order2 = MethodChainingOrderBuilder.forCustomer(CUSTOMER)
                .buy(80).stock(IBM, NYSE).at(125.00)
                .sell(50).stock(GGE, NSDQ).at(375.00)
                .end();
        log.debug("method chain : [{}]", order2.getValue());

        Order order3 = order(CUSTOMER,
                buy(80, stock(IBM, NYSE), 125.00),
                sell(50, stock(GGE, NSDQ), 375.00));
        log.debug("nested function : [{}]", order3.getValue());

        Order order4 = LambdaOrderBuilder.order( o -> {
            o.forCustomer(CUSTOMER);
            o.buy( t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock( s -> s.stock(IBM, NYSE));
            });
            o.sell( t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock( s -> s.stock(GGE, NSDQ));
            });
        });
        log.debug("lambda function : [{}]", order4.getValue());

        double value1 = TaxCalculator.calculateByLegacy(order4, true, false, true);
        log.debug("legacy value : [{}]", value1);
        double value2 = new TaxCalculator()
                .with(TaxCalculator::regional)
                .with(TaxCalculator::surcharge)
                .calculate(order4);
        log.debug("value : [{}]", value2);

        log.debug("DSL chapter10 Test main End");
    }
}