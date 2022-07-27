package base;

import moomoo.study.java.module.Apple;
import moomoo.study.java.module.Dish;
import moomoo.study.java.module.Trader;
import moomoo.study.java.module.Transaction;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    public static final String CITY_CAMBRIDGE = "Cambridge";
    public static final String CITY_MILAN = "Milan";

    public static final String FOOD_PORK = "pork";
    public static final String FOOD_BEEF = "beef";
    public static final String FOOD_CHICKEN = "chicken";
    public static final String FOOD_FRENCH_FRIES = "french fries";
    public static final String FOOD_RICE = "rice";
    public static final String FOOD_SEASON_FRUIT = "season fruit";
    public static final String FOOD_PIZZA = "pizza";
    public static final String FOOD_PRAWNS = "prawns";
    public static final String FOOD_SALMON = "salmon";

    public static final String TAG_GREASY = "greasy";
    public static final String TAG_SALTY = "salty";
    public static final String TAG_ROASTED = "roasted";
    public static final String TAG_FRIED = "fried";
    public static final String TAG_CRISP = "crisp";
    public static final String TAG_LIGHT = "light";
    public static final String TAG_NATURAL = "natural";
    public static final String TAG_FRESH = "fresh";
    public static final String TAG_TASTY = "TASTY";
    public static final String TAG_DELICIOUS = "delicious";

    public static final String SENTENCE = "In the culture of simulation, experiences on the Internet figure prominently. " +
            "In cyberspace, we can talk, exchange ideas, and assume personae of our own creation. " +
            "In an interactive computer game inspired by Star Trek, thousands of players spend up to eighty hours a week participating in intergalactic exploration and wars. " +
            "They create characters who have romantic encounters, hold jobs and collect paychecks, attend rituals and celebrations, fall in love and get married. " +
            "“This is more real than my real life,” says a character who turns out to be a man playing a woman. " +
            "In this game the self is constructed and the rules of social interaction are built, not received. " +
            "In another text-based game, each of nearly ten thousand players creates a character or several characters, specifying their genders and other physical and psychological attributes. " +
            "The characters need not be human and there are more than two genders. " +
            "Players are invited to help build the computer world itself. " +
            "Indeed, the Internet links millions of people in new spaces that are changing the way of our thinking, the nature of our sexuality, the form of our communities, and our very identities.";



    public static final Trader RAOUL = new Trader("Raoul", CITY_CAMBRIDGE);
    public static final Trader MARIO = new Trader("Mario", CITY_MILAN);
    public static final Trader ALAN = new Trader("Alan", CITY_CAMBRIDGE);
    public static final Trader BRIAN = new Trader("Brian", CITY_CAMBRIDGE);


    public static List<Apple> INVENTORY;
    public static List<Integer> NUMBERS;

    public static List<Dish> MENU;
    public static List<Dish> DESERT;

    public static List<Transaction> TRANSACTIONS;

    public static Map<String, List<String>> DISH_TAGS = new HashMap<>();

    static {
        INVENTORY = Arrays.asList(
                new Apple(Color.red, 110),
                new Apple(Color.green, 113),
                new Apple(Color.red, 213),
                new Apple(Color.white, 164),
                new Apple(Color.green, 47),
                new Apple(Color.red, 315)
        );
        NUMBERS = Arrays.asList(2, 321, 432, 3245653, 123123, 2354, 12355);

        MENU = Arrays.asList(
                new Dish(FOOD_PORK, false, 800, Dish.DishType.MEAT),
                new Dish(FOOD_BEEF, false, 700, Dish.DishType.MEAT),
                new Dish(FOOD_CHICKEN, false, 400, Dish.DishType.MEAT),
                new Dish(FOOD_FRENCH_FRIES, true, 530, Dish.DishType.OTHER),
                new Dish(FOOD_RICE, true, 350, Dish.DishType.OTHER),
                new Dish(FOOD_SEASON_FRUIT, true, 120, Dish.DishType.OTHER),
                new Dish(FOOD_PIZZA, true, 550, Dish.DishType.OTHER),
                new Dish(FOOD_PRAWNS, false, 300, Dish.DishType.FISH),
                new Dish(FOOD_SALMON, false, 450, Dish.DishType.FISH)
        );

        DESERT = Arrays.asList(
                new Dish("cookie", false, 300, Dish.DishType.OTHER),
                new Dish("ice cream", false, 350, Dish.DishType.OTHER),
                new Dish("chocolate", false, 400, Dish.DishType.OTHER)
        );

        TRANSACTIONS = Arrays.asList(
                new Transaction(BRIAN, 2011, 300),
                new Transaction(RAOUL, 2012, 1000),
                new Transaction(RAOUL, 2011, 400),
                new Transaction(MARIO, 2012, 710),
                new Transaction(MARIO, 2012, 700),
                new Transaction(ALAN, 2012, 950)
        );

        DISH_TAGS.put(FOOD_PORK, Arrays.asList(TAG_GREASY, TAG_SALTY));
        DISH_TAGS.put(FOOD_BEEF, Arrays.asList(TAG_SALTY, TAG_ROASTED));
        DISH_TAGS.put(FOOD_CHICKEN, Arrays.asList(TAG_FRIED, TAG_CRISP));
        DISH_TAGS.put(FOOD_FRENCH_FRIES, Arrays.asList(TAG_GREASY, TAG_FRIED));
        DISH_TAGS.put(FOOD_RICE, Arrays.asList(TAG_LIGHT, TAG_NATURAL));
        DISH_TAGS.put(FOOD_SEASON_FRUIT, Arrays.asList(TAG_FRESH, TAG_NATURAL));
        DISH_TAGS.put(FOOD_PIZZA, Arrays.asList(TAG_TASTY, TAG_SALTY));
        DISH_TAGS.put(FOOD_PRAWNS, Arrays.asList(TAG_TASTY, TAG_ROASTED));
        DISH_TAGS.put(FOOD_SALMON, Arrays.asList(TAG_DELICIOUS, TAG_FRESH));
    }
}
