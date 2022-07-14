package moomoo.study.java.module;

import java.awt.*;

public class Apple {
    private final Color color;
    private final int weight;

    public Apple(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Apple(int weight) {
        this.color = Color.red;
        this.weight = weight;
    }

    public Apple() {
        this.color = Color.red;
        this.weight = 100;
    }

    public Color getColor() {
        return color;
    }

    public int getColorOrder() {
        return color.getRGB();
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color=" + color +
                ", weight=" + weight +
                '}';
    }
}


