package moomoo.study.java.module;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculator {
    public DoubleUnaryOperator taxFunction = d -> d;

    public TaxCalculator with(DoubleUnaryOperator f) {
        taxFunction = taxFunction.andThen(f);
        return this;
    }

    public double calculate(Order order) {
        return taxFunction.applyAsDouble(order.getValue());
    }

    public static double regional(double v) { return v * 1.1; }
    public static double general(double v) { return v * 1.3; }
    public static double surcharge(double v) { return v * 1.05; }

    public static double calculateByLegacy(Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge) {
        double value = order.getValue();
        if(useRegional) value = regional(value);
        if(useGeneral) value = general(value);
        if(useSurcharge) value = surcharge(value);
        return value;
    }
}
