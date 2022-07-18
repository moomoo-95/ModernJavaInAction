import moomoo.study.java.module.Dish;
import moomoo.study.java.module.Trader;
import moomoo.study.java.module.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamTest {
    private static final Logger log = LoggerFactory.getLogger(StreamTest.class);

    private static final String CITY_CAMBRIDGE = "Cambridge";
    private static final String CITY_MILAN = "Milan";

    private static final Trader RAOUL = new Trader("Raoul", CITY_CAMBRIDGE);
    private static final Trader MARIO = new Trader("Mario", CITY_MILAN);
    private static final Trader ALAN = new Trader("Alan", CITY_CAMBRIDGE);
    private static final Trader BRIAN = new Trader("Brian", CITY_CAMBRIDGE);


    private static List<Dish> menu;
    private static List<Dish> desert;

    private static List<Transaction> transactions;



    @BeforeClass
    public static void setUp() {
        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.DishType.MEAT),
                new Dish("beef", false, 700, Dish.DishType.MEAT),
                new Dish("chicken", false, 400, Dish.DishType.MEAT),
                new Dish("french fries", true, 530, Dish.DishType.OTHER),
                new Dish("rice", true, 350, Dish.DishType.OTHER),
                new Dish("season fruit", true, 120, Dish.DishType.OTHER),
                new Dish("pizza", true, 550, Dish.DishType.OTHER),
                new Dish("prawns", false, 300, Dish.DishType.FISH),
                new Dish("salmon", false, 450, Dish.DishType.FISH)
        );

        desert = Arrays.asList(
                new Dish("cookie", false, 300, Dish.DishType.OTHER),
                new Dish("ice cream", false, 350, Dish.DishType.OTHER),
                new Dish("chocolate", false, 400, Dish.DishType.OTHER)
        );

        transactions = Arrays.asList(
                new Transaction(BRIAN, 2011, 300),
                new Transaction(RAOUL, 2012, 1000),
                new Transaction(RAOUL, 2011, 400),
                new Transaction(MARIO, 2012, 710),
                new Transaction(MARIO, 2012, 700),
                new Transaction(ALAN, 2012, 950)
        );
    }

    @Test
    public void testMain() {
        log.debug("Stream Test main Start");

        // 기존 필터링
        List<Dish> legacyLowCaloricDishes = new ArrayList<>();
        // 기준 이하 칼로리 필터링
        for(Dish dish : menu) {
            if (dish.getCalories() < 400) {
                legacyLowCaloricDishes.add(dish);
            }
        }
        // 칼로리 기준 오름차순 정렬
        Collections.sort(legacyLowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish dish1, Dish dish2) {
                return Integer.compare(dish1.getCalories(), dish2.getCalories());
            }
        });
        // 칼로리 기준 정렬된 순서로 이름을 리스트에 저장
        List<String> legacyLowCaloricDishesName = new ArrayList<>();
        for(Dish dish : legacyLowCaloricDishes) {
            legacyLowCaloricDishesName.add(dish.getName());
        }
        // stream 필터링
        List<String> streamLowCaloricDishesName =
                menu.stream()
                        .filter(dish -> dish.getCalories() < 400)           // 기준 이하 칼로리 필터링
                        .sorted(Comparator.comparing(Dish::getCalories))    // 칼로리 기준 오름차순 정렬
                        .map(Dish::getName)                                 // 칼로리 기준 정렬된 순서로 이름 저장
                        .collect(Collectors.toList());                      // 리스트에 저장

        log.debug("legacy : {}", Arrays.toString(legacyLowCaloricDishesName.toArray()));
        log.debug("stream : {}", Arrays.toString(streamLowCaloricDishesName.toArray()));

        // 스트림 평면화
        List<String[]> setMenu = menu.stream()
                .flatMap(dish -> desert.stream().map(afterDish -> new String[]{dish.getName(), afterDish.getName()}))
                .collect(toList());
        setMenu.forEach(dishes ->log.debug("set menu : {}", Arrays.toString(dishes)));

        // anyMatch
        if(menu.stream().anyMatch(Dish::isVegetarian)) {
            log.debug("The menu is vegetarian friendly!");
        }
        // allMatch
        if(menu.stream().allMatch(dish -> dish.getCalories() < 1000)) {
            log.debug("The menu is healthy!");
        }
        // noneMatch
        if(menu.stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
            log.debug("The menu is healthy!!");
        }
        // findAny, findFirst
        Optional<String> vegetarianDish1 = menu.parallelStream().filter(Dish::isVegetarian).map(Dish::getName).findAny();
        Optional<String> vegetarianDish2 = menu.parallelStream().filter(Dish::isVegetarian).map(Dish::getName).findFirst();
        log.debug("parallel {} {}", vegetarianDish1, vegetarianDish2);
        int sum = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        log.debug("getCalories sum {}", sum);

        int calories = menu.stream().mapToInt(Dish::getCalories).sum();
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> integerStream = intStream.boxed();
        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        int max = maxCalories.orElse(1);

        IntStream evenNumbers1 = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
        IntStream evenNumbers2 = IntStream.range(1, 100).filter(n -> n % 2 == 0);

        Stream<double[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[] {a, b, Math.sqrt(a*a + b*b)})
                        .filter(c -> c[2] % 1 == 0));
        pythagoreanTriples.limit(20).forEach(intList -> log.debug("[{}, {}, {}]", intList[0], intList[1], intList[2]));

        // stream 만들기
        // 값
        Stream<String> stringStream = Stream.of("Modern", "Java", "In", "Action");
        Stream<String> emptyStream = Stream.empty();
        // null 이 될 수 있는 객체
        Stream<String> homeValueStream = Stream.ofNullable(System.getProperty("home"));
        Stream<String> values = Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
        // 배열
        int[] numbers = {2, 3, 5, 7, 11, 13};
        int arraySum = Arrays.stream(numbers).sum();
        // 파일
        long uniqueWords = 0;
        try(Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir") + "/src/test/resources/data.txt"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct()
                    .count();
            log.debug("uniqueWords {}", uniqueWords);
        } catch (Exception e){ /*ignore*/}

        Stream.iterate(0, n -> n + 2).limit(10)
                        .forEach(n -> log.debug(" {} ", n));
        //피보나치 수열
        Stream.iterate(new int[]{0, 1}, pair -> new int[] {pair[1], pair[0] + pair[1]}).limit(20)
                .map(t -> t[0])
                .forEach(t -> log.debug("{}", t));

        Stream.generate(Math::random)
                .limit(5)
                .forEach(t -> log.debug("{}", t));

        log.debug("Stream Test main End");
    }

    @Test
    public void testSub() {
        log.debug("Stream Test sub Start");
        // 1. 2011 년에 일어난 모든 트랜잭션 오름차순
        log.debug("1. answer");
        transactions.stream()
                .filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getYear))
                .forEach( t -> log.debug("{}", t));

        // 2. 거래자가 근무하는 모든 도시 중복없이 나열
        log.debug("2. answer");
        transactions.stream()
                .map(t -> t.getTrader().getCity()).distinct()
                .forEach( t -> log.debug("{}", t));
        // 3. 케임브리지에 근무하는 모든 거래자를 찾아 이름순 정렬
        log.debug("3. answer");
        transactions.stream()
                .map(Transaction::getTrader).distinct()
                .filter(t -> t.getCity().equals(CITY_CAMBRIDGE)).sorted(Comparator.comparing(Trader::getName))
                .forEach( t -> log.debug("{}", t));
        // 4. 모든 거래자 이름을 알파벳 순으로 정렬
        log.debug("4. answer");
        transactions.stream().map(Transaction::getTrader).distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach( t -> log.debug("{}", t));
        // 5. 밀라노에 거래자 있는지 여부
        log.debug("5. answer");
        log.debug("{}", transactions.stream().anyMatch(t -> t.getTrader().getCity().equals(CITY_MILAN)));
        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션 값
        log.debug("6. answer");
        transactions.stream().filter(t -> t.getTrader().getCity().equals(CITY_CAMBRIDGE)).map(Transaction::getValue)
                .forEach( t -> log.debug("{}", t));
        // 7. 전체 트랜잭션 중 최댓값
        log.debug("7. answer");
        log.debug("{}", transactions.stream().max(Comparator.comparing(Transaction::getValue)));
        // 8. 전체 트랜잭션 중 최솟값
        log.debug("8. answer");
        log.debug("{}", transactions.stream().min(Comparator.comparing(Transaction::getValue)));
        log.debug("Stream Test sub End");
    }
}