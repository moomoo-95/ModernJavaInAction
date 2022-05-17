package moomoo.study.java.module;

import java.util.Optional;

public class Army {

    private final String name;

    private Optional<General> commander;

    public Army(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCommander(String name) {
        this.commander = Optional.of(new General(name));
    }

    public Optional<General> getCommander() {
        return commander;
    }
}
