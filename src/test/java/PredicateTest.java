import moomoo.study.java.util.CommonUtil;
import moomoo.study.java.util.Predicate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredicateTest {
    private static final Logger log = LoggerFactory.getLogger(PredicateTest.class);
    @Test
    public void testMain() {
        log.debug("Predicate Test Start");

        List<Apple> apples = Arrays.asList(
                new Apple(Color.red, 110),
                new Apple(Color.green, 113),
                new Apple(Color.red, 213),
                new Apple(Color.white, 164),
                new Apple(Color.green, 47),
                new Apple(Color.red, 315));

        List<Integer> numbers = Arrays.asList(
                2, 321, 432, 3245653, 123123, 2354, 12355);
        CommonUtil.filter(apples, apple -> Color.red.equals(apple.getColor())).forEach( apple -> log.debug("{} {}", apple.getColor(), apple.getWeight()));
        CommonUtil.filter(numbers, number -> number > 10000).forEach( number -> log.debug("{}", number));



        log.debug("Predicate Test End");
    }

    class Apple{
        private final Color color;
        private final int weight;

        public Apple(Color color, int weight) {
            this.color = color;
            this.weight = weight;
        }

        public Color getColor() {
            return color;
        }

        public int getWeight() {
            return weight;
        }
    }
}
