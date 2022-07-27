import moomoo.study.java.module.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.*;

import static base.Config.*;
import static java.util.stream.Collectors.*;
import static moomoo.study.java.module.Dish.CaloricLevel;

public class StreamTest {
    private static final Logger log = LoggerFactory.getLogger(StreamTest.class);

    @BeforeClass
    public static void setUp() {
    }

    @Test
    public void testMain5() {
        log.debug("Stream chapter5 Test main Start");

        // 기존 필터링
        List<Dish> legacyLowCaloricDishes = new ArrayList<>();
        // 기준 이하 칼로리 필터링
        for(Dish dish : MENU) {
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
                MENU.stream()
                        .filter(dish -> dish.getCalories() < 400)           // 기준 이하 칼로리 필터링
                        .sorted(Comparator.comparing(Dish::getCalories))    // 칼로리 기준 오름차순 정렬
                        .map(Dish::getName)                                 // 칼로리 기준 정렬된 순서로 이름 저장
                        .collect(Collectors.toList());                      // 리스트에 저장

        log.debug("legacy : {}", Arrays.toString(legacyLowCaloricDishesName.toArray()));
        log.debug("stream : {}", Arrays.toString(streamLowCaloricDishesName.toArray()));

        // 스트림 평면화
        List<String[]> setMenu = MENU.stream()
                .flatMap(dish -> DESERT.stream().map(afterDish -> new String[]{dish.getName(), afterDish.getName()}))
                .collect(toList());
        setMenu.forEach(dishes ->log.debug("set menu : {}", Arrays.toString(dishes)));

        // anyMatch
        if(MENU.stream().anyMatch(Dish::isVegetarian)) {
            log.debug("The menu is vegetarian friendly!");
        }
        // allMatch
        if(MENU.stream().allMatch(dish -> dish.getCalories() < 1000)) {
            log.debug("The menu is healthy!");
        }
        // noneMatch
        if(MENU.stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
            log.debug("The menu is healthy!!");
        }
        // findAny, findFirst
        Optional<String> vegetarianDish1 = MENU.parallelStream().filter(Dish::isVegetarian).map(Dish::getName).findAny();
        Optional<String> vegetarianDish2 = MENU.parallelStream().filter(Dish::isVegetarian).map(Dish::getName).findFirst();
        log.debug("parallel {} {}", vegetarianDish1, vegetarianDish2);
        int sum = MENU.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        log.debug("getCalories sum {}", sum);

        int calories = MENU.stream().mapToInt(Dish::getCalories).sum();
        IntStream intStream = MENU.stream().mapToInt(Dish::getCalories);
        Stream<Integer> integerStream = intStream.boxed();
        OptionalInt maxCalories = MENU.stream().mapToInt(Dish::getCalories).max();
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

        log.debug("Stream chapter5 Test main End");
    }

    @Test
    public void testSub() {
        log.debug("Stream Test sub Start");
        // 1. 2011 년에 일어난 모든 트랜잭션 오름차순
        log.debug("1. answer");
        TRANSACTIONS.stream()
                .filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getYear))
                .forEach( t -> log.debug("{}", t));

        // 2. 거래자가 근무하는 모든 도시 중복없이 나열
        log.debug("2. answer");
        TRANSACTIONS.stream()
                .map(t -> t.getTrader().getCity()).distinct()
                .forEach( t -> log.debug("{}", t));
        // 3. 케임브리지에 근무하는 모든 거래자를 찾아 이름순 정렬
        log.debug("3. answer");
        TRANSACTIONS.stream()
                .map(Transaction::getTrader).distinct()
                .filter(t -> t.getCity().equals(CITY_CAMBRIDGE)).sorted(Comparator.comparing(Trader::getName))
                .forEach( t -> log.debug("{}", t));
        // 4. 모든 거래자 이름을 알파벳 순으로 정렬
        log.debug("4. answer");
        TRANSACTIONS.stream().map(Transaction::getTrader).distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach( t -> log.debug("{}", t));
        // 5. 밀라노에 거래자 있는지 여부
        log.debug("5. answer");
        log.debug("{}", TRANSACTIONS.stream().anyMatch(t -> t.getTrader().getCity().equals(CITY_MILAN)));
        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션 값
        log.debug("6. answer");
        TRANSACTIONS.stream().filter(t -> t.getTrader().getCity().equals(CITY_CAMBRIDGE)).map(Transaction::getValue)
                .forEach( t -> log.debug("{}", t));
        // 7. 전체 트랜잭션 중 최댓값
        log.debug("7. answer");
        log.debug("{}", TRANSACTIONS.stream().max(Comparator.comparing(Transaction::getValue)));
        // 8. 전체 트랜잭션 중 최솟값
        log.debug("8. answer");
        log.debug("{}", TRANSACTIONS.stream().min(Comparator.comparing(Transaction::getValue)));
        log.debug("Stream Test sub End");
    }

    @Test
    public void testMain6() {
        log.debug("Stream chapter6 Test main Start");
        Comparator<Dish> comparator = Comparator.comparing(Dish::getCalories);
        Optional<Dish> mostCalorieDish = MENU.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
        log.debug("most calorie dish : {}", mostCalorieDish.get());
        Optional<Dish> leastCalorieDish = MENU.stream()
                .collect(Collectors.minBy(comparator));
        log.debug("least calorie dish : {}", leastCalorieDish.get());

        int totalCalories = MENU.stream().collect(Collectors.summingInt(Dish::getCalories));
        log.debug("total calories dish : {}", totalCalories);
        double avgCalories = MENU.stream().collect(Collectors.averagingDouble(Dish::getCalories));
        log.debug("avg calories dish : {}", avgCalories);
        IntSummaryStatistics summaryStatistics = MENU.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        log.debug("summaryStatistics calories dish : {}", summaryStatistics);

        String shortMenu = MENU.stream().map(Dish::toString).collect(Collectors.joining(", "));
        log.debug("shortMenu : {}", shortMenu);

        totalCalories = MENU.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        log.debug("total calories dish : {}", totalCalories);
        mostCalorieDish = MENU.stream().collect(reducing((i, j) -> (i.getCalories() > j.getCalories() ? i : j)));
        log.debug("most calorie dish : {}", mostCalorieDish.get());
        leastCalorieDish = MENU.stream().collect(reducing((i, j) -> (i.getCalories() < j.getCalories() ? i : j)));
        log.debug("least calorie dish : {}", leastCalorieDish.get());

        Map<Dish.DishType, List<Dish>> dishesByType = MENU.stream().collect(Collectors.groupingBy(Dish::getType));
        log.debug("dishesByType : {}", dishesByType.toString());

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = MENU.stream()
                .collect(Collectors.groupingBy( dish -> {
                    if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() > 400 && dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        log.debug("dishesByCaloricLevel : {}", dishesByCaloricLevel.toString());

        Map<Dish.DishType, List<Dish>> caloricDishesByType = MENU.stream()
                .collect(Collectors.groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
        log.debug("caloricDishesByType : {}", caloricDishesByType.toString());

        Map<Dish.DishType, List<String>> dishNamesByType = MENU.stream().collect(Collectors.groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        log.debug("dishNamesByType : {}", dishNamesByType.toString());

        Map<Dish.DishType, Set<String>> dishTagsByType =
                MENU.stream().collect(
                        Collectors.groupingBy(
                                Dish::getType,
                                flatMapping(dish -> DISH_TAGS.get(dish.getName()).stream(), toSet())
                        )
                );
        log.debug("dishTagsByType : {}", dishTagsByType.toString());

        Map<Dish.DishType, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                MENU.stream().collect(
                        Collectors.groupingBy(Dish::getType,
                                Collectors.groupingBy(dish -> {
                                    if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() > 400 && dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                })
                        )
                );
        log.debug("dishesByTypeCaloricLevel : {}", dishesByTypeCaloricLevel.toString());

        Map<Dish.DishType, Dish> mostCaloricByType =
                MENU.stream().collect(Collectors.groupingBy(
                            Dish::getType,
                            Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        Comparator.comparing(Dish::getCalories)
                                ),
                                Optional::get
                            )
                        )
                );
        log.debug("mostCaloricByType : {}", mostCaloricByType.toString());
        // 단순 분할
        Map<Boolean, List<Dish>> partitionedMenu =
                MENU.stream().collect(
                        Collectors.partitioningBy(Dish::isVegetarian)
                );
        log.debug("partitionedMenu : {}", partitionedMenu.toString());
        // 다수준 분할
        Map<Boolean, Map<Dish.DishType, List<Dish>>> vegetarianDishesByType =
                MENU.stream().collect(
                        Collectors.partitioningBy(
                                Dish::isVegetarian,
                                Collectors.groupingBy(Dish::getType)
                        )
                );
        log.debug("vegetarianDishesByType : {}", vegetarianDishesByType.toString());
        // 다수준 분할 연산
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
                MENU.stream().collect(
                        Collectors.partitioningBy(
                                Dish::isVegetarian,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparing(Dish::getCalories)),
                                        Optional::get
                                )
                        )
                );
        log.debug("mostCaloricPartitionedByVegetarian : {}", mostCaloricPartitionedByVegetarian.toString());

        Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(50);
        log.debug("partitionPrimes1 : {}", partitionPrimes.toString());

        Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector = partitionPrimesWithCustomCollector(50);
        log.debug("partitionPrimes2 : {}", partitionPrimesWithCustomCollector.toString());

        List<Dish> dishesByFactory = MENU.stream().collect(toList());
        List<Dish> dishesByInstance = MENU.stream().collect(new ToListCollector<>());
        List<Dish> dishes = MENU.stream().collect(ArrayList::new, List::add, List::addAll);

        log.debug("forkJoin : {}", forkJoinSum(100));


        log.debug("Stream chapter6 Test main End");
    }

    @Test
    public void testMain7() {
        log.debug("Stream chapter7 Test main Start");

        log.debug("forkJoin : {}", forkJoinSum(100));

        log.debug("Found {} words", countWordsIteratively(SENTENCE));
        Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
        log.debug("Found {} words", counterWords(stream));

        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream1 = StreamSupport.stream(spliterator, true);
        log.debug("Found {} words", counterWords(stream1));


        log.debug("Stream chapter7 Test main End");
    }

    @Test
    public void collectorHarness() {
        long fastest = Long.MAX_VALUE;
        for(int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            partitionPrimes(1_000_000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if(duration < fastest) fastest = duration;
        }
        log.debug("1. Fastest execution done in {} ms", fastest);

        fastest = Long.MAX_VALUE;
        for(int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            partitionPrimesWithCustomCollector(1_000_000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if(duration < fastest) fastest = duration;
        }
        log.debug("2. Fastest execution done in {} ms", fastest);
    }

    public boolean isPrime(int number) {
        int numberSquareRoot = (int) Math.sqrt(number);
        return IntStream.rangeClosed(2, numberSquareRoot).noneMatch( i -> number % i == 0);
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int number) {
        return IntStream.rangeClosed(2, number).boxed().collect(partitioningBy(n -> isPrime(n)));
    }

    public Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int number) {
        return IntStream.rangeClosed(2, number).boxed().collect(new PrimeNumbersCollector());
    }

    public long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    /**
     * 문자열 단어수 계산하는 메서드
     */
    public int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            // 공백문자인 경우 true
            if(Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    public int counterWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }
}