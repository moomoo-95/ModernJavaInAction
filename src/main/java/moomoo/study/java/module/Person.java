package moomoo.study.java.module;

import java.util.Optional;

public class Person {

    private final int age;
    private Car car;

    public Person(int age, Car car) {
        this(age);
        this.car = car;
    }

    public Person(int age) {
        this.age = age;
    }

    public Optional<Car> getCarAsOptional() {
        return Optional.ofNullable(car);
    }

    public int getAge() {
        return age;
    }
}
