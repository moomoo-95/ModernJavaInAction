package moomoo.study.java.module;

import java.util.Optional;

public class Car {
    private Insurance insurance;

    public Car(Insurance insurance) {
        this.insurance = insurance;
    }

    public Car() {
    }

    public Optional<Insurance> getInsuranceAsOptional() {
        return Optional.ofNullable(insurance);
    }
}
