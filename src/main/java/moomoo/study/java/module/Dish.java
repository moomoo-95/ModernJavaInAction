package moomoo.study.java.module;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final DishType type;

    public Dish(String name, boolean vegetarian, int calories, DishType type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public DishType getType() {
        return type;
    }

    public CaloricLevel getCaloricLevel() {
        if(calories <= 400) return CaloricLevel.DIET;
        else if (calories <= 700) return CaloricLevel.NORMAL;
        else return CaloricLevel.FAT;
    }

    public enum CaloricLevel { DIET, NORMAL, FAT}
    public enum DishType { MEAT, FISH, OTHER }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vegetarian=" + vegetarian +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }
}


