package moomoo.study.java.module;

import java.util.Optional;

public class Army {

    private final String name;

    private Optional<General> commander = Optional.empty();

    public Army(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCommander(General general) {
        this.commander = Optional.ofNullable(general);
    }

    public Optional<General> getCommander() {
        return commander;
    }
}
