import moomoo.study.java.module.Apple;
import moomoo.study.java.util.CommonUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

public class LambdaTest {
    private static final Logger log = LoggerFactory.getLogger(LambdaTest.class);

    private static List<Apple> inventory;
    private static List<Integer> numbers;
    @BeforeClass
    public static void setUp() {
        inventory = Arrays.asList(
                new Apple(Color.red, 110),
                new Apple(Color.green, 113),
                new Apple(Color.red, 213),
                new Apple(Color.white, 164),
                new Apple(Color.green, 47),
                new Apple(Color.red, 315)
        );
        numbers = Arrays.asList(2, 321, 432, 3245653, 123123, 2354, 12355);
    }

    @Test
    public void testMain() {
        log.debug("Lambda Test Start");

        // Predicate
        CommonUtil.filter(inventory, apple -> Color.red.equals(apple.getColor())).forEach(apple -> log.debug("{} {}", apple.getColor(), apple.getWeight()));
        CommonUtil.filter(numbers, number -> number > 10000).forEach( number -> log.debug("{}", number));

        // 익명 클래스
        Comparator<Apple> byWeightCreatedByAnonymousClass = new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight() < a2.getWeight() ? -1 : (a1.getWeight() == a2.getWeight() ? 0 : 1);
            }
        };
        // 람다 표현식
        Comparator<Apple> byWeightCreatedByLambda = (a1, a2) -> a1.getWeight() < a2.getWeight() ? -1 : (a1.getWeight() == a2.getWeight() ? 0 : 1);

        // 익명 클래스
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                log.debug("Print by anonymous class");
            }
        };
        // 람다 표현식
        Runnable r2 = () -> log.debug("Print by lambda");
        threadRun(r1);
        threadRun(r2);
        threadRun(() -> log.debug("Print by lambda2"));

        try {
            // 메서드 참조
            String oneLine = processFile(BufferedReader::readLine);
            log.debug("read : {}", oneLine);
        } catch (IOException e) {
            log.error("LambdaTest.testMain ", e);
        }

        // 생성자 메서드 참조
        Supplier<Apple> defaultAppleFactory = Apple::new;
        Apple defaultApple = defaultAppleFactory.get();

        IntFunction<Apple> weightAppleFactory = Apple::new;
        Apple weightApple = weightAppleFactory.apply(255);
        BiFunction<Color, Integer, Apple> customAppleFactory = Apple::new;
        Apple customApple = customAppleFactory.apply(Color.GREEN, 240);

        TriFunction<Integer, Integer, Integer, Color> colorFactory = Color::new;
        Color color = colorFactory.apply(1, 2, 3);

        log.debug("defaultApple : {}", defaultApple);
        log.debug("weightApple : {}", weightApple);
        log.debug("customApple : {}", customApple);
        log.debug("color : {}", color);

        // Comparator
        Comparator<Apple> appleComparator = Comparator.comparing(Apple::getWeight);
        // 오름차순
        inventory.sort(appleComparator.reversed());
        // 내림차순
        inventory.sort(appleComparator.reversed());
        // 두 번째 비교자 설정
        inventory.sort(appleComparator.reversed().thenComparing(Apple::getColorOrder));

        // Predicate
        // red apple
        Predicate<Apple> redApple = apple -> apple.getColor().equals(Color.red);
        // not red apple
        Predicate<Apple> notRedApple = redApple.negate();
        // red and heavy apple
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        // (red and heavy) or green apple
        Predicate<Apple> redAndHeavyAppleOrGreenApple = redApple.and(apple -> apple.getWeight() > 150).or(apple -> apple.getColor().equals(Color.GREEN));

        // Function
        // f(x) = x + 1, g(x) = x * 2, h(x) = g(f(x)), i(x) = f(g(x))
        Function<Integer, Integer> functionF = x -> x + 1;
        Function<Integer, Integer> functionG = x -> x * 2;
        Function<Integer, Integer> functionH = functionF.andThen(functionG);
        Function<Integer, Integer> functionI = functionF.compose(functionG);
        int inputX = 4;
        log.debug("x : {}, f(x) : {}, g(x) : {}, h(x) : {}, i(x) : {}", inputX, functionF.apply(inputX), functionG.apply(inputX), functionH.apply(inputX), functionI.apply(inputX));

        // 적분 f(x) = x + 10, f(x)의 (3, 7) 적분값
        DoubleFunction<Double> f = x -> x + 10; // 나중에

        log.debug("Lambda Test End");
    }
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    private static void threadRun(Runnable r) {
        r.run();
    }

    @FunctionalInterface
    private interface BufferedReaderProcessor{
        String process(BufferedReader b) throws IOException;
    }

    public String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/test/resources/data.txt"))){
            return p.process(br);
        }
    }
}
