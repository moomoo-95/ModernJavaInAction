package moomoo.study.java.module;

import java.util.Optional;

public class General {

    private final String name;
    private final String branch;
    private Optional<String> armyName = Optional.empty();

    public General(String name, String branch) {
        this.name = name;
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getArmyName() {
        return armyName;
    }

    public void setArmyName(String armyName) {
        this.armyName = Optional.ofNullable(armyName);
    }
}
