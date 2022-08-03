import moomoo.study.java.module.Car;
import moomoo.study.java.module.Insurance;
import moomoo.study.java.module.Person;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class OptionalTest {
    private static final Logger log = LoggerFactory.getLogger(OptionalTest.class);
    @Test
    public void testMain11() {
        log.debug("Optional chapter11 Test main Start");

//        Person person = new Person();
//        person.getCar().getInsurance().getName();
        Optional<Car> emptyCar = Optional.empty();
        Car car1 = new Car();
        Car car2 = null;
        Optional<Car> optCar1 = Optional.of(car1);
        Optional<Car> optCar2 = Optional.ofNullable(car2);
        log.debug("{} {} {}", emptyCar.orElseGet(() -> new Car()), optCar1.orElseGet(() -> new Car()), optCar2.orElseGet(() -> new Car()));

        Insurance ins1 = new Insurance("AIA");
        Insurance ins2 = new Insurance("BBC");
        Insurance ins3 = new Insurance("CIA");
        List<Car> cars = Arrays.asList(new Car(ins1), new Car(ins2), new Car(ins3));
        List<Person> people = Arrays.asList(new Person(16, cars.get(0)), new Person(20, cars.get(1)), new Person(23, cars.get(2)));

        Optional<String> optName = cars.get(0).getInsuranceAsOptional().map(Insurance::getName);
        log.debug("insurance name : {}", optName);
        optName = people.get(1).getCarAsOptional().flatMap(Car::getInsuranceAsOptional).map(Insurance::getName);
        log.debug("insurance name : {}", optName);
        String name = getCarInsuranceName(people.get(2));
        log.debug("insurance name : {}", name);
        Set<String> names = getCarInsuranceNames(people);
        log.debug("insurance names : {}", names);

        people.stream().forEach(person -> log.debug("insurance names ( > 20) : {}", getCarInsuranceNameByAge(person, 20)));

        Map<String, Integer> testMap = new HashMap<>() {{
            put("one", 1); put("two", 2); put("three", 3);
        }};

        Optional<Integer> number = Optional.ofNullable(testMap.get("four"));
        log.debug("number four : {}", number.orElse(-1));

        Optional<Integer> n1 = stringToInt("11");
        Optional<Integer> n2 = stringToInt(null);
        Optional<Integer> n3 = stringToInt("AB");
        log.debug("string to number : {} {} {}", n1.orElse(-1), n2.orElse(-1), n3.orElse(-1));


        log.debug("Optional chapter11 Test main End");
    }

    public String getCarInsuranceName(Person person) {
        Optional<Person> optPerson = Optional.ofNullable(person);
        return optPerson.flatMap(Person::getCarAsOptional)
                .flatMap(Car::getInsuranceAsOptional)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCarAsOptional)
                .map((Optional<Car> car) -> car.flatMap(Car::getInsuranceAsOptional))
                .map((Optional<Insurance> ins) -> ins.map(Insurance::getName))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    public String getCarInsuranceNameByAge(Person psn, int minAge) {
        Optional<Person> person = Optional.ofNullable(psn);
        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCarAsOptional)
                .flatMap(Car::getInsuranceAsOptional)
                .map(Insurance::getName).orElse("Unknown");
    }

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("OptionalTest.stringToInt ({}) ", s, e);
            return Optional.empty();
        }
    }
}
