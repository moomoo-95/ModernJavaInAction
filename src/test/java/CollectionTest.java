import moomoo.study.java.module.Apple;
import moomoo.study.java.module.Dish;
import moomoo.study.java.module.Point;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static base.Config.*;
import static moomoo.study.java.module.Dish.CaloricLevel;

public class CollectionTest {
    private static final Logger log = LoggerFactory.getLogger(CollectionTest.class);

    @BeforeClass
    public static void setUp() {
    }

    @Test
    public void testMain8() {
        log.debug("Collection chapter8 Test main Start");
        // list factory
        List<String> friendsList = List.of("Raphael", "Olivia", "Thibaut");
        log.debug("friends list : {}", friendsList);
        // UnsupportedOperationException 발생
        // friendsList.add("Chih-Chun");
        // friendsList.set(0, "Chih-Chun");
        ArrayList<String> friendsArrayList = new ArrayList<>(List.of("Raphael", "Olivia", "Thibaut"));
        friendsArrayList.add("Chih-Chun");
        log.debug("friends array list : {}", friendsArrayList);

        // set factory
        Set<String> friendsSet = Set.of("Raphael", "Olivia", "Thibaut");
        log.debug("friends set : {}", friendsSet);
        // 중복으로 인한 IllegalArgumentException 발생
        // Set<String> friendsSet2 = Set.of("Raphael", "Olivia", "Olivia");
        // log.debug("friends set : {}", friendsSet2);

        // map factory
        Map<String, Integer> ageOfFriendsMap = Map.of("Raphael", 30, "Olivia", 26, "Thibaut", 25);
        log.debug("friends map : {}", ageOfFriendsMap);
        Map<String, Integer> ageOfFriendsMapEntries = Map.ofEntries(
                Map.entry("Raphael", 30),
                Map.entry("Olivia", 26),
                Map.entry("Thibaut", 25)
        );
        log.debug("friends entry map : {}", ageOfFriendsMapEntries);
        // ConcurrentModificationException 발생
        // for(Transaction transaction : transactions) {
        //     if(Character.isDigit(transaction.getTrader().getCity().charAt(0))) {
        //         transactions.remove(transaction);
        //     }
        // }
        TRANSACTIONS.removeIf(transaction -> Character.isDigit(transaction.getTrader().getCity().charAt(0)));
        log.debug("transactions : {}", TRANSACTIONS);

        ArrayList<String> referenceCodes = new ArrayList<>(List.of("a12", "C14", "b13"));
        // 새 문자열이 생성됨
        // log.debug("referenceCodes : {}",
        //         referenceCodes.stream()
        //                 .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
        //                 .collect(Collectors.toList())
        // );
        referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
        log.debug("referenceCodes : {}", referenceCodes);

        ageOfFriendsMap.forEach( (friend, age) -> log.debug("{} is {} years old", friend, age));
        ageOfFriendsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(  (entry) ->  log.debug("(sorted) {} is {} years old", entry.getKey(), entry.getValue()));
        log.debug("via age : {} (-1 : not exist)", ageOfFriendsMap.getOrDefault("via", -1));

        List<Integer> lines = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        Map<Integer, Integer> dataToHash = new HashMap<>();
        // computeIfAbsent 성공 : key 값이 존재하지 않는 항목에 대해 연산
        lines.stream().forEach( n -> dataToHash.computeIfAbsent(n, value -> value*value));
        log.debug("(compute) dataToHash {}", dataToHash);
        // computeIfAbsent 실패 : key 값이 존재하면 무시
        dataToHash.computeIfAbsent(3, value -> value*2);
        log.debug("(compute) dataToHash {}", dataToHash);
        // computeIfPresent 성공 : key 값이 존재하는 항목에 대해 연산
        dataToHash.computeIfPresent(3, (key, value) -> value*2);
        log.debug("(compute) dataToHash {}", dataToHash);
        // computeIfPresent 실패 : key 값이 존재하지 않으면 무시
        dataToHash.computeIfPresent(11, (key, value) -> value*2);
        log.debug("(compute) dataToHash {}", dataToHash);
        // compute 성공 : key 값이 존재하는 항목에 대해 연산
        dataToHash.compute(3, (key, value) -> value*2);
        log.debug("(compute) dataToHash {}", dataToHash);
        // compute 실패 : key 값이 존재하지 않으면 NullPointerException 발생
        // dataToHash.compute(11, (key, value) -> value*2);
        // log.debug("dataToHash {}", dataToHash);

        log.debug("(remove) dataToHash ({}) {}", dataToHash.remove(7, 48),  dataToHash);
        log.debug("(remove) dataToHash ({}) {}", dataToHash.remove(11, 48),  dataToHash);
        log.debug("(remove) dataToHash ({}) {}", dataToHash.remove(7, 49),  dataToHash);
        log.debug("(remove) dataToHash ({}) {}", dataToHash.remove(12),  dataToHash);
        log.debug("(remove) dataToHash ({}) {}", dataToHash.remove(3),  dataToHash);

        log.debug("dataToHash : {}", dataToHash);
        dataToHash.replaceAll( (key, value) -> value*1000);
        log.debug("(replaceAll) dataToHash : {}", dataToHash);
        log.debug("(replace) dataToHash ({}) {}", dataToHash.replace(11, 1000),  dataToHash);
        log.debug("(replace) dataToHash ({}) {}", dataToHash.replace(11, 10, 100),  dataToHash);
        log.debug("(replace) dataToHash ({}) {}", dataToHash.replace(1, 10),  dataToHash);
        log.debug("(replace) dataToHash ({}) {}", dataToHash.replace(1, 100, 50),  dataToHash);
        log.debug("(replace) dataToHash ({}) {}", dataToHash.replace(1, 10, 50),  dataToHash);
        log.debug("(removeIf) dataToHash ({}) {}", dataToHash.entrySet().removeIf( entry -> entry.getValue() > 50000),  dataToHash);
        log.debug("(removeIf) dataToHash ({}) {}", dataToHash.entrySet().removeIf( entry -> entry.getValue() > 50000),  dataToHash);

        Map<String, String> aGroup = Map.ofEntries(
                Map.entry("Teo", "Star Wars"),
                Map.entry("Jack", "Star Wars"),
                Map.entry("Cristina", "James Bond")
        );

        Map<String, String> bGroup = new HashMap<>() {{
            put("Teo", "Star Wars");
            put("Jack", "Matrix");
            put("Raphael", "Star Wars");
            put("Cristina", null);
            put("Angel", null);
        }};

        log.debug("aGroup {}", aGroup);
        log.debug("bGroup {}", bGroup);
        Map<String, String> putAllGroup = new HashMap<>(aGroup);
        putAllGroup.putAll(bGroup);
        log.debug("putAllGroup {}", putAllGroup);

        Map<String, String> mergeGroup = new HashMap<>(bGroup);
        aGroup.forEach( (key, value) -> mergeGroup.merge(key, value, (movie1, movie2) -> {
            if(movie1.isEmpty() && movie2.isEmpty()) return "not exist";
            else if(movie1.isEmpty()) return movie2;
            else if(movie2.isEmpty()) return movie1;
            else if (movie1.equals(movie2)) return movie1;
            else return movie1 + " & " + movie2;
        }));
        log.debug("mergeGroup {}", mergeGroup);

        log.debug("Collection chapter8 Test main End");
    }

    @Test
    public void testMain9() {
        log.debug("Collection chapter9 Test main Start");
        // 익명 클래스를 람다 표현식으로
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                log.debug("runnable 1");
            }
        };

        Runnable r2 = () -> {
            int a = 3;
            log.debug("runnable 2");
        };
        // 람다 표현식을 메서드 참조로
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel1 = MENU.stream()
                .collect(Collectors.groupingBy( dish -> {
                    if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel2 = MENU.stream()
                .collect(Collectors.groupingBy(Dish::getCaloricLevel));

        log.debug("dishesByCaloricLevel1 {}", dishesByCaloricLevel1);
        log.debug("dishesByCaloricLevel2 {}", dishesByCaloricLevel2);
        // comparing, maxBy 같은 정적 헬퍼 메서드 활용
        INVENTORY.sort( (a1, a2) -> Integer.compare(a1.getWeight(), a2.getWeight()));
        INVENTORY.sort(Comparator.comparing(Apple::getWeight));

        // sum, maximum 등 리듀싱 연산은 내장 헬퍼 메서드 활용
        int totalCalories1 = MENU.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1 + c2);
        int totalCalories2 = MENU.stream().collect(Collectors.summingInt(Dish::getCalories));

        // 명령형 데이터 처리를 스트림으로
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : MENU) {
            if (dish.getCalories() > 300) {
                dishNames.add(dish.getName());
            }
        }

        MENU.parallelStream()
                .filter( dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());

        // 조건부 연기 실행 - 내장 자바 Logger 클래스
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CollectionTest.class.getName());
        // logger 상태가 isLoggable 로 인해 클라이언트 코드로 노출됨, 메시지 로깅 마다 상태를 확인 해야함
        if(logger.isLoggable(Level.FINER)) {
            logger.finer("log finer");
        }
        // 내부적으로 상태확인하는 log 메서드 활용
        // logger 가 FINER 이 아니더라도 항상 평가를 진행함, 메시지 생성 과정이 연기되지 못함
        logger.log(Level.FINER, "log finer");
        // 특정 조건에서만 메시지가 생성되도록 오버로드된 log 메서드
        logger.log(Level.FINER, () -> "log finer");

        // 전략 패턴
        Validator numericValidator = new Validator(new IsNumeric());
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());

        String a = "aaa";

        log.debug("{} isNumber : {} , is lowerCase : {}", a, numericValidator.validate(a), lowerCaseValidator.validate(a));
        a = "111";
        log.debug("{} isNumber : {} , is lowerCase : {}", a, numericValidator.validate(a), lowerCaseValidator.validate(a));
        a = "Acc";
        log.debug("{} isNumber : {} , is lowerCase : {}", a, numericValidator.validate(a), lowerCaseValidator.validate(a));
        // 전략 패턴 by 람다
        Validator numericValidatorByLambda = new Validator( s -> s.matches("\\d+"));
        Validator lowerCaseValidatorByLambda = new Validator( s -> s.matches("[a-z]+"));
        a = "jack";
        log.debug("{} isNumber : {} , is lowerCase : {}", a, numericValidatorByLambda.validate(a), lowerCaseValidatorByLambda.validate(a));

        // 템플릿 메서드 디자인 패턴
        WayOfGreeting wayOfGreetingHi = new WayOfGreeting() {
            @Override
            void uniqueGreet(String name) {
                log.debug("{} hi", name);
            }
        };
        WayOfGreeting wayOfGreetingHello = new WayOfGreeting() {
            @Override
            void uniqueGreet(String name) {
                log.debug("{} Hello", name);
            }
        };
        wayOfGreetingHi.greet(a);
        wayOfGreetingHello.greet(a);

        new WayOfGreetingByLambda().greet(a, s -> log.debug("{} hi~ h.i~", s));
        new WayOfGreetingByLambda().greet(a, s -> log.debug("{} Hello", s));

        // 옵저버 패턴
        Feed feed = new Feed();
        feed.registerObserver(new NYTimes());
        feed.registerObserver(new Guardian());
        feed.registerObserver(new LeMonde());

        feed.registerObserver(tweet -> {
            if (tweet != null && tweet.contains("money")) { log.debug("Breaking news in NY! {}", tweet); }
        });

        feed.registerObserver(tweet -> {
            if (tweet != null && tweet.contains("queen")) { log.debug("Yet more news from London... {}", tweet); }
        });

        feed.registerObserver(tweet -> {
            if (tweet != null && tweet.contains("wine")) { log.debug("Today cheese, wine and news! {}", tweet); }
        });
        feed.notifyObservers("The queen said her favourite book is Modern Java in Action!");

        // 의무 체인 패턴
        String str = "Aren't labdas really sexy?!";
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        log.debug("{}", p1.handle(str));

        UnaryOperator<String> headerTextProcessing = (String input) -> "From Raoul, Mario and Alan : " + input;
        UnaryOperator<String> spellCheckerProcessing = (String input) -> input.replaceAll("labda", "lambda");
        Function<String, String> pipeLine = headerTextProcessing.andThen(spellCheckerProcessing);
        log.debug("{}", pipeLine.apply(str));

        // 팩토리 디자인 패턴
        Product product1 = ProductFactory.createProduct("loan");
        Product product2 = ProductFactoryByLambda.createProduct("loan");

        try {
            testMoveRightBy();
            testComparingTwoPoints();
            testMoveAllPointsRightBy();
        } catch (Exception e) {
            log.error("CollectionTest.Lambda Testing ", e);
        }

        log.debug("Collection chapter9 Test main End");
    }

    @Test
    public void testMoveRightBy() throws Exception {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);
        Assert.assertEquals(15, p2.getX());
        Assert.assertEquals(5, p2.getY());
    }

    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(15, 20);
        int result = Point.compareByXAndThenY.compare(p1, p2);
        Assert.assertTrue(result < 0);
    }

    @Test
    public void testMoveAllPointsRightBy() throws Exception {
        List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));
        List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        Assert.assertEquals(expectedPoints, newPoints);
    }

    // 전략 패턴
    public interface ValidationStrategy {
        boolean execute(String s);
    }

    public class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    public class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    public class Validator {
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }
    // 템플릿 메서드 디자인 패턴
    abstract class WayOfGreeting {
        public void greet(String name) {
            uniqueGreet(name);
        }
        abstract void uniqueGreet(String name);
    }

    class WayOfGreetingByLambda {
        public void greet(String name, Consumer<String> uniqueGreet) {
            uniqueGreet.accept(name);
        }
    }
    // 옵저버 패턴
    interface Observer {
        void notify(String tweet);
    }

    class NYTimes implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                log.debug("Breaking news in NY! {}", tweet);
            }
        }
    }

    class Guardian implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                log.debug("Yet more news from London... {}", tweet);
            }
        }
    }

    class LeMonde implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                log.debug("Today cheese, wine and news! {}", tweet);
            }
        }
    }

    interface Subject {
        void registerObserver(Observer o);
        void notifyObservers(String tweet);
    }

    class Feed implements Subject{
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            this.observers.add(o);
        }

        @Override
        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }

    // 의무 체인 패턴
    public abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;
        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }
        public T handle(T input) {
            T r = handleWork(input);
            if(successor != null) {
                return successor.handle(r);
            }
            return r;
        }
        abstract protected T handleWork(T input);
    }

    public class HeaderTextProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String input) {
            return "From Raoul, Mario and Alan : " + input;
        }
    }

    public class SpellCheckerProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String input) {
            return input.replaceAll("labda", "lambda");
        }
    }

    // 팩토리 디자인 패턴
    public static class ProductFactory {
        public static Product createProduct(String name) {
            switch (name) {
                case "loan": return new Loan();
                case "stock": return new Stock();
                case "bond": return new Bond();
                default: throw new RuntimeException("No Such Product " + name);
            }
        }
    }

interface Product {}

static class Loan implements Product{};
static class Stock implements Product{};
static class Bond implements Product{};

public static class ProductFactoryByLambda {
    private static final Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    public static Product createProduct(String name) {
        Supplier<Product> p = map.get(name);
        if (p != null) return p.get();
        throw new RuntimeException("No Such Product " + name);
    }
}
}