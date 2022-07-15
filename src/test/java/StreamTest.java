import moomoo.study.java.module.Dish;
import moomoo.study.java.module.Trader;
import moomoo.study.java.module.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class StreamTest {
    private static final Logger log = LoggerFactory.getLogger(StreamTest.class);

    private static final Trader RAOUL = new Trader("Raoul", "Cambridge");
    private static final Trader MARIO = new Trader("Mario", "Milan");
    private static final Trader ALAN = new Trader("Alan", "Cambridge");
    private static final Trader BRIAN = new Trader("Brian", "Cambridge");


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
        log.debug("Stream Test main End");
    }

    @Test
    public void testSub() {
        log.debug("Stream Test sub Start");
        log.debug("Stream Test sub End");
    }
}